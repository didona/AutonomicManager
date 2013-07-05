package eu.cloudtm.controller;

import eu.cloudtm.StatsManager;
import eu.cloudtm.common.dto.WhatIfCustomParamDTO;
import eu.cloudtm.common.dto.WhatIfResult;
import eu.cloudtm.controller.exceptions.OracleException;
import eu.cloudtm.controller.model.KPI;
import eu.cloudtm.controller.model.utils.Forecaster;
import eu.cloudtm.controller.oracles.AbstractOracle;
import eu.cloudtm.controller.oracles.OracleInput;
import eu.cloudtm.controller.oracles.OracleInputImpl;
import eu.cloudtm.stats.Sample;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 * User: fabio
 * Date: 7/5/13
 * Time: 10:58 AM
 * To change this template use File | Settings | File Templates.
 */
public class WhatIf {

    private static Log log = LogFactory.getLog(WhatIf.class);

    private WhatIfCustomParamDTO customParam;

    private StatsManager statsManager;


    public WhatIf(WhatIfCustomParamDTO _customParam, Sample lastSample){
        customParam = _customParam;
    }

    public Set<WhatIfResult> whatIf(WhatIfCustomParamDTO customParam){

        OracleInput oracleInput = new OracleInputImpl();


        Set<KPI> result = null;
        for( String oracleName : Controller.getInstance().getOracles() ){
            IOracle oracle = AbstractOracle.getInstance(oracleName, Controller.getInstance());
            try {
                result = oracle.whatIf(oracleInput);
            } catch (OracleException e) {
                // da gestire, magari con segnalazione all'utente...
                throw new RuntimeException(e);
            }
        }

        WhatIfResult whatIfResult = new WhatIfResult(Forecaster.ANALYTICAL);
        for(KPI kpi:result){
            whatIfResult.addThroughputPoint(kpi.getPlatformConfiguration().platformSize(),kpi.getThroughput());
            whatIfResult.addResponseTimePoint(kpi.getPlatformConfiguration().platformSize(),kpi.getRtt());
            whatIfResult.addAbortRatePoint(kpi.getPlatformConfiguration().platformSize(),kpi.getAbortProbability());
        }

    }



    public static WhatIfCustomParamDTO retrieveCurrentParams(){

        WhatIfCustomParamDTO customParamDTO = new WhatIfCustomParamDTO();
        return customParamDTO;
    }



    private double validateParam(double val ){

        double ret = val;
        if( val < 0 ){
            ret = -1;
        }
        log.info("valore ritornato --> " + ret);
        return ret;
    }

}


//    private Map<String,String> evaluatedParams = new HashMap<String,String>(){{
//        //put("ACF",                                  "-1"); //1
//        //put("GetWriteTx",                           "-1"); //4
//        //put("GetReadOnlyTx",                        "-1"); //5
//        //put("RemoteGetLatency",                     "-1"); //11
//
//    }};
//
//    private Map<String,ResourceType> sampledParams = new HashMap<String,ResourceType>(){{
//        put("LocalReadOnlyTxLocalServiceTime",      ResourceType.JMX);  //7   LocalReadOnlyTxLocalServiceTime
//        put("LocalUpdateTxLocalServiceTime",        ResourceType.JMX);  //6
//        put("RetryWritePercentage",                 ResourceType.JMX);  //2   RetryWritePercentage
//        put("SuxNumPuts",                           ResourceType.JMX);  //3   SuxNumPuts
//        put("PrepareCommandBytes",                  ResourceType.JMX);  //8   PrepareCommandBytes
//        put("RTT",                                  ResourceType.JMX);  //9   AvgSuccessfulPrepareTime
//        put("CommitBroadcastWallClockTime",         ResourceType.JMX);  //10  CommitBroadcastWallClockTime
//
//    }};
