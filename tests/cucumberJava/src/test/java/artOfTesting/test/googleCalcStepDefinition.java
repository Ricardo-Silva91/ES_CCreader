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
import static org.apache.commons.io.FilenameUtils.separatorsToSystem;

public class googleCalcStepDefinition {
	
	
	protected WebDriver driver;
	
        static String currentCardId = "";
        static String CurentCardFirstName = "";
        
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
	}
		
            
        @Given("^new event 1$")
        public void new_event()
        {
            String newCard = currentCardId;
            while(newCard.equals(currentCardId))
            {
                db.connect(databasePath);
                newCard = db.get_current_user();
                db.connection_close();
            }
            currentCardId=newCard;
        }
            
	@Given("^I open user page$")
	public void I_open_google() {
		//Set implicit wait of 10 seconds and launch google
		driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
		driver.get("http://localhost:3389/CC_Local_Server-1/UserPage_servlet");
                
                WebElement calculatorTextBox = driver.findElement(By.id("greets"));
		CurentCardFirstName = calculatorTextBox.getText();
                
                
	}
	
	@When("^I enter \"([^\"]*)\" in search textbox$")
	public void I_enter_in_search_textbox(String additionTerms) throws InterruptedException {
		//Write term in google textbox
                
                TimeUnit.SECONDS.sleep(10);
                
		WebElement googleTextBox = driver.findElement(By.id("lst-ib"));
		googleTextBox.sendKeys(additionTerms);
					
		//Click on searchButton
		WebElement searchButton = driver.findElement(By.name("btnK"));
		searchButton.click();
	}
	
	@Then("^I should get right result$")
	public void I_should_get_correct_result() {
		//Get result from calculator
		
                Assert.assertEquals(CurentCardFirstName, "Hello, HUGO GONÇALO this is your first log");
                
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


