package gfb.models;

import lombok.Data;

import java.util.Map;

@Data
public class RestAssuredData {

    private String payload;
    private String requestUrl;
    private Map<String, String> parameters;
    private Map<String, String> headers;

}
