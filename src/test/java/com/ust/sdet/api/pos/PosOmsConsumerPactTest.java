package com.ust.sdet.api.pos;

import au.com.dius.pact.consumer.MockServer;
import au.com.dius.pact.consumer.dsl.PactDslJsonBody;
import au.com.dius.pact.consumer.dsl.PactDslWithProvider;
import au.com.dius.pact.consumer.junit5.PactConsumerTestExt;
import au.com.dius.pact.consumer.junit5.PactTestFor;
import au.com.dius.pact.core.model.PactSpecVersion;
import au.com.dius.pact.core.model.V4Pact;
import au.com.dius.pact.core.model.annotations.Pact;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.junit.jupiter.api.Assertions.assertEquals;
@ExtendWith(PactConsumerTestExt.class)
@PactTestFor(
        providerName = "oms-provider",
        pactVersion = PactSpecVersion.V4
)
class PosOmsConsumerPactTest {


    @Pact(
            provider = "oms-provider",
            consumer = "pos-consumer"
    )
    V4Pact getOrder(PactDslWithProvider builder) {

        return builder
                .given("Order 123 exists")
                .uponReceiving("a request for order 123")
                .path("/orders/123")
                .method("GET")
                .willRespondWith()
                .status(200)
                .matchHeader(
                        "Content-Type",
                        "application/json(;.*)?",
                        "application/json"
                )
                .body(new PactDslJsonBody()
                        .integerType("orderId", 123)
                        .stringType("status", "CONFIRMED")
                        .numberType("total", 42.0)
                )

                .toPact(V4Pact.class);
    }


    @Pact(
            provider = "oms-provider",
            consumer = "pos-consumer"
    )
    V4Pact createOrder(PactDslWithProvider builder) {

        return builder

                .given("Creating a new order")

                .uponReceiving("a request for creating an Order")
                .path("/orders/")
                .method("POST")

                .matchHeader(
                        "Content-Type",
                        "application/json(;.*)?",
                        "application/json"
                )

                .body(new PactDslJsonBody()
                        .stringType("sku", "SKU-9")
                        .integerType("quantity", 20)
                )

                .willRespondWith()
                .status(201)

                .matchHeader(
                        "Content-Type",
                        "application/json(;.*)?",
                        "application/json"
                )

                .body(
                        new PactDslJsonBody()
                                .integerType("statusCode", 201)
                                .integerType("orderId", 101)
                                .stringType("status", "CREATED")
                                .numberType("total", 2000)

                )

                .toPact(V4Pact.class);
    }


    @Pact(
            provider = "oms-provider",
            consumer = "pos-consumer"
    )
    V4Pact getInventoryShow(PactDslWithProvider builder) {

        return builder

                .given("SKU-9 has Stock")

                .uponReceiving("a request for Sku-9")
                .path("/Inventory/SKU-9")
                .method("GET")

                .willRespondWith()
                .status(200)

                .matchHeader(
                        "Content-Type",
                        "application/json(;.*)?",
                        "application/json"
                )

//                .body(new PactDslJsonBody()
//                        .integerType("id", 7)
//                        .stringType("status", "Confirmed")
//                        .numberType("total", 42)
//                                .stringType("sku", "SKU-9")
//                                .integerType("quantity", 20)
//                )

                .body(
                        new PactDslJsonBody()
                                .integerType("statusCode", 201)
                                .stringType("sku", "SKU-9")
                                .integerType("quantity", 20)
                )


                .toPact(V4Pact.class);
    }


    @Test
    @PactTestFor(pactMethod = "getOrder")
    void testGetOrder(MockServer mockServer) {


        OmsClient client =
                new OmsClient(mockServer.getUrl());


        OmsClient.Order order =
                client.getOrder(123);


        assertEquals(200, order.statuscode());
        assertEquals(123, order.orderId());
        assertEquals("CONFIRMED", order.status());
        assertEquals(42.0, order.total());

    }


    @Test
    @PactTestFor(pactMethod = "createOrder")
    void testCreateOrder(MockServer mockServer) {


        OmsClient client =
                new OmsClient(mockServer.getUrl());


        OmsClient.Order order =
                client.createOrder(

                        "SKU-9",
                        20
                );


        assertEquals(201, order.statuscode());
        assertEquals("CREATED", order.status());
        assertEquals(2000.0, order.total());

    }


    @Test
    @PactTestFor(pactMethod = "getInventoryShow")
    void testGetInventory(MockServer mockServer) {


        OmsClient client =
                new OmsClient(mockServer.getUrl());


        OmsClient.Inventory inventory =
                client.getInventory(7);


        assertEquals(200, inventory.statuscode());
        assertEquals("SKU-9", inventory.sku());
        assertEquals(20, inventory.quantity());

    }

}