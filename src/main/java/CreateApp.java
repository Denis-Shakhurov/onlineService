import controller.RegistrationController;
import controller.StartController;
import controller.UserController;
import gg.jte.ContentType;
import gg.jte.TemplateEngine;
import gg.jte.resolve.ResourceCodeResolver;
import io.javalin.Javalin;
import io.javalin.rendering.template.JavalinJte;
import service.UserService;
import utils.NamedRoutes;

public class CreateApp {
    private final NamedRoutes namedRoutes = new NamedRoutes();
    private final UserService userService = new UserService();
    private final UserController userController = new UserController(userService);
    private final RegistrationController registrationController = new RegistrationController();
    private final StartController startController = new StartController(userService);

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

        app.get(namedRoutes.getStartPath(), startController::index);

        app.get(namedRoutes.getRegistrationPath(), registrationController::index);
        app.get(namedRoutes.getRegistrationUserPath(), registrationController::indexUser);
        app.get(namedRoutes.getRegistrationMasterPath(), registrationController::indexMaster);
        app.post(namedRoutes.getRegistrationUserPath(), ctx -> {userController.create(ctx, "user");});
        app.post(namedRoutes.getRegistrationMasterPath(), ctx -> {userController.create(ctx, "master");});

        return app;
    }
}
