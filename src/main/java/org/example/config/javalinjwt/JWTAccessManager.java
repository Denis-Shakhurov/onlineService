package org.example.config.javalinjwt;

import com.auth0.jwt.interfaces.DecodedJWT;
import io.javalin.http.Context;
import io.javalin.http.Handler;
import io.javalin.http.HttpStatus;
import io.javalin.security.RouteRole;
import org.jetbrains.annotations.NotNull;

import java.util.Map;
import java.util.Optional;
import java.util.Set;

public class JWTAccessManager implements Handler {
    private final String userRoleClaim;
    private final Map<String, RouteRole> rolesMapping;
    private final RouteRole defaultRole;
    private final JavalinJWT javalinJWT;

    public JWTAccessManager(JavalinJWT javalinJWT, String userRoleClaim, Map<String, RouteRole> rolesMapping, RouteRole defaultRole) {
        this.userRoleClaim = userRoleClaim;
        this.rolesMapping = rolesMapping;
        this.defaultRole = defaultRole;
        this.javalinJWT = javalinJWT;
    }

    private RouteRole extractRole(Context context) {
        if (!javalinJWT.containsJWT(context)) {
            return defaultRole;
        }

        DecodedJWT jwt = javalinJWT.getDecodedFromContext(context);

        String userLevel = jwt.getClaim(userRoleClaim).asString();

        return Optional.ofNullable(rolesMapping.get(userLevel)).orElse(defaultRole);
    }

    @Override
    public void handle(@NotNull Context context) {
        RouteRole role = extractRole(context);
        Set<RouteRole> permittedRoles = context.routeRoles();
        if (!permittedRoles.contains(role)) {
            context.sessionAttribute("flash", "Необходимо аторизироваться");
            context.status(HttpStatus.UNAUTHORIZED);
            context.redirect("/");
        }
    }
}
