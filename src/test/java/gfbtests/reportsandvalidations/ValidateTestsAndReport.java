package gfbtests.reportsandvalidations;

import com.google.gson.JsonObject;
import gfb.utils.FileHelper;
import org.apache.commons.io.FileUtils;
import org.json.JSONException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.ScreenshotException;
import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;
import org.testng.asserts.SoftAssert;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

public class ValidateTestsAndReport {

    private static SoftAssert softAssert = new SoftAssert();
    private static Map<String, String> configMan;

    public ValidateTestsAndReport(Map<String, String> configMan) {
        this.configMan = configMan;
    }

    public static void validatePOJOs(Object actualResultClass, JsonObject expectedResult) {
        SoftAssert softAssert = new SoftAssert();
        JsonObject actualResult = FileHelper.readPojoIntoJson(actualResultClass);
        softAssert.assertEquals(actualResult, expectedResult);
        softAssert.assertAll();
    }

    public static void validateJsonResponses(String expectedResult, String actualResult) throws JSONException {
        JSONAssert.assertEquals(expectedResult, actualResult, JSONCompareMode.NON_EXTENSIBLE);
    }

    public static SoftAssert uiValidation(WebDriver driver, String actualResult, String expectedResult, String testCaseName) {
        takeScreenshot(testCaseName, driver);
        softAssert.assertEquals(actualResult, expectedResult);
        return softAssert;
    }

    private static void takeScreenshot(String screenshotName, WebDriver driver) {
        screenshotName = new SimpleDateFormat("ddMMMyyyy_hhmmss").format(new Date()) + "_" + screenshotName + "_"
                + configMan.get("searchKeyword");
        screenshotName = screenshotName.replaceAll("[^a-zA-Z0-9-_]", "") + ".png";
        File imagePath = new File(configMan.get("reportingAndImagesLocation") + "screenshots/" + screenshotName);
        File srcFile = (File) ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);

        try {
            FileUtils.copyFile(srcFile, new File(configMan.get("reportingAndImagesLocation") + screenshotName));
        } catch (ScreenshotException | IOException e) {
            System.out.println("An error occurred while generating a screenshot - " + e.getMessage());
        }
    }
}
