package gfbtests.api;

import gfb.models.ResponseData;
import gfb.utils.FileHelper;
import gfb.utils.GeneratePayload;
import gfb.utils.RestAssuredClient;
import gfbtests.BaseTest;
import io.restassured.response.Response;
import org.apache.hc.core5.http.HttpStatus;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import java.util.List;

public class TodoAPITest extends BaseTest {

    @Parameters({"requestMethod", "urlPath", "todoItem", "editTodoItem", "status", "isNegativeTest", "errorMessage"})
    @BeforeMethod
    public void setUpTest(String requestMethod, String urlPath, @Optional String todoItem, @Optional String editTodoItem,
                          @Optional("inprogress") String status, @Optional("false") boolean isNegativeTest,
                          @Optional String errorMessage) {
        this.requestMethod = requestMethod;
        this.urlPath = urlPath;
        this.status = status;
        this.errorMessage = errorMessage;
        this.isNegativeTest = isNegativeTest;
        restAssuredData.setRequestUrl(url + urlPath);
        headers.put("Content-Type", "application/json");
        parameters.put("", "");
        restAssuredData.setHeaders(headers);
        restAssuredData.setParameters(parameters);
        this.todoItem = todoItem;
        this.editTodoItem = editTodoItem;

        //region Data setup
        restAssuredData.setPayload(GeneratePayload.setAddPayload(todoItem, status, null).toString());
        //region POST
        if (requestMethod.equalsIgnoreCase("POST")) {
            if (isNegativeTest) {
                restAssuredClient = new RestAssuredClient(restAssuredData);
                List<ResponseData> responseDataList = FileHelper.readJsonObjectIntoPojo(restAssuredClient.postRequest().asString(), ResponseData[].class);
                restAssuredData.setRequestUrl(restAssuredData.getRequestUrl().replaceAll("todos", urlPath));

                restAssuredData.setPayload(GeneratePayload.setAddPayload(responseDataList.get(0).getTodo(), status,
                        String.valueOf(responseDataList.get(0).getId())).toString());
            }
        }
        //endregion
        //region GET
        if (requestMethod.equalsIgnoreCase("GET")) {
            restAssuredClient = new RestAssuredClient(restAssuredData);
            restAssuredClient.postRequest();
        }
        //endregion
        //region PATCH
        if (requestMethod.equalsIgnoreCase("PATCH")) {
            restAssuredData.setPayload(GeneratePayload.setAddPayload(todoItem, status, null).toString());
            restAssuredData.setRequestUrl(restAssuredData.getRequestUrl().replaceAll(urlPath, "todos"));
            restAssuredClient = new RestAssuredClient(restAssuredData);
            List<ResponseData> responseDataList = FileHelper.readJsonObjectIntoPojo(restAssuredClient.postRequest().asString(), ResponseData[].class);
            ResponseData responseData = responseDataList.stream().filter(todoData -> todoItem.equals(todoData.getTodo())).findAny().orElse(null);
            restAssuredData.setPayload(GeneratePayload.setEditPayload(responseData.getId(), editTodoItem).toString());
            //set the url back to do editing request
            restAssuredData.setRequestUrl(restAssuredData.getRequestUrl().replaceAll("todos", urlPath));
        }
        //endregion
        //region DELETE
        if (requestMethod.equalsIgnoreCase("DELETE")) {
            restAssuredData.setRequestUrl(restAssuredData.getRequestUrl().replaceAll(urlPath, "todos"));
            restAssuredClient = new RestAssuredClient(restAssuredData);
            List<ResponseData> responseDataList = FileHelper.readJsonObjectIntoPojo(restAssuredClient.postRequest().asString(), ResponseData[].class);
            ResponseData responseData = responseDataList.stream().filter(todoData -> todoItem.equals(todoData.getTodo())).findAny().orElse(null);
            restAssuredData.setPayload(GeneratePayload.setDeletePayload(responseData.getId()).toString());
            //set the url back to do editing request
            restAssuredData.setRequestUrl(restAssuredData.getRequestUrl().replaceAll("todos", urlPath));
        }
        //endregion
        restAssuredClient = new RestAssuredClient(restAssuredData);
        //endregion
    }

    @Test
    public void runTest() {
        Response response = null;
        switch (requestMethod.toUpperCase()) {
            case "POST":
            case "PATCH":
            case "DELETE":
                response = restAssuredClient.postRequest();
                break;

            case "GET":
                response = restAssuredClient.getRequest();
                break;

            default:
                throw new UnsupportedOperationException("The request method " + requestMethod +
                        " is incorrect. Either use POST, PATCH, GET or DELETE");
        }

        List<ResponseData> responseDataList = FileHelper.readJsonObjectIntoPojo(response.asString(), ResponseData[].class);
        Assert.assertEquals(response.getStatusCode(), HttpStatus.SC_OK);

        if (isNegativeTest) {
            Assert.assertFalse(responseDataList.stream().anyMatch(item -> item.getTodo().equals(todoItem)),
                    "This test should have returned a false assertion with an error message '" + errorMessage + "', but it did not.");
        } else {
            if (requestMethod.equalsIgnoreCase("DELETE")) {
                Assert.assertFalse(responseDataList.stream().anyMatch(item -> item.getTodo().equals(todoItem)));
            } else {
                Assert.assertTrue(responseDataList.stream().anyMatch(item -> item.getTodo().equals(requestMethod.equalsIgnoreCase("PATCH")
                        ? editTodoItem : todoItem)));
            }
        }
    }

    @Test
    public void cleanUpData() {
        restAssuredData.setRequestUrl(restAssuredData.getRequestUrl().replaceAll(urlPath, "todos"));
        restAssuredClient = new RestAssuredClient(restAssuredData);
        List<ResponseData> responseDataList = FileHelper.readJsonObjectIntoPojo(restAssuredClient.getRequest().asString(), ResponseData[].class);
        restAssuredData.setRequestUrl(restAssuredData.getRequestUrl().replaceAll("todos", urlPath));
        for (ResponseData responseData : responseDataList) {
            restAssuredData.setPayload(GeneratePayload.setDeletePayload(responseData.getId()).toString());
            restAssuredClient = new RestAssuredClient(restAssuredData);
            restAssuredClient.postRequest().then().statusCode(HttpStatus.SC_OK);
        }
    }

    @Test
    public void addExistingItemTest() {
        restAssuredClient = new RestAssuredClient(restAssuredData);
        restAssuredClient.postRequest();
        List<ResponseData> responseDataList = FileHelper.readJsonObjectIntoPojo(restAssuredClient.postRequest().asString(), ResponseData[].class);
        long itemsAdded = responseDataList.stream().filter(todoData -> todoItem.equals(todoData.getTodo())).count();
        Assert.assertFalse(itemsAdded > 1,
                "This test should have returned a false assertion with an error message '" + errorMessage + "', but it did not.");
    }
}
