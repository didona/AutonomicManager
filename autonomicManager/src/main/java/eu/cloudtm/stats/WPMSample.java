package eu.cloudtm.stats;

import eu.cloudtm.common.WPMParam;
import eu.cloudtm.wpm.logService.remote.events.PublishAttribute;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * This class is a container for several statistics, organized in JMX, MEM
 * Created by: Fabio Perfetti
 * E-mail: perfabio87@gmail.com
 * Date: 6/10/13
 */
public class WPMSample implements Sample {

    private static AtomicInteger counter = new AtomicInteger(0);

    private final long id;
    private final long timestamp;
    private final Map<String, PublishAttribute<Double>> aggregateJMXStack;
    private final Map<String, PublishAttribute<Double>> aggregateMEMStack;



    public static WPMSample getInstance(Map<String, PublishAttribute<Double>> jmx, Map<String, PublishAttribute<Double>> mem){
        return new WPMSample(counter.getAndIncrement(),jmx,mem);
    }

    private WPMSample(long _id, Map<String, PublishAttribute<Double>> _jmx, Map<String, PublishAttribute<Double>> _mem){
        this.timestamp = System.currentTimeMillis();
        this.id = _id;
        this.aggregateJMXStack = Collections.unmodifiableMap(_jmx);
        this.aggregateMEMStack = Collections.unmodifiableMap(_mem);
    }

    public long getId(){ return id; }

    public long getTimestamp(){ return timestamp; }

    public Map<String, PublishAttribute<Double>> getJmx(){
        return aggregateJMXStack;
    }

    public Map<String, PublishAttribute<Double>> getMem(){
        return aggregateMEMStack;
    }

    public double getAggregate(WPMParam param, int classIdx){
        Map<String, PublishAttribute<Double>> map = null;
        double ret = Double.NEGATIVE_INFINITY;

        switch (param.getResourceType()){
            case JMX:
                if(aggregateJMXStack.containsKey(param.getParamKey())){
                    ret = aggregateJMXStack.get(param.getParamKey()).getValue();
                }
                break;
                default:
                    throw new IllegalArgumentException(param.toString());
        }
        return ret;
    }

}
