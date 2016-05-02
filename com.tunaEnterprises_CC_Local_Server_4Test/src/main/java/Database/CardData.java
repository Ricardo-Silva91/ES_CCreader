package Database;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import org.json.*;

/**
 * Class representing the data in the portuguese citizen card
 *
 * @author Ricardo
 */
public class CardData {

    /**
     * first name of the card's owner
     */
    private String firstname;

    /**
     * last name of the card's owner
     */
    private String lastname;

    /**
     * birth date of the card's owner
     */
    private String birthDate;

    /**
     * height of the card's owner
     */
    private String height;

    /**
     * sex of the card's owner
     */
    private String sex;

    /**
     * first name of the card's owner's father
     */
    private String firstnameFather;

    /**
     * last name of the card's owner's father
     */
    private String lastnameFather;

    /**
     * first name of the card's owner's mother
     */
    private String firstnameMother;

    /**
     * last name of the card's owner's mother
     */
    private String lastnameMother;

    /**
     * BI number of the card
     */
    private String numBI;

    /**
     * NIF number of the card
     */
    private String numNIF;

    /**
     * SNS number of the card
     */
    private String numSNS;

    /**
     * SS number of the card
     */
    private String numSS;

    /**
     * official number of the card
     */
    private String cardNumber;

    /**
     * official PAN number of the card
     */
    private String cardNumberPAN;

    /**
     * card version
     */
    private String cardVersion;

    /**
     * nationality of the card's owner
     */
    private String nationality;

    /**
     * country of the card's owner
     */
    private String country;

    /**
     * document type
     */
    private String documentType;

    /**
     * delivery date of the card
     */
    private String deliveryDate;

    /**
     * delivery entity of the card
     */
    private String deliveryEntity;

    /**
     * locale of the card
     */
    private String locale;

    /**
     * mrz1 of the card
     */
    private String mrz1;

    /**
     * mrz2 of the card
     */
    private String mrz2;

    /**
     * mrz3 of the card
     */
    private String mrz3;

    /**
     * notes saved on the card
     */
    private String notes;

    /**
     * internal password (not actually in the card, for use in this specific
     * system)
     */
    private String authentication;

    /**
     * Class constructor
     *
     * @param firstname first name of the card's owner
     * @param lastname last name of the card's owner
     * @param birthDate birth date of the card's owner
     * @param height height of the card's owner
     * @param sex sex of the card's owner
     * @param firstnameFather first name of the card's owner's father
     * @param lastnameFather last name of the card's owner's father
     * @param firstnameMother first name of the card's owner's mother
     * @param lastnameMother last name of the card's owner's mother
     * @param numBI BI number of the card
     * @param numNIF NIF number of the card
     * @param numSNS SNS number of the card
     * @param numSS SS number of the card
     * @param cardNumber official number of the card
     * @param cardNumberPAN official PAN number of the card
     * @param cardVersion card version
     * @param nationality nationality of the card's owner
     * @param country country of the card's owner
     * @param documentType document type
     * @param deliveryDate delivery date of the card
     * @param deliveryEntity delivery entity of the card
     * @param locale locale of the card
     * @param mrz1 mrz1 of the card
     * @param mrz2 mrz2 of the card
     * @param mrz3 mrz3 of the card
     * @param notes notes saved on the card
     * @param authentication internal password
     */
    public CardData(String firstname, String lastname, String birthDate, String height, String sex, String firstnameFather, String lastnameFather, String firstnameMother, String lastnameMother, String numBI, String numNIF, String numSNS, String numSS, String cardNumber, String cardNumberPAN, String cardVersion, String nationality, String country, String documentType, String deliveryDate, String deliveryEntity, String locale, String mrz1, String mrz2, String mrz3, String notes, String authentication) {
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
        this.authentication = authentication;
    }

    /**
     * getter for first name of the card's owner
     *
     * @return first name of the card's owner
     */
    public String getFirstname() {
        return firstname;
    }

    /**
     * getter for last name of the card's owner
     *
     * @return last name of the card's owner
     */
    public String getLastname() {
        return lastname;
    }

    /**
     * getter for birth date of the card's owner
     *
     * @return birth date of the card's owner
     */
    public String getBirthDate() {
        return birthDate;
    }

    /**
     * getter for height of the card's owner
     *
     * @return height of the card's owner
     */
    public String getHeight() {
        return height;
    }

    /**
     * getter for sex of the card's owner
     *
     * @return sex of the card's owner
     */
    public String getSex() {
        return sex;
    }

    /**
     * getter for first name of the card's owner's father
     *
     * @return first name of the card's owner's father
     */
    public String getFirstnameFather() {
        return firstnameFather;
    }

    /**
     * getter for last name of the card's owner's father
     *
     * @return last name of the card's owner's father
     */
    public String getLastnameFather() {
        return lastnameFather;
    }

    /**
     * getter for first name of the card's owner's mother
     *
     * @return first name of the card's owner's mother
     */
    public String getFirstnameMother() {
        return firstnameMother;
    }

    /**
     * getter for last name of the card's owner's mother
     *
     * @return last name of the card's owner's mother
     */
    public String getLastnameMother() {
        return lastnameMother;
    }

    /**
     * getter for BI number of the card
     *
     * @return BI number of the card
     */
    public String getNumBI() {
        return numBI;
    }

    /**
     * getter for NIF number of the card
     *
     * @return NIF number of the card
     */
    public String getNumNIF() {
        return numNIF;
    }

    /**
     * getter for SNS number of the card
     *
     * @return SNS number of the card
     */
    public String getNumSNS() {
        return numSNS;
    }

    /**
     * getter for SS number of the card
     *
     * @return SS number of the card
     */
    public String getNumSS() {
        return numSS;
    }

    /**
     * getter for official number of the card
     *
     * @return official number of the card
     */
    public String getCardNumber() {
        return cardNumber;
    }

    /**
     * getter for official PAN number of the card
     *
     * @return official PAN number of the card
     */
    public String getCardNumberPAN() {
        return cardNumberPAN;
    }

    /**
     * getter for card version
     *
     * @return card version
     */
    public String getCardVersion() {
        return cardVersion;
    }

    /**
     * getter for nationality of the card's owner
     *
     * @return nationality of the card's owner
     */
    public String getNationality() {
        return nationality;
    }

    /**
     * getter for country of the card's owner
     *
     * @return country of the card's owner
     */
    public String getCountry() {
        return country;
    }

    /**
     * getter for document type
     *
     * @return document type
     */
    public String getDocumentType() {
        return documentType;
    }

    /**
     * getter for delivery date of the card
     *
     * @return delivery date of the card
     */
    public String getDeliveryDate() {
        return deliveryDate;
    }

    /**
     * getter for delivery entity of the card
     *
     * @return delivery entity of the card
     */
    public String getDeliveryEntity() {
        return deliveryEntity;
    }

    /**
     * getter for locale of the card
     *
     * @return locale of the card
     */
    public String getLocale() {
        return locale;
    }

    /**
     * getter for mrz1 of the card
     *
     * @return mrz1 of the card
     */
    public String getMrz1() {
        return mrz1;
    }

    /**
     * getter for mrz2 of the card
     *
     * @return mrz2 of the card
     */
    public String getMrz2() {
        return mrz2;
    }

    /**
     * getter for mrz3 of the card
     *
     * @return mrz3 of the card
     */
    public String getMrz3() {
        return mrz3;
    }

    /**
     * getter for notes saved on the card
     *
     * @return notes saved on the card
     */
    public String getNotes() {
        return notes;
    }

    /**
     * getter for internal password
     *
     * @return internal password
     */
    public String getAuthentication() {
        return authentication;
    }

    /**
     * method used to send card data to json file
     *
     * @param outFile path to the file to be written
     */
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

        //System.out.println(card_js);
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

    /**
     * method used to create json file with only the BI number
     *
     * @param outFile path to the file to be written
     */
    public void sendIDToJsonFile(String outFile) {
        JSONObject obj = new JSONObject();

        obj.put("numBI", this.numBI);

        //System.out.println(card_js);
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

    /**
     * method used to get json string with card data and interaction information
     *
     * @param roomCode code of the room with the reader (i.e: 4.2.11)
     * @param interactionType type of interaction (inserted/removed)
     * @return string with the resulting json
     */
    public String getJson(String roomCode, String interactionType) {
        JSONObject card_js = new JSONObject();
        JSONObject interaction_js = new JSONObject();
        JSONObject final_js = new JSONObject();

        interaction_js.put("interaction", interactionType);
        interaction_js.put("roomCode", roomCode);
        interaction_js.put("time", System.currentTimeMillis());

        card_js.put("birthDate", this.birthDate);
        card_js.put("cardNumber", this.cardNumber);
        card_js.put("cardNumberPAN", this.cardNumberPAN);
        card_js.put("cardVersion", this.cardVersion);
        card_js.put("country", this.country);
        card_js.put("deliveryDate", this.deliveryDate);
        card_js.put("deliveryEntity", this.deliveryEntity);
        card_js.put("documentType", this.documentType);
        card_js.put("firstname", this.firstname);
        card_js.put("firstnameFather", this.firstnameFather);
        card_js.put("firstnameMother", this.firstnameMother);
        card_js.put("height", this.height);
        card_js.put("lastname", this.lastname);
        card_js.put("lastnameFather", this.lastnameFather);
        card_js.put("lastnameMother", this.lastnameMother);
        card_js.put("locale", this.locale);
        card_js.put("mrz1", this.mrz1);
        card_js.put("mrz2", this.mrz2);
        card_js.put("mrz3", this.mrz3);
        card_js.put("nationality", this.nationality);
        card_js.put("notes", this.notes);
        card_js.put("numBI", this.numBI);
        card_js.put("numNIF", this.numNIF);
        card_js.put("numSNS", this.numSNS);
        card_js.put("numSS", this.numSS);
        card_js.put("sex", this.sex);

        final_js.put("person_info", card_js);
        final_js.put("interaction_info", interaction_js);

        return final_js.toString();
    }

    /**
     * method used to get json string with card data and interaction information
     *
     * @return string with the resulting json
     */
    public String getJson_brief() {
        JSONObject card_js = new JSONObject();
        JSONObject final_js = new JSONObject();


        card_js.put("birthDate", this.birthDate);
        card_js.put("cardNumber", this.cardNumber);
        card_js.put("cardNumberPAN", this.cardNumberPAN);
        card_js.put("cardVersion", this.cardVersion);
        card_js.put("country", this.country);
        card_js.put("deliveryDate", this.deliveryDate);
        card_js.put("deliveryEntity", this.deliveryEntity);
        card_js.put("documentType", this.documentType);
        card_js.put("firstname", this.firstname);
        card_js.put("firstnameFather", this.firstnameFather);
        card_js.put("firstnameMother", this.firstnameMother);
        card_js.put("height", this.height);
        card_js.put("lastname", this.lastname);
        card_js.put("lastnameFather", this.lastnameFather);
        card_js.put("lastnameMother", this.lastnameMother);
        card_js.put("locale", this.locale);
        card_js.put("mrz1", this.mrz1);
        card_js.put("mrz2", this.mrz2);
        card_js.put("mrz3", this.mrz3);
        card_js.put("nationality", this.nationality);
        card_js.put("notes", this.notes);
        card_js.put("numBI", this.numBI);
        card_js.put("numNIF", this.numNIF);
        card_js.put("numSNS", this.numSNS);
        card_js.put("numSS", this.numSS);
        card_js.put("sex", this.sex);

        final_js.put("person_info", card_js);

        return final_js.toString();
    }
    
}
