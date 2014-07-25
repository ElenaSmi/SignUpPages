package core;
/**One instance of that class represents SignUp page **/

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.Select;

public class SignUpPage {
	
	WebDriver driver;
        
    /*Constructor*/
    public SignUpPage(WebDriver wd) {
        driver = wd;
    }
    
    /*This method verifies title of the page*/
	public void verifyTitle(String expectedTitle, String baseUrl){
		driver.get(baseUrl);
		driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
		
		String titleActual = driver.getTitle();
		
		assertEquals(expectedTitle, titleActual);
	}
	
	/*This method verifies that link redirected to the expected page*/
	public void  verifyLink(String titleExpected, String baseUrl, String idImg){
		driver.get(baseUrl);
		driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
		
		driver.findElement(By.id(idImg)).click();
		driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
		ArrayList<String> allTabs = new ArrayList<String>(
				driver.getWindowHandles());
		driver.switchTo().window(allTabs.get(1));
		String titleActual = driver.getTitle();
		driver.close();
		driver.switchTo().window(allTabs.get(0));
		
		assertEquals(titleExpected, titleActual);

	}
	
	/*This method verifies that errors are handled correctly when user doesn't enter or entered
	 *  wrong credentials into the field*/
	public void verify_errorHandling(String fName, String lName, String email,
			String phone, String errorExpected, String baseUrl){
		driver.get(baseUrl); 
		driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
		
		driver.findElement(By.id("id_fname")).clear();
		driver.findElement(By.id("id_fname")).sendKeys(fName);
		driver.findElement(By.id("id_lname")).clear();
		driver.findElement(By.id("id_lname")).sendKeys(lName);
		driver.findElement(By.id("id_email")).clear();
		driver.findElement(By.id("id_email")).sendKeys(email);
		driver.findElement(By.id("id_phone")).clear();
		driver.findElement(By.id("id_submit_button")).click();
						
		if(fName.matches("/^[a-zA-Z,.'-]{3,30}$/")){
			if(lName.matches("/^[a-zA-Z,.'-]{3,30}$/")){
				if(email.matches("/[a-zA-Z0-9]{2,}@([0-9a-zA-Z][-\\w]*\\.)+[a-zA-Z]{2,6}/")){
					if(phone.matches("/^\\(?[\\d]{3}\\)?[\\s-]?[\\d]{3}[\\s-]?[\\d]{4}$/")){
					}else{
						String errorActual = driver.findElement(By.id("ErrorLine")).getText();
						assertEquals(errorExpected,errorActual);
					}
				}else{
					String errorActual = driver.findElement(By.id("ErrorLine")).getText();
					assertEquals(errorExpected,errorActual);
				}
			}else{
				String errorActual = driver.findElement(By.id("ErrorLine")).getText();
				assertEquals(errorExpected,errorActual);
			}
	    }else{
			String errorActual = driver.findElement(By.id("ErrorLine")).getText();
     		assertEquals(errorExpected,errorActual);
		}

	}
	
	/*This method fills up the form with valid credentials and submits it */
	public void submitForm(String fName, String lName,String email, String phone,
			String gender, String state, Boolean terms, String baseUrl, String titleConformPage){
		driver.get(baseUrl); 
		driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
		
		driver.findElement(By.id("id_fname")).clear();
		driver.findElement(By.id("id_fname")).sendKeys(fName);
		driver.findElement(By.id("id_lname")).clear();
		driver.findElement(By.id("id_lname")).sendKeys(lName);
		driver.findElement(By.id("id_email")).clear();
		driver.findElement(By.id("id_email")).sendKeys(email);
		driver.findElement(By.id("id_phone")).clear();
		driver.findElement(By.id("id_phone")).sendKeys(phone);
		if (gender.equalsIgnoreCase("male")){
			driver.findElement(By.id("id_g_radio_01")).click();
		}else if(gender.equalsIgnoreCase("female")){
			driver.findElement(By.id("id_g_radio_02")).click();
		}
		if (terms == true){
			driver.findElement(By.id("id_checkbox")).click();
		}
		if (state.isEmpty()){
			
		}else{
			
			new Select(driver.findElement(By.id("id_state"))).selectByVisibleText(state);
		}
		driver.findElement(By.id("id_submit_button")).click();
		assertEquals(driver.getTitle(), titleConformPage);
		
		
		
		
	}
	
	
	public String verifyContent(String webElementId,String baseUrl){
		
		
		driver.get(baseUrl); 
		driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
		String actualContent = driver.findElement(By.id(webElementId)).getText();
		return actualContent;
		
	}

}
