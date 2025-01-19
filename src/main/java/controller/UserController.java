package controller;

import com.lambdaworks.crypto.SCryptUtil;
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

    public void indexLogin(Context ctx) {
        BasePage basePage = new BasePage();

        basePage.setFlash(ctx.consumeSessionAttribute(FLASH));
        ctx.status(HttpStatus.OK);
        ctx.render("users/login.jte", model(PAGE, basePage));
    }

    public void show(Context ctx) {
        Integer id = Integer.parseInt(ctx.pathParam(ID));
        User user = userService.findById(id)
                .orElseThrow(() -> new NotFoundResponse("User with id " + id + " not found"));

        UserPage userPage = new UserPage();
        userPage.setUser(user);
        addUserInfoInBasePage(userPage, user);

        userPage.setFlash(ctx.consumeSessionAttribute(FLASH));
        ctx.status(HttpStatus.OK);
        ctx.render("users/show.jte", model(PAGE, userPage));
    }

    public void create(Context ctx, String role) {
        User user = new User();

        getFormParamAndSetUser(ctx, user);
        user.setRole(role);

        if (!userService.existsByEmail(user.getEmail())) {
            Integer id = userService.save(user).getId();

            ctx.cookie(USER_ID, String.valueOf(id));
            addTokenInCookie(ctx, user, provider);

            ctx.status(HttpStatus.CREATED);
            ctx.redirect(namedRoutes.getUserPath(id));
        } else {
            ctx.sessionAttribute(FLASH, "Пользователь с " + user.getEmail() + " уже зарегистрирован");
            ctx.status(HttpStatus.BAD_REQUEST);
            ctx.redirect(namedRoutes.getRegistrationPath());
        }
    }

    public void update(Context ctx) {
        Integer id = Integer.parseInt(ctx.pathParam(ID));
        User user = userService.findById(id)
                .orElseThrow(() -> new NotFoundResponse("User with id " + id + " not found"));

        getFormParamAndSetUser(ctx, user);

        if (!userService.existsByEmail(user.getEmail())) {
            User updatedUser = userService.update(user);

            ctx.cookie(USER_ID, String.valueOf(id));
            addTokenInCookie(ctx, updatedUser, provider);

            ctx.sessionAttribute(FLASH, "Изменения сохранены");
            ctx.status(HttpStatus.OK);
            ctx.redirect(namedRoutes.getUserPath(id));
        } else {
            ctx.sessionAttribute(FLASH, "Пользователь с " + user.getEmail() + " уже зарегистрирован");
            ctx.status(HttpStatus.BAD_REQUEST);
            ctx.redirect(namedRoutes.getRegistrationPath());
        }
    }

    public void login(Context ctx) {
        String email = ctx.formParam("email");
        String password = ctx.formParam("password");

        User user = userService.findByEmail(email).orElse(null);
        String hashedPassword = user != null ? user.getPassword() : null;

        if (user != null && SCryptUtil.check(password, hashedPassword)) {
            ctx.cookie(USER_ID, String.valueOf(user.getId()));
            addTokenInCookie(ctx, user, provider);

            ctx.sessionAttribute(FLASH, "Привет " + user.getFirstName() + "!");
            ctx.status(HttpStatus.OK);
            ctx.redirect(namedRoutes.getUserPath(user.getId()));
        } else if (user != null && !SCryptUtil.check(password, hashedPassword)) {
            ctx.sessionAttribute(FLASH, "Не верный пароль");
            ctx.status(HttpStatus.BAD_REQUEST);
            ctx.redirect(namedRoutes.getLoginUserPath());
        } else if (user == null) {
            ctx.status(HttpStatus.BAD_REQUEST);
            ctx.sessionAttribute(FLASH, "Не верный email");
            ctx.redirect(namedRoutes.getLoginUserPath());
        }
    }

    public void logout(Context ctx) {
        ctx.sessionAttribute(FLASH, null);
        ctx.cookie(USER_ID, "");
        ctx.cookie(JWT, "");
        ctx.status(HttpStatus.OK);
        ctx.redirect(namedRoutes.getStartPath());
    }

    private void getFormParamAndSetUser(Context ctx, User user) {
        String firstName = ctx.formParam("firstName");
        String lastName = ctx.formParam("lastName");
        String email = ctx.formParam("email");
        String password = ctx.formParam("password");
        String role = user.getRole();

        String hashedPassword = SCryptUtil.scrypt(password, 2, 2, 2);

        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setEmail(email);
        user.setPassword(hashedPassword);
        user.setRole(role);
    }
}
