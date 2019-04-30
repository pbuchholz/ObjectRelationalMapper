package de.bu.governance.healthmetrics.storage.sql.interpreter;

/**
 * Implementation of {@link SqlCriteriaOperandInterpreter} which interprets
 * operands of type {@link String} and makes them useable in sql statements.
 * 
 * @author Philipp Buchholz
 */
public class StringCriteriaOperandSqlInterpreter implements SqlCriteriaOperandInterpreter {

	private static final String VALUE_SEPERATOR = "'";

	@Override
	public <T> String interpret(T operand) {
		return VALUE_SEPERATOR //
				.concat(operand.toString()) //
				.concat(VALUE_SEPERATOR);
	}

}
