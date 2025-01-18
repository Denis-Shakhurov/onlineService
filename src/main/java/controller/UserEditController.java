package controller;

import dto.BasePage;
import io.javalin.http.Context;
import io.javalin.http.HttpStatus;
import service.UserService;

import static io.javalin.rendering.template.TemplateUtil.model;

public class UserEditController extends BaseController {
    private final UserService userService;

    public UserEditController(UserService userService) {
        this.userService = userService;
    }

    public void index(Context ctx) {
        Integer id = Integer.parseInt(ctx.pathParam(ID));

        BasePage basePage = new BasePage();

        userService.findById(id).ifPresent(user -> addUserInfoInBasePage(basePage, user));

        ctx.status(HttpStatus.OK);
        ctx.render("users/edit.jte", model(PAGE, basePage));
    }
}
