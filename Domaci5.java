import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import static org.openqa.selenium.Keys.SPACE;

public class Domaci5 {

    //Otici na sajt Herkouapp(https://the-internet.herokuapp.com/)
    // i napisati sto vise test case-eva (Vas izbor sta cete testirati).

    WebDriver driver;
    WebDriverWait wait;
    String validUsername, validPassword, loggedInURL;


    @BeforeClass
    public void setUp(){
        WebDriverManager.chromedriver().setup();
        validUsername = "tomsmith";
        validPassword = "SuperSecretPassword!";
        loggedInURL = "https://the-internet.herokuapp.com/";
    }
    @BeforeMethod
    public void pageSetUp(){
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
        driver.get(loggedInURL);
    }
    @Test(priority = 10)
    public void addRemoveElements() {
        WebElement addRemoveElementsBtn = driver.findElement(By.linkText("Add/Remove Elements"));
        addRemoveElementsBtn.click();
        WebElement addElementButton = driver.findElement(By.cssSelector("button[onclick='addElement()']"));
        addElementButton.click();

        WebElement deleteElementButton = driver.findElement(By.cssSelector("button[onclick='deleteElement()']"));
        deleteElementButton.click();

        Assert.assertEquals(driver.getCurrentUrl(), "https://the-internet.herokuapp.com/add_remove_elements/");

        boolean isButtonPresent = false;

        try{
            WebElement deleteElementButton1 = driver.findElement(By.cssSelector("button[onclick='deleteElement()']"));
            isButtonPresent = deleteElementButton1.isDisplayed();
        }catch (Exception e){
            System.out.println(e);
        }
        Assert.assertFalse(isButtonPresent);
    }
    @Test(priority = 20)
    public void checkBoxes() {
        WebElement checkBoxBtn = driver.findElement(By.linkText("Checkboxes"));
        checkBoxBtn.click();
        List<WebElement> checkBox = driver.findElements(By.cssSelector("input[type = 'checkbox']"));
        for (WebElement element : checkBox){
            if(element.getText().equals(" checkbox 1"));
            element.click();
            break;
        }
        Assert.assertNotEquals(driver.getCurrentUrl(),  loggedInURL );
    }
    //----------------------------------------------
    @Test(priority = 30)
    public void contextMenu()  {

        WebElement contextMenuBtn = driver.findElement(By.linkText("Context Menu"));
        contextMenuBtn.click();
        Assert.assertEquals(driver.getCurrentUrl(), "https://the-internet.herokuapp.com/context_menu");

        WebElement hotSpot = driver.findElement(By.id("hot-spot"));
        Actions actions = new Actions(driver);
        actions.contextClick(hotSpot).perform();

        Alert promp = driver.switchTo().alert();
        String actualText = promp.getText();
        Assert.assertEquals(actualText,"You selected a context menu");

        driver.switchTo().alert().accept();
    }

    @Test(priority = 40)
    public void dropdown (){
        WebElement dropdownBtn = driver.findElement(By.linkText("Dropdown"));
        dropdownBtn .click();

        Assert.assertEquals(driver.getCurrentUrl(), "https://the-internet.herokuapp.com/dropdown");

        WebElement text = driver.findElement(By.cssSelector("option[disabled = 'disabled']"));
        Assert.assertTrue(text.isDisplayed());

        WebElement optionsField = driver.findElement(By.id("dropdown"));
        optionsField.click();

        WebElement option1 = driver.findElement(By.cssSelector("option[value = '1']"));
        option1.click();

        String actual = option1.getText();
        String expected = "Option 1";
        Assert.assertEquals(actual,expected);
    }
    @Test(priority = 50)
    public void entryAd()  {
        WebElement entryAdBtn = driver.findElement(By.linkText("Entry Ad"));
        entryAdBtn.click();

        Assert.assertEquals(driver.getCurrentUrl(),"https://the-internet.herokuapp.com/entry_ad");

        wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.className("modal-footer"))));

        WebElement modalWindowCloseBtn = driver.findElement(By.className("modal-footer"));
        Assert.assertTrue(modalWindowCloseBtn.isDisplayed());

        modalWindowCloseBtn.click();

        boolean isCloseButtonPresent = false;
        try{
            WebElement modalWindowAfter = driver.findElement(By.className("modal-footer"));
            isCloseButtonPresent = modalWindowAfter.isDisplayed();
        }catch(Exception e){
            System.out.println(e);
        }
        Assert.assertFalse(isCloseButtonPresent);

        WebElement reEnableAd = driver.findElement(By.linkText("click here"));
        reEnableAd.click();

        wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.className("modal-footer"))));

        WebElement modalWindowCloseBtn1 = driver.findElement(By.className("modal-footer"));
        Assert.assertTrue(modalWindowCloseBtn1.isDisplayed());
    }
    @Test(priority = 60)
    public void formAuthentication(){
        WebElement formAuthenticationBtn = driver.findElement(By.linkText("Form Authentication"));
        formAuthenticationBtn.click();

        Assert.assertEquals(driver.getCurrentUrl(),"https://the-internet.herokuapp.com/login");

        WebElement usernameField = driver.findElement(By.id("username"));
        usernameField.clear();
        usernameField.sendKeys(validUsername);

        WebElement passwordField = driver.findElement(By.id("password"));
        passwordField.clear();
        passwordField.sendKeys(validPassword);

        WebElement loginBtn =driver.findElement(By.cssSelector("button[type = 'submit']"));
        loginBtn.click();

        String expectedURL = "https://the-internet.herokuapp.com/secure";
        Assert.assertEquals(driver.getCurrentUrl(),expectedURL);

        WebElement loginMessage = driver.findElement(By.id("flash-messages"));
        Assert.assertTrue(loginMessage.isDisplayed());

        WebElement logOutBtn = driver.findElement(By.linkText("Logout"));
        Assert.assertTrue(logOutBtn.isDisplayed());
    }
    @Test(priority = 70)
    public void javaScriptAlert() {
        WebElement jsAlertBtn = driver.findElement(By.linkText("JavaScript Alerts"));
        jsAlertBtn.click();

        Assert.assertEquals(driver.getCurrentUrl(), "https://the-internet.herokuapp.com/javascript_alerts");

        WebElement clickJsAlert = driver.findElement(By.cssSelector("button[onclick = 'jsPrompt()']"));
        clickJsAlert.click();

        Alert prompt = driver.switchTo().alert();
        String actualPromptText = prompt.getText();
        String expectedText = "I am a JS prompt";
        Assert.assertEquals(actualPromptText,expectedText);

        prompt.sendKeys("Ivana");
        prompt.accept();

        WebElement result = driver.findElement(By.id("result"));
        String actualresult = result.getText();
        String ecpectedText = "You entered: Ivana";
        Assert.assertEquals(actualresult,ecpectedText);
    }
    @Test(priority = 80)
    public void keyPresses(){
        WebElement keyPressesBtn= driver.findElement(By.linkText("Key Presses"));
        keyPressesBtn.click();

        Assert.assertEquals(driver.getCurrentUrl(), "https://the-internet.herokuapp.com/key_presses");

        WebElement inputField = driver.findElement(By.id("target"));
        inputField.sendKeys(SPACE);

        WebElement text = driver.findElement(By.id("result"));
        String actualText = text.getText();
        String expectedText = "You entered: SPACE";
        Assert.assertEquals(actualText,expectedText);
    }

    @Test(priority = 90)
    public void multipleWindow(){
        Set<String>windowHandlesBefore=driver.getWindowHandles();
        WebElement multipleWindowBtn= driver.findElement(By.linkText("Multiple Windows"));
        multipleWindowBtn.click();
        Assert.assertEquals(driver.getCurrentUrl(),"https://the-internet.herokuapp.com/windows");

        WebElement clickHereBtn = driver.findElement(By.linkText("Click Here"));
        clickHereBtn.click();

        Set<String>windowHandlesAfter=driver.getWindowHandles();
        Assert.assertTrue(windowHandlesAfter.size() > windowHandlesBefore.size());

        ArrayList <String> tabs = new ArrayList<>(driver.getWindowHandles());
        driver.switchTo().window(tabs.get(1));
        Assert.assertEquals(driver.getCurrentUrl(),"https://the-internet.herokuapp.com/windows/new");

        WebElement newWindowText = driver.findElement(By.className("example"));
        String actualText = newWindowText.getText();
        String ecpectedtext = "New Window";
        Assert.assertEquals(actualText,ecpectedtext);
    }

    @AfterMethod
    public void tearDown(){
        driver.quit();
    }
}
