import org.example.CreateApp;
import org.example.controller.ServiceController;
import io.javalin.Javalin;
import io.javalin.http.Context;
import io.javalin.http.HttpStatus;
import io.javalin.http.NotFoundResponse;
import org.example.model.Service;
import org.example.model.User;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import org.junit.jupiter.api.*;
import org.example.service.ServiceService;
import org.example.service.UserService;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertThrows;
import static org.mockito.Mockito.*;

public class ServiceControllerTest {
    Javalin app;
    private static MockWebServer mockBackEnd;
    private Context ctx;
    private final CreateApp createApp = new CreateApp();
    private ServiceService serviceService;
    private UserService userService;
    private ServiceController controller;

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
        serviceService = mock(ServiceService.class);
        controller = new ServiceController(serviceService, userService);
    }

    @Test
    @DisplayName("Successfully create new org.example.service with valid user ID and form parameters")
    public void createServiceWithValidParamsTest() {

        User user = new User();
        user.setId(1);

        when(ctx.cookie("userId")).thenReturn("1");
        when(ctx.formParam("name")).thenReturn("Test Service");
        when(ctx.formParam("description")).thenReturn("Test Description");
        when(ctx.formParam("price")).thenReturn("100.0");
        when(userService.findById(1)).thenReturn(Optional.of(user));

        controller.create(ctx);

        verify(ctx).status(HttpStatus.CREATED);
        verify(serviceService).save(any(Service.class));
        verify(ctx).redirect("/users/1/services");
    }

    @Test
    @DisplayName("Successfully update existing org.example.service with all fields modified")
    public void updateServiceWithAllFieldsModifiedTest() {

        User user = new User();
        user.setId(1);

        Service existingService = new Service();
        existingService.setId(1);
        existingService.setName("Old Name");
        existingService.setDescription("Old Description");
        existingService.setPrice(50.0);
        existingService.setUser(user);

        when(ctx.pathParam("id")).thenReturn("1");
        when(ctx.formParam("name")).thenReturn("New Name");
        when(ctx.formParam("description")).thenReturn("New Description");
        when(ctx.formParam("price")).thenReturn("100.0");
        when(serviceService.findById(1)).thenReturn(Optional.of(existingService));
        when(userService.findById(1)).thenReturn(Optional.of(user));

        controller.update(ctx);

        verify(serviceService).update(argThat(service ->
                service.getName().equals("New Name") &&
                        service.getDescription().equals("New Description") &&
                        service.getPrice().equals(100.0) &&
                        service.getUser().equals(user)
        ));
        verify(ctx).status(HttpStatus.OK);
        verify(ctx).redirect("/users/1/services");
    }

    @Test
    @DisplayName("Successfully display list of services for authenticated user")
    public void showServicesForAuthenticatedUserTest() {

        User user = new User();
        user.setId(1);
        List<Service> services = List.of(new Service(), new Service());

        when(ctx.cookie("userId")).thenReturn("1");
        when(ctx.pathParam("id")).thenReturn("1");
        when(serviceService.findAllForUser(1)).thenReturn(services);
        when(userService.findById(1)).thenReturn(Optional.of(user));
        when(ctx.consumeSessionAttribute("flash")).thenReturn(null);

        controller.index(ctx);

        verify(ctx).status(HttpStatus.OK);
        verify(ctx).render(eq("services/index.jte"), anyMap());
    }

    @Test
    @DisplayName("Successfully render create org.example.service form for authenticated user")
    public void renderCreateServiceFormForAuthenticatedUserTest() {

        User user = new User();
        user.setId(1);

        when(ctx.cookie("userId")).thenReturn("1");
        when(userService.findById(1)).thenReturn(Optional.of(user));

        controller.indexCreate(ctx);

        verify(ctx).status(HttpStatus.OK);
        verify(ctx).render(eq("services/create.jte"), anyMap());
    }

    @Test
    @DisplayName("Handle unauthorized access when user is not logged in")
    public void indexUnauthorizedAccessTest() {

        when(ctx.cookie("userId")).thenReturn("");

        controller.index(ctx);

        verify(ctx).sessionAttribute("flash", "Необходимо авторизироваться");
        verify(ctx).status(HttpStatus.UNAUTHORIZED);
        verify(ctx).redirect("/");
    }

    @Test
    @DisplayName("Handle invalid org.example.service ID in path parameters")
    public void updateWithInvalidServiceIdTest() {

        when(ctx.pathParam("id")).thenReturn("999");
        when(serviceService.findById(999)).thenReturn(Optional.empty());

        assertThrows(NotFoundResponse.class, () -> controller.update(ctx));

        verify(ctx, never()).status(HttpStatus.OK);
        verify(ctx, never()).redirect(anyString());
    }

    @Test
    @DisplayName("Handle org.example.service not found during update operation")
    public void updateServiceNotFoundTest() {

        when(ctx.pathParam("id")).thenReturn("1");
        when(serviceService.findById(1)).thenReturn(Optional.empty());

        assertThrows(NotFoundResponse.class, () -> controller.update(ctx));

        verify(ctx, never()).status(HttpStatus.OK);
        verify(serviceService, never()).update(any(Service.class));
    }
}
