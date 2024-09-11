package com.banreservas.tpay.account.resources;


import com.banreservas.tpay.account.dtos.TPayAccountRequest;
import com.banreservas.tpay.account.repositories.ITPayRepository;
import com.banreservas.tpay.account.services.IServiceTPayAccount;
import io.quarkus.test.InjectMock;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import io.smallrye.mutiny.Uni;
import jakarta.ws.rs.core.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@QuarkusTest
class TPayAccountResourceTest {


    @InjectMock
    IServiceTPayAccount serviceTPayAccount;


    @InjectMock
    ITPayRepository itPayRepository;

    @BeforeEach
    void init() {
        when(serviceTPayAccount.unmaskProduct(any())).thenReturn(Uni.createFrom().item(Response.ok().build()));
    }


    @Test
    void testHelloEndpoint() {
        TPayAccountRequest request = new TPayAccountRequest("03289578563","CuentaCorriente");
        given()
                .contentType(ContentType.JSON)
                .body(request)
                .header("date", "2024-09-10")
                .header("channel", "web")
                .header("terminal", "terminal1")
                .header("user", "user1")
                .header("operationName", "operation1")
                .when()
                .post("/tpay/getDetailAccountByProduct")
                .then()
                .statusCode(200)              ;
    }
}
