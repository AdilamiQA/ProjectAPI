package com.automation.api.Core;


import java.io.IOException;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;

import com.automation.api.Util.ExtentReport;
import com.automation.api.Util.Helper;
import com.automation.api.Util.Logging;
import com.relevantcodes.extentreports.LogStatus;



public class BaseTest {
	
	public static String baseurl="";
	protected static Logging log;
	
	@Parameters({ "ReportName", "FlowType" })
	@BeforeSuite(alwaysRun = true)
	public void config(@Optional("Optional name Automation ") String reportname, @Optional("API Report") String flow)
			throws IOException {
		
        baseurl=Helper.propertyReader(Helper.commonFilePath, "baserurl");
        //Create the path in which we will create folder to keep html reports
		String subfolderpath=System.getProperty("user.dir")+"/htmlReports/"+com.automation.api.Util.Helper.Timestamp();
		//create sub folder
		com.automation.api.Util.Helper.CreateFolder(subfolderpath);
		
		ExtentReport.initialize(subfolderpath+"/"+"API_Automation.html");
		
		// Log path
		com.automation.api.Util.Logging.setLogPath(subfolderpath+"/"+"Training_logs.log");

		// create logging instance
		log = Logging.getInstance();

	}

	@BeforeMethod(alwaysRun = true)
	public static void LogBeforeMethod() {
		final Logging log = Logging.getInstance();

		log.info("Test case", "*********************************************************************");
	}

	@AfterMethod(alwaysRun = true)
	public void getResult(ITestResult result) {
		
		if (result.getStatus() == ITestResult.SUCCESS) {
			ExtentReport.extentlog.log(LogStatus.PASS, "Test case : "+ result.getName()+" is passed ");

		} else if (result.getStatus() == ITestResult.FAILURE) {
			ExtentReport.extentlog.log(LogStatus.FAIL, "Test case : "+ result.getName()+" is failed ");
			ExtentReport.extentlog.log(LogStatus.FAIL, "Test case is failed due to:  " + result.getThrowable());
			
		} else if (result.getStatus() == ITestResult.SKIP) {
			ExtentReport.extentlog.log(LogStatus.SKIP, "Test case is Skiped " + result.getName());
		}
		ExtentReport.extentreport.endTest(ExtentReport.extentlog);
	}

	@AfterSuite(alwaysRun = true)
	public void endReport() {
		ExtentReport.extentreport.flush();
		ExtentReport.extentreport.close();
		Logging.setinstanceNull();
	}

}

