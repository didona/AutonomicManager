package eu.cloudtm.oracles;

import eu.cloudtm.statistics.EvaluatedParam;
import eu.cloudtm.statistics.ProcessedSample;
import eu.cloudtm.statistics.Param;

import java.util.Map;

/**
 * Created by: Fabio Perfetti
 * E-mail: perfabio87@gmail.com
 * Date: 7/10/13
 */
public class InputOracleWPM implements InputOracle {

    private ProcessedSample processedSample;
    private Map<ForecastParam, Object> forecastParam;

    public InputOracleWPM(ProcessedSample processedSample, Map<ForecastParam, Object> forecastParam) {
        this.processedSample = processedSample;
        this.forecastParam = forecastParam;
    }

    @Override
    public Object getParam(Param param) {
        return processedSample.getParam(param);
    }

    @Override
    public Object getForecastParam(ForecastParam param) {
        return forecastParam.get(param);
    }

    @Override
    public double getEvaluatedParam(EvaluatedParam param){
        return processedSample.getEvaluatedParam(param);
    }

}