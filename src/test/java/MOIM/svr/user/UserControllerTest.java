package MOIM.svr.user;

import MOIM.svr.user.userDto.UserSignUpDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootTest
@MockBean(JpaMetamodelMappingContext.class)
@AutoConfigureRestDocs
@AutoConfigureMockMvc
class UserControllerTest {
    private UserStub userStub;
    private UserService userService;
    @Test
    @DisplayName("회원가입")
    void userRegisterTest() throws Exception {
        UserSignUpDto userSignUpDto = userStub.userRegister();
        User user = userService.registerUser(userSignUpDto);
    }
}
