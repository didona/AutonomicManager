package eu.cloudtm.common.dto;

import eu.cloudtm.controller.model.utils.Forecaster;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by: Fabio Perfetti
 * E-mail: perfabio87@gmail.com
 * Date: 6/17/13
 */
public class WhatIfResult {

    private Forecaster forecaster;

    private Collection<Collection<Double>> throughput = new ArrayList<Collection<Double>>();
    private Collection<Collection<Double>> responseTime = new ArrayList<Collection<Double>>();
    private Collection<Collection<Double>> abortRate = new ArrayList<Collection<Double>>();

    public WhatIfResult(Forecaster _forecaster){
        forecaster = _forecaster;
    }

    public void addThroughputPoint(long x, double y){
        Collection<Double> point = new ArrayList<Double>();
        point.add((double) x);
        point.add(y);
        throughput.add(point);
    }

    public void addResponseTimePoint(long x, double y){
        Collection<Double> point = new ArrayList<Double>();
        point.add((double) x);
        point.add(y);
        responseTime.add(point);
    }

    public void addAbortRatePoint(long x, double y){
        Collection<Double> point = new ArrayList<Double>();
        point.add((double) x);
        point.add(y);
        abortRate.add(point);
    }

    @Override
    public int hashCode() {
        return forecaster.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return forecaster.equals((Forecaster) obj);
    }



}
