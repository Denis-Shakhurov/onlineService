import com.lambdaworks.crypto.SCryptUtil;
import org.example.CreateApp;
import org.example.config.Provider;
import org.example.config.javalinjwt.JWTProvider;
import org.example.controller.UserController;
import io.javalin.Javalin;
import io.javalin.http.Context;
import io.javalin.http.HttpStatus;
import io.javalin.http.NotFoundResponse;
import org.example.model.Order;
import org.example.model.User;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.example.service.OrderService;
import org.example.service.UserService;
import org.example.utils.NamedRoutes;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertThrows;
import static org.mockito.Mockito.*;

public class UserControllerTest {
    Javalin app;
    private static MockWebServer mockBackEnd;
    private Context ctx;
    private final CreateApp createApp = new CreateApp();
    private UserService userService;
    private OrderService orderService;
    private Provider provider;
    private JWTProvider<User> jwtProvider;
    private UserController controller;
    private NamedRoutes namedRoutes;



    @BeforeAll
    static void setUpMock() throws IOException {
        mockBackEnd = new MockWebServer();
        var html = Files.readString(Paths.get("src/test/resources/pageForTest.html"));
        var serverResponse = new MockResponse()
                .addHeader("Content-Type", "text/html; charset=utf-8")
                .setResponseCode(HttpStatus.OK.getCode())
                .setBody(html);
        mockBackEnd.enqueue(serverResponse);
        mockBackEnd.start();
    }

    @AfterAll
    static void tearDown() throws IOException {
        mockBackEnd.shutdown();
    }

    @BeforeEach
    public final void setUp() {
        app = createApp.getApp();
        ctx = mock(Context.class);
        userService = mock(UserService.class);
        orderService = mock(OrderService.class);
        provider = mock(Provider.class);
        jwtProvider = mock(JWTProvider.class);
        controller = new UserController(userService, orderService, provider);
        namedRoutes = mock(NamedRoutes.class);
    }

    @Test
    @DisplayName("Successfully retrieve and display list of all users in index method")
    public void getAllUsersTest() {
        List<User> users = Arrays.asList(
                new User(),
                new User()
        );
        when(userService.getAll()).thenReturn(users);

        controller.index(ctx);

        verify(ctx).status(HttpStatus.OK);
        verify(ctx).render(eq("users/index.jte"), any());
        verify(userService).getAll();
    }

    @Test
    @DisplayName("Login user with correct credentials and set session attributes")
    public void successLoginUserTest() {
        User user = new User();
        user.setId(1);
        user.setFirstName("John");
        user.setPassword(SCryptUtil.scrypt("password", 2, 2, 2));

        when(ctx.formParam("email")).thenReturn("john@example.com");
        when(ctx.formParam("password")).thenReturn("password");
        when(userService.findByEmail("john@example.com")).thenReturn(Optional.of(user));
        when(provider.create()).thenReturn(jwtProvider);
        when(jwtProvider.generateToken(user)).thenReturn("token");

        controller.login(ctx);

        verify(ctx).cookie("userId", "1");
        verify(ctx).cookie("jwt", "token");
        verify(ctx).sessionAttribute("flash", "Привет John!");
        verify(ctx).status(HttpStatus.OK);
        verify(ctx).redirect(anyString());
    }

    @Test
    @DisplayName("Update user with valid data and existing ID")
    public void updateUserWithValidDataAndExistingIdTest() {
        int userId = 1;
        User existingUser = new User();
        existingUser.setId(userId);
        existingUser.setEmail("oldemail@example.com");

        when(ctx.cookie("userId")).thenReturn(String.valueOf(userId));
        when(userService.findById(userId)).thenReturn(Optional.of(existingUser));
        when(provider.create()).thenReturn(jwtProvider);
        when(jwtProvider.generateToken(existingUser)).thenReturn("token");
        when(ctx.formParam("email")).thenReturn("new@email.com");
        when(ctx.formParam("password")).thenReturn("qwerty");
        when(ctx.formParam("firstName")).thenReturn("");
        when(ctx.formParam("lastName")).thenReturn("");

        controller.update(ctx);

        verify(userService).update(existingUser);
        verify(ctx).status(HttpStatus.OK);
        verify(ctx).redirect(anyString());
    }

    @Test
    @DisplayName("Show user details with orders based on user role")
    public void showUserWithOrdersAndValidRoleTest() {
        User user = new User();
        user.setId(1);
        user.setRole("user");
        List<Order> orders = Arrays.asList(new Order(), new Order());

        when(ctx.cookie("userId")).thenReturn("1");
        when(userService.findById(1)).thenReturn(Optional.of(user));
        when(orderService.findAllByUserId(1)).thenReturn(orders);

        controller.show(ctx);

        verify(ctx).status(HttpStatus.OK);
        verify(ctx).render(eq("users/show.jte"), any());
        verify(userService).findById(1);
        verify(orderService).findAllByUserId(1);
    }

    @Test
    @DisplayName("Create new user with unique email and set proper cookies and JWT token")
    public void createUserAndSetsCookiesJwt() {

        when(provider.create()).thenReturn(jwtProvider);
        when(jwtProvider.generateToken(any(User.class))).thenReturn("mockedToken");
        when(ctx.formParam("firstName")).thenReturn("John");
        when(ctx.formParam("lastName")).thenReturn("Doe");
        when(ctx.formParam("email")).thenReturn("john.doe@example.com");
        when(ctx.formParam("password")).thenReturn("password123");
        when(userService.existsByEmail("john.doe@example.com")).thenReturn(false);
        when(userService.save(any(User.class))).thenReturn(new User() {{ setId(1); }});
        when(namedRoutes.getUserPath(1)).thenReturn("/users/1");

        controller.create(ctx, "user");

        verify(ctx).cookie(eq("userId"), eq("1"));
        verify(ctx).cookie(eq("jwt"), eq("mockedToken"));
        verify(ctx).status(HttpStatus.CREATED);
        verify(ctx).redirect("/users/1");
    }

    @Test
    @DisplayName("Logout user and clear all cookies and session attributes")
    public void logoutClearsCookiesAndSession() {

        controller.logout(ctx);

        verify(ctx).sessionAttribute("flash", null);
        verify(ctx).cookie("userId", "");
        verify(ctx).cookie("jwt", "");
        verify(ctx).status(HttpStatus.OK);
        verify(ctx).redirect(anyString());
    }

    @Test
    @DisplayName("Handle user creation with already existing email")
    public void createUserWithExistingEmailTest() {

        when(ctx.formParam("email")).thenReturn("test@test.com");
        when(ctx.formParam("password")).thenReturn("123456");
        when(ctx.formParam("firstName")).thenReturn("John");
        when(ctx.formParam("lastName")).thenReturn("Doe");

        when(userService.existsByEmail("test@test.com")).thenReturn(true);

        controller.create(ctx, "user");

        verify(ctx).status(HttpStatus.BAD_REQUEST);
        verify(ctx).sessionAttribute(eq("flash"), anyString());
        verify(ctx).redirect(anyString());
    }

    @Test
    @DisplayName("Handle login attempt with non-existent email")
    public void loginWithNonExistentEmail() {

        String nonExistentEmail = "nonexistent@example.com";
        String password = "password";

        when(ctx.formParam("email")).thenReturn(nonExistentEmail);
        when(ctx.formParam("password")).thenReturn(password);
        when(userService.findByEmail(nonExistentEmail)).thenReturn(Optional.empty());

        controller.login(ctx);

        verify(ctx).status(HttpStatus.BAD_REQUEST);
        verify(ctx).sessionAttribute(eq("flash"), eq("Не верный email"));
        verify(ctx).redirect(anyString());
    }

    @Test
    @DisplayName("Handle login attempt with incorrect password")
    public void loginIncorrectPasswordTest() {

        String email = "test@example.com";
        String incorrectPassword = "wrongPassword";
        User user = new User();
        user.setEmail(email);
        user.setPassword(SCryptUtil.scrypt("correctPassword", 2, 2, 2));

        when(ctx.formParam("email")).thenReturn(email);
        when(ctx.formParam("password")).thenReturn(incorrectPassword);
        when(userService.findByEmail(email)).thenReturn(Optional.of(user));

        controller.login(ctx);

        verify(ctx).sessionAttribute("flash", "Не верный пароль");
        verify(ctx).status(HttpStatus.BAD_REQUEST);
        verify(ctx).redirect(anyString());
    }

    @Test
    @DisplayName("Handle user update with duplicate email")
    public void updateWithDuplicate_EmailTest() {

        User existingUser = new User();
        existingUser.setId(1);
        existingUser.setEmail("existing@example.com");

        when(ctx.cookie("userId")).thenReturn("1");
        when(userService.findById(1)).thenReturn(Optional.of(existingUser));
        when(ctx.formParam("email")).thenReturn("duplicate@example.com");
        when(ctx.formParam("password")).thenReturn("password123");
        when(ctx.formParam("firstName")).thenReturn("John");
        when(ctx.formParam("lastName")).thenReturn("Doe");
        when(userService.existsByEmail("duplicate@example.com")).thenReturn(true);

        controller.update(ctx);

        verify(ctx).sessionAttribute("flash", "Пользователь с duplicate@example.com уже зарегистрирован");
        verify(ctx).status(HttpStatus.BAD_REQUEST);
        verify(ctx).redirect(anyString());
    }

    @Test
    @DisplayName("Handle missing or malformed user ID cookie")
    public void indexEditHandlesMissingUserIdCookieTest() {

        when(ctx.cookie("userId")).thenReturn(null);

        assertThrows(NullPointerException.class, () -> {
            controller.indexEdit(ctx);
        });

        verify(ctx, never()).status(HttpStatus.OK);
        verify(ctx, never()).render(eq("users/edit.jte"), any());
    }

    @Test
    @DisplayName("Handle user not found when showing details")
    public void showUserNotFoundThrowsNotFoundResponseTest() {

        when(ctx.cookie("userId")).thenReturn("1");
        when(userService.findById(1)).thenReturn(Optional.empty());

        assertThrows(NotFoundResponse.class, () -> {
            controller.show(ctx);
        });

        verify(ctx).cookie("userId");
        verify(userService).findById(1);
    }

    @Test
    @DisplayName("Process empty form parameters during user update")
    public void updateWithEmptyFormParametersTest() {

        User existingUser = new User();
        existingUser.setId(1);
        existingUser.setFirstName("John");
        existingUser.setLastName("Doe");
        existingUser.setEmail("john.doe@example.com");
        existingUser.setPassword("hashedPassword");

        when(ctx.cookie("userId")).thenReturn("1");
        when(userService.findById(1)).thenReturn(Optional.of(existingUser));
        when(ctx.formParam("firstName")).thenReturn("");
        when(ctx.formParam("lastName")).thenReturn("");
        when(ctx.formParam("email")).thenReturn("");
        when(ctx.formParam("password")).thenReturn("");
        when(userService.update(any(User.class))).thenReturn(existingUser);
        when(provider.create()).thenReturn(jwtProvider);
        when(jwtProvider.generateToken(existingUser)).thenReturn("token");

        controller.update(ctx);

        verify(userService).findById(1);
        verify(userService).update(any(User.class));
        verify(ctx).cookie("userId", "1");
        verify(ctx).sessionAttribute("flash", "Изменения сохранены");
        verify(ctx).status(HttpStatus.OK);
        verify(ctx).redirect(anyString());
    }
}
