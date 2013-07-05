package eu.cloudtm.controller.oracles;

import eu.cloudtm.StatsManager;
import eu.cloudtm.common.WPMParam;
import eu.cloudtm.stats.Sample;

/**
 * Created with IntelliJ IDEA.
 * User: fabio
 * Date: 7/5/13
 * Time: 12:11 PM
 * To change this template use File | Settings | File Templates.
 */
public interface OracleInput {

    public double getAggregate(WPMParam param, int classIdx);
}
