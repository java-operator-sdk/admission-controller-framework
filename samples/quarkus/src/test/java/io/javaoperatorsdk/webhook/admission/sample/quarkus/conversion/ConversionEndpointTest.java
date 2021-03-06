package io.javaoperatorsdk.webhook.admission.sample.quarkus.conversion;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;

import static io.javaoperatorsdk.webhook.admission.sample.quarkus.conversion.ConversionEndpoint.ASYNC_CONVERSION_PATH;
import static io.javaoperatorsdk.webhook.admission.sample.quarkus.conversion.ConversionEndpoint.CONVERSION_PATH;
import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;

@QuarkusTest
class ConversionEndpointTest {

  final static String expectedResult =
      "{\"apiVersion\":\"apiextensions.k8s.io/v1\",\"kind\":\"ConversionReview\",\"response\":{\"convertedObjects\":[{\"apiVersion\":\"sample.javaoperatorsdk/v2\",\"kind\":\"MultiVersionCustomResource\",\"metadata\":{\"creationTimestamp\":\"2021-09-04T14:03:02Z\",\"name\":\"resource1\",\"namespace\":\"default\",\"resourceVersion\":\"143\",\"uid\":\"3415a7fc-162b-4300-b5da-fd6083580d66\"},\"spec\":{\"value\":\"1\",\"additionalValue\":\"default_additional_value\"},\"status\":{\"ready\":true,\"message\":null}},{\"apiVersion\":\"sample.javaoperatorsdk/v2\",\"kind\":\"MultiVersionCustomResource\",\"metadata\":{\"creationTimestamp\":\"2021-09-04T14:03:02Z\",\"name\":\"resource2\",\"namespace\":\"default\",\"resourceVersion\":\"14344\",\"uid\":\"1115a7fc-162b-4300-b5da-fd6083580d55\"},\"spec\":{\"value\":\"2\",\"additionalValue\":\"default_additional_value\"},\"status\":{\"ready\":false,\"message\":null}}],\"result\":{\"apiVersion\":\"v1\",\"kind\":\"Status\",\"status\":\"Success\"},\"uid\":\"705ab4f5-6393-11e8-b7cc-42010a800002\"}}";

  @Disabled
  @Test
  void conversion() {
    testConversion(CONVERSION_PATH);
  }

  @Disabled
  @Test
  void asyncConversion() {
    testConversion(ASYNC_CONVERSION_PATH);
  }

  public void testConversion(String path) {
    given().contentType(ContentType.JSON)
        .body(jsonRequest())
        .when().post("/" + path)
        .then()
        .statusCode(200)
        .body(is(expectedResult));
  }

  private String jsonRequest() {
    try (InputStream is = this.getClass().getResourceAsStream("/conversion-request.json")) {
      return new String(is.readAllBytes(), StandardCharsets.UTF_8);
    } catch (IOException e) {
      throw new IllegalStateException(e);
    }
  }
}
