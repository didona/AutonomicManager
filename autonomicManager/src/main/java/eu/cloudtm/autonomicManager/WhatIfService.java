package eu.cloudtm.autonomicManager;

import eu.cloudtm.autonomicManager.commons.*;
import eu.cloudtm.autonomicManager.commons.dto.WhatIfCustomParamDTO;
import eu.cloudtm.autonomicManager.commons.dto.WhatIfDTO;
import eu.cloudtm.autonomicManager.oracles.Oracle;
import eu.cloudtm.autonomicManager.oracles.OracleService;
import eu.cloudtm.autonomicManager.oracles.OracleServiceFactory;
import eu.cloudtm.autonomicManager.oracles.OutputOracle;
import eu.cloudtm.autonomicManager.statistics.ProcessedSample;
import eu.cloudtm.autonomicManager.statistics.samples.CustomSample;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * User: Fabio Perfetti perfabio87 [at] gmail.com Date: 7/8/13 Time: 1:33 PM
 */
public class WhatIfService {

   private static Log log = LogFactory.getLog(WhatIfService.class);
   private final ProcessedSample processedSample;

   public WhatIfService(ProcessedSample processedSample) {
      this.processedSample = processedSample;
      log.trace("WhatIfService instanced");
   }

   //TODO: Be aware of this: the getParam method throws runtimeExceptions if a param is not set!!!
   //TODO so the check "if !=null" is useless as it's never reached.
   public WhatIfCustomParamDTO retrieveCurrentValues() {

      log.trace("B Retrieving current values");

      WhatIfCustomParamDTO customParam = new WhatIfCustomParamDTO();

      Object acfObj = processedSample.getEvaluatedParam(EvaluatedParam.ACF);
      if (acfObj != null) {
         customParam.setACF((Double) acfObj);
         log.trace("ACF set");
      } else {
         log.warn("ACF is not set!");
         customParam.setACF(0.0);
      }

      Object avgCommitAsync = processedSample.getParam(Param.AvgCommitAsync);
      if (avgCommitAsync != null) {

         double val = ((Number) avgCommitAsync).doubleValue();
         customParam.setAvgCommitAsync(val);
         log.trace("commit set");

      } else {
         log.warn("AvgCommitAsync is not set!");
         customParam.setAvgCommitAsync(0);
      }

      Object avgPrepareAsync = processedSample.getParam(Param.AvgPrepareRtt);
      if (avgPrepareAsync != null) {
         double val = ((Number) avgPrepareAsync).doubleValue();
         customParam.setAvgPrepareAsync(val);
         log.trace("Prepare set");
      } else {
         log.warn("AvgPrepareAsync is not set!");
         customParam.setAvgPrepareAsync(0);
      }

      Object avgPrepareCommandSize = processedSample.getParam(Param.AvgPrepareCommandSize);
      if (avgPrepareCommandSize != null) {

         double val = ((Number) avgPrepareCommandSize).doubleValue();
         customParam.setAvgPrepareCommandSize(val);
         log.trace("PrepareCommandSize set");
      } else {
         log.warn("AvgPrepareCommandSize is not set!");
         customParam.setAvgPrepareCommandSize(0);
      }

      Object avgNumPutsBySuccessfulLocalTx = processedSample.getParam(Param.AvgNumPutsBySuccessfulLocalTx);
      if (avgNumPutsBySuccessfulLocalTx != null) {

         double val = ((Number) avgNumPutsBySuccessfulLocalTx).doubleValue();
         customParam.setAvgNumPutsBySuccessfulLocalTx(val);
         log.trace("Put Set");
      } else {
         log.warn("AvgNumPutsBySuccessfulLocalTx is not set!");
         customParam.setAvgNumPutsBySuccessfulLocalTx(0.0);
      }

      Object percentageSuccessWriteTransactions = processedSample.getParam(Param.PercentageSuccessWriteTransactions);
      if (percentageSuccessWriteTransactions != null) {

         double val = ((Number) percentageSuccessWriteTransactions).doubleValue();
         customParam.setPercentageSuccessWriteTransactions(val);
         log.trace("WriteXact set");
      } else {
         log.warn("PercentageSuccessWriteTransactions is not set!");
         customParam.setPercentageSuccessWriteTransactions(0.0);
      }

      Object localUpdateTxLocalServiceTime = processedSample.getParam(Param.LocalUpdateTxLocalServiceTime);
      if (localUpdateTxLocalServiceTime != null) {

         double val = ((Number) localUpdateTxLocalServiceTime).doubleValue();
         customParam.setLocalUpdateTxLocalServiceTime(val);
         log.trace("UpdateTxService set");
      } else {
         log.warn("LocalUpdateTxLocalServiceTime is not set!");
         customParam.setLocalUpdateTxLocalServiceTime(0.0);
      }

      Object localReadOnlyTxLocalServiceTime = processedSample.getParam(Param.LocalReadOnlyTxLocalServiceTime);
      if (localReadOnlyTxLocalServiceTime != null) {

         double val = ((Number) localReadOnlyTxLocalServiceTime).doubleValue();
         customParam.setLocalReadOnlyTxLocalServiceTime(val);
         log.trace("ROTxService set");
      } else {
         log.warn("LocalReadOnlyTxLocalServiceTime is not set!");
         customParam.setLocalReadOnlyTxLocalServiceTime(0);
      }

      Object avgRemoteGetRtt = processedSample.getParam(Param.AvgRemoteGetRtt);
      if (avgRemoteGetRtt != null) {

         double val = ((Number) avgRemoteGetRtt).doubleValue();
         customParam.setAvgRemoteGetRtt(val);
         log.trace("Remote get set");
      } else {
         log.warn("AvgRemoteGetRtt is not set!");
         customParam.setAvgRemoteGetRtt(0);
      }

      Object avgGetsPerWrTransaction = processedSample.getParam(Param.AvgGetsPerWrTransaction);
      if (avgGetsPerWrTransaction != null) {

         double val = ((Number) avgGetsPerWrTransaction).doubleValue();
         customParam.setAvgGetsPerWrTransaction(val);
         log.trace("GetPerTx set");
      } else {
         log.warn("AvgGetsPerWrTransaction is not set!");
         customParam.setAvgGetsPerWrTransaction(0.0);
      }

      Object avgGetsPerROTransaction = processedSample.getParam(Param.AvgGetsPerROTransaction);
      if (avgGetsPerROTransaction != null) {

         double val = ((Number) avgGetsPerROTransaction).doubleValue();
         customParam.setAvgGetsPerROTransaction(val);
         log.trace("GetPerRo set");
      } else {
         log.warn("AvgGetsPerROTransaction is not set!");
         customParam.setAvgGetsPerROTransaction(0);
      }


      log.trace("retrieving current values done!");
      return customParam;
   }

   public List<WhatIfDTO> evaluate(WhatIfCustomParamDTO customParamDTO) {

      log.trace("evaluating what-if analysis");

      Map<Param, Object> customParam = extractCustomParam(customParamDTO);
      Map<EvaluatedParam, Object> customEvaluatedParam = extractCustomEvaluatedParam(customParamDTO);

      log.trace("Custom params extracted");

      List<WhatIfDTO> result = new ArrayList<WhatIfDTO>();

      for (Forecaster forecaster : customParamDTO.getForecasters()) {
         WhatIfDTO currWhatIfResult = new WhatIfDTO(forecaster, customParamDTO.getXaxis());

         Oracle oracle = forecaster.getInstance();
         OracleService oracleService = OracleServiceFactory.buildOracleService(oracle);
         log.info("Querying " + forecaster);

                /* creating custom Sample, with custom maps */
         CustomSample customSample = new CustomSample(processedSample, customParam, customEvaluatedParam);

                /* invoking OracleService, which returns a map of configurations-output */

         Map<PlatformConfiguration, OutputOracle> currForecast;

         Integer nodeMin = customParamDTO.getFixedNodesMin();
         Integer nodeMax = customParamDTO.getFixedNodesMax();
         ReplicationProtocol protocol = customParamDTO.getFixedProtocol();
         Integer degreeMin = customParamDTO.getFixedDegreeMin();
         Integer degreemax = customParamDTO.getFixedDegreeMax();

         switch (customParamDTO.getXaxis()) {
            case NODES:
               log.info("Nodes on x-axis");
               log.info("Min Nodes = " + nodeMin);
               log.info("Max Nodes = " + nodeMax);
               log.info("Fixed Degree = " + degreemax);
               log.info("Protocol = " + protocol);
               currForecast = oracleService.whatIf(customSample,
                       nodeMin,
                       nodeMax,
                       protocol,
                       degreemax);
               break;
            case DEGREE:
               log.info("Degrees on x-axis");
               log.info("Min Degree = " + degreeMin);
               log.info("Max Degree = " + degreemax);
               log.info("Fixed Nodes = " + nodeMax);
               log.info("Protocol = " + protocol);
               currForecast = oracleService.whatIf(customSample,
                       degreeMin,
                       degreemax,
                       nodeMax,
                       protocol);
               break;
            case PROTOCOL:
               log.info("Protocols on x-axis");
               log.info("Fixed nodes " + nodeMax);
               log.info("Fixed degree " + degreemax);
               currForecast = oracleService.whatIf(customSample,
                       nodeMax,
                       degreemax);
               break;
            default:
               throw new IllegalStateException("Xaxis can't be null!");
         }

         for (Map.Entry<PlatformConfiguration, OutputOracle> entry : currForecast.entrySet()) {

            long xaxis = 0;
            switch (customParamDTO.getXaxis()) {
               case NODES:
                  xaxis = entry.getKey().platformSize();
                  break;
               case DEGREE:
                  xaxis = entry.getKey().replicationDegree();
                  break;
               case PROTOCOL:
                  xaxis = entry.getKey().replicationProtocol().getId();
                  break;
            }
            OutputOracle currOut = entry.getValue();

            if (currOut == null) {
               log.info("Forecaster " + forecaster + " returned a null OutputOracle for PlatformConfiguration " + xaxis);
               continue;
            }

            //TODO FIX TX CLASSES
            log.warn("FIX TX CLASSES");


            currWhatIfResult.addThroughputPoint(xaxis, currOut.throughput(0));
            currWhatIfResult.addReadResponseTimePoint(xaxis, currOut.responseTime(0));
            currWhatIfResult.addWriteResponseTimePoint(xaxis, currOut.responseTime(1));
            currWhatIfResult.addAbortRatePoint(xaxis, currOut.abortRate(1));
         }


         result.add(currWhatIfResult);
      }
      return result;
   }

   private Map<Param, Object> extractCustomParam(WhatIfCustomParamDTO whatIfCustomParam) {
      Map<Param, Object> customParam = new HashMap<Param, Object>();
      if (whatIfCustomParam != null) {
         log.trace("Extracting AvgCommitAsync, whatIfCustomParam contains: " + whatIfCustomParam.getAvgCommitAsync());
         customParam.put(Param.AvgCommitAsync, whatIfCustomParam.getAvgCommitAsync());

         log.trace("Extracting AvgRemoteGetRtt, whatIfCustomParam contains: " + whatIfCustomParam.getAvgRemoteGetRtt());
         customParam.put(Param.AvgPrepareRtt, whatIfCustomParam.getAvgPrepareAsync());

         log.trace("Extracting AvgPrepareRtt, whatIfCustomParam contains: " + whatIfCustomParam.getAvgPrepareAsync());
         customParam.put(Param.AvgRemoteGetRtt, whatIfCustomParam.getAvgRemoteGetRtt());

         log.trace("Extracting AvgGetsPerRoTransaction, whatIfCustomParam contains: " + whatIfCustomParam.getAvgGetsPerROTransaction());
         customParam.put(Param.AvgGetsPerROTransaction, whatIfCustomParam.getAvgGetsPerROTransaction());

         log.trace("Extracting AvgGetsPerWrTransaction, whatIfCustomParam contains: " + whatIfCustomParam.getAvgGetsPerWrTransaction());
         customParam.put(Param.AvgGetsPerWrTransaction, whatIfCustomParam.getAvgGetsPerWrTransaction());

         log.trace("Extracting AvgPrepareCommandSize, whatIfCustomParam contains: " + whatIfCustomParam.getAvgPrepareCommandSize());
         customParam.put(Param.AvgPrepareCommandSize, whatIfCustomParam.getAvgPrepareCommandSize());

         log.trace("Extracting AvgNumPutsBySuccessfulLocalTx, whatIfCustomParam contains: " + whatIfCustomParam.getAvgCommitAsync());
         customParam.put(Param.AvgNumPutsBySuccessfulLocalTx, whatIfCustomParam.getAvgNumPutsBySuccessfulLocalTx());

         log.trace("Extracting PercentageSuccessWriteTransactions, whatIfCustomParam contains: " + whatIfCustomParam.getPercentageSuccessWriteTransactions());
         customParam.put(Param.PercentageSuccessWriteTransactions, whatIfCustomParam.getPercentageSuccessWriteTransactions());

         log.trace("Extracting LocalUpdateTxLocalServiceTime, whatIfCustomParam contains: " + whatIfCustomParam.getLocalUpdateTxLocalServiceTime());
         customParam.put(Param.LocalUpdateTxLocalServiceTime, whatIfCustomParam.getLocalUpdateTxLocalServiceTime());

         log.trace("Extracting LocalReadOnlyTxLocalServiceTime, whatIfCustomParam contains: " + whatIfCustomParam.getLocalReadOnlyTxLocalServiceTime());
         customParam.put(Param.LocalReadOnlyTxLocalServiceTime, whatIfCustomParam.getLocalReadOnlyTxLocalServiceTime());
      }
      log.trace("extractCustomParam has extracted " + customParam.size() + " params");
      return customParam;
   }

   private Map<EvaluatedParam, Object> extractCustomEvaluatedParam(WhatIfCustomParamDTO whatIfCustomParam) {
      Map<EvaluatedParam, Object> customParam = new HashMap<EvaluatedParam, Object>();
      if (whatIfCustomParam != null) {
         log.trace("Extracting ACF, whatIfCustomParam contains: " + whatIfCustomParam.getACF());
         customParam.put(EvaluatedParam.ACF, whatIfCustomParam.getACF());
      }
      log.trace("extractCustomEvaluatedParam has extracted " + customParam.size() + " params");
      return customParam;
   }

}
