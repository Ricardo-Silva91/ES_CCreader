/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Card_Reader;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.*;

/**
 *
 * @author Ricardo
 */
public class CardData {

    private String firstname;
    private String lastname;
    private String birthDate;
    private String height;
    private String sex;
    private String firstnameFather;
    private String lastnameFather;
    private String firstnameMother;
    private String lastnameMother;
    private String numBI;
    private String numNIF;
    private String numSNS;
    private String numSS;
    private String cardNumber;
    private String cardNumberPAN;
    private String cardVersion;
    private String nationality;
    private String country;
    private String documentType;
    private String deliveryDate;
    private String deliveryEntity;
    private String locale;
    private String mrz1;
    private String mrz2;
    private String mrz3;
    private String notes;

    public CardData(String firstname, String lastname, String birthDate, String height, String sex, String firstnameFather, String lastnameFather, String firstnameMother, String lastnameMother, String numBI, String numNIF, String numSNS, String numSS, String cardNumber, String cardNumberPAN, String cardVersion, String nationality, String country, String documentType, String deliveryDate, String deliveryEntity, String locale, String mrz1, String mrz2, String mrz3, String notes) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.birthDate = birthDate;
        this.height = height;
        this.sex = sex;
        this.firstnameFather = firstnameFather;
        this.lastnameFather = lastnameFather;
        this.firstnameMother = firstnameMother;
        this.lastnameMother = lastnameMother;
        this.numBI = numBI;
        this.numNIF = numNIF;
        this.numSNS = numSNS;
        this.numSS = numSS;
        this.cardNumber = cardNumber;
        this.cardNumberPAN = cardNumberPAN;
        this.cardVersion = cardVersion;
        this.nationality = nationality;
        this.country = country;
        this.documentType = documentType;
        this.deliveryDate = deliveryDate;
        this.deliveryEntity = deliveryEntity;
        this.locale = locale;
        this.mrz1 = mrz1;
        this.mrz2 = mrz2;
        this.mrz3 = mrz3;
        this.notes = notes;
    }

    public String getFirstname() {
        return firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public String getHeight() {
        return height;
    }

    public String getSex() {
        return sex;
    }

    public String getFirstnameFather() {
        return firstnameFather;
    }

    public String getLastnameFather() {
        return lastnameFather;
    }

    public String getFirstnameMother() {
        return firstnameMother;
    }

    public String getLastnameMother() {
        return lastnameMother;
    }

    public String getNumBI() {
        return numBI;
    }

    public String getNumNIF() {
        return numNIF;
    }

    public String getNumSNS() {
        return numSNS;
    }

    public String getNumSS() {
        return numSS;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public String getCardNumberPAN() {
        return cardNumberPAN;
    }

    public String getCardVersion() {
        return cardVersion;
    }

    public String getNationality() {
        return nationality;
    }

    public String getCountry() {
        return country;
    }

    public String getDocumentType() {
        return documentType;
    }

    public String getDeliveryDate() {
        return deliveryDate;
    }

    public String getDeliveryEntity() {
        return deliveryEntity;
    }

    public String getLocale() {
        return locale;
    }

    public String getMrz1() {
        return mrz1;
    }

    public String getMrz2() {
        return mrz2;
    }

    public String getMrz3() {
        return mrz3;
    }

    public String getNotes() {
        return notes;
    }

    public void sendToJsonFile(String outFile) {
        JSONObject obj = new JSONObject();

        obj.put("birthDate", this.birthDate);
        obj.put("cardNumber", this.cardNumber);
        obj.put("cardNumberPAN", this.cardNumberPAN);
        obj.put("cardVersion", this.cardVersion);
        obj.put("country", this.country);
        obj.put("deliveryDate", this.deliveryDate);
        obj.put("deliveryEntity", this.deliveryEntity);
        obj.put("documentType", this.documentType);
        obj.put("firstname", this.firstname);
        obj.put("firstnameFather", this.firstnameFather);
        obj.put("firstnameMother", this.firstnameMother);
        obj.put("height", this.height);
        obj.put("lastname", this.lastname);
        obj.put("lastnameFather", this.lastnameFather);
        obj.put("lastnameMother", this.lastnameMother);
        obj.put("locale", this.locale);
        obj.put("mrz1", this.mrz1);
        obj.put("mrz2", this.mrz2);
        obj.put("mrz3", this.mrz3);
        obj.put("nationality", this.nationality);
        obj.put("notes", this.notes);
        obj.put("numBI", this.numBI);
        obj.put("numNIF", this.numNIF);
        obj.put("numSNS", this.numSNS);
        obj.put("numSS", this.numSS);
        obj.put("sex", this.sex);

        //System.out.println(obj);
        try {
            File fout = new File(outFile);
            FileOutputStream fouts = new FileOutputStream(fout);
            fouts.write(obj.toString().getBytes());
            fouts.close();
            
        } catch (FileNotFoundException ex) {
            System.err.println("Error: File not Found!");
            ex.printStackTrace();
            //Logger.getLogger(CardData.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            System.err.println("Error: can't wirte to file!");
            ex.printStackTrace();
            //Logger.getLogger(CardData.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
    
    
    public String getJson(String roomCode, String interactionType) {
        JSONObject obj = new JSONObject();

        obj.put("interaction", interactionType);
        obj.put("roomCode", roomCode);
        obj.put("birthDate", this.birthDate);
        obj.put("cardNumber", this.cardNumber);
        obj.put("cardNumberPAN", this.cardNumberPAN);
        obj.put("cardVersion", this.cardVersion);
        obj.put("country", this.country);
        obj.put("deliveryDate", this.deliveryDate);
        obj.put("deliveryEntity", this.deliveryEntity);
        obj.put("documentType", this.documentType);
        obj.put("firstname", this.firstname);
        obj.put("firstnameFather", this.firstnameFather);
        obj.put("firstnameMother", this.firstnameMother);
        obj.put("height", this.height);
        obj.put("lastname", this.lastname);
        obj.put("lastnameFather", this.lastnameFather);
        obj.put("lastnameMother", this.lastnameMother);
        obj.put("locale", this.locale);
        obj.put("mrz1", this.mrz1);
        obj.put("mrz2", this.mrz2);
        obj.put("mrz3", this.mrz3);
        obj.put("nationality", this.nationality);
        obj.put("notes", this.notes);
        obj.put("numBI", this.numBI);
        obj.put("numNIF", this.numNIF);
        obj.put("numSNS", this.numSNS);
        obj.put("numSS", this.numSS);
        obj.put("sex", this.sex);
     
        return obj.toString();
    }

}
