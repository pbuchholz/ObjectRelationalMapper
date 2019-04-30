package de.bu.governance.healthmetrics.storage.sql;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import de.bu.governance.healthmetrics.model.HealthMetrics;
import de.bu.governance.healthmetrics.model.HealthMetrics.HealthMetricsType;
import de.bu.governance.healthmetrics.model.HealthMetrics.HealthMetricsUnit;
import de.bu.governance.healthmetrics.model.HealthMetricsIdentifier;
import de.bu.governance.healthmetrics.storage.Reflections;
import de.bu.governance.healthmetrics.storage.sql.generator.FieldMappingTargetBag;
import de.bu.governance.healthmetrics.storage.sql.healthmetrics.HealthMetricsTypeConverter;
import de.bu.governance.healthmetrics.storage.sql.healthmetrics.HealthMetricsUnitConverter;

/**
 * Builds {@link FieldMapping}s for testing purposes.
 * 
 * @author Philipp Buchholz
 */
public final class FieldMappingTestBuilder {

	private FieldMappingTestBuilder() {

	}

	public static List<FieldMapping<?>> buildFieldMappingsForHealthMetrics() {
		List<FieldMapping<?>> fieldMappings = new ArrayList<>();
		fieldMappings.add(FieldMapping.<String>builder() //
				.columnName("healthmetricscategory") //
				.getter(Reflections.findGetterOn(HealthMetrics.class, "HealthMetricsCategory")) //
				.setter(Reflections.findSetterOn(HealthMetrics.class, "HealthMetricsCategory")) //
				.fieldType(String.class) //
				.build());
		fieldMappings.add(FieldMapping.<HealthMetricsType>builder() //
				.columnName("healthmetricstype") //
				.getter(Reflections.findGetterOn(HealthMetrics.class, "HealthMetricsType")) //
				.setter(Reflections.findSetterOn(HealthMetrics.class, "HealthMetricsType")) //
				.fieldType(HealthMetricsType.class) //
				.typeConverter(new HealthMetricsTypeConverter()) //
				.build());
		fieldMappings.add(FieldMapping.<HealthMetricsUnit>builder() //
				.columnName("healthmetricsunit") //
				.getter(Reflections.findGetterOn(HealthMetrics.class, "HealthMetricsUnit")) //
				.setter(Reflections.findSetterOn(HealthMetrics.class, "HealthMetricsUnit")) //
				.fieldType(HealthMetricsUnit.class) //
				.typeConverter(new HealthMetricsUnitConverter()) //
				.build());
		fieldMappings.add(FieldMapping.<HealthMetricsUnit>builder() //
				.columnName("healthmetricsvalue") //
				.getter(Reflections.findGetterOn(HealthMetrics.class, "HealthMetricsValue")) //
				.setter(Reflections.findGetterOn(HealthMetrics.class, "HealthMetricsValue")) //
				.fieldType(HealthMetricsUnit.class) //
				.build());
		return fieldMappings;
	}

	public static List<FieldMapping<?>> buildIdFieldMappingsForHealthMetricsIdentifier() {
		List<FieldMapping<?>> idFieldMappings = new ArrayList<>();
		idFieldMappings.add(FieldMapping.<UUID>builder() //
				.columnName("uuid") //
				.getter(Reflections.findGetterOn(HealthMetricsIdentifier.class, "UUID")) //
				.setter(Reflections.findSetterOn(HealthMetricsIdentifier.class, "UUID")) //
				.fieldType(UUID.class) //
				.typeConverter(new UUIDConverter()) //
				.build());
		return idFieldMappings;
	}

	public static FieldMappingTargetBag buildHealthMetricsFieldMappingTargetBag(HealthMetrics target) {
		return new FieldMappingTargetBag(target, FieldMappingTestBuilder.buildFieldMappingsForHealthMetrics());
	}

	public static FieldMappingTargetBag buildHealthMetricsIdFieldMappingTargetBag(HealthMetricsIdentifier target) {
		return new FieldMappingTargetBag(target,
				FieldMappingTestBuilder.buildIdFieldMappingsForHealthMetricsIdentifier());
	}

}
