package gfb.utils;

import gfb.models.RestAssuredData;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.Filter;
import io.restassured.filter.FilterContext;
import io.restassured.response.Response;
import io.restassured.specification.FilterableRequestSpecification;
import io.restassured.specification.FilterableResponseSpecification;
import io.restassured.specification.RequestSpecification;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.Map;

public class RestAssuredClient {

    private final String payload;
    private final Map<String, String> headers;
    private final Map<String, String> parameters;
    private RequestSpecification requestSpec;


    public RestAssuredClient(RestAssuredData restAssuredData) {
        this.payload = restAssuredData.getPayload();
        this.headers = restAssuredData.getHeaders();
        this.parameters = restAssuredData.getParameters();
        this.requestSpec = new RequestSpecBuilder().setBaseUri(restAssuredData.getRequestUrl()).build();
    }

    public Response getRequest() {
        requestSpec = RestAssured.given()
                .headers(headers)
                .spec(requestSpec)
                .with().filter(new RestAssuredRequestFilter());
        return requestSpec.params(parameters).get();
    }

    public Response postRequest() {
        requestSpec = RestAssured.given()
                .headers(headers)
                .spec(requestSpec)
                .with().filter(new RestAssuredRequestFilter());
        return requestSpec.with().body(payload).post();
    }

    public Response putRequest() {
        requestSpec = RestAssured.given()
                .headers(headers)
                .spec(requestSpec)
                .with().filter(new RestAssuredRequestFilter());
        return requestSpec.with().body(payload).put();
    }

    public Response patchRequest() {
        requestSpec = RestAssured.given()
                .headers(headers)
                .spec(requestSpec)
                .with().filter(new RestAssuredRequestFilter());
        return requestSpec.with().body(payload).patch();
    }

    public Response deleteRequest() {
        requestSpec = RestAssured.given()
                .headers(headers)
                .spec(requestSpec)
                .with().filter(new RestAssuredRequestFilter());
        return requestSpec.params(parameters).delete();
    }

    public static class RestAssuredRequestFilter implements Filter {
        private final Log log = LogFactory.getLog(RestAssuredRequestFilter.class);

        @Override
        public Response filter(FilterableRequestSpecification requestSpec, FilterableResponseSpecification responseSpec,
                               FilterContext ctx) {
            Response response = ctx.next(requestSpec, responseSpec);

            System.out.println(requestSpec.getMethod() + " " + requestSpec.getURI() + " \n Request Body =>" + requestSpec.getBody() + "\n Response Status => " +
                    response.getStatusCode() + " " + response.getStatusLine() + " \n Response Body => " + response.getBody().asPrettyString());
            return response;
        }
    }
}
