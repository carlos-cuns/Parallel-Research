package Grayscale;

import java.awt.Color;
import java.awt.image.BufferedImage;

public class GrayscaleTask implements Runnable {
    private BufferedImage image;
    private BufferedImage grayScaleImage;
    private int endIndex;
    private int startIndex;

    public GrayscaleTask(BufferedImage image, BufferedImage grayScaleImage, int startIndex, int endIndex) {
        this.image = image;
        this.grayScaleImage = grayScaleImage;
        this.startIndex = startIndex;
        this.endIndex = endIndex;
    }

    @Override
    public void run() {
        int width = image.getWidth();

        for (int y = startIndex; y < endIndex; y++) {
            for (int x = 0; x < width; x++) {
                Color color = new Color(image.getRGB(x, y));
                
                int grayColorValue = (color.getRed() + color.getGreen() + color.getBlue()) / 3;

                Color newColor = new Color(grayColorValue, grayColorValue, grayColorValue);
                
                grayScaleImage.setRGB(x, y, newColor.getRGB());
            }
        }
    }
}