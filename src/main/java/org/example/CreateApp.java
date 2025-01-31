package org.example;

import com.lambdaworks.crypto.SCryptUtil;
import org.example.config.Provider;
import org.example.config.javalinjwt.JWTAccessManager;
import org.example.config.javalinjwt.JWTProvider;
import org.example.config.javalinjwt.JavalinJWT;
import gg.jte.ContentType;
import gg.jte.TemplateEngine;
import gg.jte.resolve.ResourceCodeResolver;
import io.javalin.Javalin;
import io.javalin.http.Handler;
import io.javalin.rendering.template.JavalinJte;
import io.javalin.security.RouteRole;
import org.example.controller.*;
import org.example.model.*;
import org.example.service.OrderService;
import org.example.service.ServiceService;
import org.example.service.UserService;
import org.example.utils.NamedRoutes;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

public class CreateApp {
    private final JavalinJWT javalinJWT = new JavalinJWT();
    private final Provider provider = new Provider();
    private final NamedRoutes namedRoutes = new NamedRoutes();
    private final UserService userService = new UserService();
    private final OrderService orderService = new OrderService();
    private final ServiceService serviceService = new ServiceService();
    private final UserController userController = new UserController(userService, orderService, provider);
    private final ServiceController serviceController = new ServiceController(serviceService, userService);
    private final RegistrationController registrationController = new RegistrationController();
    private final StartController startController = new StartController(userService, orderService);
    private final OrderController orderController = new OrderController(orderService, userService, serviceService);

    private TemplateEngine createTemplateEngine() {
        ClassLoader classLoader = App.class.getClassLoader();
        ResourceCodeResolver codeResolver = new ResourceCodeResolver("templates", classLoader);
        return TemplateEngine.create(codeResolver, ContentType.Html);
    }

    public Javalin getApp() {
        Javalin app = Javalin.create(config -> {
            config.fileRenderer(new JavalinJte(createTemplateEngine()));
            config.bundledPlugins.enableDevLogging();
        });

        // create the provider
        JWTProvider<User> providerUser = provider.create();

        Handler decodeHandler = javalinJWT.createCookieDecodeHandler(providerUser);
        // create the access manager
        Map<String, RouteRole> rolesMapping = new HashMap<>() {{
            put("user", Role.USER);
            put("master", Role.MASTER);
            put("admin", Role.ADMIN);
        }};

        JWTAccessManager accessManager = new JWTAccessManager(javalinJWT,"role", rolesMapping, Role.GUEST);

        // set the paths
        app.before(decodeHandler);
        app.beforeMatched(accessManager);

        app.get(namedRoutes.getUsersPath(), userController::index, Role.USER, Role.MASTER);
        app.get(namedRoutes.getUserPath("{id}"), userController::show, Role.USER, Role.MASTER);
        app.get(namedRoutes.getEditUserPath("{id}"), userController::indexEdit, Role.USER, Role.MASTER);
        app.post(namedRoutes.getEditUserPath("{id}"), userController::update, Role.USER, Role.MASTER);
        app.post(namedRoutes.getLoginUserPath(), userController::login, Role.USER, Role.MASTER, Role.GUEST);
        app.get(namedRoutes.getLoginUserPath(), userController::indexLogin, Role.USER, Role.MASTER, Role.GUEST);
        app.get(namedRoutes.getLogoutUserPath(), userController::logout, Role.USER, Role.MASTER, Role.GUEST);

        app.get(namedRoutes.getServicesByUserPath("{id}"), serviceController::index, Role.USER, Role.MASTER);
        app.post(namedRoutes.getServicesPath(), serviceController::create, Role.USER, Role.MASTER);
        app.get(namedRoutes.getServicesPath(), serviceController::indexCreate, Role.USER, Role.MASTER);
        app.get(namedRoutes.getEditServicesPath("{id}"), serviceController::indexEdit, Role.USER, Role.MASTER);
        app.post(namedRoutes.getEditServicesPath("{id}"), serviceController::update, Role.USER, Role.MASTER);

        app.get(namedRoutes.getOrdersByServicePath("{id}"), orderController::indexCreate, Role.USER);
        app.post(namedRoutes.getOrdersByServicePath("{id}"), orderController::create, Role.USER);

        app.get(namedRoutes.getStartPath(), startController::index, Role.GUEST, Role.USER, Role.MASTER);

        app.get(namedRoutes.getRegistrationPath(), registrationController::index, Role.USER, Role.GUEST, Role.MASTER);
        app.get(namedRoutes.getRegistrationUserPath(), registrationController::indexUser, Role.USER, Role.GUEST, Role.MASTER);
        app.get(namedRoutes.getRegistrationMasterPath(), registrationController::indexMaster, Role.USER, Role.GUEST, Role.MASTER);
        app.post(namedRoutes.getRegistrationUserPath(), ctx -> {userController.create(ctx, "user");}, Role.USER, Role.GUEST, Role.MASTER);
        app.post(namedRoutes.getRegistrationMasterPath(), ctx -> {userController.create(ctx, "master");}, Role.USER, Role.GUEST, Role.MASTER);

        createEntityForDemonstration();

        return app;
    }

    private void createEntityForDemonstration() {
        // create master
        User master1 = userService.save(createUser(
                "Bob",
                "Smith",
                "smith@example.com",
                SCryptUtil.scrypt("password", 2, 2, 2),
                "master"));
        User master2 = userService.save(createUser(
                "Alisa",
                "Smith",
                "smithAlisa@example.com",
                SCryptUtil.scrypt("password", 2, 2, 2),
                "master"));

        // create service
        Service service = serviceService.save(createService(
                "Стрижка",
                "Стрижка на высшем уровне",
                1500d,
                master1));

        Service service2 = serviceService.save(createService(
                "Детская стрижка",
                "Стрижка для детей от 3 лет",
                1000d,
                master1));

        Service service3 = serviceService.save(createService(
                "Стрижка комбо",
                "Классическая стрижка + оформление бороды",
                2000d,
                master1));

        Service service4 = serviceService.save(createService(
                "Маникюр",
                "Классический маникюр",
                1000d,
                master2));

        Service service5 = serviceService.save(createService(
                "Педикюр",
                "Классический педикюр",
                1000d,
                master2));

        // create customer
        User user = userService.save(createUser(
                "Denis",
                "Ivanov",
                "denis@mail.com",
                SCryptUtil.scrypt("password", 2, 2, 2),
                "user"
        ));

        User user2 = userService.save(createUser(
                "Marina",
                "Petrova",
                "petrova@mail.com",
                SCryptUtil.scrypt("password", 2, 2, 2),
                "user"
        ));

        User user3 = userService.save(createUser(
                "David",
                "Glamurnyi",
                "best@mail.com",
                SCryptUtil.scrypt("password", 2, 2, 2),
                "user"
        ));

        // create order
        orderService.save(createOrder(
                user,
                service,
                LocalDateTime.now().plusDays(7),
                OrderStatus.CREATED
        ));

        orderService.save(createOrder(
                user,
                service3,
                LocalDateTime.now().plusDays(21),
                OrderStatus.CREATED
        ));

        orderService.save(createOrder(
                user2,
                service4,
                LocalDateTime.now().plusDays(3),
                OrderStatus.CREATED
        ));

        orderService.save(createOrder(
                user2,
                service5,
                LocalDateTime.now().plusDays(7),
                OrderStatus.CREATED
        ));

        orderService.save(createOrder(
                user3,
                service3,
                LocalDateTime.now().minusDays(3),
                OrderStatus.SUCCEEDED
        ));

        orderService.save(createOrder(
                user3,
                service5,
                LocalDateTime.now().minusDays(5),
                OrderStatus.SUCCEEDED
        ));

    }

    private User createUser(String firstName, String lastName, String email, String password, String role) {
        User user = new User();
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setEmail(email);
        user.setPassword(password);
        user.setRole(role);
        return user;
    }

    private Service createService(String serviceName, String description, double price, User user) {
        Service service = new Service();
        service.setName(serviceName);
        service.setDescription(description);
        service.setPrice(price);
        service.setUser(user);
        return service;
    }

    private Order createOrder(User user, Service service, LocalDateTime date, OrderStatus status) {
        Order order = new Order();
        order.setService(service);
        order.setUser(user);
        order.setPrice(service.getPrice());
        order.setOrderDate(date);
        order.setOrderStatus(status);
        return order;
    }
}
