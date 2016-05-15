package artOfTesting.test;

import java.util.concurrent.TimeUnit;

import org.junit.Assert;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.By;

import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.openqa.selenium.chrome.ChromeDriver;

import Util.Database_connector_sqlite;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import static org.apache.commons.io.FilenameUtils.separatorsToSystem;
import org.json.JSONException;
import org.json.JSONObject;

public class googleCalcStepDefinition {

    protected WebDriver driver;

    static String currentCardId = "";
    static String CurentCardFirstName = "abc";
    String greet = "";

    static String RestServer_URL = "http://localhost:3390/Rester-1/rest/ES_REST_API/find_person/";

    private static String baseDirectory = System.getProperty("user.home");
    private static Database_connector_sqlite db;
    private static String databasePath = separatorsToSystem(baseDirectory + "/" + "es_module_rabbit_tests.db");

    @Before
    public void setup() {
        driver = new ChromeDriver();//FirefoxDriver();
        db = new Database_connector_sqlite();
        db.connect(databasePath);
        currentCardId = db.get_current_user();
        db.connection_close();
        
        if(currentCardId.endsWith("dummy"))
        {
            currentCardId = "1";
        }
    }

    @Given("^new event 1$")
    public void new_event() {
        
    }

    private static String getUserNameFromRest() {
        String name = "";

        try {
            URL url;
            url = new URL(RestServer_URL + currentCardId);
            System.out.println("request sent: " + url);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setDoOutput(true);
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Content-Type", "application/json");

            if (conn.getResponseCode() != 200) {
                throw new RuntimeException("Failed : HTTP error code : "
                        + conn.getResponseCode());
            }

            BufferedReader br = new BufferedReader(new InputStreamReader(
                    (conn.getInputStream())));

            String output;
            System.out.println("Output from Server .... \n");
            while ((output = br.readLine()) != null) {
                System.out.println(output);
                JSONObject jo = new JSONObject(output);
                JSONObject person_info = (JSONObject) jo.getJSONObject("person_info");
                name = person_info.getString("firstname");
            }

            conn.disconnect();

        } catch (MalformedURLException ex) {
            Logger.getLogger(googleCalcStepDefinition.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ProtocolException ex) {
            Logger.getLogger(googleCalcStepDefinition.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(googleCalcStepDefinition.class.getName()).log(Level.SEVERE, null, ex);
        } catch (JSONException ex) {
            Logger.getLogger(googleCalcStepDefinition.class.getName()).log(Level.SEVERE, null, ex);
        }

        return name;
    }


    @Given("^I open user page$")
    public void I_open_google() {
        //Set implicit wait of 10 seconds and launch google
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        driver.get("http://localhost:3390/CC_Local_Server-1/UserPage_servlet");

        WebElement calculatorTextBox = driver.findElement(By.id("greets"));
        greet = calculatorTextBox.getText();

    }
    
    @Given("^Id received is \"([^\"]*)\"$")
    public void idReceived(String expectedId)
    {
        Assert.assertEquals(currentCardId, expectedId);
        
    }
    
    @Given("^Id \"([^\"]*)\" corresponds to name \"([^\"]*)\"$")
    public void checkRestName(String usedId, String expectedName)
    {
        currentCardId = usedId;
        CurentCardFirstName = getUserNameFromRest();
        
        Assert.assertEquals(expectedName, CurentCardFirstName);
        
        
    }

/*    @When("^I enter \"([^\"]*)\" in search textbox$")
    public void I_enter_in_search_textbox(String additionTerms) throws InterruptedException {
        //Write term in google textbox

        TimeUnit.SECONDS.sleep(10);

        WebElement googleTextBox = driver.findElement(By.id("lst-ib"));
        googleTextBox.sendKeys(additionTerms);

        //Click on searchButton
        WebElement searchButton = driver.findElement(By.name("btnK"));
        searchButton.click();
    }*/

    
    @Given("^I get user info$")
    public void get_user_info() {

        String newCard = currentCardId;
        while (newCard.equals(currentCardId)) {
            db.connect(databasePath);
            newCard = db.get_current_user();
            db.connection_close();
        }
        currentCardId = newCard;
        
                
        
        CurentCardFirstName = getUserNameFromRest();

    }
    
    
    @Then("^I should get name \"([^\"]*)\" in header$")
    public void I_should_get_correct_result(String expectedName) {
        //Get result from calculator

        
        if (CurentCardFirstName.equals("dummy")) {
            Assert.assertEquals(greet, "Sorry, but there is no card in the reader");

        } else {
            Assert.assertEquals(greet, "Hello, " + expectedName + " please log in");

        }

        /*WebElement calculatorTextBox = driver.findElement(By.id("greets"));
		String result = calculatorTextBox.getText();
				
		//Verify that result of 2+2 is 4
		Assert.assertEquals(result, expectedResult);
         */
        driver.close();
    }
    
    @Then("^I should get right result$")
    public void I_should_get_correct_result() {
        //Get result from calculator

        System.err.println("Looking for: " + CurentCardFirstName);
        
        if (CurentCardFirstName.equals("dummy")) {
            Assert.assertEquals(greet, "Sorry, but there is no card in the reader");

        } else {
            Assert.assertEquals(greet, "Hello, " + CurentCardFirstName + " please log in");

        }

        /*WebElement calculatorTextBox = driver.findElement(By.id("greets"));
		String result = calculatorTextBox.getText();
				
		//Verify that result of 2+2 is 4
		Assert.assertEquals(result, expectedResult);
         */
        driver.close();
    }

    @After
    public void closeBrowser() {
        driver.quit();
    }

}
