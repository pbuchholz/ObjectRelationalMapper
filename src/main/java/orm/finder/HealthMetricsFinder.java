package de.bu.governance.healthmetrics.storage.finder;

import java.util.List;

import de.bu.governance.healthmetrics.model.HealthMetrics;
import de.bu.governance.healthmetrics.model.ServiceEndpointInstanceIdentifier;

/**
 * Finder which provides find methods specific to {@link HealthMetrics}.
 * 
 * @author Philipp Buchholz
 */
public interface HealthMetricsFinder {

	/**
	 * Finds a {@link List} of {@link HealthMetrics} for a ServiceEndpointInstance.
	 * 
	 * @param serviceEndpointInstanceIdentifier
	 * @return
	 */
	List<HealthMetrics> findManyByServiceEndpointInstanceIdentifier(
			ServiceEndpointInstanceIdentifier serviceEndpointInstanceIdentifier);

	/**
	 * Finds a list of {@link HealthMetrics} for a ServiceEndpointInstance
	 * identified using the passed in serviceEndpointName.
	 * 
	 * @param serviceEndpointName
	 * @return
	 */
	List<HealthMetrics> findManyByServiceEndpointName(String serviceEndpointName);

}
