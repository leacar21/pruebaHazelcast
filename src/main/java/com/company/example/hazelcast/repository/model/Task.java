package com.company.example.hazelcast.repository.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.company.example.hazelcast.repository.model.generic.AbstractHibernateEntity;

@Entity
@Table(name = "Z_TASK")
public class Task
    extends AbstractHibernateEntity<Long>
    implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(generator = "increment")
    @GenericGenerator(name = "increment", strategy = "native")
    @Column(name = "ID", nullable = false)
    private Long id;
    
    @Column(name = "TRANSACTION_ID", nullable = false)
    private String transactionId;
    
    @Column(name = "REQUEST_ID", nullable = false)
	private String requestId;
	
    @Column(name = "COUNTRY", nullable = false)
	private String country;
	
    @Column(name = "CREATION", nullable = false)
	private Date creation;
	
    @Column(name = "CHECKIN", nullable = false)
	private Date checkIn;
    

	@Override
	public Long getId() {
		return id;
	}

	@Override
	public void setId(Long id) {
		this.id = id;
	}

	public String getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}

	public String getRequestId() {
		return requestId;
	}

	public void setRequestId(String requestId) {
		this.requestId = requestId;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public Date getCreation() {
		return creation;
	}

	public void setCreation(Date creation) {
		this.creation = creation;
	}

	public Date getCheckIn() {
		return checkIn;
	}

	public void setCheckIn(Date checkIn) {
		this.checkIn = checkIn;
	}

}
