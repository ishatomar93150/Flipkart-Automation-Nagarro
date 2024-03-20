package com.flipkart.Flipkart_Automation;

import java.io.IOException;

import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.testng.Assert;
import org.testng.SkipException;
import org.testng.annotations.Test;

public class RemovefromCartTest extends TestClass {

    @Test
    public void testRemoveFromCart() throws IOException {
        XSSFWorkbook workbook = new XSSFWorkbook("TestRunner.xlsx");
        XSSFSheet sheet = workbook.getSheet("Sheet1");

        String excelExecutionStatus = sheet.getRow(11).getCell(1).getStringCellValue();
        logger.info("Execution Status from Excel: " + excelExecutionStatus);

        // Check if Execution Required is set to "Yes" in Excel
        if (!excelExecutionStatus.equalsIgnoreCase("Yes")) {
            throw new SkipException("Execution not required for this test case.");
        }

        // Test Case 
        String flipkartURL = config.getProperty("flipkart.url");
        driver.get(flipkartURL);

        String productToSearch = config.getProperty("product.to.search");
        homePage.searchProduct(productToSearch);

        String productURL = config.getProperty("product.url");
        driver.get(productURL);
        homePage.addToCart();
        homePage.removeFromCart();

        Assert.assertEquals(homePage.getProperty(flipkartURL), "https://www.flipkart.com/");
        // For this example, just pause the execution to observe the result.
        try {
            Thread.sleep(5000); // Sleep for 5 seconds
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
