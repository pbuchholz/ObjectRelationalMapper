package de.bu.governance.healthmetrics.storage;

import java.lang.reflect.Method;
import java.util.stream.Stream;

public final class Reflections {

	public static final String GETTER_PREFIX = "get";
	public static final String SETTER_PREFIX = "set";

	private Reflections() {

	}

	public static boolean isGetter(Method method) {
		return method.getName().startsWith(GETTER_PREFIX);
	}

	public static boolean isSetter(Method method) {
		return method.getName().startsWith(SETTER_PREFIX);
	}

	public static <T> Method findGetterOn(Class<T> type, String propertyName) {
		return Stream.of(type.getMethods()) //
				.filter((m) -> m.getName().equals(GETTER_PREFIX.concat(propertyName))) //
				.findFirst() //
				.orElse(null);
	}

	public static <T> Method findSetterOn(Class<T> type, String propertyName) {
		return Stream.of(type.getMethods()) //
				.filter((m) -> m.getName().equals(SETTER_PREFIX.concat(propertyName))) //
				.findFirst() //
				.orElse(null);
	}

}
