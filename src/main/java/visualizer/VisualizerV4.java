package visualizer;

import enumerations.ip.IPType;
import ip.I_IP;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Visualizer IPv4 Object
 */

class VisualizerV4 extends Visualizer {

    private static final Logger log = LoggerFactory.getLogger(VisualizerV4.class);

    VisualizerV4(I_IP ipObject){
        super(ipObject, IPType.IPv4);
        log.info("Created VisualizerV4 Object with I_IP Object: {}", ipObject);
    }

}

