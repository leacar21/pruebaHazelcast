package com.company.example.hazelcast.repository.dao.task;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections4.CollectionUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.hibernate.sql.JoinType;
import org.springframework.stereotype.Component;

import com.company.example.hazelcast.dto.TaskDTO;
import com.company.example.hazelcast.repository.dao.AbstractHibernateEntityDAO;
import com.company.example.hazelcast.repository.model.Task;

@Component
public class TaskDAO
    extends AbstractHibernateEntityDAO<Task, Long> {


    public synchronized void delete(String key) {
		System.out.println(">>>>> MAP delete");
	}
	
	public synchronized void store(String key, Map<Long, TaskDTO> value) {
			System.out.println(">>>>> MAP store");
	}
	
	public synchronized void storeAll(Map<String, Map<Long, TaskDTO>> map) {
	    for (Map.Entry<String, Map<Long, TaskDTO>> entry : map.entrySet())
	        store(entry.getKey(), entry.getValue());
	}
	
	public synchronized void deleteAll(Collection<String> keys) {
	    for (String key : keys) delete(key);
	}
	
	public synchronized Map<Long, TaskDTO> load(String key) {
			System.out.println(">>>>> MAP load");
	//		Map<Long, TaskDTO> Map<Long, TaskDTO> = new Map<Long, TaskDTO>();
	//		Map<Long, TaskDTO>.setCode("pepe");
	//		Map<Long, TaskDTO>.setId(12345);
	//		Map<Long, TaskDTO>.setType(TaskType.CLAIM);
			return null;
	}
	
	public synchronized Map<String, Map<Long, TaskDTO>> loadAll(Collection<String> keys) {
			System.out.println(">>>>> MAP loadAll");
	    Map<String, Map<Long, TaskDTO>> result = new HashMap<String, Map<Long, TaskDTO>>();
	    for (String key : keys) result.put(key, load(key));
	    return result;
	}
	
	public Iterable<String> loadAllKeys() {
			System.out.println(">>>>> MAP loadAllKeys");
	    return null;
	}
    
    
    
    @SuppressWarnings("unchecked")
    public List<Task> getAll() {
        Criteria criteria = this.getSession();
        criteria.add(Restrictions.isNull("deletedDate"));
        criteria.setMaxResults(2000);
        criteria.addOrder(Order.asc("currentQueueDate"));
        List<Task> res = criteria.list();
        return res;
    }

    @SuppressWarnings("unchecked")
    public Task readByCodeAndSystem(String id, String system) {

        Criteria criteria = this.getSession();
        criteria.add(Restrictions.eq("system", system));
        criteria.add(Restrictions.eq("code", id));
        criteria.add(Restrictions.isNull("deletedDate"));

        List<Task> list = criteria.list();

        if (CollectionUtils.isNotEmpty(list)) {
            return list.iterator().next();
        }
        return null;
    }

    @SuppressWarnings("unchecked")
    public Task readAllByCodeAndSystem(String id, String system) {

        Criteria criteria = this.getSession();
        criteria.add(Restrictions.eq("system", system));
        criteria.add(Restrictions.eq("code", id));
        List<Task> list = criteria.list();
        if (CollectionUtils.isNotEmpty(list)) {
            return list.iterator().next();
        }
        return null;
    }

    public List<Task> find(Map<String, String> queryParams) {
        Criteria criteria = this.getSession();
        criteria.add(Restrictions.isNull("deletedDate"));
        queryParams.entrySet().stream().forEach(e -> criteria.add(Restrictions.eq(e.getKey(), e.getValue())));
        List<Task> list = criteria.list();
        return (CollectionUtils.isNotEmpty(list)) ? list : new ArrayList<>();
    }


    @SuppressWarnings("unchecked")
    public List<Task> findByReservationId(String reservationId) {

        Criteria criteria = this.getSession();
        criteria.add(Restrictions.eq("reservationId", reservationId));
        criteria.add(Restrictions.isNull("deletedDate"));
        return criteria.list();
    }

    @SuppressWarnings("unchecked")
    public List<Task> findByTransactionId(String transactionId) {

        Criteria criteria = this.getSession();
        criteria.add(Restrictions.eq("transactionId", transactionId));
        criteria.add(Restrictions.isNull("deletedDate"));
        return criteria.list();
    }

    @SuppressWarnings("unchecked")
    public List<Task> findByPreassignerUser(String preassignerUser) {

        // Criteria criteria = this.getSession();
        // criteria.add(Restrictions.eq("preassignerUser", preassignerUser));
        // criteria.add(Restrictions.isNull("deletedDate"));
        // criteria.add(Restrictions.not(Restrictions.eqOrIsNull("preassignedUsers", "")));
        // return criteria.list();

        Criteria criteria = this.join("listPreassignedUsers", "pu", JoinType.INNER_JOIN);
        criteria.add(Restrictions.eq("pu.preassignerUser", preassignerUser));
        criteria.add(Restrictions.eq("pu.erased", false));
        criteria.add(Restrictions.isNull("deletedDate"));
        List<Task> list = criteria.list();
        return list;
    }

    @SuppressWarnings("unchecked")
    public List<Task> findByPreassignedUser(String preassignedUser) {

        // Criteria criteria = this.getSession();
        // criteria.add(Restrictions.ilike("preassignedUsers", "%" + preassignedUser + "%"));
        // criteria.add(Restrictions.isNull("deletedDate"));
        // return criteria.list();

        Criteria criteria = this.join("listPreassignedUsers", "pu", JoinType.INNER_JOIN);
        criteria.add(Restrictions.eq("pu.userName", preassignedUser));
        criteria.add(Restrictions.eq("pu.erased", false));
        criteria.add(Restrictions.isNull("deletedDate"));
        List<Task> list = criteria.list();
        return list;
    }

    @SuppressWarnings("unchecked")
    public List<Task> findPreassigned(String userName, int maxListSizePerUser, boolean filter, boolean erased) {
        Criteria criteria = this.join("listPreassignedUsers", "pu", JoinType.INNER_JOIN);
        criteria.add(Restrictions.eq("pu.userName", userName));
        if (!erased) { // Si erased == true, no agregamos el criterio así trae borradas y no borradas
            criteria.add(Restrictions.eq("pu.erased", erased));
        }
        if (filter) {
            criteria.add(Restrictions.or(Restrictions.isNull("penalizedDate"), Restrictions.le("penalizedDate", new Date())));
            criteria.add(Restrictions.eq("suspended", false));
            criteria.add(Restrictions.isNull("user"));
        }

        if (maxListSizePerUser != 0) {
            criteria.setMaxResults(maxListSizePerUser);
        }

        criteria.add(Restrictions.isNull("deletedDate"));
        // criteria.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);

        return criteria.list();
    }

    /* Servicio temporal para migración de datos en respuesta a cambio en modelo de datos */
    @SuppressWarnings("unchecked")
    public List<Task> findOldPreassigneds(Long id) {

        Criteria criteria = this.getSession();
        criteria.add(Restrictions.isNotNull("preassignedUsers"));
        criteria.add(Restrictions.isNull("deletedDate"));
        criteria.add(Restrictions.gt("id", id));
        criteria.addOrder(Order.asc("id"));
        criteria.setMaxResults(200);
        return criteria.list();
    }

    @SuppressWarnings("unchecked")
    public List<Task> findByRequestId(String requestId) {

        Criteria criteria = this.getSession();
        criteria.add(Restrictions.eq("requestId", requestId));
        criteria.add(Restrictions.isNull("deletedDate"));
        return criteria.list();
    }

    @SuppressWarnings("unchecked")
    public List<Task> findByReservationIdAndProductId(String reservationId, String productId) {

        Criteria criteria = this.getSession();
        criteria.add(Restrictions.eq("reservationId", reservationId));
        criteria.add(Restrictions.eq("productId", productId));
        criteria.add(Restrictions.isNull("deletedDate"));
        return criteria.list();
    }

    @SuppressWarnings("unchecked")
    public List<Task> findByIds(List<String> ids) {

        Criteria criteria = this.getSession();

        if (ids.size() == 1) {
            criteria.add(Restrictions.eq("code", ids.get(0)));
        } else {
            criteria.add(Restrictions.in("code", ids));
        }
        criteria.add(Restrictions.isNull("deletedDate"));
        return criteria.list();
    }

    @SuppressWarnings("unchecked")
    public List<Task> findPreassignedTaskByUser(String preassignerUser) {

        Criteria criteria = this.join("taskType", "t", JoinType.INNER_JOIN);
        criteria.add(Restrictions.eq("preassignerUser", preassignerUser));
        criteria.add(Restrictions.isNull("deletedDate"));
        List<Task> list = criteria.list();
        return list;
    }

}
