package de.bu.governance.healthmetrics.storage.sql.healthmetrics;

import de.bu.governance.healthmetrics.model.HealthMetrics.HealthMetricsType;
import de.bu.governance.healthmetrics.model.HealthMetrics.HealthMetricsUnit;
import de.bu.governance.healthmetrics.storage.sql.TypeConverter;

/**
 * {@link TypeConverter} for {@link HealthMetricsUnit}.
 * 
 * @author Philipp Buchholz
 */
public class HealthMetricsTypeConverter implements TypeConverter<HealthMetricsType> {

	@Override
	public HealthMetricsType convertFrom(String input) {
		return HealthMetricsType.valueOf(input);
	}

	@Override
	public String convertTo(Object input) {
		return (input == null) ? "" : input.toString();
	}

}
