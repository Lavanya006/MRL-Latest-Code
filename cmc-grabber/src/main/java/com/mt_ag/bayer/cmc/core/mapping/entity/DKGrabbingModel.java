package com.mt_ag.bayer.cmc.core.mapping.entity;

import java.time.LocalDateTime;

public class DKGrabbingModel {
    private String crop;
    private String country;
    private String substance;
    private double residueLevel;
    private double mrl;
    private LocalDateTime samplingDate;

    public DKGrabbingModel() {
    }

    public DKGrabbingModel(String crop, String country, String substance, double residueLevel, double mrl, LocalDateTime samplingDate) {
        this.crop = crop;
        this.country = country;
        this.substance = substance;
        this.residueLevel = residueLevel;
        this.mrl = mrl;
        this.samplingDate = samplingDate;
    }

    public String getCrop() {
        return crop;
    }

    public void setCrop(String crop) {
        this.crop = crop;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getSubstance() {
        return substance;
    }

    public void setSubstance(String substance) {
        this.substance = substance;
    }

    public double getResidueLevel() {
        return residueLevel;
    }

    public void setResidueLevel(double residueLevel) {
        this.residueLevel = residueLevel;
    }

    public double getMrl() {
        return mrl;
    }

    public void setMrl(double mrl) {
        this.mrl = mrl;
    }

    public LocalDateTime getSamplingDate() {
        return samplingDate;
    }

    public void setSamplingDate(LocalDateTime samplingDate) {
        this.samplingDate = samplingDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DKGrabbingModel that = (DKGrabbingModel) o;

        if (Double.compare(that.residueLevel, residueLevel) != 0) return false;
        if (Double.compare(that.mrl, mrl) != 0) return false;
        if (crop != null ? !crop.equals(that.crop) : that.crop != null) return false;
        if (country != null ? !country.equals(that.country) : that.country != null) return false;
        if (substance != null ? !substance.equals(that.substance) : that.substance != null) return false;
        return samplingDate != null ? samplingDate.equals(that.samplingDate) : that.samplingDate == null;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = crop != null ? crop.hashCode() : 0;
        result = 31 * result + (country != null ? country.hashCode() : 0);
        result = 31 * result + (substance != null ? substance.hashCode() : 0);
        temp = Double.doubleToLongBits(residueLevel);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(mrl);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + (samplingDate != null ? samplingDate.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "DKGrabbingModel{" +
                "crop='" + crop + '\'' +
                ", country='" + country + '\'' +
                ", substance='" + substance + '\'' +
                ", residueLevel=" + residueLevel +
                ", mrl=" + mrl +
                ", samplingDate=" + samplingDate +
                '}';
    }
}