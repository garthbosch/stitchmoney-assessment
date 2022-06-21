package gfbtests.gui;

import gfb.actions.GUIActions;
import gfbtests.BaseTest;
import gfbtests.reportsandvalidations.ValidateTestsAndReport;
import org.testng.ITestContext;
import org.testng.annotations.*;

public class TodoGUITest extends BaseTest {

    private String elementAttribute;

    @Parameters({"todoItem", "editTodoItem", "status", "isNegativeTest", "errorMessage", "elementAttribute"})
    @BeforeMethod
    public void setUpTest(ITestContext context, @Optional String todoItem, @Optional String editTodoItem,
                          @Optional("inprogress") String status, @Optional("false") boolean isNegativeTest,
                          @Optional String errorMessage, @Optional String elementAttribute) {
        this.testCaseName = context.getCurrentXmlTest().getName();
        this.status = status;
        this.errorMessage = errorMessage;
        this.isNegativeTest = isNegativeTest;
        this.todoItem = todoItem;
        this.editTodoItem = editTodoItem;
        this.elementAttribute = elementAttribute;
    }

    @Test
    public void addTodoItemTest() {
        guiActions = new GUIActions(driver);
        guiActions.addTodoItem(todoItem);
        if (status.equalsIgnoreCase("done")) {
            guiActions.markItemDone(todoItem);
            String actualResult = guiActions.getAddedItemAttribute(todoItem, elementAttribute);
            String expectedResult = "text-decoration: line-through;";
            softAssert = ValidateTestsAndReport.uiValidation(driver.getDriver(), actualResult, expectedResult, testCaseName);
        } else {
            if (isNegativeTest) {
                boolean actualResult = guiActions.isItemDisplayed(todoItem);
                softAssert = ValidateTestsAndReport.uiValidation(driver.getDriver(), String.valueOf(actualResult),
                        "false", testCaseName);
                softAssert.assertAll();
            } else {
                String actualResult = guiActions.getAddedItemText(todoItem);
                softAssert = ValidateTestsAndReport.uiValidation(driver.getDriver(), actualResult, todoItem, testCaseName);
            }
        }
        softAssert.assertAll();
    }

    @Test
    public void addExistingItemTest() {
        guiActions = new GUIActions(driver);
        guiActions.addTodoItem(todoItem);
        guiActions.addTodoItem(todoItem);
        boolean actualResult = guiActions.isDuplicatesTodoItems(todoItem);
        softAssert = ValidateTestsAndReport.uiValidation(driver.getDriver(), String.valueOf(actualResult),
                "false", testCaseName);
        softAssert.assertAll();
    }

    @Test
    public void deleteTodoItemTest() throws InterruptedException {
        guiActions = new GUIActions(driver);
        guiActions.addTodoItem(todoItem);
        if (status.equalsIgnoreCase("done")) {
            guiActions.markItemDone(todoItem);
        }
        guiActions.deleteItem(todoItem);
        boolean actualResult = guiActions.isItemDisplayed(todoItem);
        softAssert = ValidateTestsAndReport.uiValidation(driver.getDriver(), String.valueOf(actualResult),
                status.equalsIgnoreCase("done") ? "true" : "false", testCaseName);
        driver.pause(5);
        softAssert.assertAll();
    }

    @Test
    public void deleteAllTodoItemTest() throws InterruptedException {
        guiActions = new GUIActions(driver);
        guiActions.deleteAllItems();
    }

    /*This method will always close the Selenium WebDriver whether the test pass or fail */
    @AfterClass(alwaysRun = true)
    public void closeDown() {
        driver.shutdown();
    }
}
