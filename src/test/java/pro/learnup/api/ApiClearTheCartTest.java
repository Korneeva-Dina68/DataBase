package pro.learnup.api;

import com.github.javafaker.Faker;
import io.restassured.http.Header;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import pro.learnup.api.dto.CartDto;
import pro.learnup.api.dto.PhoneDto;
import pro.learnup.api.dto.UserDto;
import pro.learnup.api.endpoints.*;
import pro.learnup.api.ext.ApiTestExtension;
import pro.learnup.testdata.DbTestDataHelper;


@ExtendWith(ApiTestExtension.class)
@DisplayName("/api/cart")
public class ApiClearTheCartTest {

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
    @DisplayName("/api/cart: 200: очистка корзины")
    void clearPhone() {

        new ApiClearTheCartEndpoint().clearPhones(userDto, phoneDto);
    }

    @AfterEach
    void tearDown() {
        DbTestDataHelper.deleteUser(userDto);
    }
}
