package orm.sql.generator;

import java.util.List;

import orm.CriteriasBuilder.Criterias;
import orm.sql.FieldMapping;

/**
 * Generates SQL statements.
 * 
 * @author Philipp Buchholz
 */
public interface SqlGenerator {

	public enum SqlClause {
		FROM(" FROM "), //
		WHERE(" WHERE "), //
		VALUES(" VALUES ");

		private String clause;

		public String clause() {
			return this.clause;
		}

		private SqlClause(String clause) {
			this.clause = clause;
		}
	}

	public enum DmlStatement {
		SELECT("SELECT "), //
		INSERT("INSERT INTO "), //
		UPDATE("UPDATE "), //
		DELETE("DELETE FROM ");

		private String statement;

		public String statement() {
			return this.statement;
		}

		DmlStatement(String statement) {
			this.statement = statement;
		}
	}

	String generateInsertStatement(String tableName, FieldMappingTargetBag... fieldMappingTargetBags);

	String generateSelectStatement(String tableName, List<FieldMapping<?>> columns, FieldMappingTargetBag idBag);

	String generateSelectStatement(String tableName, List<FieldMapping<?>> columns);

	String generateSelectStatement(String tableName, List<FieldMapping<?>> columns, Criterias criterias);

}
