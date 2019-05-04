package orm.sql.interpreter;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import orm.Criteria;
import orm.Criteria.CriteriaOperator;
import orm.CriteriasBuilder.Criterias;

@ApplicationScoped
public class SqlCriteriaInterpreter {

	private static final String SPACE = " ";
	private static final String AND_OPERATION = "AND";
	private static final String OR_OPERATION = "OR";

	@Inject
	private SqlCriteriaOperandInterpreterRegistry sqlCriteriaOperandInterpreterRegistry;

	/**
	 * Interprets the {@link CriteriaOperator} and returns the matching sql
	 * operator.
	 * 
	 * @param criteriaOperator
	 * @return
	 */
	private String interpretCriteriaOperator(CriteriaOperator criteriaOperator) {
		String sqlOperator = null;

		switch (criteriaOperator) {
		case EQUALS:
			sqlOperator = "=";
			break;
		case GREATER_THAN:
			sqlOperator = ">";
			break;
		case LESSER_THAN:
			sqlOperator = "<";
			break;
		}

		return sqlOperator;
	}

	/**
	 * Interprets the given {@link Criterias} as sql and returns the result as
	 * {@link String}.
	 * 
	 * @param criterias
	 * @return
	 */
	public String interpret(Criterias criterias) {
		return criterias.getCriterias().stream() //
				.map(criteria -> this.interpret(criteria)) //
				.reduce((left, right) -> left.concat(right)) //
				.get();
	}

	private String interpret(Criteria<?> criteria) {
		SqlCriteriaOperandInterpreter sqlCriteriaOperandInterpreter = sqlCriteriaOperandInterpreterRegistry
				.getSqlCriteriaOperandInterpreterForOperandType(criteria.getValueOperandType());

		String interpretedCriteria = criteria.getFieldOperand() //
				.concat(SPACE) //
				.concat(this.interpretCriteriaOperator(criteria.getCriteriaOperator())) //
				.concat(SPACE) //
				.concat(sqlCriteriaOperandInterpreter.interpret(criteria.getValueOperand()));

		switch (criteria.getCriteriaConjunction()) {
		case AND:
			interpretedCriteria = interpretedCriteria //
					.concat(SPACE) //
					.concat(AND_OPERATION) //
					.concat(SPACE);
			break;
		case NONE:
			break;
		case OR:
			interpretedCriteria = interpretedCriteria //
					.concat(SPACE) //
					.concat(OR_OPERATION) //
					.concat(SPACE);
			break;
		}

		return interpretedCriteria;
	}

}
