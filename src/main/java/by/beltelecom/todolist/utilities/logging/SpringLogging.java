package by.beltelecom.todolist.utilities.logging;

public class SpringLogging {

    public static class Autowiring {

        private static final String AUTOWIRE_IN_CONFIGURATION = "Autowire [%s] bean in [%s] configuration class.";

        public static String autowireInConfiguration(Class<?> aBean, Class<?> aConfigurationClass) {
            return String.format(Autowiring.AUTOWIRE_IN_CONFIGURATION,
                    aBean.getCanonicalName(), aConfigurationClass.getCanonicalName());
        }

    }
}
