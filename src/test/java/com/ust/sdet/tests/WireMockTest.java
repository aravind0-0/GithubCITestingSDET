package com.ust.sdet.tests;

import com.github.tomakehurst.wiremock.junit5.WireMockExtension;
import com.github.tomakehurst.wiremock.matching.StringValuePattern;
import io.restassured.RestAssured;
import io.restassured.matcher.ResponseAwareMatcher;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;

import java.net.http.HttpClient;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.options;
import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class WireMockTest {

//    @RegisterExtension
//    static WireMockExtension wm = WireMockExtension.newInstance()
//            .options(options().dynamicPort())
//            .build();
//
//    private HttpClient client;
//
//    @BeforeEach
//    void pointConsumerAtWiremock(){
//        RestAssured.baseURI = wm.getRuntimeInfo().getHttpBaseUrl();
//        client = HttpClient.newHttpClient();
//    }
//
//    @Test
//    void returnsConfirmedOrderOverHttpTest() {
//
//        wm.stubFor(get(urlPathEqualTo("/orders"))
//                .willReturn(aResponse()
//                        .withStatus(200)
//                        .withHeader("Content-Type", "application/json")
//                        .withBody("""
//                                    {
//                                      "id": "123",
//                                      "status": "CONFIRMED",
//                                      "total": 250
//                                    }
//                                """)));
//
//
//        given()
//                .queryParam("id", "123")
//                .when()
//                .get("/orders")
//                .then()
//                .statusCode(200)
//                .body("status", equalTo("CONFIRMED"))
//                .body("total", equalTo(250))
//                ;
//
//        wm.verify(getRequestedFor(urlPathEqualTo("/orders"))
//                .withQueryParam("id", (StringValuePattern) equalTo("123")));
//
//    }



    }
