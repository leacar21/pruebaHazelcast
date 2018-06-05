package com.company.example.hazelcast.repository.model.generic;

public interface HibernateEntity<ID> {

    ID getId();

    void setId(ID id);
}
