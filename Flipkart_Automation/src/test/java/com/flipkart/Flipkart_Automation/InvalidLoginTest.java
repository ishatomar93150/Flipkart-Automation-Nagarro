package com.flipkart.Flipkart_Automation;

import java.io.IOException;

import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.testng.Assert;
import org.testng.SkipException;
import org.testng.annotations.Test;

public class InvalidLoginTest extends TestClass {

    @Test
    public void testLoginWithInvalidCredentials() throws IOException {
        XSSFWorkbook workbook = new XSSFWorkbook("TestRunner.xlsx");
        XSSFSheet sheet = workbook.getSheet("Sheet1");

        String excelExecutionStatus = sheet.getRow(5).getCell(1).getStringCellValue();
        logger.info("Execution Status from Excel: " + excelExecutionStatus);

        // Check if Execution Required is set to "Yes" in Excel
        if (!excelExecutionStatus.equalsIgnoreCase("Yes")) {
            throw new SkipException("Execution not required for this test case.");
        }

        // Test Case 
        String flipkartURL = config.getProperty("flipkart.url");
        driver.get(flipkartURL);
        homePage.clickLogin();
        loginPage.enterUsername(config.getProperty("invalid.username"));
        loginPage.enterPassword(config.getProperty("invalid.password"));
        loginPage.clickLogin();

        // Add assertion or validation here based on your requirements

        // For this example, just pause the execution to observe the result.
        try {
            Thread.sleep(5000); // Sleep for 5 seconds
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
