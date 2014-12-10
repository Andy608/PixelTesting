package core;

import graphics.PixelCanvas;
import graphics.PixelFrame;

import java.awt.EventQueue;

public class Main {
	private static PixelCanvas canvasInstance = new PixelCanvas();
	private static PixelFrame frameInstance = new PixelFrame(canvasInstance);
	
	public static void main(String[] args) {
		
		EventQueue.invokeLater(new Runnable() {
			
			@Override
			public void run() {
				Loop.gameInstance = new Loop();
				Loop.getGame().setCanvas(canvasInstance);
				Loop.getGame().setFrame(frameInstance);
				Loop.getGame().start();
			}
		});
	}
}
