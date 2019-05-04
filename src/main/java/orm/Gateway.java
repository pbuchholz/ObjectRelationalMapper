package orm;

import java.util.List;

import orm.CriteriasBuilder.Criterias;

/**
 * Gateway which enables access to defined types.
 * 
 * @author Philipp Buchholz
 */
public interface Gateway<I, T> {

	/**
	 * Adds an object.
	 * 
	 * @param identifier
	 * @param add
	 */
	void add(I identifier, T add);

	/**
	 * Finds one object using the given identifier.
	 * 
	 * @param identifier
	 * @return
	 */
	T findOneByIdentifier(I identifier);

	/**
	 * Finds a {@link List} of type T objects using the given {@link Criterias}.
	 * 
	 * @param criterias
	 * @return
	 * @throws GatewayException
	 */
	List<T> findManyByCriterias(Criterias criterias);

	/**
	 * Finds one object of type T using the given {@link Criterias}.
	 * 
	 * @param criterias
	 * @return
	 * @throws GatewayException
	 */
	T findOneByCriterias(Criterias criterias);

	/**
	 * Finds all objects available.
	 * 
	 * @return
	 */
	List<T> findAll();

	/**
	 * Returns the supported Type.
	 * 
	 * @return
	 */
	Class<T> supportedType();

}
