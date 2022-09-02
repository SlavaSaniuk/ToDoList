package by.beltelecom.todolist.security.rest.jwt;

/**
 * {@link JsonWebTokenService} service configuration bean used to generate and validate JWT tokens.
 */
public interface JsonWebTokenService {

    /**
     * Generate new JWN token for specified email.
     * @param aEmail - {@link String} email.
     * @return - {@link String} JWT token.
     */
    String generateToken(String aEmail);

    /**
     * Verify JWT token and return email.
     * @param aToken - {@link String} JWT token.
     * @return - {@link String} email.
     */
    String verifyToken(String aToken);
}
