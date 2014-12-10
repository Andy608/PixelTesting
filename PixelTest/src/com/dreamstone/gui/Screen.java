package com.dreamstone.gui;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;

import com.dreamstone.core.Game;
import com.dreamstone.file.FileSystem;

public class Screen extends PixelContainer {
	
	private PixelContainer testBitmap;
	private static Random rand;
	
	private BufferedImage img;
	File grass = FileSystem.makeFile(FileSystem.getResourcesFolder().getAbsolutePath(), "\\textures\\Individual Tiles\\Grass Tiles\\grass_to_dirt_east_1.png");
	private int r = (int) (Math.random() * 256);
	private int g = (int) (Math.random() * 256);
	private int b = (int) (Math.random() * 256);
	private int color;
	private boolean rCheck;
	private boolean gCheck;
	private boolean bCheck;
	
	public Screen(int w, int h) {
		super(w, h);
		
		rand = new Random();
		rCheck = false;
		gCheck = false;
		bCheck = false;
		testBitmap = new PixelContainer(50, 50);
		
		try {
			img = FileSystem.readImageFile(grass);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		/*for (int i = 0; i < 10 * 10; i++) {
			testBitmap.pixels[i] = (rand.nextInt() * rand.nextInt(5) / 4) >> 16;
		}*/
		
		for (int i = 0; i < 50 * 50; i++) {
			testBitmap.pixels[i] = rand.nextInt() * (rand.nextInt(10) / 9);
		}
		//clear();
	}

	public void render(Game game) {
		clear();
		colorChange();
		for (int i = 0; i < 100; i++) {
			int xp = /*(int) (game.time + i * 8) % 400 - 200;*/(int) ((Math.sin(((game.time + i) * 300) / 4000.0 * (Math.PI / 6)) * 100));
			int yp = /*(int) 0;*/(int)((Math.cos(((game.time - i) * 300) / 4000.0 * (Math.PI / 7 * 2)) * 60));
			draw(testBitmap, (this.width - 50) / 2 + xp, (this.height - 50) / 2 + yp);
		}
	}
	
	public void colorChange() {
		
		if (!rCheck && r < 255) {
			r++;
		}
		
		if (!gCheck && g < 255) {
			g++;
		}
		
		if (!bCheck && b < 255) {
			b++;
		}
		
		if (rCheck && r > 1) {
			r--;
		}
		
		if (gCheck && g > 1) {
			g--;
		}
		
		if (bCheck && b > 1) {
			b--;
		}
		
		if (r == 255) {
			rCheck = true;
		}
		
		if (g == 255) {
			gCheck = true;
		}
		
		if (b == 255) {
			bCheck = true;
		}

		if (r == 1) {
			rCheck = false;
		}
		
		if (g == 1) {
			gCheck = false;
		}
		
		if (b == 1) {
			bCheck = false;
		}
		
		color = r << 16;
		color += g << 8;
		color += b;
		for (int i = 0; i < 50 * 50; i++) {
			if (!(testBitmap.pixels[i] == 0)) {
				testBitmap.pixels[i] = color;
			}
		}
	}
	
	public void clear() {
		for (int i = 0; i < pixels.length; i++) {
			pixels[i] = 0;
		}
	}
	
}