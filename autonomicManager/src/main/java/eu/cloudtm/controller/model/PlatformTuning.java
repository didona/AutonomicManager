package eu.cloudtm.controller.model;

import com.google.gson.Gson;
import eu.cloudtm.controller.model.utils.Forecaster;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Created by: Fabio Perfetti
 * E-mail: perfabio87@gmail.com
 * Date: 6/1/13
 */
public class PlatformTuning {

    private static Log log = LogFactory.getLog(PlatformTuning.class);

    private volatile Forecaster scaleForecaster = Forecaster.ANALYTICAL;

    private volatile Forecaster protocolForecaster = Forecaster.ANALYTICAL;

    private volatile Forecaster degreeForecaster = Forecaster.ANALYTICAL;


    public PlatformTuning(){

    }

    public Forecaster getScaleForecaster(){
        return scaleForecaster;
    }

    public void setScaleForecaster(Forecaster forecaster){
        scaleForecaster = forecaster;
    }

    public Forecaster getProtocolForecaster(){
        return protocolForecaster;
    }

    public void setProtocolForecaster(Forecaster forecaster){
        protocolForecaster = forecaster;
    }

    public Forecaster getDegreeForecaster(){
        return degreeForecaster;
    }

    public void setDegreeForecaster(Forecaster forecaster){
        degreeForecaster = forecaster;
    }

    public PlatformTuning toJSON() {
        log.info("TO IMPLEMENT");
        Gson gson = new Gson();
        PlatformTuning state = gson.fromJson(gson.toJson(this), PlatformTuning.class);
        return state;
    }
}