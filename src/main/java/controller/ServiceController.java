package controller;

import dto.BasePage;
import dto.UserPage;
import io.javalin.http.Context;
import io.javalin.http.HttpStatus;
import io.javalin.http.NotFoundResponse;
import io.javalin.http.UnauthorizedResponse;
import model.Service;
import model.User;
import service.ServiceService;
import service.UserService;

import java.util.List;
import java.util.Objects;

import static io.javalin.rendering.template.TemplateUtil.model;

public class ServiceController extends BaseController {
    private final ServiceService serviceService;
    private final UserService userService;

    public ServiceController(ServiceService serviceService, UserService userService) {
        this.serviceService = serviceService;
        this.userService = userService;
    }

    public void index(Context ctx) {
        if (!ctx.cookie(USER_ID).equals("")) {
            int masterId = Integer.parseInt(ctx.pathParam(ID));
            int userId = Integer.parseInt(Objects.requireNonNull(ctx.cookie(USER_ID)));


            List<Service> services = serviceService.findAllForUser(masterId);
            User user = userService.findById(userId)
                    .orElseThrow(() -> new NotFoundResponse("Service not found"));

            UserPage userPage = new UserPage();
            userPage.setServices(services);
            userPage.setUser(user);
            userPage.setFlash(ctx.consumeSessionAttribute(FLASH));

            ctx.status(HttpStatus.OK);
            ctx.render("services/index.jte", model(PAGE, userPage));
    } else {
            ctx.sessionAttribute(FLASH, "Необходимо авторизироваться");
            ctx.status(HttpStatus.UNAUTHORIZED);
            ctx.redirect(namedRoutes.getStartPath());
        }
    }

    public void indexCreate(Context ctx) {
        BasePage basePage = new BasePage();

        String userId = ctx.cookie(USER_ID);
        Integer id = userId != null && !userId.equals("") ? Integer.parseInt(userId) : null;

        if (id != null) {
            userService.findById(id).ifPresent(user -> addUserInfoInBasePage(basePage, user));
        }

        ctx.status(HttpStatus.OK);
        ctx.render("services/create.jte", model(PAGE, basePage));
    }

    public void indexEdit(Context ctx) {
        int id = Integer.parseInt(ctx.pathParam(ID));
        Service service = serviceService.findById(id)
                        .orElseThrow(() -> new NotFoundResponse("Service not found"));

        int userId = service.getUser().getId();
        User user = service.getUser();

        BasePage basePage = new BasePage();

        addUserInfoInBasePage(basePage, user);
        basePage.setService(service);

        ctx.status(HttpStatus.OK);
        ctx.render("services/edit.jte", model(PAGE, basePage));
    }

    public void create(Context ctx) {
        String userId = ctx.cookie(USER_ID);
        Integer id = userId != null && !userId.equals("") ? Integer.parseInt(userId) : null;
        String name = ctx.formParam("name");
        String description = ctx.formParam("description");
        Double price = Double.valueOf(Objects.requireNonNull(ctx.formParam("price")));
        User user = userService.findById(id)
                .orElseThrow(() -> new NotFoundResponse("User not found"));

        Service service = new Service();
        service.setName(name);
        service.setPrice(price);
        service.setUser(user);
        service.setDescription(description);

        serviceService.save(service);

        ctx.sessionAttribute(FLASH, "Услуга добавлена");
        ctx.status(HttpStatus.CREATED);
        ctx.redirect(namedRoutes.getServicesByUserPath(id));
    }

    public void update(Context ctx) {
        int id = Integer.parseInt(ctx.pathParam(ID));

        Service service = serviceService.findById(id)
                .orElseThrow(() -> new NotFoundResponse("Service not found"));

        int userId = service.getUser().getId();
        String name = Objects.equals(ctx.formParam("name"), "")
                ? service.getName()
                : ctx.formParam("name");
        String description = Objects.equals(ctx.formParam("description"), "")
                ? service.getDescription()
                : ctx.formParam("description");
        Double price = Objects.equals(ctx.formParam("price"), "")
                ? service.getPrice()
                : Double.valueOf(Objects.requireNonNull(ctx.formParam("price")));

        service.setName(name);
        service.setDescription(description);
        service.setPrice(price);
        service.setUser(userService.findById(userId).orElse(null));

        serviceService.update(service);

        ctx.sessionAttribute(FLASH, "Изменения сохранены");
        ctx.status(HttpStatus.OK);
        ctx.redirect(namedRoutes.getServicesByUserPath(userId));
    }
}
