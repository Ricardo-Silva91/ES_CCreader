/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Card_Reader;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author rofler
 */
public class Database_connector {

    private static String url;
    private static String user;
    private static String pass;

    private Connection con = null;
    private Statement st = null;
    private ResultSet rs = null;

    private int connection_established = 0;

    public Database_connector(String url, String user, String pass) {
        this.url = url;
        this.user = user;
        this.pass = pass;
    }

    public int connect() {
        try {
            con = DriverManager.getConnection(url, user, pass);
            st = con.createStatement();
            rs = st.executeQuery("SELECT VERSION()");

            if (rs.next()) {
                System.out.println(rs.getString(1));
            }
        } catch (SQLException ex) {
            Logger.getLogger(Database_connector.class.getName()).log(Level.SEVERE, null, ex);
            return 1;
        }
        connection_established = 1;
        return 0;
    }

    public int connection_close() {
        try {
            if (rs != null) {
                rs.close();
            }
            if (st != null) {
                st.close();
            }
            if (con != null) {
                con.close();
            }

        } catch (SQLException ex) {
            System.err.println("database close error");
            ex.printStackTrace();
            return 1;
        }
        System.out.println("Database Connection closed");
        connection_established = 0;
        return 0;
    }

    public int person_exists(String id) {

        int res = 0;

        try {
            PreparedStatement pst = con.prepareStatement("SELECT count(*) from person where numBI = ?");
            pst.setString(1, id);

            ResultSet rs = pst.executeQuery();

            if (rs.next()) {
                int reg = rs.getInt(1);
                if (reg == 0) {
                    res = 0;
                } else {
                    res = 1;
                }
            }

        } catch (SQLException ex) {
            Logger.getLogger(Database_connector.class.getName()).log(Level.SEVERE, null, ex);
            res = -1;
        }

        return res;
    }

    public int interaction_exists(String time) {

        int res = 0;

        try {
            PreparedStatement pst = con.prepareStatement("SELECT count(*) from interaction where time = ?");
            pst.setString(1, time);

            ResultSet rs = pst.executeQuery();

            if (rs.next()) {
                int reg = rs.getInt(1);
                if (reg == 0) {
                    res = 0;
                } else {
                    res = 1;
                }
            }

        } catch (SQLException ex) {
            Logger.getLogger(Database_connector.class.getName()).log(Level.SEVERE, null, ex);
            res = -1;
        }

        return res;
    }

    public int dump_interaction(CardData card, String roomCode, String interaction) {

        int res = 0;

        try {

            if (person_exists(card.getNumBI()) == 0) {
                //insert person
                PreparedStatement pst = con.prepareStatement("INSERT into person(country, firstname, notes, documentType, cardVersion, numBI, lastnameFather, lastnameMother, numSNS, firstnameMother, locale, deliveryDate, height, numSS, sex, cardNumberPAN, firstnameFather, birthDate, mrz3, mrz2, lastname, mrz1, nationality, numNIF, cardNumber, deliveryEntity) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
                pst.setString(1, card.getCountry());
                pst.setString(2, card.getFirstname());
                pst.setString(3, card.getNotes());
                pst.setString(4, card.getDocumentType());
                pst.setString(5, card.getCardVersion());
                pst.setString(6, card.getNumBI());
                pst.setString(7, card.getLastnameFather());
                pst.setString(8, card.getLastnameMother());
                pst.setString(9, card.getNumSNS());
                pst.setString(10, card.getFirstnameMother());
                pst.setString(11, card.getLocale());
                pst.setString(12, card.getDeliveryDate());
                pst.setString(13, card.getHeight());
                pst.setString(14, card.getNumSNS());
                pst.setString(15, card.getSex());
                pst.setString(16, card.getCardNumberPAN());
                pst.setString(17, card.getFirstnameFather());
                pst.setString(18, card.getBirthDate());
                pst.setString(19, card.getMrz3());
                pst.setString(20, card.getMrz2());
                pst.setString(21, card.getLastname());
                pst.setString(22, card.getMrz1());
                pst.setString(23, card.getNationality());
                pst.setString(24, card.getNumNIF());
                pst.setString(25, card.getCardNumber());
                pst.setString(26, card.getDeliveryEntity());

                pst.executeUpdate();

            } else {
                System.out.println("This person already is in the database");
            }

            // insert interaction
            PreparedStatement pst_int = con.prepareStatement("INSERT into interaction(interaction, roomCode, time, person_id) values(?,?,?,?)");

            pst_int.setString(1, interaction);
            pst_int.setString(2, roomCode);
            pst_int.setString(3, String.valueOf(System.currentTimeMillis()) );
            pst_int.setString(4, card.getNumBI());
            
            pst_int.executeUpdate();
            
        } catch (SQLException ex) {
            System.err.println("error uploading person");
            res = -1;
        }

        return res;
    }
    
    public int update_curent_card(String numBI) {

        int res = 0;

        try {

            if (person_exists(numBI) == 1) {
                //insert person
                PreparedStatement pst = con.prepareStatement("UPDATE current_card SET person_id=? ;");
                pst.setString(1, numBI);
                
                
                pst.executeUpdate();

            } else {
                System.out.println("This person does not exist!");
            }

            
        } catch (SQLException ex) {
            System.err.println("error uploading current card");
            res = -1;
        }

        return res;
    }

}
