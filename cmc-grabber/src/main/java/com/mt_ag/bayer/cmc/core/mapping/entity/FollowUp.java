package com.mt_ag.bayer.cmc.core.mapping.entity;

public class FollowUp {

	private String reference;
	private String followUpFrom;
	private String date;
	private String followUpType;
	private String info;

	public FollowUp(String reference, String followUpFrom, String date, String followUpType, String info) {
		super();
		this.reference = reference;
		this.followUpFrom = followUpFrom;
		this.date = date;
		this.followUpType = followUpType;
		this.info = info;
	}

	public FollowUp() {
		super();
		this.reference = "";
		this.followUpFrom = "";
		this.date = "";
		this.followUpType = "";
		this.info = "";
	}

	public void setNextFollowUpValue(int id, String value) {

		switch (id) {
		case 0:
			this.reference = value;
			break;
		case 1:
			this.followUpFrom = value;
			break;
		case 2:
			this.followUpFrom = value;
			break;
		case 3:
			this.date = value;
			break;
		case 4:
			this.followUpType = value;
			break;
		case 5:
			this.info = value;
			break;
		default:
			break;
		}
	}

	public String getReference() {
		return reference;
	}

	public void setReference(String reference) {
		this.reference = reference;
	}

	public String getFollowUpFrom() {
		return followUpFrom;
	}

	public void setFollowUpFrom(String followUpFrom) {
		this.followUpFrom = followUpFrom;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getFollowUpType() {
		return followUpType;
	}

	public void setFollowUpType(String followUpType) {
		this.followUpType = followUpType;
	}

	public String getInfo() {
		return info;
	}

	public void setInfo(String info) {
		this.info = info;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((date == null) ? 0 : date.hashCode());
		result = prime * result + ((followUpFrom == null) ? 0 : followUpFrom.hashCode());
		result = prime * result + ((followUpType == null) ? 0 : followUpType.hashCode());
		result = prime * result + ((reference == null) ? 0 : reference.hashCode());
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
		FollowUp other = (FollowUp) obj;
		if (date == null) {
			if (other.date != null)
				return false;
		} else if (!date.equals(other.date))
			return false;
		if (followUpFrom == null) {
			if (other.followUpFrom != null)
				return false;
		} else if (!followUpFrom.equals(other.followUpFrom))
			return false;
		if (followUpType == null) {
			if (other.followUpType != null)
				return false;
		} else if (!followUpType.equals(other.followUpType))
			return false;
		if (reference == null) {
			if (other.reference != null)
				return false;
		} else if (!reference.equals(other.reference))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "FollowUp [reference=" + reference + ", followUpFrom=" + followUpFrom + ", date=" + date
				+ ", followUpType=" + followUpType + ", info=" + info + "]";
	}

}
