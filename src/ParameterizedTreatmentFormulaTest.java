import static org.junit.Assert.*;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;
/**
 * Doesn't work at the moment; Will need to fix up once it is implemented.
 * 
 * @author alexc126
 *
 */
@RunWith(Parameterized.class)
public class ParameterizedTreatmentFormulaTest {
	private static WebDriver driver;
	private static String baseUrl;
	private static StringBuffer verificationErrors = new StringBuffer();
	private String datum;
	private static String username = new AccountCred().getUserName();
	private static String password = new AccountCred().getPassword();
	private static String verification = new AccountCred().getVerificiationCode();
	private static PrintWriter file; 
	
	public ParameterizedTreatmentFormulaTest(String datum){
		this.datum = datum;
	}
	
	@Parameters(name = "{index}: {0}")
	public static Collection<String> generateData(){
		InputStream inputStream = ParameterizedTreatmentFormulaTest
				.class.getClassLoader()
				.getResourceAsStream("treatment_formula.csv");

		 BufferedReader br = null;
		 String line = "";
		 String cvsSplitBy = "\t";
		 ArrayList<String> list = new ArrayList<String>();
		 try{
				br = new BufferedReader(new InputStreamReader(inputStream ));
		
				while ((line = br.readLine()) != null) {
		
			        // use comma as separator
					String[] entry = line.split(cvsSplitBy);
					String name = entry[0] + "-"+ entry[1];
					list.add(name + ", " + entry[2]);
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

 	@BeforeClass
 	public static void setUp() throws Exception {
 		try {
			file = new PrintWriter("Treatment_Formula_Results.txt");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
 		driver = new FirefoxDriver();
		baseUrl = "http://dev.credencys.com/";
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		driver.get("http://dev.credencys.com/" + "tcm/index.php/site/login");
		driver.findElement(By.id("LoginForm_username")).clear();
		driver.findElement(By.id("LoginForm_username")).sendKeys(username);
		driver.findElement(By.id("LoginForm_password")).clear();
		driver.findElement(By.id("LoginForm_password")).sendKeys(password);
		driver.findElement(By.name("yt0")).click();
		driver.findElement(By.name("yt0")).click();
		driver.findElement(By.id("LoginForm_verificationCode")).clear();
		driver.findElement(By.id("LoginForm_verificationCode")).sendKeys(verification);
		driver.findElement(By.name("yt0")).click();	  
		driver.findElement(By.cssSelector("#yw2 > li.records-icn > a[title=\"Records\"]")).click();
	    driver.findElement(By.xpath("//div[@id='dashboard']/ul/li[2]/a/div/img")).click();
	    driver.findElement(By.cssSelector("td.text-center.rec-icn > a > img")).click();
	    driver.findElement(By.id("FormConsultation_chief_complain")).clear();
	    driver.findElement(By.id("FormConsultation_chief_complain")).sendKeys("Test");
	    driver.findElement(By.id("tcm_disease_id")).click();
	    driver.findElement(By.id("ui-id-2")).click();
	    driver.findElement(By.id("consult-done")).click();
	    driver.findElement(By.id("next-button")).click();
	    driver.findElement(By.id("dia-done")).click();
	    driver.findElement(By.linkText("TCM Formula Prescription 中医方正")).click();
	    }
	
 	// Doesn't work at the moment
	@Test
	public void testDiagnosisTreatment() {
		System.out.println("datum: " + datum);
	    driver.findElement(By.id("tff")).click();
		driver.findElement(By.id("tff")).clear();
		driver.findElement(By.id("tff")).sendKeys(getCode(datum));
	    driver.findElement(By.id("formula_comments")).click();
	    driver.findElement(By.id("formula_comments")).clear();
		driver.findElement(By.id("formula_comments")).sendKeys(datum);
	    driver.findElement(By.id("tff")).click();
	    try{
		    WebElement menu = getWhenVisible(By.id("ui-id-7"), 5);
		    String mySelectElm = menu.getAttribute("innerText");
		    System.out.println(mySelectElm);
		    if(mySelectElm.contains(getData(datum))){
		    	assertTrue("Entry present", true);
		    }
		    else{
		    	file.println(datum+"\t"+"Entry not present");
		    	assertFalse("Entry not present", true);
		    }
	    }
	    catch(TimeoutException e){
	    	file.println(datum+"\t"+"Window timeout");
	    	assertFalse("Timeout while waiting for window", true);	  	    	
	    }
    }
	
	@AfterClass	
	public static void tearDown() throws Exception {
		driver.quit();
		file.close();
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
		String s = data.substring(0,n);
		System.out.println("data: "+s);
		return s;
	}
	
	public String getCode(String data){
		int n = data.indexOf(",");		
		String s = data.substring(n+2, data.length());
		System.out.println("code: "+s);
		return s;
	}
}
