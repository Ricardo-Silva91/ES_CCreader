/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tunaenterprises.es_tests;

import java.util.logging.Level;
import java.util.logging.Logger;
import selenium_tests.ES_test_Card;

/**
 *
 * @author rofler
 */
public class brain {
    
    public static void main(String[] args) {
        
            System.err.println("fda");
        ES_test_Card test_noCard =new ES_test_Card();
        try {
            System.err.println("as");
            test_noCard.setUp();
            System.err.println("setup");
            test_noCard.testES_test_Card();
            test_noCard.tearDown();
        } catch (Exception ex) {
            Logger.getLogger(brain.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
