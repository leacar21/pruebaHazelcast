package com.company.example.hazelcast.repository.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.company.example.hazelcast.repository.model.generic.AbstractHibernateEntity;

@Entity
@Table(name = "Z_SUBTEAM")
public class SubTeam
    extends AbstractHibernateEntity<Long>
    implements Serializable {

    private static final long serialVersionUID = 1L;

//    @Id
//    @GeneratedValue(generator = "increment")
//    @GenericGenerator(name = "increment", strategy = "native")
//    @Column(name = "ID", nullable = false)
    @Id
    @Column(name = "ID", nullable = false)
    private Long id;
    
    @Column(name = "CODE", nullable = false)
	private String code;
    
	@Override
	public Long getId() {
		return id;
	}

	@Override
	public void setId(Long id) {
		this.id = id;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

}
