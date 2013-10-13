package eu.cloudtm.autonomicManager.configs;

import eu.cloudtm.autonomicManager.commons.Forecaster;
import eu.cloudtm.autonomicManager.commons.PlatformConfiguration;
import eu.cloudtm.autonomicManager.commons.ReplicationProtocol;
import eu.cloudtm.autonomicManager.commons.WhatIFEnum;
import eu.cloudtm.autonomicManager.oracles.OracleServiceEnum;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;

/**
 * // TODO: Document this
 *
 * @author diego
 * @since 4.0
 */
public class AdaptationManagerConfig extends PropertiesConfiguration {

   public AdaptationManagerConfig(String fileName) throws ConfigurationException {
      super(fileName);
   }

   /**
    * *********************** PLATFORM ************************
    */

   public PlatformConfiguration initPlatformConfig() {
      ReplicationProtocol rp = ReplicationProtocol.valueOf(getString(KeyConfig.ENVIRONMENT_INIT_R_PROT.key()));
      int initNodes = getInt(KeyConfig.ENVIRONMENT_INIT_NODES.key());
      int rD = getInt(KeyConfig.ENVIRONMENT_INIT_R_DEGREE.key());
      if (rD < initNodes)
         rD = initNodes;
      return new PlatformConfiguration(initNodes, rD, rp);
   }

   /**
    * *********************** CHANGE DETECTOR ************************
    */
   //For the dummy
   public int getEvaluationPeriod() {
      return getInt(KeyConfig.ALERT_MANAGER_EVALUATION_PERIOD.key());
   }

   public int getAvgWindowSize() {
      return getInt(KeyConfig.CHANGE_DETECTOR_AVG_WINDOW.key());
   }

   /**
    * *********************** ALERT MANAGER ************************
    */

   public boolean isAlertManagerPolicyPureReactive() {
      return isAlertManagerPolicyEqualTo("REACTIVE");
   }

   public boolean isAlertManagerPolicyMix() {
      return isAlertManagerPolicyEqualTo("MIX");
   }

   public boolean isAlertManagerPolicyProactive() {
      return isAlertManagerPolicyEqualTo("PROACTIVE");
   }

   public boolean isAlertManagerPolicyTimeReactive() {
      return isAlertManagerPolicyEqualTo("REACTIVE_TIME");
   }

   private boolean isAlertManagerPolicyEqualTo(String to) {
      return getString(KeyConfig.ALERT_MANAGER_POLICY.key()).equals(to);
   }

   /**
    * *********************** FORECASTERS ************************
    */

   public Forecaster getDefaultForecaster() {
      return (Forecaster.valueOf(getString(KeyConfig.FORECASTER_DEFAULT.key())));
   }

   /**
    * *********************** ORACLES ************************
    */
   public boolean isOracleServiceHillClimbing() {
      return isOracleService(OracleServiceEnum.HILL_CLIMBING);
   }

   public boolean isOracleServiceExhaustive() {
      return isOracleService(OracleServiceEnum.EXHAUSTIVE);
   }

   public boolean isOracleServiceFake() {
      return isOracleService(OracleServiceEnum.FAKE);
   }

   /**
    * *********************** ORACLE SERVICE ************************
    */

   private boolean isOracleService(OracleServiceEnum ose) {
      return OracleServiceEnum.valueOf(getString(KeyConfig.ORACLE_SERVICE.key())).equals(ose);
   }

   public int oracleServiceMinNodes() {
      return getInt(KeyConfig.ORACLE_SERVICE_MIN_NODES.key());
   }

   public int oracleServiceMaxNodes() {
      return getInt(KeyConfig.ORACLE_SERVICE_MAX_NODES.key());
   }

   public int oracleServiceMinRD() {
      return getInt(KeyConfig.ORACLE_SERVICE_MIN_RD.key());
   }

   public int oracleServiceMaxRD() {
      return getInt(KeyConfig.ORACLE_SERVICE_MAX_RD.key());
   }

   /**
    * *********************** ORACLES ************************
    */

   public boolean enforceStability() {
      String key = getString(KeyConfig.ENFORCE_STABILITY.key());
      return key != null && key.equals("true");
   }

   public boolean isUsingStub() {
      return !(stub() == null || stub().isEmpty());
   }

   public String stub() {
      return getString(KeyConfig.STUB.key());
   }

   /**
    * *********************** WHAT IF ************************
    */

   public boolean isWhatIfAllDomain() {
      return isWhatIfDomainX(WhatIFEnum.ALL);
   }

   public boolean isWhatIfFixedDomain() {
      return isWhatIfDomainX(WhatIFEnum.FIXED);
   }

   public boolean isWhatIfSamplingDomain() {
      return isWhatIfDomainX(WhatIFEnum.SAMPLING);
   }

   private boolean isWhatIfDomainX(WhatIFEnum e) {
      return WhatIFEnum.valueOf(getString(KeyConfig.WHATIF_DOMAIN.key())).equals(e);
   }

   public int whatIfStep() {
      return getInt(KeyConfig.WHATIF_STEP.key());
   }

   public int whatIfSplit() {
      return getInt(KeyConfig.WHATIF_SPLIT.key());
   }


}
