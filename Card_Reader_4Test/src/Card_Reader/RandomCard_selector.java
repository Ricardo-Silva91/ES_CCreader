/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Card_Reader;


import java.util.Random;

/**
 *
 * @author rofler
 */
public class RandomCard_selector {
    
    public static CardData getRandomCard(String daString)
    {
        Database_connector_sqlite db = new Database_connector_sqlite();
        
        Random rand = new Random();
        
        int cardId = rand.nextInt(5-2) +2;
        System.err.println(cardId);
                
        db.connect(daString);
        CardData card;
        card = db.getInfoByIID(Integer.toString(cardId));
        db.connection_close();
        
        return card;
    }
    
    
}
