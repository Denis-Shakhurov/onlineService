package controller;

import config.Provider;
import dto.BasePage;
import dto.UserPage;
import dto.UsersPage;
import io.javalin.http.Context;
import io.javalin.http.HttpStatus;
import io.javalin.http.NotFoundResponse;
import model.User;
import service.UserService;

import java.util.List;

import static io.javalin.rendering.template.TemplateUtil.model;

    public class UserController extends BaseController {
        private final Provider provider = new Provider();
        private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    public void index(Context ctx) {
        List<User> users = userService.getAll();
        UsersPage usersPage = new UsersPage(users);

        ctx.status(HttpStatus.OK);
        ctx.render("users/index.jte", model(PAGE, usersPage));
     }

     public void indexEdit(Context ctx) {
        Integer id = Integer.parseInt(ctx.pathParam(ID));

        BasePage basePage = new BasePage();

        userService.findById(id).ifPresent(user -> addUserInfoInBasePage(basePage, user));

        ctx.status(HttpStatus.OK);
        ctx.render("users/edit.jte", model(PAGE, basePage));
    }

    public void show(Context ctx) {
        Integer id = Integer.parseInt(ctx.pathParam(ID));
        User user = userService.findById(id)
                .orElseThrow(() -> new NotFoundResponse("User with id " + id + " not found"));

        UserPage userPage = new UserPage(user);
        addUserInfoInBasePage(userPage, user);

        ctx.status(HttpStatus.OK);
        ctx.render("users/show.jte", model(PAGE, userPage));
    }

    public void create(Context ctx, String role) {
        String firstName = ctx.formParam("firstName");
        String lastName = ctx.formParam("lastName");
        String email = ctx.formParam("email");
        String password = ctx.formParam("password");

        User user = new User();

        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setEmail(email);
        user.setPassword(password);
        user.setRole(role);

        Integer id = userService.save(user).getId();

        ctx.cookie(USER_ID, String.valueOf(id));
        addTokenInCookie(ctx, user, provider);

        ctx.status(HttpStatus.CREATED);
        ctx.redirect(namedRoutes.getUserPath(id));
    }

    public void update(Context ctx) {
        Integer id = Integer.parseInt(ctx.pathParam(ID));
        User user = userService.findById(id)
                .orElseThrow(() -> new NotFoundResponse("User with id " + id + " not found"));

        String firstName = ctx.formParam("firstName");
        String lastName = ctx.formParam("lastName");
        String email = ctx.formParam("email");
        String password = ctx.formParam("password");
        String role = user.getRole();

        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setEmail(email);
        user.setPassword(password);
        user.setRole(role);

        User updatedUser = userService.update(user);

        ctx.cookie(USER_ID, String.valueOf(id));
        addTokenInCookie(ctx, updatedUser, provider);

        ctx.status(HttpStatus.OK);
        ctx.redirect(namedRoutes.getUserPath(id));
    }
}
