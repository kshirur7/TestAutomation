package com.eurowings.qa.testcases;

import com.eurowings.qa.base.TestBase;
import com.eurowings.qa.helper.WaitHelper;
import com.eurowings.qa.logger.loggerHelper;
import com.eurowings.qa.pages.Flightstatus;
import org.apache.log4j.Logger;
import org.openqa.selenium.Keys;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;

import java.text.ParseException;

public class TC01VerifyFlightStatus extends TestBase {
    Flightstatus flightstatus;
    private Logger log = loggerHelper.getLogger(WaitHelper.class);


    public TC01VerifyFlightStatus() {
        super();
    }

    @BeforeMethod
    public void setup() {
        initialization();
        flightstatus = new Flightstatus(driver);

    }


    @Test(priority=1)
    public void verifyFlightSearchToday() throws InterruptedException, ParseException {
        log.info("----------------------------------" + "Starting TC01 Verify flight status for Today" + "----------------------------------");
        flightstatus.acceptCookies();
        flightstatus.crossbtn_click();
        flightstatus.departureInput("CGN");
        flightstatus.destinationInput("BER");
        flightstatus.selectday("Today");
        flightstatus.showflightstatusBtn_click();
        boolean verifyflightstatustoday = flightstatus.showflightstatus();
        Assert.assertTrue( verifyflightstatustoday, "Flights are displayed and Date displayed for Today in UI is as expected");
        log.info("----------------------------------" + "Ending TC01 Verify flight status for Today" + "----------------------------------");
    }

    @Test  (priority=2)
    public void verifyFlightSearchTomorrow() throws InterruptedException, ParseException {
        log.info("----------------------------------" + "Starting TC02 Verify flight status for Tomorrow" + "----------------------------------");
        flightstatus.acceptCookies();
        flightstatus.crossbtn_click();
        flightstatus.departureInput("CGN");
        flightstatus.destinationInput("BER");
        flightstatus.selectday("Tomorrow");
        flightstatus.showflightstatusBtn_click();
        boolean verifyflightstatustomorrow = flightstatus.showflightstatus();
        Assert.assertTrue( verifyflightstatustomorrow,"Flights are displayed and Date displayed  in UI for Tomorrow is as expected");
        log.info("----------------------------------" + "Ending TC02 Verify flight status for Tomorrow" + "----------------------------------");
    }


    @Test(priority=3)
    public void verifyUpdateStatus() throws InterruptedException, ParseException {
        log.info("----------------------------------" + "Starting TC03 Verify Date and Time Format for Last Update" + "----------------------------------");
        flightstatus.acceptCookies();
        flightstatus.crossbtn_click();
        flightstatus.departureInput("CGN");
        flightstatus.destinationInput("BER");
        flightstatus.selectday("Tomorrow");
        flightstatus.showflightstatusBtn_click();
        boolean verifyflightstatustomorrow = flightstatus.showflightstatus();
        Assert.assertTrue( verifyflightstatustomorrow,"Flights are displayed and Date displayed for Tomorrow in UI is as expected");
        flightstatus.updateStatus_click();
        boolean verifydatetimeformat=flightstatus.verifyUpdateStatus();
        Assert.assertTrue( verifydatetimeformat,"Verify Date and time format in Updatestatus DD/MM/YYYY at HH:MM AM/PM ");
        log.info("----------------------------------" + "Ending TC03 Verify Date and Time Format for Last Update" + "----------------------------------");
    }


}
