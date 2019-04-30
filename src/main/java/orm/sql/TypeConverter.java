package de.bu.governance.healthmetrics.storage.sql;

/**
 * Converts to and from type of a database field.
 * 
 * @author Philipp Buchholz
 */
public interface TypeConverter<T> {

	T convertFrom(String input);

	// TODO This method signature is to generic but currently we need object as argument
	// TODO Isnt there a better method signature?
	String convertTo(Object input);

}
