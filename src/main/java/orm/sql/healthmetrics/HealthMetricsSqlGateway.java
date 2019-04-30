package de.bu.governance.healthmetrics.storage.sql.healthmetrics;

import javax.annotation.Resource;
import javax.enterprise.context.ApplicationScoped;
import javax.sql.DataSource;

import de.bu.governance.healthmetrics.model.HealthMetrics;
import de.bu.governance.healthmetrics.model.HealthMetricsIdentifier;
import de.bu.governance.healthmetrics.storage.sql.AbstractSqlGateway;
import de.bu.governance.healthmetrics.storage.sql.FieldMapper;
import de.bu.governance.healthmetrics.storage.sql.SqlGateway;

@ApplicationScoped
@SqlGateway(HealthMetrics.class)
public class HealthMetricsSqlGateway extends AbstractSqlGateway<HealthMetricsIdentifier, HealthMetrics> {

	@Resource(name = "java:app/jdbc/HealthMetrics")
	private DataSource healthMetricsDataSource;

	@Override
	public FieldMapper<HealthMetrics, HealthMetricsIdentifier> getFieldMapper() {
		return new HealthMetricsFieldMapper();
	}

	@Override
	protected DataSource getDataSource() {
		return healthMetricsDataSource;
	}

	@Override
	public Class<HealthMetrics> supportedType() {
		return HealthMetrics.class;
	}

	@Override
	protected String tableName() {
		return "healthmetrics";
	}

}
