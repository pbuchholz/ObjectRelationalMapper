package de.bu.governance.healthmetrics.storage.sql.healthmetrics;

import de.bu.governance.healthmetrics.model.HealthMetrics.HealthMetricsUnit;
import de.bu.governance.healthmetrics.storage.sql.TypeConverter;

/**
 * {@link TypeConverter} for {@link HealthMetricsUnit}.
 * 
 * @author Philipp Buchholz
 */
public class HealthMetricsUnitConverter implements TypeConverter<HealthMetricsUnit> {

	@Override
	public HealthMetricsUnit convertFrom(String input) {
		return HealthMetricsUnit.valueOf(input);
	}

	@Override
	public String convertTo(Object input) {
		return (input == null) ? "" : input.toString();
	}

}
