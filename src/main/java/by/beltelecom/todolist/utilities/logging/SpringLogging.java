package by.beltelecom.todolist.utilities.logging;

public class SpringLogging {

    public static class Autowiring {

        private static final String AUTOWIRE_IN_CONFIGURATION = "Autowire [%s] bean in [%s] configuration class.";

        public static String autowireInConfiguration(Class<?> aBean, Class<?> aConfigurationClass) {
            return String.format(Autowiring.AUTOWIRE_IN_CONFIGURATION,
                    aBean.getCanonicalName(), aConfigurationClass.getCanonicalName());
        }

    }

    public static class Creation {
        private static final String START_CREATE_BEAN = "Create new Spring bean[%s];";

        public static String createBean(Class<?> aBeanType) {
            return String.format(Creation.START_CREATE_BEAN, aBeanType.getCanonicalName());
        }
    }
}
