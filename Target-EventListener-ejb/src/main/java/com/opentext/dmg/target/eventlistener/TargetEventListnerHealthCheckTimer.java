package com.opentext.dmg.target.eventlistener;

import java.util.Timer;
import java.util.TimerTask;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


public class TargetEventListnerHealthCheckTimer extends TimerTask{

	private static final Log LOGGER = LogFactory.getLog(TargetEventListnerHealthCheckTimer.class);
	Timer timer;

	public TargetEventListnerHealthCheckTimer() {
		timer = new Timer(true);
		timer.scheduleAtFixedRate(this, 0, 900 * 1000);

	}
	
	@Override
	public void run() {
		 LOGGER.info("Events Listener is alive!");
		
	}
	
}