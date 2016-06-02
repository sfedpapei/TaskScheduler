package org.timesheet.service;

import java.util.ArrayList;
import java.util.List;

public class InMemoryDao<E, K> implements GenericDao<E, K> {

	private List<E> entities = new ArrayList<E>();

	public void add(E entity) {
		entities.add(entity);
	}

	public void update(E entity) {
		throw new UnsupportedOperationException("Not supported in memory");

	}

	public void remove(E entity) {
		entities.remove(entity);

	}

	public E find(K key) {
		if (entities.isEmpty()) {
			return null;
		} else {
			// just return the first one sice we are not using any keys ATM

			return entities.get(0);
		}
	}

	public List<E> list() {
		return entities;
	}

}
