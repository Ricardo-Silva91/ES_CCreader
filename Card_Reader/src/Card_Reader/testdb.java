package Card_Reader;

/**
 * class used for testing purposes (not for official releases)
 * @author rofler
 */
public class testdb {
    
    /**
     * main function
     * @param args 
     */
    public static void main(String[] args) {
        Database_connector_mysql db = new Database_connector_mysql("jdbc:mysql://localhost:3306/es_module", "root", "");
        db.connect();
        System.out.println(db.interaction_exists(""));
        db.connection_close();
        
    }
}
