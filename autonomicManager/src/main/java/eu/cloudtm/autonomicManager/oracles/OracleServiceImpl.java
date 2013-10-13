package eu.cloudtm.autonomicManager.oracles;

import eu.cloudtm.autonomicManager.commons.ForecastParam;
import eu.cloudtm.autonomicManager.commons.PlatformConfiguration;
import eu.cloudtm.autonomicManager.commons.ReplicationProtocol;
import eu.cloudtm.autonomicManager.configs.AdaptationManagerConfig;
import eu.cloudtm.autonomicManager.configs.Config;
import eu.cloudtm.autonomicManager.debug.WPMInputOracleDumper;
import eu.cloudtm.autonomicManager.oracles.exceptions.OracleException;
import eu.cloudtm.autonomicManager.statistics.ProcessedSample;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.util.*;

/**
 * Created by: Fabio Perfetti E-mail: perfabio87@gmail.com Date: 6/14/13
 */
public class OracleServiceImpl implements OracleService {

   private static Log log = LogFactory.getLog(OracleServiceImpl.class);
   protected int nodesMin = 2, nodesMax = 10; // TODO: da rendere parametrizzabili


   protected int degreeMin = 2;
   private Oracle oracle;
   private final static boolean dump = false;

   public OracleServiceImpl(Oracle oracle) {
      this.oracle = oracle;
      setMinMax();
   }

   public PlatformConfiguration minimizeCosts(ProcessedSample sample,
                                              double arrivalRateToGuarantee,
                                              double abortRateToGuarantee,
                                              double responseTimeToGuarantee)
           throws OracleException {

      PlatformConfiguration configuration = exploreAllCases(sample, arrivalRateToGuarantee, abortRateToGuarantee, responseTimeToGuarantee);
      return configuration;
   }

   private PlatformConfiguration exploreAllCases(ProcessedSample sample,
                                                 double arrivalRateToGuarantee,
                                                 double abortRateToGuarantee,
                                                 double responseTimeToGuarantee) throws OracleException {

      PlatformConfiguration finalConfiguration = null;
      boolean found = false;
      int numNodes = nodesMin;

      while (numNodes <= nodesMax && !found) {
         int repDegree = nodesMin;
         while (repDegree <= numNodes && !found) {
            for (ReplicationProtocol protocol : ReplicationProtocol.values()) {

               log.trace("Preparing query for <" + numNodes + ", " + repDegree + ", " + protocol + ">");

               PlatformConfiguration currConf = new PlatformConfiguration(numNodes, repDegree, protocol);
               OutputOracle outputOracle = doForecast(currConf, sample);

               if (outputOracle.throughput(0) >= arrivalRateToGuarantee && outputOracle.abortRate(0) <= abortRateToGuarantee) {
                  found = true;
                  finalConfiguration = new PlatformConfiguration(numNodes, repDegree, protocol);
                  break;
               }
            }
            repDegree++;
         }
         numNodes++;
      }

      return finalConfiguration;
   }

   @Override
   public PlatformConfiguration maximizeThroughput(ProcessedSample sample) throws OracleException {

      PlatformConfiguration finalConfiguration = null, newC;
      boolean found = false;
      double maxThroughput = 0, currT;

      int numNodes = nodesMin;

      while (numNodes <= nodesMax) {
         int repDegree = nodesMin;
         while (repDegree <= numNodes) {
            for (ReplicationProtocol protocol : ReplicationProtocol.values()) {

               log.trace("Querying <" + numNodes + ", " + repDegree + ", " + protocol + ">");

               PlatformConfiguration currConf = new PlatformConfiguration(numNodes, repDegree, protocol);
               OutputOracle outputOracle = doForecast(currConf, sample);
               currT = outputOracle.throughput(0) + outputOracle.throughput(1);
               if (currT > maxThroughput) {

                  newC = new PlatformConfiguration(numNodes, repDegree, protocol);
                  if (finalConfiguration != null)
                     log.trace("Ex optimal was " + finalConfiguration + "with throughput " + maxThroughput + " now is " + newC + " with throughput " + currT);
                  finalConfiguration = newC;
                  maxThroughput = currT;
               }
            }
            repDegree++;
         }
         numNodes++;
      }
      log.trace("Optimal configuration is " + finalConfiguration);
      return finalConfiguration;
   }

   /**
    * What-if with Protocols on X-axis
    *
    * @param sample
    * @param fixedNodes
    * @param fixedDegree
    * @return
    */
   @Override
   public final Map<PlatformConfiguration, OutputOracle> whatIf(ProcessedSample sample, int fixedNodes, int fixedDegree) {
      TreeMap<PlatformConfiguration, OutputOracle> result = new TreeMap<PlatformConfiguration, OutputOracle>();
      if (fixedNodes <= 1)
         throw new IllegalArgumentException("fixedDegree must be > 1");
      if (fixedDegree > fixedNodes)
         throw new IllegalArgumentException("fixedDegree must be >= fixedNodes");

      for (ReplicationProtocol protocol : ReplicationProtocol.values()) {

         log.trace("Preparing query for <" + fixedNodes + ", " + fixedDegree + ", " + protocol + ">");

         PlatformConfiguration currConf = new PlatformConfiguration(fixedNodes, fixedDegree, protocol);
         OutputOracle currOutputOracle = doForecast(currConf, sample);
         result.put(currConf, currOutputOracle);
      }
      return result;

   }

   /**
    * What-if with Degree on X-axis
    *
    * @param sample
    * @param fixedNodes
    * @param fixedProtocol
    * @return
    */
   @Override
   public Map<PlatformConfiguration, OutputOracle> whatIf(ProcessedSample sample, int minNumDegree, int maxNumDegree,
                                                          int fixedNodes, ReplicationProtocol fixedProtocol) {

      TreeMap<PlatformConfiguration, OutputOracle> result = new TreeMap<PlatformConfiguration, OutputOracle>();
      if (fixedNodes <= 1)
         throw new IllegalArgumentException("fixedDegree must be > 0");
      if (fixedProtocol == null)
         throw new IllegalArgumentException("fixedProtocol must be not null");

      if (minNumDegree < degreeMin) {
         minNumDegree = degreeMin;
      }
      if (maxNumDegree > fixedNodes) {
         maxNumDegree = fixedNodes;
      }
      try {
         for (int degree : range(minNumDegree, maxNumDegree)) {

            log.trace("Preparing query for <" + fixedNodes + ", " + degree + ", " + fixedProtocol + ">");

            PlatformConfiguration currConf = new PlatformConfiguration(fixedNodes, degree, fixedProtocol);
            OutputOracle currOutputOracle = doForecast(currConf, sample);
            result.put(currConf, currOutputOracle);
         }
         return result;
      } catch (Exception e) {
         log.error(e);
         log.error(Arrays.toString(e.getStackTrace()));
         return null;
      }

   }

   /**
    * What-if with Nodes on X-axis
    *
    * @param sample
    * @param fixedProtocol
    * @param fixedDegree
    * @return
    */
   @Override
   public final Map<PlatformConfiguration, OutputOracle> whatIf(ProcessedSample sample, int minNumNodes,
                                                                int maxNumNodes, ReplicationProtocol fixedProtocol,
                                                                int fixedDegree) {
      log.trace("Nodes whatif");
      TreeMap<PlatformConfiguration, OutputOracle> result = new TreeMap<PlatformConfiguration, OutputOracle>();
      if (fixedDegree <= 0)
         throw new IllegalArgumentException("fixedDegree must be > 0");
      if (fixedProtocol == null)
         throw new IllegalArgumentException("fixedDegree must be not null");

      if (minNumNodes < nodesMin) {
         minNumNodes = nodesMin;
      }
      if (maxNumNodes > nodesMax) {
         maxNumNodes = nodesMax;
      }
      log.trace("Going to what-if with number of nodes");
      int degree = 0;
      for (int nodes : range(minNumNodes, maxNumNodes)) {

         if (fixedDegree > nodes) {
            degree = nodes;
         } else {
            degree = fixedDegree;
         }

         log.trace("Preparing query for <" + nodes + ", " + degree + ", " + fixedProtocol + ">");

         PlatformConfiguration currConf = new PlatformConfiguration(nodes, degree, fixedProtocol);
         OutputOracle currOutputOracle = doForecast(currConf, sample);
         result.put(currConf, currOutputOracle);
      }
      return result;
   }

   private List<Integer> range(int min, int max) {
      log.trace("Range invoked");
      AdaptationManagerConfig config = Config.getInstance();
      if (config.isWhatIfFixedDomain()) {
         log.trace("Fixed step whatif");
         return fixedNodesRange(min, max, config.whatIfGranularity());
      }
      if (config.isWhatIfSamplingDomain()) {
         log.trace("Sampling whatif");
         return samplingNodesRange(min, max, config.whatIfGranularity());
      }
      log.trace("All solutions whatif");
      return fixedNodesRange(min, max, 1);
   }

   private List<Integer> fixedNodesRange(int min, int max, int step) {
      log.trace("FixedNodeRange with " + min + ", " + max + ", " + step);
      ArrayList<Integer> ai = new ArrayList<Integer>();
      for (int i = min; i < max; i += step) {
         ai.add(i);
      }
      ai.add(max);
      return ai;
   }

   private List<Integer> samplingNodesRange(int min, int max, int split) {
      double mi = (double) min;
      double ma = (double) max;
      double spli = (double) split;
      double step = (ma - mi) / spli;
      if (step < 1)
         step = 1;
      return fixedNodesRange(min, max, (int) step);
   }

   protected OutputOracle doForecast(PlatformConfiguration currConf, ProcessedSample sample) {

      Map<ForecastParam, Object> forecastParam = new HashMap<ForecastParam, Object>();
      forecastParam.put(ForecastParam.NumNodes, currConf.platformSize());
      forecastParam.put(ForecastParam.ReplicationDegree, currConf.replicationDegree());
      forecastParam.put(ForecastParam.ReplicationProtocol, currConf.replicationProtocol());

      InputOracleWPM inputOracle = new InputOracleWPM(sample, forecastParam);

      if (dump) {
         WPMInputOracleDumper dumper = new WPMInputOracleDumper(inputOracle);
         try {
            dumper.dump("dump_nodes_" + currConf.platformSize());
         } catch (ParserConfigurationException e) {
            log.warn(e, e);
            throw new RuntimeException(e);
         } catch (TransformerException e) {
            log.warn(e, e);
            throw new RuntimeException(e);
         }
      }

      OutputOracle currOutputOracle = null;
      try {
         log.info("Forecasting for " + currConf);
         currOutputOracle = oracle.forecast(inputOracle);
      } catch (OracleException e) {
         log.warn("An error occured during the forecasting. The configuration " + currConf + " will be skipped!");

         if (log.isDebugEnabled()) {
            log.debug(e, e);
         }
      }
      return currOutputOracle;
   }

   private void setMinMax() {
      AdaptationManagerConfig config = Config.getInstance();
      this.nodesMin = config.oracleServiceMinNodes();
      this.nodesMax = config.oracleServiceMaxNodes();
      this.degreeMin = config.oracleServiceMinRD();
   }
}
