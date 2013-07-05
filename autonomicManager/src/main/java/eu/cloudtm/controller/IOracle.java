package eu.cloudtm.controller;

import eu.cloudtm.controller.exceptions.OracleException;
import eu.cloudtm.controller.model.KPI;
import eu.cloudtm.controller.oracles.OracleInput;

import java.util.Set;

/**
 * Created by: Fabio Perfetti
 * E-mail: perfabio87@gmail.com
 * Date: 6/12/13
 */
public interface IOracle {

    public KPI minimizeCosts(OracleInput sample, double arrivalRate, double abortRate, double responseTime) throws OracleException;

    public Set<KPI> whatIf(OracleInput sample) throws OracleException;

    public KPI forecast(OracleInput sample) throws OracleException;

    //public KPI maximizeThroughput(OracleInput sample);

    //public KPI forecastWithCustomParam(WPMSample sample, WhatIfCustomParamDTO customParam, int numNodes, int numThreads) throws OracleException;

}
