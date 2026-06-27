package com.ust.sdet.api.pos;

import io.restassured.http.ContentType;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

final class OmsClient {


    private final String baseUrl;


    OmsClient(String url)
    {

        this.baseUrl = url;

    }



    Order getOrder(int id){


        Response response =
                given()
                        .baseUri(baseUrl)

                        .when()
                        .get("/orders/" + id);


        return new Order(

                response.statusCode(),

                ((Number) response.path("id")).intValue(),

                response.path("status"),

                ((Number) response.path("total")).doubleValue()

        );

    }




    Order createOrder(
            String sku,
            int quantity
    ){


        String jsonBody =
                """
                {
                  "sku":"%s",
                  "quantity":%d
                }
                """.formatted(
                        sku,
                        quantity
                );



        Response response =
                given()
                        .baseUri(baseUrl)
                        .contentType("application/json")
                        .body(jsonBody)

                        .when()
                        .post("/orders/");




        return new Order(
                response.statusCode(),
                response.path("orderId"),
                response.path("status"),
                response.jsonPath().getDouble("total")

        );


    }




    Inventory getInventory(int id){


        Response response =
                given()

                        .baseUri(baseUrl)

                        .when()

                        .get("/Inventory/SKU-9");


//        return new Order(
//
//                response.statusCode(),
//
//                ((Number) response.path("orderId")).intValue(),
//
//                response.path("status"),
//
//                ((Number) response.path("total")).doubleValue()
//
//        );

        return new Inventory(
                response.statusCode(),
                response.path("sku"),
                ((Number) response.path("quantity")).intValue()
        );


    }




    record Order(
            int statuscode,
            int orderId,
            String status,
            double total
    ){}



    record CreateOrder(
            int statuscode,
            String sku,
            int quantity
    ){}


    record Inventory(
            int statuscode,
            String sku,
            int quantity
    ){}



}