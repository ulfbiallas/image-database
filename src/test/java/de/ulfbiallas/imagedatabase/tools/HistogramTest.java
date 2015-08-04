package de.ulfbiallas.imagedatabase.tools;


import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.Arrays;

import org.junit.Assert;
import org.junit.Test;

import de.ulfbiallas.imagedatabase.tools.Histogram;



public class HistogramTest {

    @Test
    public void testBlackImage() {
        BufferedImage image = createBlackImage();
        Histogram histogram = new Histogram(image);
        double[] histogramVector = histogram.getData();
        System.out.println(Arrays.toString(histogramVector));

        Assert.assertArrayEquals(histogramVector, getHistogramVectorForBlackImage(), 0.001);
    }

    @Test
    public void testWhiteImage() {
        BufferedImage image = createYellowImage();
        Histogram histogram = new Histogram(image);
        double[] histogramVector = histogram.getData();
        System.out.println(Arrays.toString(histogramVector));

        Assert.assertArrayEquals(histogramVector, getHistogramVectorForYellowImage(), 0.001);
    }

    @Test
    public void testCheckerboardImage() {
        BufferedImage image = createCheckerboardImage();
        Histogram histogram = new Histogram(image);
        double[] histogramVector = histogram.getData();
        System.out.println(Arrays.toString(histogramVector));

        Assert.assertArrayEquals(histogramVector, getHistogramVectorForCheckerboardImage(), 0.001);
    }



    private BufferedImage createBlackImage() {
        BufferedImage image = new BufferedImage(10, 10, BufferedImage.TYPE_INT_RGB);
        int ix, iy;
        for(iy=0; iy<image.getHeight(); ++iy) {
            for(ix=0; ix<image.getWidth(); ++ix) {
                image.setRGB(ix, iy, Color.BLACK.getRGB());
            }
        }
        return image;
    }

    private BufferedImage createYellowImage() {
        BufferedImage image = new BufferedImage(10, 10, BufferedImage.TYPE_INT_RGB);
        int ix, iy;
        for(iy=0; iy<image.getHeight(); ++iy) {
            for(ix=0; ix<image.getWidth(); ++ix) {
                image.setRGB(ix, iy, Color.YELLOW.getRGB());
            }
        }
        return image;
    }

    private BufferedImage createCheckerboardImage() {
        BufferedImage image = new BufferedImage(10, 10, BufferedImage.TYPE_INT_RGB);
        int ix, iy;
        for(iy=0; iy<image.getHeight(); ++iy) {
            for(ix=0; ix<image.getWidth(); ++ix) {
                if((ix + iy) % 2 == 0) {
                    image.setRGB(ix, iy, Color.GREEN.getRGB());
                } else {
                    image.setRGB(ix, iy, Color.BLUE.getRGB());
                }
            }
        }
        return image;
    }

    private double[] getHistogramVectorForBlackImage() {
        double[] histogramVector = {1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0};
        return histogramVector;
    }

    private double[] getHistogramVectorForYellowImage() {
        double[] histogramVector = {0.0, 0.0, 0.0, 1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0};
        return histogramVector;
    }

    private double[] getHistogramVectorForCheckerboardImage() {
        double[] histogramVector = {0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.5, 0.0, 0.0, 0.0, 0.0, 0.0, 0.5, 0.0, 0.0, 0.0, 0.0, 0.0};
        return histogramVector;
    }

}
