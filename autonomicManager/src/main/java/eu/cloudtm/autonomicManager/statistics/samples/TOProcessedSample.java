package eu.cloudtm.autonomicManager.statistics.samples;

import eu.cloudtm.autonomicManager.commons.Param;
import eu.cloudtm.autonomicManager.statistics.ProcessedSample;
import eu.cloudtm.autonomicManager.statistics.Sample;

/**
 * // TODO: Document this
 *
 * @author diego
 * @since 4.0
 */
public class TOProcessedSample extends ProcessedSample {

   public TOProcessedSample(Sample sample) {
      super(sample);
   }

   //TODO: wire this
   @Override
   protected Double getACF() {
      return 1.0D / ((Number) getParam(Param.NumberOfEntries)).doubleValue() * 5;
   }

}
