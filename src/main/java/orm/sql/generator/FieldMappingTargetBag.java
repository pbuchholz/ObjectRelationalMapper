package de.bu.governance.healthmetrics.storage.sql.generator;

import java.util.List;

import de.bu.governance.healthmetrics.storage.sql.FieldMapping;

public class FieldMappingTargetBag {
	private Object target;
	private List<FieldMapping<?>> fieldMappings;

	public Object getTarget() {
		return this.target;
	}

	public List<FieldMapping<?>> getFieldMappings() {
		return this.fieldMappings;
	}

	public FieldMappingTargetBag(Object target, List<FieldMapping<?>> fieldMappings) {
		this.target = target;
		this.fieldMappings = fieldMappings;
	}
}
