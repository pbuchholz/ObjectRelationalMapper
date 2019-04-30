package de.bu.governance.healthmetrics.storage.sql.interpreter;

import java.util.LinkedHashMap;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;

@Singleton
public class SqlCriteriaOperandInterpreterRegistry {

	private Map<Class<?>, SqlCriteriaOperandInterpreter> interpreterMappings;

	@PostConstruct
	private void populateMapping() {
		interpreterMappings = new LinkedHashMap<>();
		interpreterMappings.put(Long.class, new LongCriteriaOperandSqlInterpreter());
		interpreterMappings.put(String.class, new StringCriteriaOperandSqlInterpreter());
	}

	public SqlCriteriaOperandInterpreter getSqlCriteriaOperandInterpreterForOperandType(Class<?> operandType) {
		return interpreterMappings.get(operandType);
	}

}
