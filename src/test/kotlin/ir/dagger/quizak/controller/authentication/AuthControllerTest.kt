package ir.dagger.quizak.controller.authentication

import ir.dagger.quizak.controller.command.UserCommand
import ir.dagger.quizak.services.db.UserService
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.ArgumentMatchers.anyString
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.junit.jupiter.MockitoExtension
import org.mockito.kotlin.any
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import org.springframework.web.util.NestedServletException

@ExtendWith(MockitoExtension::class)
internal class AuthControllerTest {
    @Mock lateinit var userService: UserService
    @InjectMocks lateinit var controller: AuthController
    private lateinit var mockMvc: MockMvc

    @BeforeEach
    fun setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build()
    }

    @Test
    fun loginPage() {
        mockMvc.perform(get("/login"))
            .andExpect(status().isOk)
            .andExpect(view().name("authentication/login"))
    }

    @Test
    fun signupForm() {
        mockMvc.perform(get("/signup"))
            .andExpect(status().isOk)
            .andExpect(view().name("authentication/signup"))
            .andExpect(model().attributeExists("user"))
    }

    @Test
    fun signup() {
        //given
        val user = UserCommand()
        `when`(userService.createUser(any())).thenReturn(user)
        //when
        mockMvc.perform(post("/signup")
            .param("id", "1"))
            //then
            .andExpect(status().is3xxRedirection)
    }

    @Test
    fun verifyEmailPage() {
        //given
        val userCommand = UserCommand()
        `when`(userService.findUserByUniqueName(anyString())).thenReturn(userCommand)

        //when
        mockMvc.perform(get("/verifyEmail/id/iahrari"))
            //then
            .andExpect(status().isOk)
            .andExpect(view().name("authentication/verifyEmail"))
    }

    @Test
    fun verifyEmailPageFails(){
        //given
        val userCommand = UserCommand().apply {
            isEnabled = true
        }
        `when`(userService.findUserByUniqueName(anyString())).thenReturn(userCommand)

        //when

//            .andExpect(status().isExpectationFailed)
        assertThrows<NestedServletException> {
            mockMvc.perform(get("/verifyEmail/id/iahrari"))
        }
    }

    @Test
    fun verifyEmail() {
        mockMvc.perform(get("/verifyEmail/1"))
            .andExpect(status().is3xxRedirection)
    }
}