package com.vaadin.Hibernate;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by deft on 10.12.2016.
 */
@Entity
@Table(name = "parcels", schema = "javastudy", catalog = "")
//@Table(name = "parcels", schema = "javastudy")
public class ParcelsEntity implements Serializable, Cloneable{

    private int id;
    private String partName;
    private String pn;
    private String vendor;
    private Integer qty;
    private Date shipped;
    private Date receive;

    @Id
    @Column(name = "ID", nullable = false)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "PART_NAME", nullable = true, length = 20)
    public String getPartName() {
        return partName;
    }

    public void setPartName(String partName) {
        this.partName = partName;
    }

    @Basic
    @Column(name = "PART_NUMBER", nullable = true, length = 10)
    public String getPn() {
        return pn;
    }

    public void setPn(String partNumber) {
        this.pn = partNumber;
    }

    @Basic
    @Column(name = "Vendor", nullable = true, length = 20)
    public String getVendor() {
        return vendor;
    }

    public void setVendor(String vendor) {
        this.vendor = vendor;
    }

    @Basic
    @Column(name = "Qty", nullable = true)
    public Integer getQty() {
        return qty;
    }

    public void setQty(Integer qty) {
        this.qty = qty;
    }

    @Temporal(TemporalType.DATE)
    @Column(name = "Shipped", nullable = true)
    public Date getShipped() {
        return shipped;
    }

    public void setShipped(Date shipped) {
        this.shipped = shipped;
    }

    @Temporal(TemporalType.DATE)
    @Column(name = "Receive", nullable = true)
    public Date getReceive() {
        return receive;
    }

    public void setReceive(Date receive) {
        this.receive = receive;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ParcelsEntity that = (ParcelsEntity) o;

        if (id != that.id) return false;
        if (partName != null ? !partName.equals(that.partName) : that.partName != null) return false;
        if (pn != null ? !pn.equals(that.pn) : that.pn != null) return false;
        if (vendor != null ? !vendor.equals(that.vendor) : that.vendor != null) return false;
        if (qty != null ? !qty.equals(that.qty) : that.qty != null) return false;
        if (shipped != null ? !shipped.equals(that.shipped) : that.shipped != null) return false;
        if (receive != null ? !receive.equals(that.receive) : that.receive != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (partName != null ? partName.hashCode() : 0);
        result = 31 * result + (pn != null ? pn.hashCode() : 0);
        result = 31 * result + (vendor != null ? vendor.hashCode() : 0);
        result = 31 * result + (qty != null ? qty.hashCode() : 0);
        result = 31 * result + (shipped != null ? shipped.hashCode() : 0);
        result = 31 * result + (receive != null ? receive.hashCode() : 0);
        return result;
    }

    public void setParams(String partName, String partNumber, String vendor, int qty, Date shipped, Date receive) {
        this.partName = partName;
        this.pn = partNumber;
        this.vendor = vendor;
        this.qty = qty;
        this.receive = receive;
        this.shipped = shipped;
    }

    @Override
    public ParcelsEntity clone() throws CloneNotSupportedException {
        return (ParcelsEntity) super.clone();
    }

//    public boolean isPersisted() {
//        return id >0;
//    }
}
