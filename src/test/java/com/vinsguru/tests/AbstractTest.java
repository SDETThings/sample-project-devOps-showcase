package com.vinsguru.tests;

import com.google.common.util.concurrent.Uninterruptibles;
import com.vinsguru.util.Constants;
import com.vinsguru.util.Config;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.*;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;

public abstract class AbstractTest {

    protected WebDriver driver;
    private  static  final Logger log = LoggerFactory.getLogger(Config.class);

    @BeforeSuite()
    public void setupConfig(){
        Config.initialize();
    }
    @BeforeTest
    @Parameters({"browser"})
    public void setDriver(String browser) throws MalformedURLException {
        if(Boolean.parseBoolean(Config.get(Constants.GRID_ENABLED)))
        {
            this.driver = getRemoteDriver(browser);
        }else{
            this.driver = getLocalDriver();
        }
    }
  /*  private WebDriver getRemoteDriver(String browser) throws MalformedURLException {
        Capabilities capabilities;
        if(System.getProperty("browser").equalsIgnoreCase("Chrome")){
            capabilities = new ChromeOptions();
        }else{
            capabilities = new FirefoxOptions();
        }
        return new RemoteWebDriver(new URL("http://localhost:4444/wd/hub"),capabilities);
    }
    private WebDriver getLocalDriver(){
        WebDriverManager.chromedriver().setup();
        return new ChromeDriver();
    }*/

    /*private WebDriver getRemoteDriver(String browser) throws MalformedURLException {
        Capabilities capabilities;
        if(browser.equalsIgnoreCase("Chrome")){
            capabilities = new ChromeOptions();
        }else{
            capabilities = new FirefoxOptions();
        }
        return new RemoteWebDriver(new URL("http://localhost:4444/wd/hub"),capabilities);
    }*/
    private WebDriver getRemoteDriver(String browser) throws MalformedURLException {
        Capabilities capabilities = new ChromeOptions();
        if(Constants.FIREFOX.equalsIgnoreCase(Config.get(Constants.BROWSER))){
            capabilities = new FirefoxOptions();
        }
        String urlFormat = Config.get(Constants.GRID_URL_FORMAT);
        String hubHost = Config.get(Constants.GRID_HUB_HOST);
        String url = String.format(urlFormat,hubHost);

        return new RemoteWebDriver(new URL(url),capabilities);
    }
    private WebDriver getLocalDriver(){
        WebDriverManager.chromedriver().setup();
        return new ChromeDriver();
    }
    @AfterTest
    public void quitDriver(){
        this.driver.quit();
    }
    @AfterMethod()
    public void sleep(){
        Uninterruptibles.sleepUninterruptibly(Duration.ofSeconds(5));
    }

}
