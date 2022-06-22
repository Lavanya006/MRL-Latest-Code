package com.mt_ag.bayer.cmc.persistence.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "CMC_MRP")
@SequenceGenerator(name = "CMC_S_T", sequenceName = "CMC_S_T", allocationSize = 0, initialValue = 1)
public class MrlProduct {

    @Id
    @GeneratedValue(generator = "CMC_S_T", strategy = GenerationType.SEQUENCE)
    private Long id;
    private String productName;
    private double mrl;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public double getMrl() {
        return mrl;
    }

    public void setMrl(double mrl) {
        this.mrl = mrl;
    }

    @Override
    public String toString() {
        return "MrlProduct [id=" + id + ", productName=" + productName + ", mrl=" + mrl + "]";
    }

    public MrlProduct(Long id, String productName, double mrl) {
        super();
        this.id = id;
        this.productName = productName;
        this.mrl = mrl;
    }

    public MrlProduct() {
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        long temp;
        temp = Double.doubleToLongBits(mrl);
        result = prime * result + (int) (temp ^ (temp >>> 32));
        result = prime * result + ((productName == null) ? 0 : productName.hashCode());
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
        MrlProduct other = (MrlProduct) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        if (Double.doubleToLongBits(mrl) != Double.doubleToLongBits(other.mrl))
            return false;
        if (productName == null) {
            if (other.productName != null)
                return false;
        } else if (!productName.equals(other.productName))
            return false;
        return true;
    }

}

