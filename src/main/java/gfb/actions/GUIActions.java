package gfb.actions;

import gfb.models.PageLocators;
import gfb.utils.SeleniumWebDriverUtils;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.testng.asserts.SoftAssert;

import java.util.List;

public class GUIActions extends SoftAssert {

    private SeleniumWebDriverUtils driver;

    public GUIActions(SeleniumWebDriverUtils driver) {
        this.driver = driver;
    }

    public void addTodoItem(String todoItem) {
        try {
            driver.enterText(PageLocators.ADD_TODO_TEXTBOX.getElement(), PageLocators.ADD_TODO_TEXTBOX.getElementType(),
                    todoItem);
            driver.clickElement(PageLocators.SUBMIT_BUTTON.getElement(), PageLocators.SUBMIT_BUTTON.getElementType());
        } catch (WebDriverException e) {
            throw new WebDriverException(e);
        }
    }

    public void markItemDone(String todoItem) {
        try {
            String todoLabelElement = String.format(PageLocators.TODO_ITEM_LABEL.getElement(), todoItem);
            String todoLabelElementType = String.format(PageLocators.TODO_ITEM_LABEL.getElementType(), todoItem);
            String doneElement = String.format(PageLocators.DONE_BUTTON.getElement(), todoLabelElement);
            String doneElementType = String.format(PageLocators.DONE_BUTTON.getElementType(), todoLabelElementType);
            driver.clickElement(doneElement, doneElementType);
        } catch (WebDriverException e) {
            throw new WebDriverException(e);
        }
    }

    public String getAddedItemText(String todoItem) throws WebDriverException {
        String todoLabelElement = String.format(PageLocators.TODO_ITEM_LABEL.getElement(), todoItem);
        String todoLabelElementType = String.format(PageLocators.TODO_ITEM_LABEL.getElementType(), todoItem);
        return driver.getText(todoLabelElement, todoLabelElementType);
    }

    public String getAddedItemAttribute(String todoItem, String elementAttribute) throws WebDriverException {
        String todoLabelElement = String.format(PageLocators.TODO_ITEM_LABEL.getElement(), todoItem);
        String todoLabelElementType = String.format(PageLocators.TODO_ITEM_LABEL.getElementType(), todoItem);
        return driver.getAttribute(todoLabelElement, todoLabelElementType, elementAttribute);
    }

    public boolean isItemDisplayed(String todoItem) throws WebDriverException {
        String todoLabelElement = String.format(PageLocators.TODO_ITEM_LABEL.getElement(), todoItem);
        String todoLabelElementType = String.format(PageLocators.TODO_ITEM_LABEL.getElementType(), todoItem);
        return driver.isElementInDOM(todoLabelElement, todoLabelElementType);
    }

    public boolean isDuplicatesTodoItems(String todoItem) throws WebDriverException {
        String todoLabelElement = String.format(PageLocators.TODO_ITEM_LABEL.getElement(), todoItem);
        String todoLabelElementType = String.format(PageLocators.TODO_ITEM_LABEL.getElementType(), todoItem);
        return driver.findElements(todoLabelElement, todoLabelElementType).size() > 1;
    }

    public void deleteItem(String todoItem) {
        try {
            String todoLabelElement = String.format(PageLocators.TODO_ITEM_LABEL.getElement(), todoItem);
            String todoLabelElementType = String.format(PageLocators.TODO_ITEM_LABEL.getElementType(), todoItem);
            String element = String.format(PageLocators.DELETE_BUTTON.getElement(), todoLabelElement);
            String elementType = String.format(PageLocators.DELETE_BUTTON.getElementType(), todoLabelElementType);
            driver.clickElement(element, elementType);
        } catch (WebDriverException e) {
            throw new WebDriverException(e);
        }
    }

    public void deleteAllItems() {
        try {
            String element = String.format(PageLocators.DELETE_BUTTON.getElement(), "/");
            String elementType = String.format(PageLocators.DELETE_BUTTON.getElementType(), "/");
            List<WebElement> deleteButtons = driver.findElements(element, elementType);
            for (int i = 0; i < deleteButtons.size(); i++) {
                driver.clickElement(element, elementType);
            }
        } catch (WebDriverException e) {
            throw new WebDriverException(e);
        }
    }
}
