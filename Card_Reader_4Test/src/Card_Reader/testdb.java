package Card_Reader;

import static org.apache.commons.io.FilenameUtils.separatorsToSystem;

/**
 * class used for testing purposes (not for official releases)
 *
 * @author rofler
 */
public class testdb {

    private static String baseDirectory = System.getProperty("user.home");
    private static String databasePath = separatorsToSystem(baseDirectory + "/" + "es_module_tests.db");

    /**
     * main function
     *
     * @param args
     */
    public static void main(String[] args) {

        System.out.println(RandomCard_selector.getRandomCard(databasePath).getNumBI());
        
    }
}
