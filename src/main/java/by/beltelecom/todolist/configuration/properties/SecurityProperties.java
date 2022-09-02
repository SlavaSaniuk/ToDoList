package by.beltelecom.todolist.configuration.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;

/**
 * {@link SecurityProperties} configuration properties bean hold all security configuration properties.
 * Security configuration propertied defined in application.properties resource with prefix: "to.do.security".
 */
@Getter @Setter
@ConfigurationProperties(value = "to.do.security", ignoreInvalidFields = true)
public class SecurityProperties {

    @NestedConfigurationProperty
    private Passwords passwords;
    @NestedConfigurationProperty
    private Jwt jwt;

    @Getter @Setter
    public static class Passwords {

        private int minLength = 1;
        private boolean useNumbers = false;
        private boolean useUppercaseLetter = false;
        private boolean useSpecialSymbols = false;
    }

    /**
     * {@link Jwt} static class represent a Json web token configuration properties.
     * JWT properties defined in application.properties with prefix "to.do.security.properties".
     */
    @Getter @Setter
    public static class Jwt {

        /**
         * Default value of secret-key property is "DEFAULT_SECRET_KEY".
         */
        public static final String DEFAULT_SECRET_KEY = "DEFAULT_SECRET_KEY";
        /**
         * Default value of subject property is "User Details".
         */
        public static final String DEFAULT_SUBJECT = "User Details";
        /**
         * Default value of issuer property is "to.do";
         */
        public static final String DEFAULT_ISSUER = "to.do";

        private String secretKey = Jwt.DEFAULT_SECRET_KEY;

        private String subject = Jwt.DEFAULT_SUBJECT;

        private String issuer = Jwt.DEFAULT_ISSUER;

    }

}
