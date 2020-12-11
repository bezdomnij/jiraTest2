package com.codecool.pages;

import com.codecool.util.WebDriverSingleton;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.concurrent.TimeUnit;


public class LoginPage {
    WebDriver driver = WebDriverSingleton.getInstance();
    WebDriverWait wait = new WebDriverWait(driver, 5);

    public LoginPage() {
        PageFactory.initElements(driver, this);
    }


    @FindBy(id = "login-form-username")
    private WebElement username;

    @FindBy(id = "login-form-password")
    private WebElement password;

//    @FindBy(id = "login-form-submit")
    @FindBy(id = "login")
    private WebElement loginButton;

    @FindBy(xpath = "//div/p[contains(text(),'Sorry, your username and password are incorrect - please try again.')]")
    private WebElement loginError;

    @FindBy(xpath = "//p[contains(text(),'Sorry, your userid is required to answer a CAPTCHA')]")
    private WebElement captcha;


    public void loginSuccessful() {
//        driver.get("https://jira.codecool.codecanvas.hu/secure/Dashboard.jspa");
//        driver.manage().window().maximize();
        wait.until(ExpectedConditions.visibilityOf(username));
        username.sendKeys(System.getenv("USERNAME"));
        password.sendKeys(System.getenv("PASSWORD"));
        loginButton.click();
    }

    public WebElement loginFailed(String reason) throws InterruptedException {
        driver.manage().window().maximize();
        if (reason.equals("wrongUsername")) {
            username.sendKeys("wrongUsername");
            password.sendKeys(System.getenv("PASSWORD"));
        } else {
            Thread.sleep(3000);
            username.sendKeys(System.getenv("USERNAME"));
            password.sendKeys("wrongPassword");
        }
        loginButton.click();
        WebDriverWait wait = new WebDriverWait(driver, 10);
        wait.until(ExpectedConditions.visibilityOf(loginError));
        return loginError;
    }

    public WebElement loginWrongPassword3Times() throws InterruptedException {
        for (int i = 0; i < 3; i++) {
            Thread.sleep(1000);
            username.sendKeys(System.getenv("USERNAME"));
            password.sendKeys("wrongPassword");
            loginButton.click();
        }
        WebDriverWait wait = new WebDriverWait(driver, 10);
        wait.until(ExpectedConditions.visibilityOf(captcha));
        return captcha;
    }
}
