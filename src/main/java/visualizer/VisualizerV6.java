package visualizer;

import enumerations.ip.IPType;
import ip.I_IP;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Visualizer IPv6 Object
 */

class VisualizerV6 extends Visualizer {

    private static final Logger log = LoggerFactory.getLogger(VisualizerV6.class);

    VisualizerV6(I_IP ipObject) {
        super(ipObject, IPType.IPv6);
        log.info("Created VisualizerV6 Object with I_IP Object: {}", ipObject);
    }

}
