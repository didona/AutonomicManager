package eu.cloudtm.controller.model.utils;

/**
 * Created by: Fabio Perfetti
 * E-mail: perfabio87@gmail.com
 * Date: 5/30/13
 */
public enum Forecaster {
    MANUAL(false), ANALYTICAL(true), SIMULATOR(true), MACHINE_LEARNING(true);

    private final boolean autoScaling;

    private Forecaster(final boolean autoScaling) {
        this.autoScaling = autoScaling;
    }

    public boolean isAutoScaling(){
        return autoScaling;
    }
}
