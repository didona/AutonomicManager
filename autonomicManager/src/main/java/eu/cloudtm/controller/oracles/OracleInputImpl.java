package eu.cloudtm.controller.oracles;

import eu.cloudtm.common.WPMParam;
import eu.cloudtm.controller.model.utils.Forecaster;
import eu.cloudtm.stats.WPMSample;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: fabio
 * Date: 7/5/13
 * Time: 12:21 PM
 * To change this template use File | Settings | File Templates.
 */
public class OracleInputImpl implements OracleInput {

    private WPMSample sample;

    private int numNodes;

    private int numThreads;

    private List<Forecaster> oracoles;

    public OracleInputImpl(){

    }

    @Override
    public double getAggregate(WPMParam param, int classIdx) {
        return sample.getAggregate(param, classIdx);
    }
}
