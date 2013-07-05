package eu.cloudtm.common;

import eu.cloudtm.wpm.parser.ResourceType;

/**
 * Created with IntelliJ IDEA.
 * User: fabio
 * Date: 7/5/13
 * Time: 11:31 AM
 * To change this template use File | Settings | File Templates.
 */
public enum WPMParam {
    LocalReadOnlyTxLocalServiceTime(1, "LocalReadOnlyTxLocalServiceTime", ResourceType.JMX),
    LocalUpdateTxLocalServiceTime(2, "LocalUpdateTxLocalServiceTime", ResourceType.JMX),
    RetryWritePercentage(3, "RetryWritePercentage", ResourceType.JMX),
    SuxNumPuts(4, "SuxNumPuts", ResourceType.JMX),
    PrepareCommandBytes(5, "PrepareCommandBytes", ResourceType.JMX),
    RTT(6, "RTT", ResourceType.JMX),
    CommitBroadcastWallClockTime(7, "CommitBroadcastWallClockTime", ResourceType.JMX);


    private int id;
    private String paramKey;
    private ResourceType resourceType;

    private WPMParam(int _id, String _key, ResourceType _resourceType){
        id= _id;
        paramKey = _key;
        resourceType = _resourceType;
    }

    public String getParamKey(){
        return paramKey;
    }

    public ResourceType getResourceType(){
        return resourceType;
    }

    @Override
    public String toString(){
        String toString = "{ " +
                "id: " + id + ", " +
                "paramKey: " + paramKey + ", " +
                "resourceType: " + resourceType +
                "}";
        return toString;
    }
}
