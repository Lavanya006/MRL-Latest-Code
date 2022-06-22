package com.mt_ag.bayer.cmc.persistence.entity;

import java.util.Set;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "CMC_SUB")
@SequenceGenerator(name = "CMC_S_S", sequenceName = "CMC_S_S", allocationSize = 0, initialValue = 1)
public class Substance {

	@Id
	@GeneratedValue(generator = "CMC_S_S", strategy = GenerationType.SEQUENCE)
	private Long id;
	private String name;

	@ElementCollection(fetch = FetchType.EAGER)
	@JoinTable(name = "CMC_SUB_SNA")
	private Set<String> otherNames;

	public Substance() {

	}

	public Substance(String name, Set<String> otherNames) {
		super();
		this.name = name;
		this.otherNames = otherNames;
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

	public Set<String> getOtherNames() {
		return otherNames;
	}

	public void setOtherNames(Set<String> otherNames) {
		this.otherNames = otherNames;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((otherNames == null) ? 0 : otherNames.hashCode());
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
		Substance other = (Substance) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (otherNames == null) {
			if (other.otherNames != null)
				return false;
		} else if (!otherNames.equals(other.otherNames))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Substance [id=" + id + ", name=" + name + ", otherNames=" + otherNames + "]";
	}

}
