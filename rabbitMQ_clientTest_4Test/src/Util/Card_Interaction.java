/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Util;

/**
 *
 * @author rofler
 */
public class Card_Interaction {
    
    private String interaction;
    private String roomCode;
    private String time;
    private String person_id;

    public Card_Interaction(String interaction, String roomCode, String time, String person_id) {
        this.interaction = interaction;
        this.roomCode = roomCode;
        this.time = time;
        this.person_id = person_id;
    }

    public String getInteraction() {
        return interaction;
    }

    public String getRoomCode() {
        return roomCode;
    }

    public String getTime() {
        return time;
    }

    public String getPerson_id() {
        return person_id;
    }
    
    
    
}
