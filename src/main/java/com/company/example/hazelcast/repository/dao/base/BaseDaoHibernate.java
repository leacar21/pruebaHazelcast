package com.company.example.hazelcast.repository.dao.base;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.hibernate.LockMode;
import org.hibernate.criterion.DetachedCriteria;
import org.springframework.orm.hibernate5.support.HibernateDaoSupport;

public abstract class BaseDaoHibernate<T extends Serializable, E> extends HibernateDaoSupport implements BaseDao<T, E> {
    public void deleteAll(final Collection<T> instances) {
    	getHibernateTemplate().deleteAll(instances);
    }
    public int bulkUpdate(final String query) {
        return getHibernateTemplate().bulkUpdate(query);
    }
    @SuppressWarnings("unchecked")
	public E save(final T instance) {
        return (E) getHibernateTemplate().save(instance);
    }
    public void saveOrUpdate(final T instance) {
        getHibernateTemplate().saveOrUpdate(instance);
    }
    public void persist(final T transientInstance) {
        getHibernateTemplate().persist(transientInstance);
    }
    public void attachDirty(final T instance) {
        getHibernateTemplate().saveOrUpdate(instance);
    }
    public void attachClean(final T instance) {
        getHibernateTemplate().lock(instance, LockMode.NONE);
    }
    public void delete(final T persistentInstance) {
        getHibernateTemplate().delete(persistentInstance);
    }
    public T merge(final T detachedInstance) {
        final T result = getHibernateTemplate().merge(detachedInstance);
        return result;
    }
    public List<T> findByExample(final T instance) {
        final List<T> results = getHibernateTemplate().findByExample(instance);
        return results;
    }
    @SuppressWarnings("unchecked")
	public List<T> findByQuery(final String queryString) {
        final List<T> results = (List<T>) getHibernateTemplate().find(queryString);
        return results;
    }
    @SuppressWarnings("unchecked")
	public List<Map<String, Object>> findMapByQuery(final String queryString) {
        final List<Map<String, Object>> results = (List<Map<String, Object>>) getHibernateTemplate().find(queryString);
        return results;
    }
    @SuppressWarnings("unchecked")
	public List<T> findByCriteria(final DetachedCriteria criteria) {
        return (List<T>) getHibernateTemplate().findByCriteria(criteria);
    }
    public abstract List<T> findAll();
    public abstract T findById(E id);
    
}
