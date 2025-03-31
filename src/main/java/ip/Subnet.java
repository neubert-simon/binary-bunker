package ip;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utility.ParameterValidation;

import java.util.Objects;

/**
 * Abstract representation of a subnets
 */
public abstract class Subnet implements ISubnet {

    private static final Logger log = LoggerFactory.getLogger(Subnet.class);

    private final int prefix;

    Subnet(final int prefix) {
        ParameterValidation.validateParameters(prefix < 0, log, "Prefix can't be lesser than 0.");
        this.prefix = prefix;
    }

    //region Getter
    public int getPrefix() {
        return prefix;
    }
    //endregion

    //region java.lang.Object Overrides
    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (!(o instanceof Subnet subnet)) return false;
        return prefix == subnet.getPrefix();
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(prefix);
    }
    //endregion
}