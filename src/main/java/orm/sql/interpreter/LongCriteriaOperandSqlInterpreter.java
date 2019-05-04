package orm.sql.interpreter;

/**
 * Implementation of {@link SqlCriteriaOperandInterpreter} which interprets
 * operands of type {@link Long} and makes them useable in sql statements.
 * 
 * @author Philipp Buchholz
 */
public class LongCriteriaOperandSqlInterpreter implements SqlCriteriaOperandInterpreter {

	@Override
	public <T> String interpret(T operand) {
		return String.valueOf(operand);
	}

}
