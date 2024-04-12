package com.avalant.wm.scheduler.time;

import javax.annotation.Resource;
import javax.ejb.LocalBean;
import javax.ejb.Remote;
import javax.ejb.SessionContext;
import javax.ejb.Stateless;
import javax.ejb.Timeout;
import javax.ejb.Timer;

/**
 * Session Bean implementation class TimerSessionBean
 */
@Stateless
@Remote(TimerSessionBeanRemote.class)
@LocalBean
public class TimerSessionBean implements TimerSessionBeanRemote {

	
	@Resource
	private SessionContext context;
	
    /**
     * Default constructor. 
     */
    public TimerSessionBean() {
        // TODO Auto-generated constructor stub
    }

	@Override
	public void createTimer(long duration) {
		context.getTimerService().createTimer(duration, "aaaaaaaaaaaaaa");
		
	}

    @Timeout
    public void timeOutHandler(Timer timer){
    	System.out.println("timeoutHandler : " + timer.getInfo());        
    	timer.cancel();
    }
}
