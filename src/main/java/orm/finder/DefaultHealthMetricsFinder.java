package de.bu.governance.healthmetrics.storage.finder;

import java.util.List;
import java.util.UUID;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import de.bu.governance.healthmetrics.model.HealthMetrics;
import de.bu.governance.healthmetrics.model.HealthMetricsIdentifier;
import de.bu.governance.healthmetrics.model.ServiceEndpointInstance;
import de.bu.governance.healthmetrics.model.ServiceEndpointInstanceIdentifier;
import de.bu.governance.healthmetrics.storage.Criteria.CriteriaOperator;
import de.bu.governance.healthmetrics.storage.CriteriasBuilder;
import de.bu.governance.healthmetrics.storage.CriteriasBuilder.Criterias;
import de.bu.governance.healthmetrics.storage.Gateway;
import de.bu.governance.healthmetrics.storage.sql.SqlGateway;

/**
 * Default implementation of {@link HealthMetricsFinder}.
 * 
 * @author Philipp Buchholz
 */
@ApplicationScoped
public class DefaultHealthMetricsFinder implements HealthMetricsFinder {

	@Inject
	@SqlGateway(ServiceEndpointInstance.class)
	private Gateway<ServiceEndpointInstanceIdentifier, ServiceEndpointInstance> serviceEndpointInstanceGateway;

	@Inject
	@SqlGateway(HealthMetrics.class)
	private Gateway<HealthMetricsIdentifier, HealthMetrics> healthMetricsGateway;

	@Override
	public List<HealthMetrics> findManyByServiceEndpointInstanceIdentifier(
			ServiceEndpointInstanceIdentifier serviceEndpointInstanceIdentifier) {
		Criterias criterias = CriteriasBuilder.<UUID>newCriteriasBuilder() //
				.fieldOperand("serviceendpointuuid") //
				.operator(CriteriaOperator.EQUALS) //
				.valueOperand(UUID.class, serviceEndpointInstanceIdentifier.getUUID()) //
				.none() //
				.build();
		return this.healthMetricsGateway.findManyByCriterias(criterias);
	}

	@Override
	public List<HealthMetrics> findManyByServiceEndpointName(String serviceEndpointName) {
		Criterias criterias = CriteriasBuilder.<String>newCriteriasBuilder() //
				.fieldOperand("serviceendpointname") //
				.operator(CriteriaOperator.EQUALS) //
				.valueOperand(String.class, serviceEndpointName) //
				.none() //
				.build();

		ServiceEndpointInstance serviceEndpointInstance = this.serviceEndpointInstanceGateway
				.findOneByCriterias(criterias);

		assert (null != serviceEndpointInstance);

		return this.findManyByServiceEndpointInstanceIdentifier(
				serviceEndpointInstance.getServiceEndpointInstanceIdentifier());
	}

}
