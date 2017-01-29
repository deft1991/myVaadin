package com.vaadin.VaadinTutorial;

import java.io.Serializable;
import java.util.Date;

@SuppressWarnings("serial")
public class Parcel implements Serializable, Cloneable {

    private Long id;
    private String partName = "";
    private String partNumber = "";
    private String vendor;
    private Long qty;
    private Date shipped;
    private Date recieved;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPartName() {
        return partName;
    }

    public void setPartName(String partName) {
        this.partName = partName;
    }

    public String getPartNumber() {
        return partNumber;
    }

    public void setPartNumber(String partNumber) {
        this.partNumber = partNumber;
    }

    public String getVendor() {
        return vendor;
    }

    public void setVendor(String vendor) {
        this.vendor = vendor;
    }

    public Long getQty() {
        return qty;
    }

    public void setQty(Long qty) {
        this.qty = qty;
    }

    public Date getShipped() {
        return shipped;
    }

    public void setShipped(Date shipped) {
        this.shipped = shipped;
    }

    public Date getRecieved() {
        return recieved;
    }

    public void setRecieved(Date recieved) {
        this.recieved = recieved;
    }

    public boolean isPersisted() {
        return id != null;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (this.id == null) {
            return false;
        }

        if (obj instanceof Parcel && obj.getClass().equals(getClass())) {
            return this.id.equals(((Parcel) obj).id);
        }

        return false;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 43 * hash + (id == null ? 0 : id.hashCode());
        return hash;
    }

    @Override
    public Parcel clone() throws CloneNotSupportedException {
        return (Parcel) super.clone();
    }

    @Override
    public String toString() {
        return partName + " " + partNumber;
    }
}
