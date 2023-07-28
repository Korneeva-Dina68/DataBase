package pro.learnup.api;

import com.github.javafaker.Faker;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import pro.learnup.api.dto.UserDto;
import pro.learnup.api.endpoints.ApiAuthRegisterEndpoint;
import pro.learnup.api.ext.ApiTestExtension;
import pro.learnup.testdata.DbTestDataHelper;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.stream.Stream;

import static io.restassured.RestAssured.given;

@ExtendWith(ApiTestExtension.class)
@DisplayName("/api/auth/register")
public class ApiAuthRegisterTest {
    UserDto userDto;
    static Faker faker = new Faker();
    public static Stream<UserDto> successfulCreateUserRequests() {

        return Stream.of(UserDto.builder()
                        .address(faker.address().fullAddress())
                        .phone(faker.phoneNumber().phoneNumber())
                        .email(faker.internet().emailAddress())
                        .username(faker.name().fullName())
                        .password(faker.internet().password())
                        .build());
    }

    @ParameterizedTest()
    @DisplayName("/api/auth/register: 201: успешное создание юзера")
    @MethodSource("successfulCreateUserRequests")
    void createUserTest(UserDto userDto){

        this.userDto = new ApiAuthRegisterEndpoint().registerNewUser(userDto);

        SoftAssertions softAssertions = new SoftAssertions();
        softAssertions.assertThat(this.userDto)
                .as("Созданный юзер должен быть равен ожидаемому")
                .usingRecursiveComparison()
                .ignoringFields("id", "orders", "password", "token")
                .isEqualTo(userDto);

        softAssertions.assertThat(this.userDto.getId().toString()).isNotEmpty();
        softAssertions.assertThat(this.userDto.getPassword()).isNotEmpty();
        softAssertions.assertThat(this.userDto.getToken()).isNotEmpty();
        softAssertions.assertThat(this.userDto.getOrders()).isEmpty();
        softAssertions.assertAll();
    }

    @AfterEach
    void tearDown() {
        DbTestDataHelper.deleteUser(userDto);
    }
}
