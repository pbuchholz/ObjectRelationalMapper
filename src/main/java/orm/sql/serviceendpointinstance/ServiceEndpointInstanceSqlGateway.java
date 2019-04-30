package de.bu.governance.healthmetrics.storage.sql.serviceendpointinstance;

import javax.annotation.Resource;
import javax.enterprise.context.ApplicationScoped;
import javax.sql.DataSource;

import de.bu.governance.healthmetrics.model.ServiceEndpointInstance;
import de.bu.governance.healthmetrics.model.ServiceEndpointInstanceIdentifier;
import de.bu.governance.healthmetrics.storage.sql.AbstractSqlGateway;
import de.bu.governance.healthmetrics.storage.sql.FieldMapper;
import de.bu.governance.healthmetrics.storage.sql.SqlGateway;

@ApplicationScoped
@SqlGateway(ServiceEndpointInstance.class)
public class ServiceEndpointInstanceSqlGateway
		extends AbstractSqlGateway<ServiceEndpointInstanceIdentifier, ServiceEndpointInstance> {

	@Resource(name = "java:app/jdbc/HealthMetrics")
	private DataSource dataSource;

	@Override
	protected DataSource getDataSource() {
		return dataSource;
	}

	@Override
	protected String tableName() {
		return "serviceendpointinstances";
	}

	@Override
	public Class<ServiceEndpointInstance> supportedType() {
		return ServiceEndpointInstance.class;
	}

	@Override
	public FieldMapper<ServiceEndpointInstance, ServiceEndpointInstanceIdentifier> getFieldMapper() {
		return new ServiceEndpointInstanceFieldMapper();
	}

}
