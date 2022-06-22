package com.mt_ag.bayer.cmc.persistence.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name="CMC_DTS")
@SequenceGenerator(name = "CMC_S_D", sequenceName = "CMC_S_D", allocationSize = 0, initialValue = 1)
public class DataSource {
	public static final String RASFF_DATA_SOURCE = "RASFF";
	public static final String UK_DATA_SOURCE = "UK";
	public static final String DK_DATA_SOURCE = "DK";

	@Id
	@GeneratedValue(generator = "CMC_S_D", strategy = GenerationType.SEQUENCE)
	private Long id;
	private String name;
	private String description;
	private String url;
	private Integer intervalInDays;

	public DataSource() {
	}

	public DataSource(String name, String description, String url, Integer intervalInDays) {
		super();
		this.name = name;
		this.description = description;
		this.url = url;
		this.intervalInDays = intervalInDays;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Integer getIntervalInDays() {
		return intervalInDays;
	}

	public void setIntervalInDays(Integer intervalInDays) {
		this.intervalInDays = intervalInDays;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((intervalInDays == null) ? 0 : intervalInDays.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((url == null) ? 0 : url.hashCode());
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
		DataSource other = (DataSource) obj;
		if (intervalInDays == null) {
			if (other.intervalInDays != null)
				return false;
		} else if (!intervalInDays.equals(other.intervalInDays))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (url == null) {
			if (other.url != null)
				return false;
		} else if (!url.equals(other.url))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "DataSource [id=" + id + ", name=" + name + ", description=" + description + ", url=" + url
				+ ", intervalInDays=" + intervalInDays + "]";
	}

}
