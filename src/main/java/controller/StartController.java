package controller;

import dto.BasePage;
import io.javalin.http.Context;
import io.javalin.http.HttpStatus;
import io.javalin.http.UnauthorizedResponse;
import model.User;
import service.UserService;

import java.util.List;

import static io.javalin.rendering.template.TemplateUtil.model;

public class StartController extends BaseController{
    private final UserService userService;

    public StartController(UserService userService) {
        this.userService = userService;
    }

    public void index(Context ctx) {
        BasePage basePage = new BasePage();

        String userId = ctx.cookie(USER_ID);
        Integer id = userId != null && !userId.equals("") ? Integer.parseInt(userId) : null;

        if (id != null) {
            userService.findById(id).ifPresent(user -> addUserInfoInBasePage(basePage, user));
        }

        List<User> masters = userService.getAllByRole("master");
        basePage.setMasters(masters);
        basePage.setFlash(ctx.consumeSessionAttribute(FLASH));
        ctx.status(HttpStatus.OK);
        ctx.render("start.jte", model(PAGE, basePage));
    }
}
