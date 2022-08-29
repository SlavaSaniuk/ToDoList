package by.beltelecom.todolist.configuration.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;

@Getter @Setter
@ConfigurationProperties("to.do.security")
public class SecurityProperties {

    private String helloString = "default-value";

    @NestedConfigurationProperty
    private Passwords passwords;

    @Getter @Setter
    public static class Passwords {

        private int minLength = 1;
        private boolean useNumbers = false;
        private boolean useUppercaseLetter = false;
        private boolean useSpecialSymbols = false;
    }

}
