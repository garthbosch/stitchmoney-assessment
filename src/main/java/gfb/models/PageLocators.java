package gfb.models;

import lombok.Getter;

/*This enum is to store all the DOM elements of Bing needed to run the tests*/
@Getter
public enum PageLocators {

    ADD_TODO_TEXTBOX("//input[@class='input form-control']", PageLocators.XPATH),
    SUBMIT_BUTTON("//button[@type='submit']", PageLocators.XPATH),
    TODO_ITEM_LABEL("//span[normalize-space()='%s']", PageLocators.XPATH),
    DONE_BUTTON("%s/following-sibling::div/button[@class='btn btn-outline-success']", PageLocators.XPATH),
    DELETE_BUTTON("%s/following-sibling::div/button[@class='btn btn-outline-danger']", PageLocators.XPATH)
    ;
    //multiple usage of same strings
    private static final String XPATH = "xpath";
    private static final String ID = "id";
    private final String element;
    private final String elementType;

    PageLocators(String element, String elementType) {
        this.element = element;
        this.elementType = elementType;
    }
}
