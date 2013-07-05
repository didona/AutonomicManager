package eu.cloudtm.RESTServer.resources;

import com.google.gson.Gson;
import eu.cloudtm.StatsManager;
import eu.cloudtm.common.dto.WhatIfCustomParamDTO;
import eu.cloudtm.controller.Controller;
import eu.cloudtm.controller.WhatIf;
import eu.cloudtm.controller.model.ACF;
import eu.cloudtm.controller.oracles.common.PublishAttributeException;
import eu.cloudtm.wpm.parser.ResourceType;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.inject.Singleton;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.util.Map;

@Singleton
@Path("/whatif")
public class WhatIfResource extends AbstractResource {

    private static Log log = LogFactory.getLog(WhatIfResource.class);
    private Gson gson = new Gson();

    @PUT
    @Consumes("application/x-www-form-urlencoded")
    @Produces("application/json")
    public synchronized Response whatIf(
            @DefaultValue("-1") @FormParam("acf") double acf,
            @DefaultValue("-1") @FormParam("RetryWritePercentage") double retryWritePercentage,
            @DefaultValue("-1") @FormParam("SuxNumPuts") double suxNumPuts,
            @DefaultValue("-1") @FormParam("GetWriteTx") double getWriteTx,
            @DefaultValue("-1") @FormParam("GetReadOnlyTx") double getReadOnlyTx,
            @DefaultValue("-1") @FormParam("LocalUpdateTxLocalServiceTime") double localUpdateTxLocalServiceTime,
            @DefaultValue("-1") @FormParam("LocalReadOnlyTxLocalServiceTime") double localReadOnlyTxLocalServiceTime,
            @DefaultValue("-1") @FormParam("PrepareCommandBytes") double prepareCommandBytes,
            @DefaultValue("-1") @FormParam("RTT") double rtt,
            @DefaultValue("-1") @FormParam("CommitBroadcastWallClockTime") double commitBroadcastWallClockTime,
            @DefaultValue("-1") @FormParam("RemoteGetLatency") double remoteGetLatency
    ){

        WhatIfCustomParamDTO customParam = new WhatIfCustomParamDTO();
        customParam.setACF( acf );
        customParam.setCommitBroadcastWallClockTime( commitBroadcastWallClockTime );
        customParam.setGetReadOnlyTx( getReadOnlyTx );
        customParam.setGetWriteTx( getWriteTx );
        customParam.setLocalReadOnlyTxLocalServiceTime( localReadOnlyTxLocalServiceTime );
        customParam.setLocalUpdateTxLocalServiceTime( localUpdateTxLocalServiceTime );
        customParam.setPrepareCommandBytes( prepareCommandBytes );
        customParam.setSuxNumPuts( suxNumPuts );
        customParam.setRemoteGetLatency( remoteGetLatency );
        customParam.setRetryWritePercentage( retryWritePercentage );
        customParam.setRTT(  rtt );

        WhatIf resource = new WhatIf(customParam);


        resource.whatIf( customParam );



        StringBuffer json = new StringBuffer();
        json.append(  );

        log.info(json);
        Response.ResponseBuilder builder = Response.ok( json.toString() );
        return makeCORS(builder);
    }


    @GET @Path("/values")
    @Produces("application/json")
    public synchronized Response updateValuesFromSystem() {

        WhatIfCustomParamDTO customParam = WhatIf.retrieveCurrentParams();
        String json = new Gson().toJson(customParam, WhatIfCustomParamDTO.class);

        Response.ResponseBuilder builder = Response.ok(json.toString());
        return makeCORS(builder);
    }


    private String getJSON(String key, String val){
        StringBuffer strBuf = new StringBuffer();
        strBuf.append( gson.toJson( key ) );
        strBuf.append( ":" );
        strBuf.append( gson.toJson( val ) );
        return strBuf.toString();
    }

}


//        json.append("{ ");
//        for( Map.Entry<String,ResourceType> param : sampledParams.entrySet() ){
//            if (json.length() > 3) {
//                json.append(" , ");
//            }
//            json.append( getJSON(param.getKey(), String.valueOf(StatsManager.getInstance().getAvgAttribute(param.getKey(), lastSample, param.getValue())) ) );
//        }
//
//        if (json.length() > 3) {
//            json.append(" , ");
//        }
//
//        double acf;
//        try {
//            acf = ACF.evaluate(lastSample.getJmx(), Controller.getInstance().getCurrentConfiguration().threadPerNode(), Controller.TIME_WINDOW );
//            log.info("********************************** ACF = " + acf + " ***************************");
//        } catch (PublishAttributeException e) {
//            e.printStackTrace();
//            acf = -1;
//        }
//        json.append( getJSON("ACF", String.valueOf(acf) ) );
//
//        json.append(" }");
