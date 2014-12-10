package core;

import graphics.PixelCanvas;

import javax.swing.JFrame;

public class Loop implements Runnable {
	
	static Loop gameInstance;
	private static JFrame frameInstance;
	private static PixelCanvas canvasInstance;
	private static int time;
	
	private Thread gameThread;
	private boolean running;
	
	protected Loop() {
		System.out.println("IM READY!");
		time = 0;
	}
	
	public synchronized void start() {
		if (running) return;
		running = true;
		gameThread = new Thread(this);
		gameThread.start();
	}
	
	public synchronized void stop() {
		if (!running) return;
		running = false;
		try {
			gameThread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void run() {
		int frames = 0;
		
		double unproccessedSeconds = 0;
		long lastTime = System.nanoTime();
		double secondsPerTick = 1 / 60.0;
		int tickCount = 0;
		
		while (running) {
			long now = System.nanoTime();
			long passedTime = now - lastTime;
			lastTime = now;
			
			if (passedTime < 0) {
				passedTime = 0;
			}
			
			if (passedTime > 1_000_000_000) {
				passedTime = 1_000_000_000;
			}
			unproccessedSeconds += passedTime / 1_000_000_000.0;
			
			//boolean ticked = false;
			while (unproccessedSeconds > secondsPerTick) {
				tick();
				
				unproccessedSeconds -= secondsPerTick;
				//ticked = true;
				
				tickCount++;
				if (tickCount % 60 == 0) {
					System.out.println("Ticks: " + tickCount + ", Fps: " + frames);
					lastTime += 1000;
					frames = 0;
					tickCount = 0;
				}
			}
			
			//Updates frames independently from the game logic (ticks).
			this.getCanvas().render();
			frames++;
			
			//Controls the frames to update the same time as the game logic (ticks).
			/*if (ticked) {
				render();
				frames++;
			}*/
		}
	}
	
	public void tick() {
		time++;
	}
	
	public int getTime() {
		return time;
	}
	
	public static Loop getGame() {
		return gameInstance;
	}
	
	public void setFrame(JFrame frame) {
		frameInstance = frame;
	}
	
	public JFrame getFrame() {
		return frameInstance;
	}
	
	public void setCanvas(PixelCanvas canvas) {
		canvasInstance = canvas;
	}
	
	public PixelCanvas getCanvas() {
		return canvasInstance;
	}
}