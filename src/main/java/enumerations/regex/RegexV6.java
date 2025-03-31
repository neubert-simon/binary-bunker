package enumerations.regex;

public enum RegexV6 {

    VALID_CHARACTERS("^[0-9a-fA-F:]+$", "Address containes illegal characters"),
    VALID_LENGTH("^.{2,39}$", "Address must be between 2 and 39 characters long"),
    VALID_COLON_START("^::.*$|^[^:].*$", "Address starts with single colon"),
    VALID_COLON_END("^.*::$|^.*[^:]$", "Address ends with single colon"),
    COLON_REPETITION_THRICE("^(?!.*:{3,}).*$", "Address contains a tripple colon (:::)"),
    DOUBLE_COLON_REPETITION_TWICE("^(?!(.*:{2}).*\\1).*$", "Address contains several double colons (::)"), // check doesnt always work
    CONTAINS_SEVEN_COLONS("^([^:]*:[^:]*){7}$|^.*::.*$", "Address contains to few colons and no shortening (::)"),
    MAX_SEVEN_COLONS("^([^:]*:[^:]*){0,7}$", "Address contains to many colons")
    ;


    public final String regex, errorMessage;

    RegexV6(final String regex, final String errorMessage) {
        this.regex = regex;
        this.errorMessage = errorMessage;
    }

}
