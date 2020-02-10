public class GeneralData {
    public static String CHANGE_ROOM_COMMAND = "/r";
    public static String CLOSE_COMMAND = "/e";
    public static String DELIMITER = ":/:";
    public static String INITIALIZE_COMMAND = "/i";

    public static final String DEFAULT_ROOM_NAME = "main";


    private static String SMILE_COMMAND = "(smile)";
    private static String SMILE_REPLACE = ":)";
    private static String EXCITED_COMMAND = "(excited)";
    private static String EXCITED_REPLACE = ":D";

    /**
     * Takes a provided message and replaces commands with emoji's
     * @param message Message to convert
     * @return
     */
    public static String commandReplacemer (String message) {
        //System.out.println("Running replacer: " + message);
        message = message.replace(SMILE_COMMAND, SMILE_REPLACE);
        message = message.replace(EXCITED_COMMAND, EXCITED_REPLACE);
        return message;
    }
}
