package pro.learnup.api;

import com.github.javafaker.Faker;
import com.mongodb.client.model.Filters;
import io.restassured.http.Header;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import pro.learnup.api.dto.UserDto;
import pro.learnup.api.ext.ApiTestExtension;
import pro.learnup.testdata.DbTestDataHelper;

import static io.restassured.RestAssured.given;
import static pro.learnup.db.MongoConnector.getDataBase;

@ExtendWith(ApiTestExtension.class)
@DisplayName("/api/auth/login")
public class ApiAuthorizationUserTest {
    static Faker faker = new Faker();
    String userName = faker.name().fullName();
    String token;

    @BeforeEach
    void setUp() {
        token = given()
                .body("{\n" +
                        "  \"address\": \"russia\",\n" +
                        "  \"email\": \"sdgrdsg@vas.ru\",\n" +
                        "  \"password\": \""+userName+"\",\n" +
                        "  \"phone\": \"8999999999\",\n" +
                        "  \"username\": \""+userName+"\"\n" +
                        "}")
                .post("/api/auth/register")
                .then()
                .statusCode(201)
                .extract()
                .jsonPath()
                .getString("token");
    }

    @Test()
    @DisplayName("/api/auth/login: 201: успешная авторизация юзера")
    void authorizationUserTest(){
        given()
                .header(new Header("Authorization", "Bearer " + token))
                .body("{\n" +
                        "  \"password\": \""+userName+"\",\n" +
                        "  \"username\": \""+userName+"\"\n" +
                        "}")
                .post("/api/auth/login")
                .then()
                .statusCode(200);
    }

    @AfterEach
    void tearDown() {
        getDataBase().getCollection("users").deleteOne(Filters.eq("token"));
    }
}
