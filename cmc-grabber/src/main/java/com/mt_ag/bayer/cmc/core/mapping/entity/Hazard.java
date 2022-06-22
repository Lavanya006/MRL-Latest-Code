package com.mt_ag.bayer.cmc.core.mapping.entity;

import java.util.HashSet;
import java.util.Set;

public class Hazard {

	private String subtance;
	private String hazardCategory;
	private Set<Double> analyticalResults;
	private String units;
	private String samplingDate;

	public Hazard() {
		super();
		this.subtance = "";
		this.hazardCategory = "";		
		this.analyticalResults = new HashSet<Double>();
		this.units = "";
		this.samplingDate = "";
	}

	public Hazard(String subtance, String hazardCategory, Set<Double> analyticalResults, String units,
			String samplingDate) {
		super();
		this.subtance = subtance;
		this.hazardCategory = hazardCategory;
		this.analyticalResults = analyticalResults;
		this.units = units;
		this.samplingDate = samplingDate;
	}

	public void setNextHazardValue(int id, String value) {

		switch (id) {
		case 0:
			this.subtance = value;
			break;
		case 1:
			this.hazardCategory = value;
			break;
		case 2:
			try {
				String[] valueParts = value.split(";");
				for (String val : valueParts) {
					this.analyticalResults.add(Double.parseDouble(val));
				}
			} catch (Exception e) {
				// this.analyticalResults.add(0);
			}
			break;
		case 3:
			this.units = value;
			break;
		case 4:
			this.samplingDate = value;
			break;
		default:
			break;
		}
	}

	public String getSubtance() {
		return subtance;
	}

	public void setSubtance(String subtance) {
		this.subtance = subtance;
	}

	public String getHazardCategory() {
		return hazardCategory;
	}

	public void setHazardCategory(String hazardCategory) {
		this.hazardCategory = hazardCategory;
	}

	public Set<Double> getAnalyticalResults() {
		return analyticalResults;
	}

	public void setAnalyticalResults(Set<Double> analyticalResults) {
		this.analyticalResults = analyticalResults;
	}

	public String getUnits() {
		return units;
	}

	public void setUnits(String units) {
		this.units = units;
	}

	public String getSamplingDate() {
		return samplingDate;
	}

	public void setSamplingDate(String samplingDate) {
		this.samplingDate = samplingDate;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((analyticalResults == null) ? 0 : analyticalResults.hashCode());
		result = prime * result + ((hazardCategory == null) ? 0 : hazardCategory.hashCode());
		result = prime * result + ((samplingDate == null) ? 0 : samplingDate.hashCode());
		result = prime * result + ((subtance == null) ? 0 : subtance.hashCode());
		result = prime * result + ((units == null) ? 0 : units.hashCode());
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
		Hazard other = (Hazard) obj;
		if (analyticalResults == null) {
			if (other.analyticalResults != null)
				return false;
		} else if (!analyticalResults.equals(other.analyticalResults))
			return false;
		if (hazardCategory == null) {
			if (other.hazardCategory != null)
				return false;
		} else if (!hazardCategory.equals(other.hazardCategory))
			return false;
		if (samplingDate == null) {
			if (other.samplingDate != null)
				return false;
		} else if (!samplingDate.equals(other.samplingDate))
			return false;
		if (subtance == null) {
			if (other.subtance != null)
				return false;
		} else if (!subtance.equals(other.subtance))
			return false;
		if (units == null) {
			if (other.units != null)
				return false;
		} else if (!units.equals(other.units))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Hazard [subtance=" + subtance + ", hazardCategory=" + hazardCategory + ", analyticalResults="
				+ analyticalResults + ", units=" + units + ", samplingDate=" + samplingDate + "]";
	}

}
