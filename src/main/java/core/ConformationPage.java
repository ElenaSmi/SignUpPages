package core;

import static org.junit.Assert.assertEquals;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class ConformationPage {
	WebDriver driver;
    
    /*Constructor*/
    public ConformationPage(WebDriver wd) {
        driver = wd;
    }
    
    public void verifyBackbutton(String buttonId, String titleSubmitPage){
    	driver.findElement(By.id(buttonId)).click();
    	assertEquals(driver.getTitle(), titleSubmitPage);
    	
    }
    
    public String verifyContent(String webElementId,String baseUrl){
    		   		
    	String actualContent = driver.findElement(By.id(webElementId)).getText();
    	return actualContent;
    		
    }

}
