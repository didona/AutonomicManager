package eu.cloudtm.RESTServer;

import eu.cloudtm.StatsManager;
import eu.cloudtm.controller.Controller;
import org.glassfish.hk2.utilities.binding.AbstractBinder;

import javax.inject.Singleton;

/**
 * Created by: Fabio Perfetti
 * E-mail: perfabio87@gmail.com
 * Date: 7/2/13
 */
public class RESTBinder extends AbstractBinder {

    private Controller controller;
    private StatsManager statsManager;

    public RESTBinder(Controller _controller, StatsManager _statsManager){
        controller = _controller;
        statsManager = _statsManager;
    }

    @Override
    protected void configure() {
        //singleton controller
        bind(Controller.class).in(Singleton.class);
        bind(controller).to(Controller.class);

        //statsManager controller
        bind(StatsManager.class).in(Singleton.class);
        bind(controller).to(Controller.class);
    }


}
