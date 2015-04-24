package de.ulfbiallas.imagedatabase.tools;

import java.awt.Color;
import java.awt.image.BufferedImage;



public class Histogram {

	private int bins = 18;
	private double factor = 1.0;
	private double[] data = new double[bins];



	public Histogram(BufferedImage image) {
		factor = 1.0 / (image.getWidth() * image.getHeight());
		processImage(image);
	}



	private void processImage(BufferedImage img) {

		int ix,iy;
		Color color;
		double hue;
		int bin;
		float[] hsv = new float[3];
		for (iy=0; iy<img.getHeight(); ++iy) {
			for (ix=0; ix<img.getWidth(); ++ix) {
				color = new Color(img.getRGB(ix, iy));
				hsv = Color.RGBtoHSB(color.getRed(), color.getGreen(), color.getBlue(), hsv);
				hue = hsv[0];
				bin = (int) Math.floor(hue * bins / 360);
				data[bin] += factor;
			}
		}
	}



	public double distance(Histogram histogram) {
		double score = 0.0;
		double kDistance;
		for(int k=0; k<bins; ++k) {
			kDistance = data[k] - histogram.getData()[k];
			score += Math.sqrt(kDistance * kDistance);
		}
		return score;
	}



	public double[] getData() {
		return data;
	}

}
