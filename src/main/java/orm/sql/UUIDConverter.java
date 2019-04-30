package de.bu.governance.healthmetrics.storage.sql;

import java.util.UUID;

public class UUIDConverter implements TypeConverter<UUID> {

	@Override
	public UUID convertFrom(String input) {
		return (null == input) ? null : UUID.fromString(input);
	}

	@Override
	public String convertTo(Object input) {
		return (null == input) ? "" : input.toString();
	}

}
