package com.vaadin.VaadinTutorial;

import com.vaadin.Hibernate.HibernateSessionFactory;
import com.vaadin.Hibernate.ParcelsEntity;
import org.hibernate.Query;
import org.hibernate.Session;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * An in memory dummy "database" for the example purposes. In a typical Java app
 * this class would be replaced by e.g. EJB or a Spring based service class.
 * <p>
 * In demos/tutorials/examples, get a reference to this service class with
 * {@link CustomerService#getInstance()}.
 */
public class CustomerService {

    // JDBC URL, username and password of MySQL server
    private static final String url = "jdbc:mysql://localhost:3306/javastudy";
    private static final String user = "root";
    private static final String password = "root";

    // JDBC variables for opening and managing connection
    private static Connection con;
    private static PreparedStatement stmt;
    private static ResultSet rs;

    Connection dbConnection = null;


    private static CustomerService instance;
    private static final Logger LOGGER = Logger.getLogger(CustomerService.class.getName());

    private final HashMap<Integer, ParcelsEntity> contacts = new HashMap<>();
    private int nextId = 0;

    private CustomerService() {
    }

    /**
     * @return a reference to an example facade for Parcel objects.
     */
    public static CustomerService getInstance() {
        if (instance == null) {
            instance = new CustomerService();
            instance.ensureTestData();
        }
        return instance;
    }

    /**
     * @return all available Parcel objects.
     */
    public synchronized List<ParcelsEntity> findAll() {
        return findAll(null);
    }

    /**
     * Finds all Parcel's that match given filter.
     *
     * @param stringFilter filter that returned objects should match or null/empty string
     *                     if all objects should be returned.
     * @return list a Parcel objects
     */
    public synchronized List<ParcelsEntity> findAll(String stringFilter) {
        ArrayList<ParcelsEntity> arrayList = new ArrayList<>();
        for (ParcelsEntity contact : contacts.values()) {
            try {
                boolean passesFilter = (stringFilter == null || stringFilter.isEmpty())
                        || contact.toString().toLowerCase().contains(stringFilter.toLowerCase());
                if (passesFilter) {
                    arrayList.add(contact.clone());
                }
            } catch (CloneNotSupportedException ex) {
                Logger.getLogger(CustomerService.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        Collections.sort(arrayList, new Comparator<ParcelsEntity>() {

            @Override
            public int compare(ParcelsEntity o1, ParcelsEntity o2) {
                return (int) (o2.getId() - o1.getId());
            }
        });
        return arrayList;
    }

//    public synchronized List<ParcelsEntity> findAll(String stringFilter, String fieldName) {
//        ArrayList<ParcelsEntity> arrayList = new ArrayList<>();
//        boolean filtered = false;
//        for (ParcelsEntity contact : contacts.values()) {
//            filtered = isFiltered(stringFilter, fieldName, filtered, contact);
//
//            try {
//                boolean passesFilter = (stringFilter == null || stringFilter.isEmpty())
//                        || filtered;
//                if (passesFilter) {
//                    arrayList.add(contact.clone());
//                }
//            } catch (CloneNotSupportedException ex) {
//                Logger.getLogger(CustomerService.class.getName()).log(Level.SEVERE, null, ex);
//            }
//        }
//        Collections.sort(arrayList, new Comparator<ParcelsEntity>() {
//
//            @Override
//            public int compare(ParcelsEntity o1, ParcelsEntity o2) {
//                return (int) (o2.getPn() - o1.getPn());
//            }
//        });
//        return arrayList;
//    }

    public synchronized List<ParcelsEntity> findAll(String pn, String partName, String vendor, String qty, Date shippedAfter, Date  shippedBefore, Date receivedAfter, Date receivedBefore) {
        ArrayList<ParcelsEntity> arrayList = new ArrayList<>();
        for (ParcelsEntity contact : contacts.values()) {
            boolean filteredPn = String.valueOf(contact.getPn()).contains(pn);
            boolean filteredPartName = String.valueOf(contact.getPartName().toLowerCase()).contains(partName.toLowerCase());
            boolean filteredVendor = String.valueOf(contact.getVendor().toLowerCase()).contains(vendor.toLowerCase());
            boolean filteredQty = String.valueOf(contact.getQty()).contains(qty.toLowerCase());
            if (shippedAfter == null)
                shippedAfter = new Date(0,0,0);
            if (shippedBefore == null)
                shippedBefore = new Date(4000,12,30);
            boolean isShipped = shippedAfter.compareTo(contact.getShipped()) * contact.getShipped().compareTo(shippedBefore) > 0;
            if (receivedAfter == null)
                receivedAfter = new Date(0,0,0);
            if (receivedBefore == null)
                receivedBefore = new Date(4000,12,30);
            boolean isReceived = receivedAfter.compareTo(contact.getReceive()) * contact.getReceive().compareTo(receivedBefore) > 0;

            try {
                boolean passesFilter = (filteredPn && filteredPartName && filteredVendor && filteredQty && isShipped && isReceived) ||
                        (isNull(pn) && isNull(partName) && isNull(vendor) && isNull(qty));
                if (passesFilter) {
                    arrayList.add(contact.clone());
                }
            } catch (CloneNotSupportedException ex) {
                Logger.getLogger(CustomerService.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        Collections.sort(arrayList, new Comparator<ParcelsEntity>() {

            @Override
            public int compare(ParcelsEntity o1, ParcelsEntity o2) {
                return (int) (o2.getId() - o1.getId());
            }
        });
        return arrayList;
    }

//    private boolean isFiltered(String stringFilter, String fieldName, boolean filtered, ParcelsEntity contact) {
//        if (fieldName.equals("PN"))
//            filtered = String.valueOf(contact.getPn()).contains(stringFilter.toLowerCase());
//        else if (fieldName.equals("Part Name"))
//            filtered = contact.getPartName().toLowerCase().contains(stringFilter.toLowerCase());
//        else if (fieldName.equals("Vendor"))
//            filtered = contact.getVendor().toLowerCase().contains(stringFilter.toLowerCase());
//        else if (fieldName.equals("Qty"))
//            filtered = String.valueOf(contact.getQty()).contains(stringFilter.toLowerCase());
//        else if (fieldName.equals("Vendor"))
//            filtered = contact.getVendor().toLowerCase().contains(stringFilter.toLowerCase());
//
//        return filtered;
//    }

    /**
     * Finds all Parcel's that match given filter and limits the resultset.
     *
     * @param stringFilter filter that returned objects should match or null/empty string
     *                     if all objects should be returned.
     * @param start        the index of first result
     * @param maxresults   maximum result count
     * @return list a Parcel objects
     */
    public synchronized List<ParcelsEntity> findAll(String stringFilter, int start, int maxresults) {
        ArrayList<ParcelsEntity> arrayList = new ArrayList<>();
        for (ParcelsEntity contact : contacts.values()) {
            try {
                boolean passesFilter = (stringFilter == null || stringFilter.isEmpty())
                        || contact.toString().toLowerCase().contains(stringFilter.toLowerCase());
                if (passesFilter) {
                    arrayList.add(contact.clone());
                }
            } catch (CloneNotSupportedException ex) {
                Logger.getLogger(CustomerService.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        Collections.sort(arrayList, new Comparator<ParcelsEntity>() {

            @Override
            public int compare(ParcelsEntity o1, ParcelsEntity o2) {
                return (int) (o2.getId() - o1.getId());
            }
        });
        int end = start + maxresults;
        if (end > arrayList.size()) {
            end = arrayList.size();
        }
        return arrayList.subList(start, end);
    }

    /**
     * @return the amount of all customers in the system
     */
    public synchronized long count() {
        return contacts.size();
    }

    /**
     * Deletes a customer from a system
     *
     * @param value the Parcel to be deleted
     */
    public synchronized void delete(Parcel value) {
        contacts.remove(value.getId());
    }

    /**
     * Persists or updates customer in the system. Also assigns an identifier
     * for new Parcel instances.
     *
     * @param entry
     */
    public synchronized void save(ParcelsEntity entry) {
        if (entry == null) {
            LOGGER.log(Level.SEVERE,
                    "Parcel is null.");
            return;
        }
        try {
            entry = (ParcelsEntity) entry.clone();
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
        contacts.put(entry.getId(), entry);
    }

    /**
     * Sample data generation
     */
    public void ensureTestData() {
        String hql = "FROM ParcelsEntity";
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }
        try {
            System.out.println("Hibernate tutorial");

            Session session = HibernateSessionFactory.getSessionFactory().openSession();
            session.beginTransaction();
            Query query = session.createQuery(hql);
            List results = query.list();
            for (Object p : results){
                save((ParcelsEntity) p);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (stmt != null) {
                    stmt.close();
                }
                if (con != null) {
                    con.close();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
                System.err.println("Error: " + ex.getMessage());
            }
        }
    }

    public boolean isNull(String value) {
        if (value.isEmpty() || value == null || value == "") {
            return true;
        }
        return false;
    }

}
