package com.mt_ag.bayer.cmc.persistence.entity;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "CMC_EXE")
@SequenceGenerator(name = "CMC_S_X", sequenceName = "CMC_S_X", allocationSize = 0, initialValue = 1)
public class Execution {

	@Id
	@GeneratedValue(generator = "CMC_S_X", strategy = GenerationType.SEQUENCE)
	private Long id;
	private LocalDateTime dateTime;
	private String dataSourceName;

	public Execution() {
	}

	public Execution(LocalDateTime dateTime, String dataSourceName) {
		super();
		this.dateTime = dateTime;
		this.dataSourceName = dataSourceName;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public LocalDateTime getDateTime() {
		return dateTime;
	}

	public void setDateTime(LocalDateTime dateTime) {
		this.dateTime = dateTime;
	}

	public String getDataSourceName() {
		return dataSourceName;
	}

	public void setDataSourceName(String dataSourceName) {
		this.dataSourceName = dataSourceName;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((dataSourceName == null) ? 0 : dataSourceName.hashCode());
		result = prime * result + ((dateTime == null) ? 0 : dateTime.hashCode());
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
		Execution other = (Execution) obj;
		if (dataSourceName == null) {
			if (other.dataSourceName != null)
				return false;
		} else if (!dataSourceName.equals(other.dataSourceName))
			return false;
		if (dateTime == null) {
			if (other.dateTime != null)
				return false;
		} else if (!dateTime.equals(other.dateTime))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Execution [id=" + id + ", dateTime=" + dateTime + ", dataSourceName=" + dataSourceName + "]";
	}

}