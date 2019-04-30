package de.bu.governance.healthmetrics.storage.sql.generator;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.UUID;
import java.util.stream.Collectors;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Spy;
import org.mockito.runners.MockitoJUnitRunner;

import de.bu.governance.healthmetrics.model.HealthMetrics;
import de.bu.governance.healthmetrics.model.HealthMetrics.HealthMetricsType;
import de.bu.governance.healthmetrics.model.HealthMetrics.HealthMetricsUnit;
import de.bu.governance.healthmetrics.model.HealthMetricsIdentifier;
import de.bu.governance.healthmetrics.model.ServiceEndpointInstanceIdentifier;
import de.bu.governance.healthmetrics.storage.sql.FieldMapperSupport;
import de.bu.governance.healthmetrics.storage.sql.FieldMappingTestBuilder;
import de.bu.governance.healthmetrics.storage.sql.interpreter.SqlCriteriaInterpreter;

/**
 * Test for {@link DefaultSqlGenerator}.
 * 
 * @author Philipp Buchholz
 */
@RunWith(MockitoJUnitRunner.class)
public class DefaultSqlGeneratorTest {

	@Spy
	private SqlCriteriaInterpreter sqlCriteriaInterpreter;

	@InjectMocks
	private DefaultSqlGenerator defaultSqlGenerator;

	@Test
	public void testGenerateSelectStatement() {
		String select = defaultSqlGenerator.generateSelectStatement("healthmetrics",
				FieldMappingTestBuilder.buildFieldMappingsForHealthMetrics());

		assertNotNull("Select statement is null.", select);
		assertEquals("Select statement generated is not correct.",
				"SELECT healthmetricscategory,healthmetricstype,healthmetricsunit,healthmetricsvalue FROM healthmetrics",
				select);
	}

	@Test
	public void testGenerateSelectByIdStatement() {
		UUID uuid = UUID.fromString("febe036b-9574-4d8f-9f32-f4e110d7b099");
		HealthMetricsIdentifier healthMetricsIdentifier = new HealthMetricsIdentifier(uuid);

		String select = defaultSqlGenerator.generateSelectStatement("healthmetrics",
				FieldMapperSupport
						.unionFieldMappings(FieldMappingTestBuilder.buildFieldMappingsForHealthMetrics(),
								FieldMappingTestBuilder.buildIdFieldMappingsForHealthMetricsIdentifier())
						.collect(Collectors.toList()),
				FieldMappingTestBuilder.buildHealthMetricsIdFieldMappingTargetBag(healthMetricsIdentifier));

		assertNotNull("Select statement generated is null.", select);
		assertEquals("Select statement generated is not correct.",
				"SELECT healthmetricscategory,healthmetricstype,healthmetricsunit,healthmetricsvalue,uuid FROM healthmetrics WHERE uuid=febe036b-9574-4d8f-9f32-f4e110d7b099",
				select);
	}

	@Test
	public void testGenerateInsertStatement() {
		UUID uuid = UUID.fromString("febe036b-9574-4d8f-9f32-f4e110d7b099");
		HealthMetricsIdentifier healthMetricsIdentifier = new HealthMetricsIdentifier(uuid);
		HealthMetrics healthMetrics = HealthMetrics.builder() //
				.serviceEndpointInstanceIdentifier(new ServiceEndpointInstanceIdentifier(UUID.randomUUID())) //
				.healthMetricsCategory("generalhealth") //
				.healthMetricsIdentifier(healthMetricsIdentifier) //
				.healthMetricsUnit(HealthMetricsUnit.SECOND) //
				.healthMetricsValue("11") //
				.healthMetricsType(HealthMetricsType.AVGREQUESTS) //
				.build();

		String insert = defaultSqlGenerator.generateInsertStatement("healthmetrics",
				FieldMappingTestBuilder.buildHealthMetricsIdFieldMappingTargetBag(healthMetricsIdentifier),
				FieldMappingTestBuilder.buildHealthMetricsFieldMappingTargetBag(healthMetrics));

		assertNotNull("Insert statement generated is null.", insert);
		assertEquals("Insert statement is not correct.",
				"INSERT INTO healthmetrics(uuid,healthmetricscategory,healthmetricstype,healthmetricsunit,healthmetricsvalue) VALUES ('febe036b-9574-4d8f-9f32-f4e110d7b099','generalhealth','AVGREQUESTS','SECOND','11')",
				insert);
	}

}
