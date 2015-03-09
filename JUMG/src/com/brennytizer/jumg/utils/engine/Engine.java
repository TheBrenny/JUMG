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
		long lastTime = System.nanoTime(); // get the last time we ticked (in nano seconds)
		double nsPerTick = 1000000000D / (double) maxTPS; // 1 billion nanoseconds per second.
														  //divide the nanoseconds per ticks to get the nanosecond spacing between ticks
		int ticks = 0; // records ticks that have passed.
		int frames = 0; // records frames that have passed.
		
		long lastTimer = Time.getCurrentTime(0); // the last time we gave tick and frame information
		long now = System.nanoTime(); // what nano time is it now?
		double delta = 0; // the amount of ticks we need to process
		boolean shouldRender = false;
		running = init(); // initialise the outside world.
		while(running) { // if we're all g, keep going.
			now = System.nanoTime();
			delta += (now - lastTime) / nsPerTick; // nano time elapsed (divided by) nano time per tick
			lastTime = now; // last tick is now
			shouldRender = false; // don't render to start with
			
			while(delta >= 1) { // If we need to process a tick
				ticks++; //Add to the tick counter
				tick(); // tick the outside world
				delta -= 1; // We ticked, so take away from the ticks we need to process.
				shouldRender = true; // We changed the game, show the results.
			}
			
			if(shouldRender) { // If we should render
				frames++; // Add to the frame counter.
				render(); // Render the outside world
			}
			
			if(Time.getCurrentTime(0) - lastTimer >= 1000) { // Is the elapsed milli time larger than 1k?
				lastTimer += 1000; // Add a second on to the total milli time.
				TICKS_PS = ticks; // Set the global counters
				FRAMES_PS = frames;
				frames = 0; // reset our local counters
				ticks = 0;
			}
			try {
				Thread.sleep(threadTimeout); // Sleep so we don't overload the computer
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