package com.eurowings.qa.pages;

import com.eurowings.qa.base.TestBase;
import com.eurowings.qa.helper.WaitHelper;
import com.eurowings.qa.logger.loggerHelper;
import org.apache.log4j.Logger;
import org.apache.poi.util.SystemOutLogger;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


public class Flightstatus extends TestBase {
    WaitHelper waitHelper;
    private Logger log = loggerHelper.getLogger(WaitHelper.class);
    @FindBy(xpath = "//textarea[@id='about']")
    WebElement bio;

    @FindBy(xpath = " //button[@type='submit']")
    WebElement save;

    @FindBy(xpath = "//button[contains(@class,'accept cookie-consent')]")
    WebElement acceptcookies;

    @FindBy(xpath = "//input[@id='originName-622380646']")
    WebElement departureInputbox;

    @FindBy(xpath = "//input[@id='destinationName1905861146']")
    WebElement destinationInputbox;

    @FindBy(xpath = "(//div[@class='grid grid-4']//select[contains(@id,'flight_search_day')])[1]")
    WebElement selectdate;

    @FindBy(xpath = "//div[@class='tab-content active']//button[contains(@class,'defaultCompactSearchSubmit')]")
    WebElement showstatusbtn;

    @FindBy(xpath = "//button[@class='cv-myew-flyout__close-btn']")
    WebElement crossbtn;

    @FindBy(xpath = "//table[@class='widget-toggle-table']")
    WebElement successHeader;

    @FindBy(xpath = "//table[@class='widget-toggle-table']//td[@class='date-head']")
    WebElement getDate;

    @FindBy(xpath = "//th[@class='a11y-th time']")
    WebElement deptime;

    @FindBy(xpath = "//td[@class='time']//span[@class='nowrap']")
    WebElement arrivalTime;

    @FindBy(xpath = "//td[@class='number']")
    WebElement flightnumnber;

    @FindBy(xpath = "//td[@class='status']")
    WebElement flightstatus;

    @FindBy(xpath = " //div[contains(@id,'form_result_id')]//p[@class='refresh-date']")
    WebElement Lastupdated;

    @FindBy(xpath = "//table[@class='widget-toggle-table']")
    List<WebElement> flightstatusresult;

    @FindBy(xpath = "//div[@class='tab-content active']//div[contains(@id,'form_result_id')]")
    WebElement Noresultfound;

    @FindBy(xpath = " //div[@class='reinitContent']//button[@class='btn-refresh']")
    WebElement updatestatus;




    public Flightstatus(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
        waitHelper = new WaitHelper(driver);
        log.info("initializing Account Page");
    }

    public void departureInput(String dept) {
        waitHelper.waitForElement(departureInputbox, 30);
        waitHelper.waitABit(300);
        departureInputbox.clear();
        //departureInputbox.click();
        departureInputbox.sendKeys(dept);
        departureInputbox.sendKeys(Keys.TAB);
    }

    public void destinationInput(String dest) {
        waitHelper.waitForElement(destinationInputbox, 30);
        waitHelper.waitABit(300);
        destinationInputbox.clear();
        //destinationInputbox.click();
        destinationInputbox.sendKeys(dest);
        destinationInputbox.sendKeys(Keys.TAB);
    }

    public void showflightstatusBtn_click() {
        waitHelper.waitForElement(showstatusbtn, 30);
        showstatusbtn.click();

    }


    public void crossbtn_click() {
        waitHelper.waitForElement(crossbtn, 30);
        crossbtn.click();

    }

    public void updateStatus_click() {
        waitHelper.waitForElement(updatestatus, 30);
        updatestatus.click();

    }

    public void selectday(String dateval) {
        WebElement dd = driver.findElement(By.xpath("(//div[@class='grid grid-4']//select[contains(@id,'flight_search_day')])[1]"));
        Select slct = new Select(dd);
        List<WebElement> op = slct.getOptions();
        for (int i = 0; i < slct.getOptions().size(); i++) {
            String options = op.get(i).getText();
            if (options.trim().equals(dateval)) {
                op.get(i).click();
                break;
            }

        }

    }


    public String getcurrentdate() throws ParseException {
        Date d = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        return sdf.format(d);
    }


    public void acceptCookies() {

        waitHelper.waitForElement(acceptcookies, 30);
        acceptcookies.click();

    }


    public boolean showflightstatus() throws ParseException {
        waitHelper.waitForElement(showstatusbtn, 30);
        showstatusbtn.click();
        boolean verifyUpdateStatus = false;
        if (flightstatusresult.size() > 0) {
            waitHelper.waitABit(300);
            log.info("Flight details are displayed ");
            String getstatus = flightstatus.getText();
            String getdate = getDate.getText();
            Select comboBox = new Select(selectdate);
            String travelDay = comboBox.getFirstSelectedOption().getText();

            if (travelDay.equals("Today")) {
                if ((getcurrentdate().equals(getdate))) {
                    log.info("Flight details displayed in screen is for Today and is verified");
                    verifyUpdateStatus = true;
                }
            }

            if (travelDay.equals("Tomorrow")) {
                String tomorrowDate = getFutureDate(1);
                if (tomorrowDate.equals(getdate)) {
                    log.info("Flight details displayed in screen is for Tomorrow and is verified");
                    verifyUpdateStatus = true;
                }
            }

        } else {
            if (Noresultfound.getText().contains("No results can be found")) {
                log.info("There are no results displayed in your selection");
                verifyUpdateStatus = true;
            }

        }
        return verifyUpdateStatus;
    }

    public boolean verifyUpdateStatus() throws ParseException {
        boolean verifydatetimeformat=false;
        String lateststatus=Lastupdated.getText();
        String currentdate= getcurrentdate();
        String currenttime=getTime();
        log.info(" date format from application"+lateststatus);
        log.info("current date "+currentdate);
        log.info("current time "+currenttime);
        if(lateststatus.contains(currentdate) && lateststatus.contains(currenttime))
        {
            verifydatetimeformat=true;
            log.info("==============Date and time is verified");
        }

        return  verifydatetimeformat;
    }

    public String  getTime() {
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("h:mm a");
        return sdf.format(cal.getTime());
    }




    public static String getFutureDate(int noOfdaysToadd) throws ParseException {
        Date predate = new Date();
        //Specifying date format that matches the given date
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        Calendar c = Calendar.getInstance();
        //Setting the date to the given date
        c.setTime((predate));
        //Number of Days to add
        c.add(Calendar.DAY_OF_MONTH, noOfdaysToadd);
        //Date after adding the days to the given date
        String newDate = sdf.format(c.getTime());
        return newDate;
    }

}
