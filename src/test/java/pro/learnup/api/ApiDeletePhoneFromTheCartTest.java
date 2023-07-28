package pro.learnup.api;

import com.github.javafaker.Faker;
import io.restassured.http.Header;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import pro.learnup.api.dto.*;
import pro.learnup.api.endpoints.*;
import pro.learnup.api.ext.ApiTestExtension;
import pro.learnup.testdata.DbTestDataHelper;

import static io.restassured.RestAssured.given;

@ExtendWith(ApiTestExtension.class)
@DisplayName("/api/cart")
public class ApiDeletePhoneFromTheCartTest {
    PhoneDto phoneDto;
    UserDto userDto;

    @BeforeEach
    void setUp() {
        userDto = new ApiAuthRegisterEndpoint().registerNewUser(ApiAuthRegisterTest.successfulCreateUserRequests().findFirst().orElseThrow());
        phoneDto = DbTestDataHelper.getAllPhones().get(0);

        new ApiAddPhoneToCartEndpoint().addPhone(userDto, phoneDto);
        new ApiGetPhonesInTheCartEndpoint().getPhones(userDto, phoneDto);
    }

    @Test()
    @DisplayName("/api/cart: 200: удаление телефона из корзины")
    void deletePhone() {
        new ApiDeletePhoneFromTheCartEndpoint().deletePhones(userDto, phoneDto);
    }
    @AfterEach
    void tearDown() {
        DbTestDataHelper.deleteUser(userDto);
    }
}