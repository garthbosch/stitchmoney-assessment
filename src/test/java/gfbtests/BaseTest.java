package gfbtests;

import gfb.models.RestAssuredData;
import gfb.utils.FileHelper;
import gfb.utils.RestAssuredClient;
import gfb.utils.SeleniumWebDriverUtils;
import gfbtests.reportsandvalidations.ValidateTestsAndReport;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.asserts.SoftAssert;

import java.util.HashMap;
import java.util.Map;

public abstract class BaseTest {

    protected SeleniumWebDriverUtils driver = new SeleniumWebDriverUtils();
    protected RestAssuredClient restAssuredClient;
    protected RestAssuredData restAssuredData = new RestAssuredData();
    protected String url;
    protected String requestMethod;
    protected Map<String, String> configMan = new HashMap<>();
    protected gfb.actions.GUIActions guiActions;
    protected SoftAssert softAssert = new SoftAssert();
    protected Map<String, String> headers = new HashMap<>();
    protected Map<String, String> parameters = new HashMap<>();

    protected ValidateTestsAndReport validateTestsAndReport;

    protected String todoItem;
    protected String editTodoItem;
    protected String urlPath;
    protected String status;
    protected boolean isNegativeTest;
    protected String errorMessage;
    protected String testCaseName;

    /*This method happens before any test class gets executed.
     * The search engine, e.g. google or bing, get passed via the TestNG xml file
     * The method loads the config properties file to a HashMap
     * The url get set depending on which search engine will be used
     * The Selenium driver gets initialised and started
     * Cookies are cleared
     * The uiActions class gets initialised*/
    @Parameters({"testType"})
    @BeforeClass
    public void setUpTests(String testType) {
        configMan = FileHelper.readPropertiesFile("src/main/resources/config.properties");
        validateTestsAndReport = new ValidateTestsAndReport(configMan);
        if (testType.equalsIgnoreCase("API")) {
            url = configMan.get("apiUrl");
        } else {
            url = configMan.get("guiUrl");
            driver.startDriver(url, System.getProperty("browserType") != null ? System.getProperty("browserType") : configMan.get("browserType"));
            driver.clearCookies();
        }
    }
}
