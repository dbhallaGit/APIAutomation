package org.qa.data;

public class users {

	String name,job,id,createdAt;
	
	public users() {}
	
	public users(String name,String job) {
		this.name=name;
		this.job=job;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getJob() {
		return job;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(String createdAt) {
		this.createdAt = createdAt;
	}

	public void setJob(String job) {
		this.job = job;
	}
	
	
}
