//package eu.cloudtm.controller.model;
//
//import eu.cloudtm.controller.model.utils.Forecaster;
//import eu.cloudtm.controller.model.utils.TuningState;
//
///**
// * Created by: Fabio Perfetti
// * E-mail: perfabio87@gmail.com
// * Date: 6/14/13
// */
//public class Tuning {
//
//    private Forecaster forecaster;
//
//    public Tuning(){
//        forecaster = Forecaster.ANALYTICAL;
//    }
//
//    public Tuning(Forecaster forecaster){
//        set(forecaster);
//    }
//
//    public Forecaster getForecaster(){
//        return forecaster;
//    }
//
//    public void set(Forecaster _forecaster){
//        if( _forecaster==null || forecaster==Forecaster.MANUAL){
//            forecaster = Forecaster.MANUAL;
//        } else {
//            forecaster = _forecaster;
//            tuning = TuningState.SELF;
//        }
//    }
//
//    public boolean isAutoTuning(){
//        if( !forecaster.equals(Forecaster.MANUAL) ){
//            return true;
//        } else {
//            return false;
//        }
//    }
//
//    @Override
//    public boolean equals(Object obj) {
//        Tuning o = (Tuning) obj;
//        if( equals(o) ){
//            if(getForecaster().equals(o.getForecaster()))
//                return true;
//        }
//        return false;
//    }
//
//}
