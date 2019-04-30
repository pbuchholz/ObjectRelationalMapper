package de.bu.governance.healthmetrics.storage;

/**
 * Represents a Criteria used to describe a set of domain objects.
 * 
 * @author Philipp Buchholz
 */
public final class Criteria<T> {

	private String fieldOperand;
	private T valueOperand;
	private Class<T> valueOperandType;
	private CriteriaOperator criteriaOperator;
	private CriteriaConjunction criteriaConjunction;

	public Criteria(String fieldOperand, T valueOperand, Class<T> valueOperandType, CriteriaOperator criteriaOperator,
			CriteriaConjunction criteriaConjunction) {
		this.fieldOperand = fieldOperand;
		this.valueOperand = valueOperand;
		this.valueOperandType = valueOperandType;
		this.criteriaOperator = criteriaOperator;
		this.criteriaConjunction = criteriaConjunction;
	}

	public String getFieldOperand() {
		return fieldOperand;
	}

	public T getValueOperand() {
		return valueOperand;
	}

	public Class<T> getValueOperandType() {
		return valueOperandType;
	}

	public CriteriaOperator getCriteriaOperator() {
		return criteriaOperator;
	}

	public CriteriaConjunction getCriteriaConjunction() {
		return criteriaConjunction;
	}

	/**
	 * Defines the conjunction of two {@link Criteria}s.
	 * 
	 * @author Philipp Buchholz
	 */
	public enum CriteriaConjunction {
		AND, //
		OR, //
		NONE
	}

	/**
	 * Defines the operator of a {@link Criteria}.
	 * 
	 * @author Philipp Buchholz
	 */
	public enum CriteriaOperator {
		EQUALS, //
		GREATER_THAN, //
		LESSER_THAN
	}

}
