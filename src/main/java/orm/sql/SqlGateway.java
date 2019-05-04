package orm.sql;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.inject.Qualifier;

import orm.Gateway;

/**
 * Describes the type of {@link Gateway} which should be injected.
 * 
 * @author Philipp Buchholz
 */
@Qualifier
@Retention(RetentionPolicy.RUNTIME)
@Target(value = { ElementType.TYPE, ElementType.FIELD, ElementType.METHOD, ElementType.PARAMETER })
public @interface SqlGateway {

	Class<?> value();

}
