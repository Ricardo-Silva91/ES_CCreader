/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Card_Reader;

/**
 *
 * @author rofler
 */
public class testdb {
    public static void main(String[] args) {
        Database_connector db = new Database_connector("jdbc:mysql://localhost:3306/es_module", "root", "");
        db.connect();
        System.out.println(db.interaction_exists(""));
        db.connection_close();
        
    }
}
