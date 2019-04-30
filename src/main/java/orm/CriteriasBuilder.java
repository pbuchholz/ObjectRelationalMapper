package de.bu.governance.healthmetrics.storage;

import java.util.LinkedHashSet;
import java.util.Set;

import de.bu.governance.healthmetrics.storage.Criteria.CriteriaConjunction;
import de.bu.governance.healthmetrics.storage.Criteria.CriteriaOperator;

/**
 * Represent a builder for {@link Criteria} objects.
 * 
 * @author Philipp Buchhholz
 */
public final class CriteriasBuilder {

	/**
	 * Starts the building of {@link Criterias}.
	 * 
	 * @return
	 */
	public static <T> CriteriaFieldOperandStep<T> newCriteriasBuilder() {
		return new CriteriaSteps<T>();
	}

	/**
	 * Encapsulates N {@link Criteria}s.
	 * 
	 * @author Philipp Buchholz
	 */
	public class Criterias {

		private Set<Criteria<?>> criterias;

		public Set<Criteria<?>> getCriterias() {
			return this.criterias;
		}

		public void addCriteria(Criteria<?> criteria) {
			if (null == criterias) {
				synchronized (this) {
					if (null == criterias) {
						/* LinkedHashSet retains ordering. */
						this.criterias = new LinkedHashSet<Criteria<?>>();
					}
				}
			}

			this.criterias.add(criteria);
		}

	}

	/**
	 * Step used to add the left sided operand.
	 * 
	 * @author Philipp Buchholz
	 *
	 * @param <T>
	 */
	public static interface CriteriaFieldOperandStep<T> {

		/**
		 * Adds the field operand for the current {@link Criteria}.
		 * 
		 * @param fieldOperand
		 *            the field name which is operated on.
		 * @return The next step necessary.
		 */
		CriteriaOperatorStep<T> fieldOperand(String fieldOperand);

	}

	/**
	 * Step used to add the value operand.
	 * 
	 * @author Philipp Buchholz
	 *
	 * @param <T>
	 */
	public static interface CriteriaValueOperandStep<T> {

		/**
		 * Adds the value operand for the current {@link Criteria}.
		 * 
		 * @param valueOperandType
		 * @param valueOperand
		 * @return
		 */
		CriteriaConjunctionStep<T> valueOperand(Class<T> valueOperandType, T valueOperand);
	}

	/**
	 * Step used to add the {@link CriteriaOperator}.
	 * 
	 * @author Philipp Buchholz
	 */
	public static interface CriteriaOperatorStep<T> {

		/**
		 * Adds the {@link CriteriaOperator} for the current {@link Criteria}.
		 * 
		 * @param criteriaOperator
		 * @return
		 */
		CriteriaValueOperandStep<T> operator(CriteriaOperator criteriaOperator);

	}

	/**
	 * Step used to add the {@link CriteriaConjunction} for the current
	 * {@link Criteria}.
	 * 
	 * @author Philipp Buchholz
	 *
	 * @param <T>
	 */
	public static interface CriteriaConjunctionStep<T> {

		/**
		 * Adds the given {@link CriteriaConjunction}. This method returns
		 * {@link CriteriaOperandStep} to be able to build the next {@link Criteria} by
		 * beginning at step one.
		 * 
		 * @param criteriaConjunction
		 * @return
		 */
		CriteriaFieldOperandStep<T> conjunction(CriteriaConjunction criteriaConjunction);

		/**
		 * Adds a {@link CriteriaConjunction#NONE} and builds the {@link Criterias}.
		 * 
		 * @param criteriaConjunction
		 * @return
		 */
		BuildCriteriaStep<T> none();

	}

	/**
	 * Step which builds the {@link Criteria}.
	 * 
	 * @author Philipp Buchholz
	 */
	public static interface BuildCriteriaStep<T> {

		/**
		 * Builds and returns the {@link Criterias}.
		 * 
		 * @return
		 */
		Criterias build();

	}

	/**
	 * Implements and thus unifies all the step interfaces defined here.
	 * 
	 * @author Philipp Buchholz
	 *
	 */
	public static class CriteriaSteps<T> implements CriteriaFieldOperandStep<T>, CriteriaValueOperandStep<T>,
			CriteriaOperatorStep<T>, CriteriaConjunctionStep<T>, BuildCriteriaStep<T> {

		private Criterias criterias = new CriteriasBuilder().new Criterias();

		private String fieldOperand;
		private T valueOperand;
		private Class<T> valueOperandType;
		private CriteriaOperator criteriaOperator;

		@Override
		public CriteriaOperatorStep<T> fieldOperand(String fieldOperand) {
			this.fieldOperand = fieldOperand;
			return this;
		}

		@Override
		public CriteriaValueOperandStep<T> operator(CriteriaOperator criteriaOperator) {
			this.criteriaOperator = criteriaOperator;
			return this;
		}

		@Override
		public CriteriaConjunctionStep<T> valueOperand(Class<T> valueOperandType, T valueOperand) {
			this.valueOperand = valueOperand;
			this.valueOperandType = valueOperandType;
			return this;
		}

		@Override
		public CriteriaFieldOperandStep<T> conjunction(CriteriaConjunction criteriaConjunction) {
			Criteria<T> criteria = new Criteria<T>(fieldOperand, valueOperand, valueOperandType, criteriaOperator,
					criteriaConjunction);
			criterias.addCriteria(criteria);
			this.reset();
			return this;
		}

		/**
		 * Private helper method to reset state variables.
		 */
		private void reset() {
			this.fieldOperand = null;
			this.valueOperand = null;
			this.valueOperandType = null;
			this.criteriaOperator = null;
		}

		/**
		 * Final step to build and return the {@link Criterias}.
		 */
		@Override
		public Criterias build() {
			return criterias;
		}

		/**
		 * Last step if nothing will follow.
		 */
		@Override
		public BuildCriteriaStep<T> none() {
			Criteria<T> criteria = new Criteria<T>(fieldOperand, valueOperand, valueOperandType, criteriaOperator,
					CriteriaConjunction.NONE);
			criterias.addCriteria(criteria);
			this.reset();
			return this;
		}

	}

}
