package controller;

import dto.BasePage;
import io.javalin.http.Context;

import static io.javalin.rendering.template.TemplateUtil.model;

public class RegistrationController extends BaseController {

    public void index(Context ctx) {
        BasePage basePage = new BasePage();
        basePage.setFlash(ctx.consumeSessionAttribute(FLASH));
        ctx.render("users/registration/registration.jte", model(PAGE, basePage));
    }

    public void indexUser(Context ctx) {
        BasePage basePage = new BasePage();
        basePage.setFlash(ctx.consumeSessionAttribute(FLASH));
        ctx.render("users/registration/registrationUser.jte", model(PAGE, basePage));
    }

    public void indexMaster(Context ctx) {
        BasePage basePage = new BasePage();
        basePage.setFlash(ctx.consumeSessionAttribute(FLASH));
        ctx.render("users/registration/registrationMaster.jte", model(PAGE, basePage));
    }
}
