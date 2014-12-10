package com.dreamstone.component;

import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Toolkit;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;

import com.dreamstone.core.Game;
import com.dreamstone.gui.Screen;

public final class EscapeCanvas extends Canvas implements Runnable {

	private static final long serialVersionUID = -5025704194120253102L;
	private static GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
	private static final int DEFAULT_WIDTH = gd.getDisplayMode().getWidth() / 6;
	private static final int DEFAULT_HEIGHT = gd.getDisplayMode().getHeight() / 6;
	private static final int SCALE = 3;
	
	private Game game;
	private Screen screen;
	private BufferedImage img;
	private int pixels[];
	
	private boolean running;
	private Thread gameThread;
	private static final int BUFFERS = 2;
	
	public EscapeCanvas() {
		Dimension preferredSize = new Dimension(DEFAULT_WIDTH * SCALE, DEFAULT_HEIGHT * SCALE);
		this.setPreferredSize(preferredSize);
		Toolkit.getDefaultToolkit().setDynamicLayout(false);
		game = new Game();
		screen = new Screen(DEFAULT_WIDTH, DEFAULT_HEIGHT);
		
		img = new BufferedImage(DEFAULT_WIDTH, DEFAULT_HEIGHT, BufferedImage.TYPE_INT_RGB);
		pixels = ((DataBufferInt)img.getRaster().getDataBuffer()).getData();
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
			
			while (unproccessedSeconds > secondsPerTick) {
				tick();
				
				unproccessedSeconds -= secondsPerTick;
				
				tickCount++;
				if (tickCount % 60 == 0) {
					System.out.println("Ticks: " + tickCount + ", Fps: " + frames);
					lastTime += 1000;
					frames = 0;
					tickCount = 0;
				}
			}
			render();
			frames++;
		}
	}
	
	private void tick() {
		game.tick();
	}
	
	private void render() {
		BufferStrategy bs = this.getBufferStrategy();
		
		if (bs == null) {
			this.createBufferStrategy(BUFFERS);
			this.requestFocus();
			return;
		}
		
		screen.render(game);
		for (int i = 0; i < DEFAULT_WIDTH * DEFAULT_HEIGHT; i++) {
			pixels[i] = screen.pixels[i];
		}
		
		Graphics g = bs.getDrawGraphics();
		g.drawImage(img, 0, 0, DEFAULT_WIDTH * SCALE, DEFAULT_HEIGHT * SCALE, null);
		
        g.dispose();
        bs.show();
	}
}
