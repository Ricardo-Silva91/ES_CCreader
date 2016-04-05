/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Card_Reader;

import java.sql.Array;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 *
 * @author rofler
 */
public class Database_connector_sqlite {
    
    private static Connection con;

    public Database_connector_sqlite() {
        try {
            Class.forName("org.sqlite.JDBC");
            con = null;

        } catch (Exception err) {
            System.err.println(err);
        }
    }
    
    /**
     * Create con to database.
     *
     * @param PathToDB path to the sqlite database file
     */
    public void connect(String PathToDB) { // create a database con
        try {
            con = DriverManager.getConnection("jdbc:sqlite:" + PathToDB);
            Statement statement = con.createStatement();
            statement.setQueryTimeout(30);  // set timeout to 30 sec.
        } catch (Exception Err) {

        }
    }
    
    
    public int person_exists(String id) {

        int res = 0;

        try {
            PreparedStatement pst = con.prepareStatement("SELECT count(*) from person where numBI =?");
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
            Logger.getLogger(Database_connector_mysql.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(Database_connector_mysql.class.getName()).log(Level.SEVERE, null, ex);
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
    
    public String get_current_user() {

        String res = null;

        try {
            PreparedStatement pst = con.prepareStatement("SELECT * from current_card");

            ResultSet rs = pst.executeQuery();

            if (rs.next()) {
                res = rs.getString(1);
            }

        } catch (SQLException ex) {
            //Logger.getLogger(Database_connector_mysql.class.getName()).log(Level.SEVERE, null, ex);
            res = null;
        }

        return res;
    }
    
    /**
     * Terminate con to the database.
     */
    public void connection_close() {
        try {
            con.close();
        } catch (Exception s) {
            System.err.println(s);
        }
    }

    
}
