package by.beltelecom.todolist.utilities.logging;

public class Checks {

    private static final String argumentNotNull = "Required argument[%s] of type[%s] must be not null.";
    private static final String propertyOfArgumentNotNull = "Required property[%s] in argument[%s] of type[%s] must be not null.";

    public static String argumentNotNull(String aArgName, Class<?> aArgType) {
        return String.format(Checks.argumentNotNull, aArgName, aArgType.getCanonicalName());
    }

    public static String propertyOfArgumentNotNull(String aPropName, String aArgName, Class<?> aArgType) {
        return String.format(Checks.propertyOfArgumentNotNull, aPropName, aArgName, aArgType.getCanonicalName());
    }


    public static class Strings {
        private static final String NOT_EMPTY_STRING = "Required argument[%s] of type[%s] must be not empty.";

        public static String stringNotEmpty(String aArgName) {
            return String.format(NOT_EMPTY_STRING, aArgName, String.class.getCanonicalName());
        }

    }

    public static class Numbers {

        private static final String numberNotZero = "Argument[%s] of type[%s] must be not zero.";

        public static String argNotZero(String aName, Class<? extends Number> aType) {
            return String.format(Numbers.numberNotZero, aName, aType.getCanonicalName());
        }
    }
}
