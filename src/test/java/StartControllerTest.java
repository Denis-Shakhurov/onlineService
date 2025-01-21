import controller.StartController;
import dto.BasePage;
import io.javalin.Javalin;
import io.javalin.http.Context;
import io.javalin.http.HttpStatus;
import model.User;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import org.junit.jupiter.api.*;
import service.UserService;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.*;

public class StartControllerTest {
    private Javalin app;
    private static MockWebServer mockBackEnd;
    private Context ctx;
    private final CreateApp createApp = new CreateApp();
    private UserService userService;
    private StartController controller;

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
        controller = new StartController(userService);
    }

    @Test
    @DisplayName("User with valid cookie ID is found and user info is added to base page")
    public void userInfoAddedWhenValidCookieIdTest() {
        User user = new User();
        user.setId(1);
        user.setFirstName("John");
        user.setLastName("Doe");
        user.setEmail("john@example.com");
        user.setRole("user");

        when(ctx.cookie("userId")).thenReturn("1");
        when(userService.findById(1)).thenReturn(Optional.of(user));
        when(userService.getAllByRole("master")).thenReturn(new ArrayList<>());

        controller.index(ctx);

        verify(ctx).render(eq("start.jte"), argThat(model -> {
            BasePage page = (BasePage) model.get("page");
            return page.getUserInfo() != null &&
                    page.getUserInfo().getId().equals(1) &&
                    page.getUserInfo().getFirstName().equals("John");
        }));
    }

    @Test
    @DisplayName("Masters list is retrieved and set in base page")
    public void mastersListRetrievedAndSetInBasePageTest() {

        List<User> masters = new ArrayList<>();
        User master1 = new User();
        master1.setId(2);
        master1.setFirstName("Alice");
        master1.setLastName("Smith");
        master1.setEmail("alice@example.com");
        master1.setRole("master");
        masters.add(master1);

        when(ctx.cookie("userId")).thenReturn(null);
        when(userService.getAllByRole("master")).thenReturn(masters);

        controller.index(ctx);

        verify(ctx).render(eq("start.jte"), argThat(model -> {
            BasePage page = (BasePage) model.get("page");
            return page.getMasters() != null &&
                    page.getMasters().size() == 1 &&
                    page.getMasters().get(0).getFirstName().equals("Alice");
        }));
    }

    @Test
    @DisplayName("Flash message from session is consumed and set in base page")
    public void flashMessageConsumedAndSetInBasePageTest() {

        when(ctx.cookie("userId")).thenReturn(null);
        when(ctx.consumeSessionAttribute("flash")).thenReturn("Test Flash Message");
        when(userService.getAllByRole("master")).thenReturn(new ArrayList<>());

        controller.index(ctx);

        verify(ctx).render(eq("start.jte"), argThat(model -> {
            BasePage page = (BasePage) model.get("page");
            return "Test Flash Message".equals(page.getFlash());
        }));
    }

    @Test
    @DisplayName("Page renders successfully with HTTP 200 status")
    public void pageRendersWithHttp200StatusTest() {

        when(ctx.cookie("userId")).thenReturn(null);
        when(userService.getAllByRole("master")).thenReturn(new ArrayList<>());

        controller.index(ctx);

        verify(ctx).status(HttpStatus.OK);
        verify(ctx).render(eq("start.jte"), argThat(model -> {
            BasePage page = (BasePage) model.get("page");
            return page.getMasters().isEmpty();
        }));
    }

    @Test
    @DisplayName("Cookie USER_ID is null or empty string")
    public void nullOrEmptyCookieId() {

        when(ctx.cookie("userId")).thenReturn("");
        when(userService.getAllByRole("master")).thenReturn(new ArrayList<>());

        controller.index(ctx);

        verify(ctx).render(eq("start.jte"), argThat(model -> {
            BasePage page = (BasePage) model.get("page");
            return page.getUserInfo() == null;
        }));
        verify(userService, never()).findById(any());
    }

    @Test
    @DisplayName("User ID from cookie not found in database")
    public void no_UserInfoWhenCookieIdNotFoundTest() {

        when(ctx.cookie("userId")).thenReturn("1");
        when(userService.findById(1)).thenReturn(Optional.empty());
        when(userService.getAllByRole("master")).thenReturn(new ArrayList<>());

        controller.index(ctx);

        verify(ctx).render(eq("start.jte"), argThat(model -> {
            BasePage page = (BasePage) model.get("page");
            return page.getUserInfo() == null;
        }));
    }
}
