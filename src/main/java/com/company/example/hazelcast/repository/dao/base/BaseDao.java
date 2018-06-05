package com.company.example.hazelcast.repository.dao.base;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.hibernate.criterion.DetachedCriteria;

public interface BaseDao<T extends Serializable, E> {
	
    public void deleteAll(Collection<T> instances);
    public int bulkUpdate(String query);
    public E save(T instance);
    //public void saveOrUpdateAll(Collection<T> instances);
    public void saveOrUpdate(T instance);
    public void persist(T transientInstance);
    public void attachDirty(T instance);
    public void attachClean(T instance);
    public void delete(T persistentInstance);
    public List<T> findByExample(T instance);
    public List<T> findByQuery(String query);
    public List<Map<String, Object>> findMapByQuery(String queryString);
    public List<T> findByCriteria(DetachedCriteria criteria);
    public T merge(T detachedInstance);
    public List<T> findAll();
    public T findById(E id);
}
