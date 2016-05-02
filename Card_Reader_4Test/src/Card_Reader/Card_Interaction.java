
package Card_Reader;

/**
 *  Class representing an interaction between the citizen card and the reader.
 * @author rofler
 */
public class Card_Interaction {
    /**
     * interaction type (inserted/removed)
     */
    private String interaction;
    
    /**
     * room code
     */
    private String roomCode;
    
    /**
     * time Stamp
     */
    private String time;
    
    /**
     * BI number of the card
     */
    private String person_id;

    /**
     * Class constructor
     * @param interaction interaction (inserted/removed)
     * @param roomCode DETI room id (i.e.: 4.2.11)
     * @param time timestamp for when the interaction occurred.
     * @param person_id BI number of the card
     */
    public Card_Interaction(String interaction, String roomCode, String time, String person_id) {
        this.interaction = interaction;
        this.roomCode = roomCode;
        this.time = time;
        this.person_id = person_id;
    }

    /**
     * getter for the interaction type
     * @return interaction type
     */
    public String getInteraction() {
        return interaction;
    }

    /**
     * getter for the room Code
     * @return room code
     */
    public String getRoomCode() {
        return roomCode;
    }

    /**
     * getter for the timeStamp
     * @return timeStamp (in unix time)
     */
    public String getTime() {
        return time;
    }

    /**
     * getter for the BI number
     * @return BI number
     */
    public String getPerson_id() {
        return person_id;
    }
    
    
    
}
