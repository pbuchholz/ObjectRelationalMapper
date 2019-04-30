package de.bu.governance.healthmetrics.storage.sql.healthmetrics;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import de.bu.governance.healthmetrics.model.HealthMetrics;
import de.bu.governance.healthmetrics.model.HealthMetrics.HealthMetricsType;
import de.bu.governance.healthmetrics.model.HealthMetrics.HealthMetricsUnit;
import de.bu.governance.healthmetrics.model.HealthMetricsIdentifier;
import de.bu.governance.healthmetrics.storage.sql.AbstractFieldMapper;
import de.bu.governance.healthmetrics.storage.sql.FieldMapping;
import de.bu.governance.healthmetrics.storage.sql.UUIDConverter;

/**
 * Maps fields for {@link HealthMetrics}s.
 * 
 * @author Philipp Buchholz
 */
public class HealthMetricsFieldMapper extends AbstractFieldMapper<HealthMetrics, HealthMetricsIdentifier> {

	@Override
	public Class<HealthMetrics> mappedType() {
		return HealthMetrics.class;
	}

	@Override
	public Class<HealthMetricsIdentifier> identifierType() {
		return HealthMetricsIdentifier.class;
	}

	// TODO Write caching interceptor!
	// TODO @Cache!
	@Override
	public List<FieldMapping<?>> idFieldMappings() {
		List<FieldMapping<?>> idFieldMappings = new ArrayList<>();
		idFieldMappings.add(FieldMapping.<UUID>builder() //
				.columnName("uuid") //
				.getter(findIdentifierGetter("UUID")) //
				.setter(findIdentifierSetter("UUID")) //
				.fieldType(UUID.class) //
				.typeConverter(new UUIDConverter()) //
				.build());
		return idFieldMappings;
	}

	// TODO Write caching interceptor!
	// TODO @Cache!
	@Override
	public List<FieldMapping<?>> fieldMappings() {
		List<FieldMapping<?>> fieldMappings = new ArrayList<>();
		fieldMappings.add(FieldMapping.<UUID>builder() //
				.columnName("serviceendpointinstanceuuid") //
				.getter(findGetter("ServiceEndpointInstanceIdentifier")) //
				.setter(findSetter("ServiceEndpointInstanceIdentifier")) //
				.fieldType(UUID.class) //
				.typeConverter(new UUIDConverter()) //
				.build());
		fieldMappings.add(FieldMapping.<String>builder() //
				.columnName("healthmetricscategory") //
				.getter(findGetter("HealthMetricsCategory")) //
				.setter(findSetter("HealthMetricsCategory")) //
				.fieldType(String.class) //
				.build());
		fieldMappings.add(FieldMapping.<HealthMetricsType>builder() //
				.columnName("healthmetricstype") //
				.getter(findGetter("HealthMetricsType")) //
				.setter(findSetter("HealthMetricsType")) //
				.fieldType(HealthMetricsType.class) //
				.typeConverter(new HealthMetricsTypeConverter()) //
				.build());
		fieldMappings.add(FieldMapping.<HealthMetricsUnit>builder() //
				.columnName("healthmetricsunit") //
				.getter(findGetter("HealthMetricsUnit")) //
				.setter(findSetter("HealthMetricsUnit")) //
				.fieldType(HealthMetricsUnit.class) //
				.typeConverter(new HealthMetricsUnitConverter()) //
				.build());
		fieldMappings.add(FieldMapping.<HealthMetricsUnit>builder() //
				.columnName("healthmetricsvalue") //
				.getter(findGetter("HealthMetricsValue")) //
				.setter(findSetter("HealthMetricsValue")) //
				.fieldType(HealthMetricsUnit.class) //
				.build());
		return fieldMappings;
	}

}
