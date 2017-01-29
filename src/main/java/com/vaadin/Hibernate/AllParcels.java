package com.vaadin.Hibernate;

/**
 * Created by deft on 12.11.2016.
 */
public class AllParcels extends ParcelsEntity {
    public static ParcelsEntity createParcel(){
        return new ParcelsEntity();
    }
}
