package by.beltelecom.todolist.configuration.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;

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

    @Getter @Setter
    public static class Jwt {

        public static final String DEFAULT_SECRET_KEY = "DEFAULT_SECRET_KEY";
        public static final String DEFAULT_SUBJECT = "User Details";
        public static final String DEFAULT_ISSUER = "to.do";

        private String secretKey = Jwt.DEFAULT_SECRET_KEY;

        private String subject = Jwt.DEFAULT_SUBJECT;

        private String issuer = Jwt.DEFAULT_ISSUER;

    }

}
