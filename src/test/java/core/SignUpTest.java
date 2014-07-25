package core;

import static org.junit.Assert.*;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.json.Json;
import javax.json.stream.JsonParser;
import javax.json.stream.JsonParser.Event;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class SignUpTest {
	
	private String baseUrl = "http://learn2test.net/qa/apps/sign_up/v1/";
	private String url_location = "http://www.geoplugin.net/json.gp?";
	private String url_weather ="http://www.wunderground.com";
	private String brExpected = "Firefox 30";
	private String titleExpectedFacebook = "Welcome to Facebook - Log In, Sign Up or Learn More";
	private String facebookIdImg = "id_img_facebook";
	private String titleTwitterExpected = "Twitter";
	private String twitterIdImg = "id_img_twitter";
	private String titleFlickrExpected = "Welcome to Flickr - Photo Sharing";
	private String flickrIdImg = "id_img_flickr";
	private String titleYoutubeExpected = "YouTube";
	private String youtubeIdImg = "id_img_youtube";
	private String titleConformPage = "Conformation";
	private String fName = "Alex";
	private String lName = "Moore";
	private String email = "alexmoore@gmail.com";
	private String phone = "415 555-1212";
	private String fNameEmpty = "";
	private String lNameEmpty = "";
	private String emailEmpty = "";
	private String phoneEmpty = "";
	String errorFnameExpected = "Please enter First Name";
	String errorlNameExpected = "Please enter Last Name";
	String errorEmailExpected = "Please enter Email Address";
	String errorPhoneExpected = "Please enter Phone Number";
	String gender = "male";
	String state = "California";
	Boolean terms = true;
	
	SignUpPage sp = new SignUpPage(driver);
	static WebDriver driver = new FirefoxDriver();
	String titleSignUpExpected = "Welcome to Sign Up";

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		driver.manage().deleteAllCookies();
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		driver.quit();
	}

	@Before
	public void setUp() throws Exception {
		
	}

	@After
	public void tearDown() throws Exception {
	}
   
	//@Ignore
	@Test
	public void test_01_verify_title() {
		sp.verifyTitle(titleSignUpExpected, baseUrl);
	}
	
	//@Ignore
	@Test
	public void  test_02_verify_link_facebook() {
		sp.verifyLink(titleExpectedFacebook, baseUrl, facebookIdImg);
	}
	
	//@Ignore
	@Test
	public void   test_03_verify_link_twitter() {
		sp.verifyLink(titleTwitterExpected, baseUrl, twitterIdImg);
	}
	
	//@Ignore
	@Test
	public void   test_04_verify_link_flickr() {
		sp.verifyLink(titleFlickrExpected, baseUrl, flickrIdImg);
	}
	
	//@Ignore
	@Test
	public void   test_05_verify_link_youtube() {
		sp.verifyLink(titleYoutubeExpected, baseUrl, youtubeIdImg);
	}
	
	//@Ignore
	@Test
	public void   test_06_verify_error_handling_face_first_name() {
		sp.verify_errorHandling(fNameEmpty,lName, email, phone, errorFnameExpected,baseUrl);
			
	}
	
	//@Ignore
	@Test
	public void    test_07_verify_error_handling_face_last_name() {
		sp.verify_errorHandling(fName, lNameEmpty, email, phone, errorlNameExpected,
				 baseUrl);
	}
	
	//@Ignore
	@Test
	public void    test_08_verify_error_handling_email() {
		sp.verify_errorHandling(fName, lName, emailEmpty, phone, errorEmailExpected,
				 baseUrl);
	}
	
	//@Ignore
	@Test
	public void    test_09_verify_error_handling_phone_number() {
		sp.verify_errorHandling(fName, lName, email, phoneEmpty, errorPhoneExpected, 
				 baseUrl);
	}
	
	//@Ignore
	@Test
	public void   test_10_verify_submit_form() {
		sp.submitForm( fName, lName, email, phone, gender,  state,  terms,  baseUrl, titleConformPage);
		
	}
	
	
	//@Ignore
	@Test
	public void    test_11_verify_content_quotes() {
		String firstQuote = sp.verifyContent("id_quotes", baseUrl);
		driver.navigate().refresh();
		String secondQuote = sp.verifyContent("id_quotes", baseUrl);
		assertNotEquals(firstQuote, secondQuote);
	}
	
	//@Ignore
	@Test
	public void    test_12_verify_content_current_city() throws IOException {
		
		URL url_loc = new URL(url_location);
		InputStream is = url_loc.openStream();
		JsonParser p = Json.createParser(is);
		String city = null;
		String state = null;
		while(p.hasNext()){
			Event e = p.next();
			if(e == Event.KEY_NAME){
				switch(p.getString()){
				case "geoplugin_city":
					p.next();
					city = p.getString();
					break;
				case "geoplugin_region":
					p.next();
					state = p.getString();
					break;
				}

			}
		}
		
		String cityStateExpected = city + ", " + state;
		String cityStateActual = sp.verifyContent( "id_current_location", baseUrl);
		assertEquals(cityStateExpected, cityStateActual);
	}
	
	//@Ignore
	@Test
	public void    test_13_verify_content_current_weather() throws IOException {
		driver.get(url_weather);
		String expectedTemp = driver.findElement(By.xpath("/html/body/div[1]/div/section"
				+ "/div[1]/div[2]/div[3]/div/span/span[1]")).getText();
		String f = driver.findElement(By.xpath("/html/body/div[1]/div/section/div[1]/"
				+ "div[2]/div[3]/div/span/span[2]")).getText();
		
		String actualTemp = sp.verifyContent("id_temperature", baseUrl);
		assertEquals(expectedTemp + " " + f, actualTemp);
		
	}
	
	//@Ignore
	@Test
	public void   test_14_verify_content_date() {
		DateFormat dateFormat = new SimpleDateFormat("MMMMM dd, YYYY ");
		Calendar cal = Calendar.getInstance();
		String currentDate = dateFormat.format(cal.getTime());
		
		String actualDate = sp.verifyContent("timestamp", baseUrl);
		
		assertEquals(currentDate, actualDate);
		
	}
	
	//@Ignore
	@Test
	public void   test_15_verify_content_os() {
		String os = System.getProperty("os.name") + " & " + brExpected;
				
		String actualOs = sp.verifyContent("os_browser", baseUrl);

		assertEquals( os, actualOs);
	}
	
	//@Ignore
	@Test
	public void    test_16_verify_content_browser() {
		String os = System.getProperty("os.name");
		String osBrExpected = os + " & " + brExpected;
		String osBrActual = sp.verifyContent("os_browser", baseUrl);
		assertEquals(osBrExpected, osBrActual);
	}
	

}
