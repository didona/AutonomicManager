package eu.cloudtm.RESTcontroller.resources;

import com.google.gson.Gson;
import com.sun.jersey.spi.resource.Singleton;
import eu.cloudtm.RESTcontroller.utils.Helper;
import eu.cloudtm.model.ReplicationDegree;
import eu.cloudtm.model.ReplicationProtocol;
import eu.cloudtm.model.State;
import eu.cloudtm.model.utils.Forecasters;
import eu.cloudtm.model.utils.ReplicationProtocols;
import eu.cloudtm.model.utils.TuningType;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;

// The Java class will be hosted at the URI path "/myresource"
@Singleton
@Path("/replication")
public class ReplicationResource {

    private static Log log = LogFactory.getLog(ReplicationResource.class);

    Gson gson = new Gson();

    @PUT
    @Path("/degree")
    @Consumes("application/x-www-form-urlencoded")
    @Produces("application/json")
    public synchronized Response setDegree(
            @FormParam("tuningType") TuningType type,
            @DefaultValue("NONE") @FormParam("tuningMethod") Forecasters method,
            @DefaultValue("0") @FormParam("degree") int degree
    ) {
        if(type==null)
            throw new WebApplicationException(Response.Status.BAD_REQUEST);

        if(type.equals(TuningType.SELF) && method.equals(Forecasters.NONE)){
            throw new WebApplicationException(Response.Status.BAD_REQUEST);
        }

        log.info("type: " + type);
        log.info("method: " + method);
        log.info("degree: " + degree);

        if( degree<=0 ){
            throw new WebApplicationException(Response.Status.BAD_REQUEST);
        }

        ReplicationDegree repDegreeConf = new ReplicationDegree();
        repDegreeConf.setTuning(type);

        if(type.equals(TuningType.MANUAL)){
            repDegreeConf.setForecaster(Forecasters.NONE);
        } else {
            repDegreeConf.setForecaster(method);
        }

        repDegreeConf.setDegree(degree);
        State.getInstance().updateReplicationDegree(repDegreeConf);

        String json = gson.toJson(State.getInstance().getReplicationDegree());
        return Helper.createResponse(json);
    }

    @PUT
    @Path("/protocol")
    @Consumes("application/x-www-form-urlencoded")
    @Produces("application/json")
    public synchronized Response setProtocol(
            @FormParam("tuningType") TuningType type,
            @DefaultValue("NONE") @FormParam("tuningMethod") Forecasters method,
            @FormParam("protocol") ReplicationProtocols protocol
    ) {
        if(type==null || protocol==null)
            throw new WebApplicationException(Response.Status.BAD_REQUEST);

        if(type.equals(TuningType.SELF) && method.equals(Forecasters.NONE)){
            throw new WebApplicationException(Response.Status.BAD_REQUEST);
        }

        log.info("type: " + type);
        log.info("method: " + method);
        log.info("protocol: " + protocol);

        ReplicationProtocol repProtocolConf = new ReplicationProtocol();
        repProtocolConf.setTuning(type);

        if(type.equals(TuningType.MANUAL)){
            repProtocolConf.setForecaster(Forecasters.NONE);
        } else {
            repProtocolConf.setForecaster(method);
        }

        repProtocolConf.setProtocol(protocol);
        State.getInstance().updateReplicationProtocol(repProtocolConf);

        String json = gson.toJson(State.getInstance().getReplicationProtocol());
        return Helper.createResponse(json);
    }


}