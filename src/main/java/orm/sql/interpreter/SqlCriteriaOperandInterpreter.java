package de.bu.governance.healthmetrics.storage.sql.interpreter;

/**
 * Interprets operands to use them in sql statements.
 * 
 * The type T represents the type of operand to interpret.
 * 
 * @author Philipp Buchholz
 *
 * @param <T>
 */
public interface SqlCriteriaOperandInterpreter {

	/**
	 * Interprets the given operand of type T and returns the result as
	 * {@link String} useable in sql statements.
	 * 
	 * @param operand
	 * @return
	 */
	<T> String interpret(T operand);

}
