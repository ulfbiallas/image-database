package de.ulfbiallas.imagedatabase.tools;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

public class ImageResizer {

	public static BufferedImage resizeWithMaximalEdgeLength(BufferedImage image, int maxEdgeLength) {

		int originalWidth = image.getWidth();
		int originalHeight = image.getHeight();
		Boolean isLandscape = originalWidth > originalHeight;

		double factor;
		if(isLandscape) {
			factor = ((double) maxEdgeLength) / ((double) originalWidth);
		} else {
			factor = ((double) maxEdgeLength) / ((double) originalHeight);
		}

		int width = (int) (originalWidth * factor);
		int height = (int) (originalHeight * factor);


		BufferedImage resizedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

		Graphics g = resizedImage.createGraphics();
		g.drawImage(image, 0, 0, width, height, null);
		g.dispose();

		return resizedImage;
	}

}
