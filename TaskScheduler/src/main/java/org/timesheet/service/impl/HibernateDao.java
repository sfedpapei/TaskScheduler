package org.timesheet.service.impl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.timesheet.domain.Employee;
import org.timesheet.service.GenericDao;
import org.springframework.transaction.annotation.Propagation;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;

@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
public class HibernateDao<E, K extends Serializable> implements
		GenericDao<E, K> {

	private SessionFactory sessionFactory;

	protected Class<? extends E> daoType;

	@SuppressWarnings("unchecked")
	public HibernateDao() {
		daoType = (Class<E>) ((ParameterizedType) getClass()
				.getGenericSuperclass()).getActualTypeArguments()[0];
	}

	@Autowired
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	protected Session currentSession() {
		return sessionFactory.getCurrentSession();

	}
	
	@Override
	public void add(E entity) {
		currentSession().save(entity);

	}

	public void update(E entity) {
		currentSession().saveOrUpdate(entity);

	}

	public void remove(E entity) {
		currentSession().delete(entity);

	}

	public E find(K key) {
		return (E) currentSession().get(daoType, key);
	}

	//I had createCriteria(Employee.class) I had to change it to daoType to get all Models
	//createCriteria run query against a particular class
	public List<E> list() {
		return currentSession().createCriteria(daoType)
				.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY)
				.list();
	}

}
