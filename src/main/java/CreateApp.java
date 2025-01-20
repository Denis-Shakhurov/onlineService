import controller.*;
import gg.jte.ContentType;
import gg.jte.TemplateEngine;
import gg.jte.resolve.ResourceCodeResolver;
import io.javalin.Javalin;
import io.javalin.rendering.template.JavalinJte;
import service.OrderService;
import service.ServiceService;
import service.UserService;
import utils.NamedRoutes;

public class CreateApp {
    private final NamedRoutes namedRoutes = new NamedRoutes();
    private final UserService userService = new UserService();
    private final OrderService orderService = new OrderService();
    private final ServiceService serviceService = new ServiceService();
    private final UserController userController = new UserController(userService, orderService);
    private final ServiceController serviceController = new ServiceController(serviceService, userService);
    private final RegistrationController registrationController = new RegistrationController();
    private final StartController startController = new StartController(userService);
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

        app.get(namedRoutes.getUsersPath(), userController::index);
        app.get(namedRoutes.getUserPath("{id}"), userController::show);
        app.get(namedRoutes.getEditUserPath("{id}"), userController::indexEdit);
        app.post(namedRoutes.getEditUserPath("{id}"), userController::update);
        app.post(namedRoutes.getLoginUserPath(), userController::login);
        app.get(namedRoutes.getLoginUserPath(), userController::indexLogin);
        app.get(namedRoutes.getLogoutUserPath(), userController::logout);

        app.get(namedRoutes.getServicesByUserPath("{id}"), serviceController::index);
        app.post(namedRoutes.getServicesPath(), serviceController::create);
        app.get(namedRoutes.getServicesPath(), serviceController::indexCreate);
        app.get(namedRoutes.getEditServicesPath("{id}"), serviceController::indexEdit);
        app.post(namedRoutes.getEditServicesPath("{id}"), serviceController::update);

        app.get(namedRoutes.getOrdersByServicePath("{id}"), orderController::indexCreate);
        app.post(namedRoutes.getOrdersByServicePath("{id}"), orderController::create);

        app.get(namedRoutes.getStartPath(), startController::index);

        app.get(namedRoutes.getRegistrationPath(), registrationController::index);
        app.get(namedRoutes.getRegistrationUserPath(), registrationController::indexUser);
        app.get(namedRoutes.getRegistrationMasterPath(), registrationController::indexMaster);
        app.post(namedRoutes.getRegistrationUserPath(), ctx -> {userController.create(ctx, "user");});
        app.post(namedRoutes.getRegistrationMasterPath(), ctx -> {userController.create(ctx, "master");});

        return app;
    }
}
