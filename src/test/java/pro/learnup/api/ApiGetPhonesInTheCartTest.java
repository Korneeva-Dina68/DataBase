package pro.learnup.api;

import com.github.javafaker.Faker;
import io.restassured.http.Header;
import org.assertj.core.api.Assertions;
import org.hamcrest.Matchers;
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

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(ApiTestExtension.class)
@DisplayName("/api/cart")
public class ApiGetPhonesInTheCartTest {
    PhoneDto phoneDto;
    UserDto userDto;

    @BeforeEach
    void setUp() {
        userDto = new ApiAuthRegisterEndpoint().registerNewUser(ApiAuthRegisterTest.successfulCreateUserRequests().findFirst().orElseThrow());
        phoneDto = DbTestDataHelper.getAllPhones().get(0);
        new ApiAddPhoneToCartEndpoint().addPhone(userDto, phoneDto);
    }
    @Test()
    @DisplayName("/api/cart: 200: проверка наличия телефона в корзине")
    void getPhonesCart() {
        new ApiGetPhonesInTheCartEndpoint().getPhones(userDto, phoneDto);
    }
}
