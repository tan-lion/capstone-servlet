package entity;

import java.sql.Date;
import java.time.LocalDate;

public class Job {
	private int id;
	private String name;
	private Date startDate;
	private Date endDate;

	public Job() {
		super();
	}

	public Job(int id, String name, Date startDate, Date endDate) {
		super();	
		this.id = id;
		this.name = name;
		this.startDate = startDate;
		this.endDate = endDate;
	}

	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public Date getStartDate() {
		return startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	
	

}
