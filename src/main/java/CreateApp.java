import controller.RegistrationController;
import controller.StartController;
import controller.UserController;
import gg.jte.ContentType;
import gg.jte.TemplateEngine;
import gg.jte.resolve.ResourceCodeResolver;
import io.javalin.Javalin;
import io.javalin.rendering.template.JavalinJte;
import service.UserService;

public class CreateApp {
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

        app.get("/users", userController::index);
        app.get("/users/{id}", userController::show);

        app.get("/", startController::index);

        app.get("/registration", registrationController::index);
        app.get("/registration/user", registrationController::indexUser);
        app.get("/registration/master", registrationController::indexMaster);
        app.post("/registration/user", ctx -> {userController.create(ctx, "user");});
        app.post("/registration/master", ctx -> {userController.create(ctx, "master");});

        return app;
    }
}
