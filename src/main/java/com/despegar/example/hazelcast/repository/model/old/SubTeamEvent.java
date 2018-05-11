package com.despegar.example.hazelcast.repository.model.old;

import com.despegar.example.hazelcast.repository.model.Event;
import com.despegar.example.hazelcast.repository.model.SubTeam;
import com.despegar.example.hazelcast.repository.model.Task;

public class SubTeamEvent{
	private Long id;
	private SubTeam subTeam;
	private Task task;
	private Event event;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public SubTeam getSubTeam() {
		return subTeam;
	}
	public void setSubTeam(SubTeam subTeam) {
		this.subTeam = subTeam;
	}
	public Task getTask() {
		return task;
	}
	public void setTask(Task task) {
		this.task = task;
	}
	public Event getEvent() {
		return event;
	}
	public void setEvent(Event event) {
		this.event = event;
	}
	
}

//@Entity
//@Table(name = "Z_SUBTEAM_EVENT")
//public class SubTeamEvent
//    extends AbstractHibernateEntity<Long>
//    implements Serializable {
//
//    private static final long serialVersionUID = 1L;
//
//    @Id
//    @GeneratedValue(generator = "increment")
//    @GenericGenerator(name = "increment", strategy = "native")
//    @Column(name = "ID", nullable = false)
//    private Long id;
//    
//    @ManyToOne
//    @JoinColumn(name = "SUBTEAM_ID")
//    private SubTeam subTeam;
//    
//    @ManyToOne
//    @JoinColumn(name = "TASK_ID")
//    private Task task;
//	
//    @ManyToOne
//    @JoinColumn(name = "EVENT_ID")
//    private Event event;
//    
//	@Override
//	public Long getId() {
//		return id;
//	}
//
//	@Override
//	public void setId(Long id) {
//		this.id = id;
//	}
//
//	public SubTeam getSubTeam() {
//		return subTeam;
//	}
//
//	public void setSubTeam(SubTeam subTeam) {
//		this.subTeam = subTeam;
//	}
//
//	public Task getTask() {
//		return task;
//	}
//
//	public void setTask(Task task) {
//		this.task = task;
//	}
//
//	public Event getEvent() {
//		return event;
//	}
//
//	public void setEvent(Event event) {
//		this.event = event;
//	}
//	
//}
