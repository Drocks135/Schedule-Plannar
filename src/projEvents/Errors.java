package projEvents;

public class Errors {

    private static boolean newError = false;
    private static String error = null;
    private static Errors instance = null;

    private Errors() {

    }

    public static Errors getInstance() {
        if (instance == null) {
            Errors instance = new Errors();
        }
        return instance;
    }

    public static Void setError(String errors) {
        newError = true;
        error = errors;
        return null;
    }

    public static boolean getBool(){
        return newError;
    }
    public static String getError() {
        if (newError == true) {
            newError = false;
            return error;
        }
        else
            return null;
    }
}