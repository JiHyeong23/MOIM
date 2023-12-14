//package MOIM.svr.user;
//
//import MOIM.svr.user.userDto.UserSignUpDto;
//import MOIM.svr.utils.ResponseDto;
//import MOIM.svr.utils.Result;
//import MOIM.svr.utils.UtilMethods;
//import com.google.gson.Gson;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.mockito.Mockito;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.MediaType;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.ResultActions;
//
//import java.time.LocalDateTime;
//
//import static org.mockito.BDDMockito.given;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
//@WebMvcTest(UserController.class)
//@MockBean(JpaMetamodelMappingContext.class)
//public class UserControllerTest {
//    @Autowired
//    private MockMvc mockMvc;
//    @MockBean
//    private UserService userService;
//    @MockBean
//    private UserMapper userMapper;
//    @MockBean
//    private UtilMethods utilMethods;
//    @Autowired
//    private Gson gson;
//
//
//    @Test
//    @DisplayName("회원가입")
//    public void createUser() throws Exception {
//        //given
//        UserSignUpDto signUp = new UserSignUpDto("test", "test@gmail.com", "123a", LocalDateTime.of(1997, 11, 20, 16, 50));
//        String content = gson.toJson(signUp);
//
//        ResponseDto response = ResponseDto.builder()
//                .result(Result.SUCCESS).httpStatus(HttpStatus.CREATED).memo("user created successfully").response(1L).build();
//
//        given(userMapper.userSignUpToUser(Mockito.any(UserSignUpDto.class))).willReturn(new User());
//        given(userService.registerUser(Mockito.any(UserSignUpDto.class))).willReturn(new User());
//        given(utilMethods.makeResponseDto(Result.SUCCESS, HttpStatus.OK, 1L, "user created successfully")).willReturn(response);
//
//        //when
//        ResultActions actions =
//                mockMvc.perform(
//                        post("/users")
//                                .accept(MediaType.APPLICATION_JSON)
//                                .contentType(MediaType.APPLICATION_JSON)
//                                .content(content)
//                );
//
//        //then
//        actions
//                .andExpect(status().isCreated())
//                .andExpect(jsonPath("$.data.nickname").value(signUp.getUserNickname()))
//                .andExpect(jsonPath("$.data.email").value(signUp.getEmail()))
//                .andExpect(jsonPath("$.data.pw").value(signUp.getPw()))
//                .andExpect(jsonPath("$.data.dob").value(signUp.getDob()));
//
//    }
//}