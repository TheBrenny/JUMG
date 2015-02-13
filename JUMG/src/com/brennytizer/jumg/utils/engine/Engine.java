package com.brennytizer.jumg.utils.engine;

import com.brennytizer.jumg.utils.Logging;
import com.brennytizer.jumg.utils.Logging.LoggingSpice;

public abstract class Engine implements Runnable {
	public static int TICKS_PS = 0;
	public static int FRAMES_PS = 0;
	public static Engine INSTANCE;
	public boolean running = false;
	public Thread thread;
	public int maxTPS;
	public int threadTimeout;
	
	public Engine() {
		this(60, 15);
	}
	public Engine(int maxTPS, int threadTimeout) {
		Logging.log(LoggingSpice.MILD, "Making an engine with max ticks: " + maxTPS);
		Engine.INSTANCE = this;
		this.maxTPS = maxTPS;
		this.threadTimeout = threadTimeout;
	}
	
	public void run() {
		Logging.log(LoggingSpice.MILD, "Starting the run.");
		long lastTime = System.nanoTime();
		double nsPerTick = 1000000000D / (double) maxTPS;
		
		int ticks = 0;
		int frames = 0;
		
		long lastTimer = System.currentTimeMillis();
		double delta = 0;
		
		running = init();
		while(running) {
			long now = System.nanoTime();
			delta += (now - lastTime) / nsPerTick;
			lastTime = now;
			boolean shouldRender = true;
			
			while(delta >= 1) {
				ticks++;
				tick();
				delta -= 1;
				shouldRender = true;
			}
			
			if(shouldRender) {
				frames++;
				render();
			}
			
			if(System.currentTimeMillis() - lastTimer >= 1000) {
				lastTimer += 1000;
				TICKS_PS = ticks;
				FRAMES_PS = frames;
				frames = 0;
				ticks = 0;
			}
			try {
				Thread.sleep(threadTimeout);
			} catch(InterruptedException e) {
				e.printStackTrace();
			}
		}
		System.exit(0);
	}
	public abstract void tick();
	public abstract void render();
	public abstract boolean init();
	public Thread start() {
		thread = new Thread(this, "Engine Thread");
		thread.start();
		return thread;
	}
	public void stop() {
		Logging.log(LoggingSpice.MILD, "Stopping engine...");
		running = false;
	}
}