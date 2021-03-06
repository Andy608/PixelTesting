package com.dreamstone.gui;

public class PixelContainer {
	
	public final int width;
	public final int height;
	public final int pixels[];
	
	public PixelContainer(int w, int h) {
		this.width = w;
		this.height = h;
		this.pixels = new int[w * h];
	}
	
	public void draw(PixelContainer pc, int xOffset, int yOffset) {
		for (int y = 0; y < pc.height; y++) {
			int yPix = y + yOffset;
			if (yPix < 0 || yPix >= height) {
				continue;
			}
			for (int x = 0; x < pc.width; x++) {
				int xPix = x + xOffset;
				if (xPix < 0 || xPix >= width) {
					continue;
				}
				
				int src = pc.pixels[x + y * pc.width];
				if (src > 0)
					pixels[xPix + yPix * width] = src;
			}
		}
	}
}
