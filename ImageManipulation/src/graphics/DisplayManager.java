package graphics;

import java.awt.Dimension;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;

public class DisplayManager {
	
	private static GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
	private static final int BUFFERS = 3;
	
	private static int frameWidth = gd.getDisplayMode().getWidth() / 2;
	private static int frameHeight = gd.getDisplayMode().getHeight() / 2;
	private static int scale = 1;
	private static Dimension resolution;
	
	public static Dimension getScreenSize() {
		return new Dimension(gd.getDisplayMode().getWidth(), gd.getDisplayMode().getHeight());
	}
	
	public static int getBuffers() {
		return DisplayManager.BUFFERS;
	}
	
	public static void setFrameWidth(int width) {
		DisplayManager.frameWidth = width;
	}
	
	public static int getFrameWidth() {
		return DisplayManager.frameWidth;
	}
	
	public static void setFrameHeight(int height) {
		DisplayManager.frameHeight = height;
	}
	
	public static int getFrameHeight() {
		return DisplayManager.frameHeight;
	}
	
	public static void setScale(int scale) {
		DisplayManager.scale = scale;
		DisplayManager.frameWidth /= scale;
		DisplayManager.frameHeight /= scale;
	}
	
	public static int getScale() {
		return DisplayManager.scale;
	}
	
	public static void setResolution(Dimension res) {
		DisplayManager.resolution = res;
	}
	
	public static Dimension getResolution() {
		return DisplayManager.resolution;
	}
}
