package selenium_tests;

import com.thoughtworks.selenium.Selenium;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.WebDriver;
import com.thoughtworks.selenium.webdriven.WebDriverBackedSelenium;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import java.util.regex.Pattern;
import static org.apache.commons.lang3.StringUtils.join;

public class ES_test_Card {
	private Selenium selenium;

	@Before
	public void setUp() throws Exception {
		WebDriver driver = new FirefoxDriver();
		String baseUrl = "http://localhost:3389/CC_Local_Server-1/";
		selenium = new WebDriverBackedSelenium(driver, baseUrl);
	}

	@Test
	public void testES_test_Card() throws Exception {
		selenium.open("/CC_Local_Server-1/");
		selenium.click("link=Got to user Page");
		selenium.waitForPageToLoad("30000");
		selenium.type("name=pass", "p");
		selenium.click("css=input[type=\"submit\"]");
		selenium.waitForPageToLoad("30000");
	}

	@After
	public void tearDown() throws Exception {
		selenium.stop();
	}
}
