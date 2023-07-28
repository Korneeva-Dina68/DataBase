package pro.learnup.api;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import pro.learnup.api.dto.PhoneDto;
import pro.learnup.api.endpoints.ApiCatalogEndpoint;
import pro.learnup.api.ext.ApiTestExtension;
import pro.learnup.testdata.DbTestDataHelper;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;


@ExtendWith(ApiTestExtension.class)
@DisplayName("/api/catalog")
public class ApiCatalogTest {
    List<PhoneDto> phoneDtoList = DbTestDataHelper.getAllPhones();

    @Test()
    @DisplayName("/api/catalog: 200, получение телефонов без авторизации")
    void getCatalogTest() {

        assertThat(new ApiCatalogEndpoint().getAllPhones())
                .containsExactlyInAnyOrderElementsOf(phoneDtoList);
    }
}
