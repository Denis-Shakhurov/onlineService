package controller;

import dto.BasePage;
import dto.UserPage;
import io.javalin.http.Context;
import io.javalin.http.HttpStatus;
import io.javalin.http.NotFoundResponse;
import model.Service;
import model.User;
import service.ServiceService;
import service.UserService;

import java.util.List;

import static io.javalin.rendering.template.TemplateUtil.model;

public class ServiceController extends BaseController {
    private final ServiceService serviceService;
    private final UserService userService;

    public ServiceController(ServiceService serviceService, UserService userService) {
        this.serviceService = serviceService;
        this.userService = userService;
    }

    public void index(Context ctx) {
        Integer id = Integer.parseInt(ctx.pathParam(ID));

        List<Service> services = serviceService.findAllForUser(id);
        User user = userService.findById(id)
                .orElseThrow(() -> new NotFoundResponse("Service not found"));

        UserPage userPage = new UserPage();
        addUserInfoInBasePage(userPage, user);
        userPage.setServices(services);
        userPage.setUser(user);

        ctx.status(HttpStatus.OK);
        ctx.render("services/index.jte", model(PAGE, userPage));
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

    public void create(Context ctx) {
        String userId = ctx.cookie(USER_ID);
        Integer id = userId != null && !userId.equals("") ? Integer.parseInt(userId) : null;
        String name = ctx.formParam("name");
        String description = ctx.formParam("description");
        Double price = Double.valueOf(ctx.formParam("price"));
        User user = userService.findById(id)
                .orElseThrow(() -> new NotFoundResponse("User not found"));

        Service service = new Service();
        service.setName(name);
        service.setPrice(price);
        service.setUser(user);
        service.setDescription(description);

        serviceService.save(service);

        ctx.status(HttpStatus.CREATED);
        ctx.redirect(namedRoutes.getServicesByUserPath(id));
    }
}
