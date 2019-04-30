package de.bu.governance.healthmetrics.storage.sql.healthmetrics;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.List;
import java.util.UUID;

import javax.annotation.Resource;
import javax.inject.Inject;
import javax.sql.DataSource;

import org.junit.Test;

import de.bu.governance.healthmetrics.model.HealthMetrics;
import de.bu.governance.healthmetrics.model.HealthMetrics.HealthMetricsType;
import de.bu.governance.healthmetrics.model.HealthMetrics.HealthMetricsUnit;
import de.bu.governance.healthmetrics.model.HealthMetricsIdentifier;
import de.bu.governance.healthmetrics.model.ServiceEndpointInstanceIdentifier;
import de.bu.governance.healthmetrics.storage.AbstractDatabaseIT;
import de.bu.governance.healthmetrics.storage.sql.SqlGateway;

public class HealthMetricsSqlGatewayDatabaseIT extends AbstractDatabaseIT {

	@Resource(name = "java:app/jdbc/HealthMetrics")
	private DataSource healthMetricsDataSource;

	@Inject
	@SqlGateway(HealthMetrics.class)
	private HealthMetricsSqlGateway healthMetricsSqlGateway;

	@Override
	protected DataSource getTestDataSource() {
		return healthMetricsDataSource;
	}

	private HealthMetrics buildAverageRequestsPerDay(HealthMetricsIdentifier healthMetricsIdentifier,
			ServiceEndpointInstanceIdentifier serviceEndpointInstanceIdentifier, String healthMetricsValue) {
		return HealthMetrics.builder() //
				.healthMetricsCategory("RandomCategory") //
				.healthMetricsIdentifier(healthMetricsIdentifier) //
				.healthMetricsType(HealthMetricsType.AVGREQUESTS) //
				.healthMetricsUnit(HealthMetricsUnit.DAY) //
				.healthMetricsValue(healthMetricsValue) //
				.serviceEndpointInstanceIdentifier(serviceEndpointInstanceIdentifier) //
				.build();
	}

	@Test
	public void testInsertHealthMetrics() {
		UUID uuid = UUID.randomUUID();
		HealthMetricsIdentifier healthMetricsIdentifier = new HealthMetricsIdentifier(uuid);
		ServiceEndpointInstanceIdentifier serviceEndpointInstanceIdentifier = new ServiceEndpointInstanceIdentifier(
				UUID.randomUUID());
		HealthMetrics healthMetrics = this.buildAverageRequestsPerDay(healthMetricsIdentifier,
				serviceEndpointInstanceIdentifier, "200");
		this.healthMetricsSqlGateway.add(healthMetricsIdentifier, healthMetrics);

		List<HealthMetrics> foundHealthMetrics = this.healthMetricsSqlGateway.findAll();
		assertNotNull("No HealthMetrics has been found after insert.", foundHealthMetrics);
		assertEquals("Wrong count of HealthMetrics has been found.", 1, foundHealthMetrics.size());

	}

}
