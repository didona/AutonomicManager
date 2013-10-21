package eu.cloudtm.autonomicManager.statistics.samples;


import eu.cloudtm.autonomicManager.commons.Param;
import eu.cloudtm.autonomicManager.statistics.ProcessedSample;
import eu.cloudtm.autonomicManager.statistics.Sample;

import java.util.Random;

/**
 * Created with IntelliJ IDEA. User: fabio Date: 7/8/13 Time: 8:38 PM To change this template use File | Settings | File
 * Templates.
 */
public class TWOPCProcessedSample extends ProcessedSample {

   public TWOPCProcessedSample(Sample sample) {
      super(sample);
   }

   //TODO: wire this
   @Override
   public Double getACF() {
      return 1.0D/((Number)getParam(Param.NumberOfEntries)).doubleValue();
   }

}
