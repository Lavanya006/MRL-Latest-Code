package com.mt_ag.bayer.cmc.persistence.entity;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import java.util.Set;

@Entity
@Table(name = "CMC_MRL")
@SequenceGenerator(name = "CMC_S_Z", sequenceName = "CMC_S_Z", allocationSize = 0, initialValue = 1)
public class MRL {
    @Id
    @GeneratedValue(generator = "CMC_S_Z", strategy = GenerationType.SEQUENCE)
    private Long id;

    private String substanceName;

    @ElementCollection(fetch = FetchType.EAGER)
    @JoinTable(name = "CMC_MRL_CZR")
    private Set<MrlProduct> mrlProducts;

    public MrlProduct getproductWithname(String name) {
        return this.getMrlProducts().stream().filter(s -> s.getProductName().equalsIgnoreCase(name)).findFirst()
                .orElse(null);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSubstanceName() {
        return substanceName;
    }

    public void setSubstanceName(String substanceName) {
        this.substanceName = substanceName;
    }

    public Set<MrlProduct> getMrlProducts() {
        return mrlProducts;
    }

    public void setMrlProducts(Set<MrlProduct> mrlProducts) {
        this.mrlProducts = mrlProducts;
    }

    public MRL() {
    }

    public MRL(Long id, String substanceName, Set<MrlProduct> mrlProducts) {
        super();
        this.id = id;
        this.substanceName = substanceName;
        this.mrlProducts = mrlProducts;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        result = prime * result + ((mrlProducts == null) ? 0 : mrlProducts.hashCode());
        result = prime * result + ((substanceName == null) ? 0 : substanceName.hashCode());
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
        MRL other = (MRL) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        if (mrlProducts == null) {
            if (other.mrlProducts != null)
                return false;
        } else if (!mrlProducts.equals(other.mrlProducts))
            return false;
        if (substanceName == null) {
            if (other.substanceName != null)
                return false;
        } else if (!substanceName.equals(other.substanceName))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "MRL[id=" + id + ", substanceName=" + substanceName + ", mrlProducts=" + mrlProducts + "]";
    }

}
