package com.mt_ag.bayer.cmc.core.mapping.entity;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class RASFFMapping {

	private String suspect;
	private String referenz;
	private String notificationType;
	private String notificationDate;
	private String actionTaken;
	private String lastUpdate;
	private String destributionStatus;
	private String notificationFrom;
	private String product;
	private String classification;
	private String productCategory;
	private String riskDecision;
	private String publishingStatus;
	private Set<String> originCountries;
	private String firstOriginCountry;
	private List<Hazard> hazards;
	private List<FollowUp> followups;
	private Set<String> concernedCountriesAndOrganisations;

	public RASFFMapping() {
		super();

		this.suspect = "";
		this.referenz = "";
		this.notificationType = "";
		this.notificationDate = "";
		this.actionTaken = "";
		this.lastUpdate = "";
		this.destributionStatus = "";
		this.notificationFrom = "";
		this.product = "";
		this.classification = "";
		this.productCategory = "";
		this.riskDecision = "";
		this.publishingStatus = "";
		this.hazards = new ArrayList<Hazard>();
		this.followups = new ArrayList<FollowUp>();
		this.concernedCountriesAndOrganisations = new HashSet<String>();
	}

	public void addNextHazard(Hazard hazard) {
		this.hazards.add(hazard);
	}

	public void addNextFollowUp(FollowUp value) {
		this.followups.add(value);
	}

	public void setNextInfo(int id, String value) {

		switch (id) {
		case 0:
			this.suspect = value;
			break;
		case 1:
			this.referenz = value;
			break;
		case 2:
			this.notificationType = value;
			break;
		case 3:
			this.notificationDate = value;
			break;
		case 4:
			this.actionTaken = value;
			break;
		case 5:
			this.lastUpdate = value;
			break;
		case 6:
			this.destributionStatus = value;
			break;
		case 7:
			this.notificationFrom = value;
			break;
		case 8:
			this.product = value;
			break;
		case 9:
			this.classification = value;
			break;
		case 10:
			this.productCategory = value;
			break;
		case 11:
			this.riskDecision = value;
			break;
		case 12:
			this.publishingStatus = value;
			break;
		default:
			this.concernedCountriesAndOrganisations.add(value);
			break;
		}
	}

	public String getSuspect() {
		return suspect;
	}

	public void setSuspect(String suspect) {
		this.suspect = suspect;
	}

	public String getReferenz() {
		return referenz;
	}

	public void setReferenz(String referenz) {
		this.referenz = referenz;
	}

	public String getNotificationType() {
		return notificationType;
	}

	public void setNotificationType(String notificationType) {
		this.notificationType = notificationType;
	}

	public String getNotificationDate() {
		return notificationDate;
	}

	public void setNotificationDate(String notificationDate) {
		this.notificationDate = notificationDate;
	}

	public String getActionTaken() {
		return actionTaken;
	}

	public void setActionTaken(String actionTaken) {
		this.actionTaken = actionTaken;
	}

	public String getLastUpdate() {
		return lastUpdate;
	}

	public void setLastUpdate(String lastUpdate) {
		this.lastUpdate = lastUpdate;
	}

	public String getDestributionStatus() {
		return destributionStatus;
	}

	public void setDestributionStatus(String destributionStatus) {
		this.destributionStatus = destributionStatus;
	}

	public String getNotificationFrom() {
		return notificationFrom;
	}

	public void setNotificationFrom(String notificationFrom) {
		this.notificationFrom = notificationFrom;
	}

	public String getProduct() {
		return product;
	}

	public void setProduct(String product) {
		this.product = product;
	}

	public String getClassification() {
		return classification;
	}

	public void setClassification(String classification) {
		this.classification = classification;
	}

	public String getProductCategory() {
		return productCategory;
	}

	public void setProductCategory(String productCategory) {
		this.productCategory = productCategory;
	}

	public String getRiskDecision() {
		return riskDecision;
	}

	public void setRiskDecision(String riskDecision) {
		this.riskDecision = riskDecision;
	}

	public String getPublishingStatus() {
		return publishingStatus;
	}

	public void setPublishingStatus(String publishingStatus) {
		this.publishingStatus = publishingStatus;
	}

	public Set<String> getOriginCountries() {
		return originCountries;
	}

	public void setOriginCountries(Set<String> originCountry) {
		this.originCountries = originCountry;
	}

	public String getFirstOriginCountry() {
		return firstOriginCountry;
	}

	public void setFirstOriginCountry(String firstOriginCountry) {
		this.firstOriginCountry = firstOriginCountry;
	}

	public List<Hazard> getHazards() {
		return hazards;
	}

	public void setHazards(List<Hazard> hazards) {
		this.hazards = hazards;
	}

	public List<FollowUp> getFollowups() {
		return followups;
	}

	public void setFollowups(List<FollowUp> followups) {
		this.followups = followups;
	}

	public Set<String> getConcernedCountriesAndOrganisations() {
		return concernedCountriesAndOrganisations;
	}

	public void setConcernedCountriesAndOrganisations(Set<String> concernedCountriesAndOrganisations) {
		this.concernedCountriesAndOrganisations = concernedCountriesAndOrganisations;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((notificationDate == null) ? 0 : notificationDate.hashCode());
		result = prime * result + ((product == null) ? 0 : product.hashCode());
		result = prime * result + ((productCategory == null) ? 0 : productCategory.hashCode());
		result = prime * result + ((referenz == null) ? 0 : referenz.hashCode());
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
		RASFFMapping other = (RASFFMapping) obj;
		if (notificationDate == null) {
			if (other.notificationDate != null)
				return false;
		} else if (!notificationDate.equals(other.notificationDate))
			return false;
		if (product == null) {
			if (other.product != null)
				return false;
		} else if (!product.equals(other.product))
			return false;
		if (productCategory == null) {
			if (other.productCategory != null)
				return false;
		} else if (!productCategory.equals(other.productCategory))
			return false;
		if (referenz == null) {
			if (other.referenz != null)
				return false;
		} else if (!referenz.equals(other.referenz))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "RASFFMapping [notificationDate=" + notificationDate + ", product=" + product + ", productCategory="
				+ productCategory + ", hazards=" + hazards + ", concernedCountriesAndOrganisations="
				+ concernedCountriesAndOrganisations + "]";
	}

}
