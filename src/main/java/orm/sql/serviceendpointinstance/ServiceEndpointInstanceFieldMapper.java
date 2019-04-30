package de.bu.governance.healthmetrics.storage.sql.serviceendpointinstance;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import de.bu.governance.healthmetrics.model.ServiceEndpointInstance;
import de.bu.governance.healthmetrics.model.ServiceEndpointInstanceIdentifier;
import de.bu.governance.healthmetrics.storage.sql.AbstractFieldMapper;
import de.bu.governance.healthmetrics.storage.sql.FieldMapping;
import de.bu.governance.healthmetrics.storage.sql.UUIDConverter;

public class ServiceEndpointInstanceFieldMapper
		extends AbstractFieldMapper<ServiceEndpointInstance, ServiceEndpointInstanceIdentifier> {

	// TODO @Cache
	// TODO The returned List of FieldMappings here should be cached using an
	// Interceptor.
	@Override
	public List<FieldMapping<?>> fieldMappings() {
		List<FieldMapping<?>> fieldMappings = new ArrayList<>();
		fieldMappings.add(FieldMapping.<String>builder() //
				.columnName("serviceendpointname") //
				.getter(findGetter("serviceEndpointName")) //
				.setter(findSetter("serviceEndpointName")) //
				.fieldType(String.class) //
				.build());
		return fieldMappings;
	}

	// TODO @Cache
	// TODO The returned List of FieldMappings here should be cached using an
	// Interceptor.
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

	@Override
	public Class<ServiceEndpointInstance> mappedType() {
		return ServiceEndpointInstance.class;
	}

	@Override
	public Class<ServiceEndpointInstanceIdentifier> identifierType() {
		return ServiceEndpointInstanceIdentifier.class;
	}

}
