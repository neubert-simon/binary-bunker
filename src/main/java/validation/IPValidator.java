package validation;

public abstract class IPValidator implements IL3Validator {

    @Override
    public abstract Number validate(String ip);
}
