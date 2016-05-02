/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Util;

import java.sql.Array;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
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
            //Logger.getLogger(Database_connector_mysql.class.getName()).log(Level.SEVERE, null, ex);
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
            //Logger.getLogger(Database_connector_mysql.class.getName()).log(Level.SEVERE, null, ex);
            res = -1;
        }

        return res;
    }

    public int getPersonId(String numBI) {

        int res = 0;

        try {
            PreparedStatement pst = con.prepareStatement("SELECT rowid from person where numBI = ?");
            pst.setString(1, numBI);

            ResultSet rs = pst.executeQuery();

            if (rs.next()) {
                res = rs.getInt(1);
            }

        } catch (SQLException ex) {
            //Logger.getLogger(Database_connector_mysql.class.getName()).log(Level.SEVERE, null, ex);
            System.err.println("cannot get person internal id");
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
            pst_int.setString(3, String.valueOf(System.currentTimeMillis()));
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

            //if (person_exists(numBI) == 1) {
                //insert person
                PreparedStatement pst = con.prepareStatement("UPDATE current_card SET person_id=? ;");
                pst.setString(1, numBI);

                pst.executeUpdate();

            //} else {
            //    System.out.println("This person does not exist!");
            //}

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

    public CardData get_person_info(String person_id) {

        CardData card = null;

        try {
            if (person_exists(person_id) == 1) {
                //insert person
                PreparedStatement pst = con.prepareStatement("SELECT"
                        + " firstname,"
                        + " lastname,"
                        + " birthDate,"
                        + " height,"
                        + " sex,"
                        + " firstnameFather,"
                        + " lastnameFather,"
                        + " firstnameMother,"
                        + " lastnameMother,"
                        + " numBI,"
                        + " numNIF,"
                        + " numSNS,"
                        + " numSS,"
                        + " cardNumber,"
                        + " cardNumberPAN,"
                        + " cardVersion,"
                        + " nationality,"
                        + " country,"
                        + " documentType,"
                        + " deliveryDate,"
                        + " deliveryEntity,"
                        + " locale,"
                        + " mrz1,"
                        + " mrz2,"
                        + " mrz3,"
                        + " notes,"
                        + " Authentication"
                        + " from person where numBI=?");

                pst.setString(1, person_id);

                ResultSet rs = pst.executeQuery();

                if (rs.next()) {

                    card = new CardData(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6), rs.getString(7), rs.getString(8), rs.getString(9), rs.getString(10), rs.getString(11), rs.getString(12), rs.getString(13), rs.getString(14), rs.getString(15), rs.getString(16), rs.getString(17), rs.getString(18), rs.getString(19), rs.getString(20), rs.getString(21), rs.getString(22), rs.getString(23), rs.getString(24), rs.getString(25), rs.getString(26), rs.getString(27));
                }

            } else {
                System.out.println("This person does not exist in the database");
            }

        } catch (SQLException ex) {
            //Logger.getLogger(Database_connector_mysql.class.getName()).log(Level.SEVERE, null, ex);
            card = null;
        }

        return card;
    }

    public List<Card_Interaction> get_user_interactions(String person_id) {

        List<Card_Interaction> res = new ArrayList<>();

        try {
            PreparedStatement pst = con.prepareStatement("SELECT interaction,roomCode,time from interaction where person_id=?");
            pst.setString(1, person_id);

            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
                res.add(new Card_Interaction(rs.getString(1), rs.getString(2), rs.getString(3), person_id));
            }

        } catch (SQLException ex) {
            //Logger.getLogger(Database_connector_mysql.class.getName()).log(Level.SEVERE, null, ex);
            System.err.println("Error: cannot get person's interactions");
            res = null;
        }

        return res;
    }

    public void update_auth(String person_id, String pass) {

        try {
            PreparedStatement pst = con.prepareStatement("update person set Authentication=? where numBI=?");
            pst.setString(1, person_id);
            pst.setString(2, pass);

            pst.executeUpdate();

        } catch (SQLException ex) {
            //Logger.getLogger(Database_connector_mysql.class.getName()).log(Level.SEVERE, null, ex);
            System.err.println("Error: cannot update pass");

        }
    }

    public void dump_rabbit_interaction(Rabbit_person rabbit_person) {

        try {
            // insert interaction
            PreparedStatement pst_int = con.prepareStatement("INSERT into interaction(interaction, roomCode, time, person_id) values(?,?,?,?)");

            pst_int.setString(1, rabbit_person.getInteraction());
            pst_int.setString(2, rabbit_person.getRoomCode());
            pst_int.setString(3, rabbit_person.getTime());
            pst_int.setString(4, rabbit_person.getPerson_id());

            pst_int.executeUpdate();
        } catch (SQLException ex) {
            //Logger.getLogger(Database_connector_sqlite.class.getName()).log(Level.SEVERE, null, ex);
            System.err.println("Cannot dump rabbit person");
        }

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
