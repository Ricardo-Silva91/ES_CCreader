/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Card_Reader;

import java.util.List;
import java.util.ListIterator;
import javax.smartcardio.*;

/**
 *
 * @author rofler
 */
public class CC_brain {

    static int debug = 0;

    private static CardTerminal terminal;

    private static void init() {
        try {
            TerminalFactory tf = TerminalFactory.getDefault();
            CardTerminals ct = tf.terminals();
            List<CardTerminal> l = null;

            try {
                l = ct.list();
            } catch (Exception e) {
                System.out.println("Error listing Terminals: " + e.toString());
                throw e;
            }

            System.out.println("List of PC/SC Readers connected:");
            ListIterator it = l.listIterator();
            while (it.hasNext()) {
                System.out.println("Reader: " + ((CardTerminal) it.next()).getName());
            }

            // Pick up the first one
            String terminalName = l.get(0).getName();
            terminal = ct.getTerminal(terminalName);
            System.out.println("Terminal fetched: " + terminal.getName());

        } catch (CardException ex) {
            System.out.println(ex.getMessage());
            //int errorNumber = Integer.parseInt(ex.getMessage().split("Error code : -")[1]);
            System.out.println("\n\nSmartcardio Exception.");
            //Logger.getLogger(CC_brain.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void main(String[] args) {

        int flag = 0;

        CC_IO ccIO = new CC_IO();

        init();

        do {
            try {

                //wait for card to be inserted
                System.out.println("Waiting for card");
                while (terminal.isCardPresent() == false);

                //get data
                ccIO.RunAnalisys();
                
                //send data for logging
                //...
                
                //wait for card to be removed before resuming action
                System.out.println("Please remove card");
                while (terminal.isCardPresent() == true);
                

            } catch (CardException ex) {
                System.out.println(ex.getMessage());
                //int errorNumber = Integer.parseInt(ex.getMessage().split("Error code : -")[1]);
                System.out.println("\n\nSmartcardio Exception.");
                //Logger.getLogger(CC_brain.class.getName()).log(Level.SEVERE, null, ex);
            }

        } while (flag == 0);

    }

}
