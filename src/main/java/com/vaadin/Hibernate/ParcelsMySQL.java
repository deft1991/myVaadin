package com.vaadin.Hibernate;

import org.hibernate.Session;

import java.sql.*;


public class ParcelsMySQL {

    static String sqlForCreateObject = "INSERT INTO parcels " +
            "(PN, Part_Name, PART_NUMBER, Vendor, Qty, Shipped, Receive)\n" +
            "VALUES\n" +
            "  (?, ?, ?, ?, ?, ?, ?)";

    // JDBC URL, username and password of MySQL server
    private static final String url = "jdbc:mysql://localhost:3306/javastudy";
    private static final String user = "root";
    private static final String password = "root";

    // JDBC variables for opening and managing connection
    private static Connection con;
    private static PreparedStatement stmt;
    private static ResultSet rs;

    public static void main(String[] args) {
        ParcelsEntity pen = new ParcelsEntity();
        pen.setParams("HPC Blade", "7", "CH-DAL", 64, setDateForHibernate(2016, 3, 15), setDateForHibernate(2016, 5, 20));
        ParcelsEntity auto = AllParcels.createParcel();
        auto.setParams("HPC Blade", "8", "CH-DAL", 65, setDateForHibernate(2016, 3, 5), setDateForHibernate(2016, 5, 13));
        ParcelsEntity moto = AllParcels.createParcel();
        moto.setParams("HPC Blade", "6", "CH-DAL", 63, setDateForHibernate(2016, 4, 9), setDateForHibernate(2016, 12, 31));
        ParcelsEntity tabac = AllParcels.createParcel();
        tabac.setParams("HPC Blade", "5", "CH-DAL", 64, setDateForHibernate(2016, 8, 13), setDateForHibernate(2016, 9, 22));

        Connection dbConnection = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }
        try {
            System.out.println("Hibernate tutorial");

            Session session = HibernateSessionFactory.getSessionFactory().openSession();

            session.beginTransaction();

            session.save(pen);
            session.flush();
            session.beginTransaction().commit();
            session.clear();
            session.save(tabac);
            session.flush();
            session.beginTransaction().commit();
            session.clear();
            session.save(auto);
            session.flush();
            session.beginTransaction().commit();
            session.clear();
            session.save(moto);
            session.flush();
            session.beginTransaction().commit();
            session.clear();
            session.close();
            System.out.println("Success");
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
//                rs.close();
//                stmt.close();
//                con.close();
        }
    }


    public static java.util.Date setDateForHibernate(int year, int mounth, int day) {
        return new java.util.Date(year - 1900, mounth - 1, day);
    }
}
