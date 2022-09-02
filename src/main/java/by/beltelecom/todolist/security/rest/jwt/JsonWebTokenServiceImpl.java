package by.beltelecom.todolist.security.rest.jwt;

import by.beltelecom.todolist.configuration.properties.SecurityProperties;
import by.beltelecom.todolist.utilities.logging.Checks;
import by.beltelecom.todolist.utilities.logging.SpringLogging;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;
import java.util.Objects;

/**
 * Default implementation of {@link JsonWebTokenService} security service bean.
 */
public class JsonWebTokenServiceImpl implements JsonWebTokenService {

    private final String jwtSecretKey; // Initialized in constructor;
    private final String jwtSubject; // Initialized in constructor;
    private final String jwtIssuer; // Initialized in constructor;

    private final String jwtClaimName = "email";

    private static final Logger LOGGER = LoggerFactory.getLogger(JsonWebTokenServiceImpl.class); // Logger;

    /**
     * Construct new {@link JsonWebTokenServiceImpl} security service bean.
     * @param aJwtConfigurationProperties - JWT configuration properties.
     */
    public JsonWebTokenServiceImpl(SecurityProperties.Jwt aJwtConfigurationProperties) {
        Objects.requireNonNull(aJwtConfigurationProperties,
                Checks.argumentNotNull("aJwtConfigurationProperties", SecurityProperties.Jwt.class));

        LOGGER.debug(SpringLogging.Creation.createBean(JsonWebTokenServiceImpl.class));

        // Map properties:
        this.jwtSecretKey = aJwtConfigurationProperties.getSecretKey().isEmpty() ?
                SecurityProperties.Jwt.DEFAULT_SECRET_KEY : aJwtConfigurationProperties.getSecretKey();
        this.jwtSubject = aJwtConfigurationProperties.getSubject().isEmpty() ?
                SecurityProperties.Jwt.DEFAULT_SUBJECT : aJwtConfigurationProperties.getSubject();
        this.jwtIssuer = aJwtConfigurationProperties.getIssuer().isEmpty() ?
                SecurityProperties.Jwt.DEFAULT_ISSUER : aJwtConfigurationProperties.getIssuer();
    }

    @Override
    public String generateToken(String aEmail) {
        Objects.requireNonNull(aEmail, Checks.argumentNotNull("aEmail", String.class));

        return JWT.create()
                .withSubject(this.jwtSubject)
                .withClaim(this.jwtClaimName, aEmail)
                .withIssuedAt(new Date())
                .withIssuer(this.jwtIssuer)
                .sign(Algorithm.HMAC256(this.jwtSecretKey));
    }

    @Override
    public String verifyToken(String aToken) {
        JWTVerifier verifier = JWT.require(Algorithm.HMAC256(this.jwtSecretKey))
                .withSubject(this.jwtSubject)
                .withIssuer(this.jwtIssuer).build();
        DecodedJWT decodedJWT = verifier.verify(aToken);
        return decodedJWT.getClaim(this.jwtClaimName).asString();
    }
}
