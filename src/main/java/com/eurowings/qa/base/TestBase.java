package com.eurowings.qa.base;

import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.eurowings.qa.helper.WaitHelper;
import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

public class TestBase {
    /*Global Variable: prop and WebDriver can be used throughout child classes*/
    public static WebDriver driver;
    //protected WaitHelper waitHelper;
    public static Properties prop;
    public static final Logger logger = Logger.getLogger(TestBase.class.getName());
    public static String sDataSheet;
    public static String sWorkingPath;
    public static int rowCount;
    public static ExtentReports extent = new ExtentReports();
    public ExtentTest test;
    protected static final String USER_DIR = "user.dir";
    ThreadLocal<ExtentTest> extentTest = new ThreadLocal<ExtentTest>();

    public TestBase() {
        prop = new Properties();
        String configFileName = "config.properties";

        try {
            FileInputStream ip = new FileInputStream(System.getProperty(USER_DIR) + "/src/main/java/com/eurowings/qa/config/" + configFileName);
            prop.load(ip);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static void initialization() {
        String browserName = prop.getProperty("browser");
        if (browserName.equalsIgnoreCase("chrome")) {
            System.setProperty("webdriver.chrome.driver", "./drivers/chromedriver.exe");
            driver = new ChromeDriver();
            logger.info("====================Opening Chrome Browser============================================");
        } else if (browserName.equalsIgnoreCase("firefox")) {
            System.setProperty("webdriver.gecko.driver", "./drivers/geckodriver");
            driver = new FirefoxDriver();
        }


        WaitHelper wait = new WaitHelper(driver);
        wait.setImplicitWait(30, TimeUnit.SECONDS);
        wait.pageLoadTime(30, TimeUnit.SECONDS);
        /*Initialize page load timeout*/
        driver.manage().window().maximize();
        driver.manage().deleteAllCookies();
       /* driver.manage().timeouts().pageLoadTimeout(TestUtil.PAGE_LOAD_TIMEOUT, TimeUnit.SECONDS);
        driver.manage().timeouts().implicitlyWait(TestUtil.IMPLICIT_WAIT, TimeUnit.SECONDS);*/

        /*Launch URL*/
        driver.get(prop.getProperty("url"));
    }

    @BeforeTest
    public void setExtent() {
        ExtentSparkReporter reporter = new ExtentSparkReporter(System.getProperty("user.dir") + "/target/surefire-reports/Extent.html");
        reporter.config().setReportName("Automation Results");
        reporter.config().setDocumentTitle("Test Results");

        extent.attachReporter(reporter);
        extent.setSystemInfo("Tester", "Krishna");

    }


    @AfterTest
    public void endReport() {
        extent.flush();
    }


    @AfterMethod
    public void tearDown(ITestResult result) throws IOException {
        test = extent.createTest(result.getMethod().getMethodName());
        extentTest.set(test);
        if (result.getStatus() == ITestResult.FAILURE) {
            test.log(Status.FAIL, "TEST CASE FAILED IS " + result.getName()); // to add name in extent report
            test.log(Status.FAIL, "TEST CASE FAILED IS " + result.getThrowable()); // to add error/exception in extent report
            String methodName = result.getMethod().getMethodName();
            String screenshotPath = getScreenShotPath(driver);
            this.takeScreenshot(methodName, driver);
            test.addScreenCaptureFromPath(screenshotPath);// adding screen shot
        } else if (result.getStatus() == ITestResult.SKIP) {
            test.log(Status.SKIP, "Test Case SKIPPED IS " + result.getName());
        } else if (result.getStatus() == ITestResult.SUCCESS) {
            test.log(Status.PASS, "Test Case PASSED IS " + result.getName());
        }
        driver.quit();
    }

    private void takeScreenshot(String testCaseName, WebDriver webDriver) throws IOException {
        String pathname;

        File scrFile = ((TakesScreenshot) webDriver).getScreenshotAs(OutputType.FILE);

        String shopFolderName = "FailedTests_Screenshots";
        String dateFail = new SimpleDateFormat("yyyyMMdd").format(new Date());
        String timeFail = new SimpleDateFormat("HHmmss").format(new Date());

        String folderName = System.getProperty(USER_DIR) + File.separator + "screenshots" + File.separator + dateFail + File.separator + shopFolderName;
        File folder = new File(folderName);

        if (!folder.exists()) {
            folder.mkdir();
        }
        pathname = folderName + File.separator + testCaseName + "_" + timeFail + ".png";

        try {
            FileUtils.copyFile(scrFile, new File(pathname));

        } catch (IOException e) {
            logger.info("Error encountered in taking screenshot");
            logger.trace(e);
        }

    }

    private String getScreenShotPath(WebDriver webDriver) {
        return ((TakesScreenshot) webDriver).getScreenshotAs(OutputType.BASE64);
    }

}
