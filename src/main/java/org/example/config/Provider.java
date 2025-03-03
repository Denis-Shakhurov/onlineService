package org.example.config;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import org.example.config.javalinjwt.JWTGenerator;
import org.example.config.javalinjwt.JWTProvider;
import org.example.model.User;

public class Provider {

    public JWTProvider<User> create() {
        JWTGenerator<User> generator = (user, alg) -> {
            JWTCreator.Builder token = JWT.create()
                    .withClaim("name", user.getFirstName())
                    .withClaim("role", user.getRole());
            return token.sign(alg);
        };

        Algorithm algorithm = Algorithm.HMAC256("very_secret");
        JWTVerifier verifier = JWT.require(algorithm).build();

        return new JWTProvider<>(algorithm, generator, verifier);
    }
}
