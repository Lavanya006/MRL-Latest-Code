package com.mt_ag.bayer.cmc.persistence.entity;

import java.time.LocalDateTime;
import java.util.Map;

import javax.persistence.CascadeType;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.Lob;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "CMC_MEA")
@SequenceGenerator(name = "CMC_S_M", sequenceName = "CMC_S_M", allocationSize = 0, initialValue = 1)
public class Measurement {

	public static final String ID_IN_CAIR_NEW = "ID in CAIRnew";

	@Id
	@GeneratedValue(generator = "CMC_S_M", strategy = GenerationType.SEQUENCE)
	private Long id;
	@OneToOne(cascade = CascadeType.DETACH, orphanRemoval = false, fetch = FetchType.EAGER)
	private Crop crop;
	@OneToOne(cascade = CascadeType.DETACH, orphanRemoval = false, fetch = FetchType.EAGER)
	private Country originCountry;
	@OneToOne(cascade = CascadeType.DETACH, orphanRemoval = false, fetch = FetchType.EAGER)
	private Country samplingCountry;
	@OneToOne(cascade = CascadeType.DETACH, orphanRemoval = false, fetch = FetchType.EAGER)
	private Substance substance;
	@ElementCollection(fetch = FetchType.EAGER)
	@OneToOne(cascade = CascadeType.DETACH, orphanRemoval = false, fetch = FetchType.EAGER)
	private Unit unit;
	@OneToOne(cascade = CascadeType.DETACH, orphanRemoval = false, fetch = FetchType.EAGER)
	private GrabbingJob grabbingJob;
	@Lob
	@ElementCollection(fetch = FetchType.EAGER)
	@JoinTable(name = "CMC_MEA_OPT")
	private Map<String, String> optionalData;
	private LocalDateTime samplingDate;
	private Double residueLevel;
	private Double mrl;
    private int mrlsign;

    public Measurement(){}

	public Measurement(Crop crop, Country originCountry, Country samplingCountry, Substance substance, Unit unit, GrabbingJob grabbingJob, Map<String, String> optionalData, LocalDateTime samplingDate, Double residueLevel, Double mrl, int mrlsign) {
		this.crop = crop;
		this.originCountry = originCountry;
		this.samplingCountry = samplingCountry;
		this.substance = substance;
		this.unit = unit;
		this.grabbingJob = grabbingJob;
		this.optionalData = optionalData;
		this.samplingDate = samplingDate;
		this.residueLevel = residueLevel;
		this.mrl = mrl;
		this.mrlsign = mrlsign;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Crop getCrop() {
		return crop;
	}

	public void setCrop(Crop crop) {
		this.crop = crop;
	}

	public Country getOriginCountry() {
		return originCountry;
	}

	public void setOriginCountry(Country originCountry) {
		this.originCountry = originCountry;
	}

	public Country getSamplingCountry() {
		return samplingCountry;
	}

	public void setSamplingCountry(Country samplingCountry) {
		this.samplingCountry = samplingCountry;
	}

	public Substance getSubstance() {
		return substance;
	}

	public void setSubstance(Substance substance) {
		this.substance = substance;
	}

	public Unit getUnit() {
		return unit;
	}

	public void setUnit(Unit unit) {
		this.unit = unit;
	}

	public GrabbingJob getGrabbingJob() {
		return grabbingJob;
	}

	public void setGrabbingJob(GrabbingJob grabbingJob) {
		this.grabbingJob = grabbingJob;
	}

	public Map<String, String> getOptionalData() {
		return optionalData;
	}

	public void setOptionalData(Map<String, String> optionalData) {
		this.optionalData = optionalData;
	}

	public LocalDateTime getSamplingDate() {
		return samplingDate;
	}

	public void setSamplingDate(LocalDateTime samplingDate) {
		this.samplingDate = samplingDate;
	}

	public Double getResidueLevel() {
		return residueLevel;
	}

	public void setResidueLevel(Double residueLevel) {
		this.residueLevel = residueLevel;
	}

	public Double getMrl() {
		return mrl;
	}

	public void setMrl(Double mrl) {
		this.mrl = mrl;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((crop == null) ? 0 : crop.hashCode());
		result = prime * result + ((mrl == null) ? 0 : mrl.hashCode());
		result = prime * result + ((residueLevel == null) ? 0 : residueLevel.hashCode());
		result = prime * result + ((samplingCountry == null) ? 0 : samplingCountry.hashCode());
		result = prime * result + ((samplingDate == null) ? 0 : samplingDate.hashCode());
		result = prime * result + ((substance == null) ? 0 : substance.hashCode());
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
		Measurement other = (Measurement) obj;
		if (crop == null) {
			if (other.crop != null)
				return false;
		} else if (!crop.equals(other.crop))
			return false;
		if (mrl == null) {
			if (other.mrl != null)
				return false;
		} else if (!mrl.equals(other.mrl))
			return false;
		if (residueLevel == null) {
			if (other.residueLevel != null)
				return false;
		} else if (!residueLevel.equals(other.residueLevel))
			return false;
		if (samplingCountry == null) {
			if (other.samplingCountry != null)
				return false;
		} else if (!samplingCountry.equals(other.samplingCountry))
			return false;
		if (samplingDate == null) {
			if (other.samplingDate != null)
				return false;
		} else if (!samplingDate.equals(other.samplingDate))
			return false;
		if (substance == null) {
			if (other.substance != null)
				return false;
		} else if (!substance.equals(other.substance))
			return false;
		return true;
	}

	

	@Override
	public String toString() {
		return "Measurement [id=" + id + ", crop=" + crop + ", originCountry=" + originCountry + ", samplingCountry="
				+ samplingCountry + ", substance=" + substance + ", unit=" + unit + ", grabbingJob=" + grabbingJob
				+ ", optionalData=" + optionalData + ", samplingDate=" + samplingDate + ", residueLevel=" + residueLevel
				+ ", mrl=" + mrl + ", mrlsign=" + mrlsign + "]";
	}
	
	public String getInfo() {
		return "Measurement [residueLevel=" + residueLevel
				+ ", mrl=" + mrl + ", mrlsign=" + mrlsign + ", crop=" + crop.getName() + ", substance=" + substance.getName() + "]";
	}

	public String measurementImportantInfo() {
		return "MMD [ " + "samplingDate=" + samplingDate + ", samplingCountry=" + ((samplingCountry != null) ? samplingCountry.getName() : samplingCountry) + ", substance="
				+ ((substance != null) ? substance.getName() : substance) + ", residueLevel=" + residueLevel + ", unit=" + ((unit != null) ? unit.getName() : unit) + ", mrl=" + mrl + "]";
	}

	public int getMrlsign() {
		return mrlsign;
	}

	public void setMrlsign(int mrlsign) {
		this.mrlsign = mrlsign;
	}
}
