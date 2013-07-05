package eu.cloudtm.controller.oracles;

import eu.cloudtm.controller.Controller;
import eu.cloudtm.controller.exceptions.OracleException;
import eu.cloudtm.controller.model.KPI;

/**
 * Created by: Fabio Perfetti
 * E-mail: perfabio87@gmail.com
 * Date: 6/24/13
 */
public class Morpher extends AbstractOracle {

    private static final String directory = "/home/fabio/Desktop/simulatore/DAGSwithCubist/Debug";
    private static final String script = "/home/fabio/Desktop/simulatore/DAGSwithCubist/Debug/DAGSwithCubist";

    Process p = null;

    public Morpher(Controller _controller) {
        super(_controller);
    }

    @Override
    public KPI forecast(OracleInput sample, int numNodes, int numThreads) throws OracleException {
        return null;
    }
}
