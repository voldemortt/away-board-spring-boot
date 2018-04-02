package com.awayboard.service;

import java.io.Serializable;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

/**
 * The Interface GenericService.
 *
 * @param <T> the generic type
 * @param <ID> the generic type
 */
public interface GenericService<T, ID extends Serializable> {
	
	/**
	 * Find all.
	 *
	 * @return the iterable
	 */
	public default Iterable<T> findAll() {
		return getRepository().findAll();
	}
	
	/**
	 * Gets the.
	 *
	 * @param id the id
	 * @return the optional
	 */
	public default Optional<T> get(ID id) {
		return getRepository().findById(id);
	}
	
	/**
	 * Save.
	 *
	 * @param entity the entity
	 * @return the t
	 */
	public default T save(T entity) {
		return getRepository().save(entity);
	}
	
	/**
	 * Delete.
	 *
	 * @param id the id
	 */
	public default void delete(ID id) {
		if (getRepository().existsById(id)) {
			getRepository().deleteById(id);
		}
		else {
			System.out.println("Employee doesn't exists: " + id);
		}
	}
	
	/**
	 * Update.
	 *
	 * @param entity the entity
	 */
	public default void update(T entity) {
		if (getRepository().existsById(getId(entity))) {
			getRepository().save(entity);
		}
		else {
			System.out.println("Can't update entry because it doesn't exist in DB: " + entity);
		}
	}
	
	/**
	 * Gets the id.
	 *
	 * @param entity the entity
	 * @return the id
	 */
	public ID getId(T entity);
	
	/**
	 * Gets the repository.
	 *
	 * @return the repository
	 */
	public CrudRepository<T, ID> getRepository();
}