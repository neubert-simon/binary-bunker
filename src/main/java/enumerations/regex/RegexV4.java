package enumerations.regex;

public enum RegexV4 {

    LENGTH("^.{7,15}$", "\"IP must be between 7 and 15 characters long (including separators."),
    SEPARATORS("^[^.]*\\.[^.]*\\.[^.]*\\.[^.]*$", "IP must include exactly three separators [.]"),
    SEPARATOR_UNPAIRED("^[^.]*([^.]\\.[^.]*)*$", "There must be an integer between every separator."),
    FORMAT("([0-9]{1,3}\\.){3}[0-9]{1,3}", "IP must be exactly 4 integers (max. 3 digits)."),
    VALUES("^([0-9]|[0-9]{2}|0[0-9]{2}|1[0-9]{2}|2[0-4][0-9]|25[0-5])(\\.([0-9]|[0-9]{2}|0[0-9]{2}|1[0-9]{2}|2[0-4][0-9]|25[0-5]))*$", "Every integer must be between 0 and 255 (inclusive)."),
    ;
    public final String regex, errorMessage;

    RegexV4(final String regex, final String errorMessage) {
        this.regex = regex;
        this.errorMessage = errorMessage;
    }
}