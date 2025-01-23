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
import org.example.model.Role;
import org.example.model.Service;
import org.example.model.User;
import org.example.service.OrderService;
import org.example.service.ServiceService;
import org.example.service.UserService;
import org.example.utils.NamedRoutes;

import java.util.HashMap;
import java.util.Map;

public class CreateApp {
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

        Handler decodeHandler = JavalinJWT.createCookieDecodeHandler(providerUser);
        // create the access manager
        Map<String, RouteRole> rolesMapping = new HashMap<>() {{
            put("user", Role.USER);
            put("master", Role.MASTER);
            put("admin", Role.ADMIN);
        }};

        JWTAccessManager accessManager = new JWTAccessManager("role", rolesMapping, Role.GUEST);

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
        Service service = new Service();
        service.setName("Стрижка");
        service.setDescription("Стрижка на высшем уровне");
        service.setPrice(1500d);
        service.setUser(master1);

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
}
