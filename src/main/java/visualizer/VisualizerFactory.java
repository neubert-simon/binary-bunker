package visualizer;

import exceptions.InvalidIPException;
import ip.I_IP;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class VisualizerFactory {

    private static final Logger log = LoggerFactory.getLogger(VisualizerFactory.class);

    /**
     * Creates instances of the Visualizer for IPv4 Addresses
     * @param ipObject The Address which needs to be visualized
     * @return The new Instance from VisualizerV4
     * @throws InvalidIPException Checks for the right IP-Address format
     */
    public static IVisualizer createVisualizerV4(I_IP ipObject) throws InvalidIPException {
        log.info("Creating VisualizerV4 Object from I_IP Object: {}", ipObject);
        return new VisualizerV4(ipObject);
    }

    /**
     * Creates instances of the Visualizer for IPv6 Addresses
     * @param ipObject The Address which needs to be visualized
     * @return The new Instance from VisualizerV6
     * @throws InvalidIPException Checks for the right IP-Address format
     */
    public static IVisualizer createVisualizerV6(I_IP ipObject) throws InvalidIPException {
        log.info("Creating VisualizerV6 Object from I_IP Object: {}", ipObject);
        return new VisualizerV6(ipObject);
    }
}
