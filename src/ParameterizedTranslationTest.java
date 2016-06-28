import static org.junit.Assert.*;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

@RunWith(Parameterized.class)
public class ParameterizedTranslationTest {
	private WebDriver driver;
	private String baseUrl;
	private StringBuffer verificationErrors = new StringBuffer();
	private String datum;
	
	public ParameterizedTranslationTest(String datum){
		this.datum = datum;
	}
	
	@Parameters(name = "{index}: {0}")
	public static Collection<String> generateData(){
		InputStream inputStream = ParameterizedTranslationTest.class.getClassLoader().getResourceAsStream("diagnosis_tcm_treatment_priciples.csv");

		 BufferedReader br = null;
		 String line = "";
		 String cvsSplitBy = "\t";
		 ArrayList<String> list = new ArrayList<String>();
		 try{
				br = new BufferedReader(new InputStreamReader(inputStream ));
		
				while ((line = br.readLine()) != null) {
		
			        // use comma as separator
					String[] country = line.split(cvsSplitBy);
					String name = country[0] + "-"+ country[1];
					list.add(name + ", " + country[2]);
				}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return list;
	}

 	@Before
 	public void setUp() throws Exception {
 		driver = new FirefoxDriver();
		baseUrl = "http://dev.credencys.com/";
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		driver.get("http://dev.credencys.com/" + "tcm/index.php/site/login");
		driver.findElement(By.id("LoginForm_username")).clear();
		driver.findElement(By.id("LoginForm_username")).sendKeys("Test1");
		driver.findElement(By.id("LoginForm_password")).clear();
		driver.findElement(By.id("LoginForm_password")).sendKeys("@Acb1234");
		driver.findElement(By.name("yt0")).click();
		driver.findElement(By.name("yt0")).click();
		driver.findElement(By.id("LoginForm_verificationCode")).clear();
		driver.findElement(By.id("LoginForm_verificationCode")).sendKeys("123");
		driver.findElement(By.name("yt0")).click();	  
		driver.get(baseUrl + "/tcm/index.php/formDiagnosis?p_id=4350");
		driver.findElement(By.id("next-button")).click();
		driver.findElement(By.cssSelector("#ui-accordion-accordionDgTtm-header-0 > h3")).click();
	}
	
	@Test
	public void testDiagnosisTreatment() {
		driver.findElement(By.id("ttp1")).clear();
		driver.findElement(By.id("ttp1")).sendKeys(getCode(datum));
	    driver.findElement(By.id("ttp2")).click();
	    getWhenVisible(By.id("ui-id-12"), 10);
	    driver.findElement(By.id("ttp1")).click();  		
	    WebElement menu = getWhenVisible(By.id("ui-id-11"), 10);
	    String mySelectElm = menu.getAttribute("innerText");
	    assertTrue(mySelectElm.contains(getData(datum)));
		driver.findElement(By.id("ttp1")).clear();
	}
	
	@After	
	public void tearDown() throws Exception {
		driver.quit();
		String verificationErrorString = verificationErrors.toString();
		if (!"".equals(verificationErrorString)) {
			fail(verificationErrorString);
		}
	}
	
	public WebElement getWhenVisible(By locator, int timeout) {
		WebElement element = null;
		WebDriverWait wait = new WebDriverWait(driver, timeout);
		element = wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
		return element;
	}
  
	public String getData(String data){
		int n = data.indexOf(",");		
		return data.substring(0, n-1);
	}
	
	public String getCode(String data){
		int n = data.indexOf(",");
		int end = data.length();
		return data.substring(n+2,end);
	}
}
