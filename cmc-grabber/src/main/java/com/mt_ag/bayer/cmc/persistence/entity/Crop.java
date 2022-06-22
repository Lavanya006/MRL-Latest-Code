package com.mt_ag.bayer.cmc.persistence.entity;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "CMC_CRO")
@SequenceGenerator(name = "CMC_S_C", sequenceName = "CMC_S_C", allocationSize = 0, initialValue = 1)
public class Crop {
	@Id
	@GeneratedValue(generator = "CMC_S_C", strategy = GenerationType.SEQUENCE)
	private Long id;

	@OneToOne(cascade = CascadeType.DETACH, orphanRemoval = false, fetch = FetchType.EAGER)
	private Family family;
	private String name;

	@ElementCollection(fetch = FetchType.EAGER)
	@JoinTable(name = "CMC_CRO_CNA")
	private Set<String> otherNames;
	

	public Crop() {

	}

	public Crop(Family family, String name, Set<String> otherNames) {
		super();
		this.family = family;
		this.name = name;
		this.otherNames = otherNames;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Family getFamily() {
		return family;
	}

	public void setFamily(Family family) {
		this.family = family;
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
		result = prime * result + ((family == null) ? 0 : family.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
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
		Crop other = (Crop) obj;
		if (family == null) {
			if (other.family != null)
				return false;
		} else if (!family.equals(other.family))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Crop [id=" + id + ", family=" + family + ", name=" + name + ", otherNames=" + otherNames + "]";
	}

}
