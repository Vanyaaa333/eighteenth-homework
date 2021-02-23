import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.Assert;
import org.testng.annotations.*;

import java.util.List;


public class SauceDemo {

    protected static String BASE_URL = "https://www.saucedemo.com/index.html";

    WebDriver driver;


    List<WebElement> listOfProductsName;
    List<WebElement> listOfProductsCost;
    WebElement loginEl;
    WebElement passwordEl;
    WebElement bottomLogin;
    WebElement productsText;
    WebElement unsuccessfulLoginInError;
    WebElement emptyLoginInFieldsError;


    @BeforeMethod
    private void setup() {
        System.setProperty("webdriver.chrome.driver", "src/test/resources/chromedriver.exe");
        ChromeOptions option = new ChromeOptions();
        //option.setHeadless(true);
        driver = new ChromeDriver(option);
        driver.get(BASE_URL);
        loginEl = driver.findElement(By.cssSelector("input[id=\"user-name\"]"));
        passwordEl = driver.findElement(By.cssSelector("#password"));
        bottomLogin = driver.findElement(By.cssSelector("[id=\"login-button\"]"));
    }

    @Test
    public void successfulLoginIn() {
        loginEl.sendKeys("standard_user");
        passwordEl.sendKeys("secret_sauce");
        bottomLogin.click();
        productsText = driver.findElement(By.cssSelector(".product_label"));
        Assert.assertEquals(productsText.getText(), "Products", "Login failed , check pass or login");
    }

    @Test
    public void unsuccessfulLoginIn(){
        loginEl.sendKeys("qwfqwf");
        passwordEl.sendKeys("secret_sauce");
        bottomLogin.click();
        unsuccessfulLoginInError = driver.findElement(By.xpath("//h3[@data-test=\"error\"]"));
        Assert.assertEquals(unsuccessfulLoginInError.getText(),"Epic sadface: Username and password do not match any user in this service","There isn't right error or success login");
    }

    @Test
    public void emptyLoginInFields(){
        loginEl.sendKeys("");
        passwordEl.sendKeys("");
        bottomLogin.click();
        emptyLoginInFieldsError = driver.findElement(By.xpath("//h3[@data-test=\"error\"]"));
        Assert.assertEquals(emptyLoginInFieldsError.getText(),"Epic sadface: Username is required","There isn't right error or success login");
    }

    @Test
    public void getProductsList(){
        loginEl.sendKeys("standard_user");
        passwordEl.sendKeys("secret_sauce");
        bottomLogin.click();
        listOfProductsName = driver.findElements(By.cssSelector(".inventory_container .inventory_item_name"));
        listOfProductsCost = driver.findElements(By.cssSelector(".inventory_container .inventory_item_price"));
        for (int i = 0; i < 6; i++) {
                System.out.println(String.format("%s-%s",listOfProductsName.get(i).getText(),listOfProductsCost.get(i).getText()));
            }
        //Assert.assertEquals(emptyLoginInFieldsError.getText(),"Epic sadface: Username is required","There isn't right error or success login");
    }


//    @AfterMethod(alwaysRun = true)
//    private void tearDown() {
//        driver.quit();
//    }
}


