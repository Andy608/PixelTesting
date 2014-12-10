package graphics;

public class PixelContainer {

	public final int width;
	public final int height;
	public final int[] pixels;
	
	public PixelContainer(int w, int h) {
		this.width = w;
		this.height = h;
		this.pixels = new int[width * height];
	}
	
	public void formatPixels(PixelContainer pixelContainer, int xOffset, int yOffset) {
		
		for (int y = 0; y < pixelContainer.height; y++) {
			int yy = y + yOffset;
			
			if (yy < 0 || yy >= height) {
				continue;
			}
			
			for (int x = 0; x < pixelContainer.width; x++) {
				int xx = x + xOffset;
				
				if (xx < 0 || xx >= width) {
					continue;
				}
				int transparentTest = pixelContainer.pixels[x + y * pixelContainer.width];
				if (transparentTest > 0)
					pixels[xx + yy * width] = transparentTest;
			}
		}
	}
}
