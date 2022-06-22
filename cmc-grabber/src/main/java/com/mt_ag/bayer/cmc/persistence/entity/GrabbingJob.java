package com.mt_ag.bayer.cmc.persistence.entity;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "CMC_GRA")
@SequenceGenerator(name = "CMC_S_G", sequenceName = "CMC_S_G", allocationSize = 0, initialValue = 1)
public class GrabbingJob {

	@Id
	@GeneratedValue(generator = "CMC_S_G", strategy = GenerationType.SEQUENCE)
	private Long id;
	@OneToOne(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
	private Execution execution;
	@OneToOne(cascade = CascadeType.DETACH, orphanRemoval = false, fetch = FetchType.EAGER)
	private DataSource dataSource;
	private Long uniqueHash;

	public GrabbingJob() {

	}

	public GrabbingJob(Execution execution, DataSource dataSource, Long uniqueHash) {
		super();
		this.execution = execution;
		this.dataSource = dataSource;
		this.uniqueHash = uniqueHash;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Execution getExecution() {
		return execution;
	}

	public void setExecution(Execution execution) {
		this.execution = execution;
	}

	public DataSource getDataSource() {
		return dataSource;
	}

	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	public Long getUniqueHash() {
		return uniqueHash;
	}

	public void setUniqueHash(Long uniqueHash) {
		this.uniqueHash = uniqueHash;
	}

	@Override
	public String toString() {
		return "GrabbingJob [id=" + id + ", execution=" + execution + ", dataSource=" + dataSource + ", uniqueHash="
				+ uniqueHash + "]";
	}

}
