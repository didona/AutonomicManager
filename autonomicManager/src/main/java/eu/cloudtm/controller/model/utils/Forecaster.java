package eu.cloudtm.controller.model.utils;

import eu.cloudtm.controller.oracles.Morpher;
import eu.cloudtm.controller.oracles.OracleTAS;
import eu.cloudtm.controller.oracles.Simulator;

/**
 * Created by: Fabio Perfetti
 * E-mail: perfabio87@gmail.com
 * Date: 5/30/13
 */
public enum Forecaster {
    MANUAL(false, null),
    ANALYTICAL(true, OracleTAS.class ),
    SIMULATOR(true, Simulator.class ),
    MACHINE_LEARNING(true, Morpher.class );

    private final boolean autoScaling;
    private final Class oracleClass;

    private Forecaster(final boolean autoScaling, final Class _oracleClass ) {
        this.autoScaling = autoScaling;
        this.oracleClass = _oracleClass;
    }

    public Class getOracleClass(){
        return oracleClass;
    }

    public boolean isAutoScaling(){
        return autoScaling;
    }
}
