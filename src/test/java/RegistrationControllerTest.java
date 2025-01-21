import controller.RegistrationController;
import controller.StartController;
import dto.BasePage;
import io.javalin.Javalin;
import io.javalin.http.Context;
import io.javalin.http.HttpStatus;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import org.junit.jupiter.api.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;

import static org.mockito.Mockito.*;

public class RegistrationControllerTest {
    Javalin app;
    private static MockWebServer mockBackEnd;
    private Context ctx;
    private final CreateApp createApp = new CreateApp();
    private RegistrationController controller;

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
        controller = new RegistrationController();
    }

    @Test
    @DisplayName("Render registration page with empty flash message")
    public void indexRendersRegistrationPageWithEmptyFlashTest() {

        when(ctx.consumeSessionAttribute("flash")).thenReturn("");

        controller.index(ctx);

        verify(ctx).render(eq("users/registration/registration.jte"), argThat(model -> {
            BasePage page = (BasePage) ((Map<String, Object>) model).get("page");
            return page.getFlash().equals("");
        }));
    }

    @Test
    @DisplayName("Validate template paths follow naming convention")
    public void templatePathsFollowNamingConventionTest() {
        Context ctx = mock(Context.class);
        RegistrationController controller = new RegistrationController();

        when(ctx.consumeSessionAttribute("flash")).thenReturn("");

        controller.index(ctx);
        verify(ctx).render(eq("users/registration/registration.jte"), any());

        controller.indexUser(ctx);
        verify(ctx).render(eq("users/registration/registrationUser.jte"), any());

        controller.indexMaster(ctx);
        verify(ctx).render(eq("users/registration/registrationMaster.jte"), any());
    }
}
