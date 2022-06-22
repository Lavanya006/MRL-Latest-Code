package com.mt_ag.bayer.cmc.persistence.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "CMC_USR")
@SequenceGenerator(name = "CMC_S_B", sequenceName = "CMC_S_B", allocationSize = 0, initialValue = 1)
public class User {

	@Id
	@GeneratedValue(generator = "CMC_S_B", strategy = GenerationType.SEQUENCE)
	private Long id;
	private String username;
	private String password;
	private boolean isAdministrator;
	private boolean isDatamanager;
	private boolean isAnalyst;

	public User(Long id, String username, String password, boolean isAdministrator, boolean isDatamanager,
			boolean isAnalyst) {
		super();
		this.id = id;
		this.username = username;
		this.password = password;
		this.isAdministrator = isAdministrator;
		this.isDatamanager = isDatamanager;
		this.isAnalyst = isAnalyst;
	}
	
	public User(Long id, String username, String password) {
		super();
		this.id = id;
		this.username = username;
		this.password = password;
	}

	public User() {
		super();
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", username=" + username + ", password=" + password + ", isAdministrator="
				+ isAdministrator + ", isDatamanager=" + isDatamanager + ", isAnalyst=" + isAnalyst + "]";
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public boolean isAdministrator() {
		return isAdministrator;
	}

	public void setAdministrator(boolean isAdministrator) {
		this.isAdministrator = isAdministrator;
	}

	public boolean isDatamanager() {
		return isDatamanager;
	}

	public void setDatamanager(boolean isDatamanager) {
		this.isDatamanager = isDatamanager;
	}

	public boolean isAnalyst() {
		return isAnalyst;
	}

	public void setAnalyst(boolean isAnalyst) {
		this.isAnalyst = isAnalyst;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + (isAdministrator ? 1231 : 1237);
		result = prime * result + (isAnalyst ? 1231 : 1237);
		result = prime * result + (isDatamanager ? 1231 : 1237);
		result = prime * result + ((password == null) ? 0 : password.hashCode());
		result = prime * result + ((username == null) ? 0 : username.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		User other = (User) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (isAdministrator != other.isAdministrator)
			return false;
		if (isAnalyst != other.isAnalyst)
			return false;
		if (isDatamanager != other.isDatamanager)
			return false;
		if (password == null) {
			if (other.password != null)
				return false;
		} else if (!password.equals(other.password))
			return false;
		if (username == null) {
			if (other.username != null)
				return false;
		} else if (!username.equals(other.username))
			return false;
		return true;
	}

}
