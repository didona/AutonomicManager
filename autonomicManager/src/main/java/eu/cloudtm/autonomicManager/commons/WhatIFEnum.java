package eu.cloudtm.autonomicManager.commons;

/**
 * // TODO: Document this
 *
 * @author diego
 * @since 4.0
 */
public enum WhatIFEnum {
   ALL, //Take all values in a given interval
   SAMPLING, //Divide the interval in X and take 1 value per interval
   FIXED //Take a value each X values
}
