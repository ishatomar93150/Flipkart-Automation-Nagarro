package com.flipkart.Flipkart_Automation;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class TestClass {
    protected WebDriver driver;
    protected HomePage homePage;
    protected LoginPage loginPage;
    protected Properties config;
    public ExtentReports extent;
    public ExtentTest test;
    
    public static final Logger logger = LogManager.getLogger(TestClass.class);
    
    @BeforeClass
    public void setUp() throws IOException {
        initializeExtentReports();
        initializeWebDriver();
        initializePageObjects();
    }
    
    private void initializeExtentReports() {
        ExtentHtmlReporter htmlReporter = new ExtentHtmlReporter("extentreport.html");
        extent = new ExtentReports();
        extent.attachReporter(htmlReporter);
    }
    
    private void initializeWebDriver() throws IOException {
        config = loadConfigProperties();
        String browser = config.getProperty("browser");
        
        switch(browser.toLowerCase()) {
            case "chrome":
                setupChromeDriver();
                break;
            case "edge":
                setupEdgeDriver();
                break;
            default:
                System.out.println("Unsupported browser specified in config.properties");
                return;
        }
        
        driver.manage().window().maximize();
    }
    
    private Properties loadConfigProperties() throws IOException {
        Properties properties = new Properties();
        try (InputStream input = getClass().getClassLoader().getResourceAsStream("config.properties")) {
            if (input == null) {
                System.out.println("Sorry, unable to find config.properties");
                return null;
            }
            properties.load(input);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return properties;
    }
    
    private void setupChromeDriver() {
        String chromeDriverPath = System.getProperty("user.dir") + "/Drivers/chromedriver.exe";
        System.setProperty("webdriver.chrome.driver", chromeDriverPath);

        ChromeOptions chromeOptions = new ChromeOptions();
        // chromeOptions.addArguments("--headless");
        driver = new ChromeDriver(chromeOptions);
    }
    
    private void setupEdgeDriver() {
        String edgeDriverPath = System.getProperty("user.dir") + "\\Flipkart_Automation-20240317T182522Z-001\\Flipkart_Automation\\Drivers\\edgedriver_win64\\msedgedriver.exe";
        System.setProperty("webdriver.edge.driver", edgeDriverPath);

        EdgeOptions edgeOptions = new EdgeOptions();
        // edgeOptions.addArguments("--headless");
        driver = new EdgeDriver(edgeOptions);
    }

    private void initializePageObjects() {
        homePage = new HomePage(driver);
        loginPage = new LoginPage(driver);
    }
    
    @AfterClass
    public void tearDown() {
        extent.flush();
        driver.quit();
    }
}
