package org.example.controller;

import org.example.config.Provider;
import org.example.dto.BasePage;
import io.javalin.http.Context;
import lombok.Getter;
import lombok.Setter;
import org.example.model.User;
import org.example.model.UserInfo;
import org.example.utils.NamedRoutes;

@Getter
@Setter
public class BaseController {
    protected NamedRoutes namedRoutes = new NamedRoutes();

    protected final String PAGE = "page";
    protected final String ID = "id";
    protected final String USER_ID = "userId";
    protected final String JWT = "jwt";
    protected final String FLASH = "flash";

    protected void addUserInfoInBasePage(BasePage basePage, User user) {
        UserInfo userInfo = UserInfo.builder()
                .id(user.getId())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .role(user.getRole())
                .build();

        basePage.setUserInfo(userInfo);
    }

    protected void addTokenInCookie(Context ctx, User user, Provider provider) {
        String token = provider.create().generateToken(user);
        ctx.cookie(JWT, token);
    }
}
