import controller.OrderController;
import io.javalin.Javalin;
import io.javalin.http.Context;
import io.javalin.http.HttpStatus;
import io.javalin.http.NotFoundResponse;
import model.Order;
import model.Service;
import model.User;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import org.junit.jupiter.api.*;
import service.OrderService;
import service.ServiceService;
import service.UserService;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.Assert.assertThrows;
import static org.mockito.Mockito.*;

public class OrderControllerTest {
    Javalin app;
    private static MockWebServer mockBackEnd;
    private Context ctx;
    private final CreateApp createApp = new CreateApp();
    private ServiceService serviceService;
    private UserService userService;
    private OrderService orderService;
    private OrderController controller;

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
        orderService = mock(OrderService.class);
        controller = new OrderController(orderService, userService, serviceService);
    }

    @Test
    @DisplayName("Successfully create order with valid user ID, service ID and future date")
    public void createOrderWithValidDataTest() {

        User user = new User();
        user.setId(1);
        Service service = new Service();
        service.setId(1);
        service.setPrice(100.0);

        when(ctx.cookie("userId")).thenReturn("1");
        when(ctx.pathParam("id")).thenReturn("1");
        when(ctx.formParam("date")).thenReturn(LocalDateTime.now().plusDays(1).toString());
        when(userService.findById(1)).thenReturn(Optional.of(user));
        when(serviceService.findById(1)).thenReturn(Optional.of(service));

        controller.create(ctx);

        verify(orderService).save(any(Order.class));
        verify(ctx).status(HttpStatus.CREATED);
        verify(ctx).redirect("/users/1");
    }

    @Test
    @DisplayName("Successfully render create order page with valid user and service")
    public void createOrderPageWithValidUserAndService() {

        User user = new User();
        user.setId(1);
        Service service = new Service();
        service.setId(1);

        when(ctx.cookie("userId")).thenReturn("1");
        when(ctx.pathParam("id")).thenReturn("1");
        when(userService.findById(1)).thenReturn(Optional.of(user));
        when(serviceService.findById(1)).thenReturn(Optional.of(service));
        when(ctx.consumeSessionAttribute("flash")).thenReturn(null);

        controller.indexCreate(ctx);

        verify(ctx).status(HttpStatus.OK);
        verify(ctx).render(eq("orders/create.jte"), anyMap());
    }

    @Test
    @DisplayName("Successfully redirect to user page after order creation")
    public void redirectToUserPageAfterOrderCreationTest() {

        User user = new User();
        user.setId(1);
        Service service = new Service();
        service.setId(1);
        service.setPrice(100.0);

        when(ctx.cookie("userId")).thenReturn("1");
        when(ctx.pathParam("id")).thenReturn("1");
        when(ctx.formParam("date")).thenReturn(LocalDateTime.now().plusDays(1).toString());
        when(userService.findById(1)).thenReturn(Optional.of(user));
        when(serviceService.findById(1)).thenReturn(Optional.of(service));

        controller.create(ctx);

        verify(orderService).save(any(Order.class));
        verify(ctx).status(HttpStatus.CREATED);
        verify(ctx).redirect("/users/1");
    }

    @Test
    @DisplayName("Status code 400 returned for invalid date")
    public void createOrderWithInvalidDateTest() {

        User user = new User();
        user.setId(1);
        Service service = new Service();
        service.setId(1);

        when(ctx.cookie("userId")).thenReturn("1");
        when(ctx.pathParam("id")).thenReturn("1");
        when(ctx.formParam("date")).thenReturn(LocalDateTime.now().minusDays(1).toString());
        when(userService.findById(1)).thenReturn(Optional.of(user));
        when(serviceService.findById(1)).thenReturn(Optional.of(service));

        controller.create(ctx);

        verify(ctx).sessionAttribute("flash", "Некорректная дата");
        verify(ctx).status(HttpStatus.BAD_REQUEST);
        verify(ctx).redirect("/services/1/orders");
    }

    @Test
    @DisplayName("Status code 404 returned for missing service/user")
    public void createOrderWithMissingServiceUserTest() {

        when(ctx.cookie("userId")).thenReturn("1");
        when(ctx.pathParam("id")).thenReturn("1");
        when(ctx.formParam("date")).thenReturn(LocalDateTime.now().plusDays(1).toString());
        when(userService.findById(1)).thenReturn(Optional.empty());
        when(serviceService.findById(1)).thenReturn(Optional.empty());

        assertThrows(NotFoundResponse.class, () -> controller.create(ctx));
    }
}
