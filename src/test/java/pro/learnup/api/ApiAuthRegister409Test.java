package pro.learnup.api;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import pro.learnup.api.dto.UserDto;
import pro.learnup.api.endpoints.ApiAuthRegisterEndpoint;
import pro.learnup.api.ext.ApiTestExtension;
import pro.learnup.testdata.DbTestDataHelper;

import static io.restassured.RestAssured.given;
import static pro.learnup.api.ApiAuthRegisterTest.successfulCreateUserRequests;

@ExtendWith(ApiTestExtension.class)
@DisplayName("/api/auth/register")
public class ApiAuthRegister409Test {
    UserDto userDto;

    @Test()
    @DisplayName("/api/auth/register: 409: ошибка: пользователь уже существует")
    void createAnExistingUser409Test(){
        userDto = new ApiAuthRegisterEndpoint().registerNewUser(successfulCreateUserRequests().findFirst().orElseThrow());

        given()
                .body(userDto)
                .post(new ApiAuthRegisterEndpoint().getEndpoint())
                .then()
                .statusCode(409)
                .body(Matchers.stringContainsInOrder("{\"message\":\"User already exists\"}"));
    }

    @AfterEach
    void tearDown() {
        DbTestDataHelper.deleteUser(userDto);
    }
}
