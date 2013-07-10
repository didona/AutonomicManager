package eu.cloudtm;

import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 * User: fabio
 * Date: 7/8/13
 * Time: 3:57 PM
 * To change this template use File | Settings | File Templates.
 */
public interface Sample {

    public long getId();

    public Set<String> getNodes();

    public double getPerNodeParam(WPMParam param, int classIdx, String nodeIp);

    public double getAvgParam(WPMParam param, int classIdx);

}