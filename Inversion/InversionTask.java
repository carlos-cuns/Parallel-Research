package Inversion;

import java.awt.Color;
import java.awt.image.BufferedImage;

public class InversionTask implements Runnable {
    private BufferedImage image;
    private BufferedImage invertedImage;
    private int endIndex;
    private int startIndex;

    public InversionTask(BufferedImage image, BufferedImage invertedImage, int startIndex, int endIndex) {
        this.image = image;
        this.invertedImage = invertedImage;
        this.startIndex = startIndex;
        this.endIndex = endIndex;
    }

    @Override
    public void run() {
        int width = image.getWidth();
        // Iterate through each pixel in the given part of the image
        for (int y = startIndex; y < endIndex; y++) {
            for (int x = 0; x < width; x++) {
                // Get pixel color
                Color color = new Color(image.getRGB(x, y));
                // Invert the RGB values
                Color newColor = invertRGB(color);
                // Set the new RGB value
                invertedImage.setRGB(x, y, newColor.getRGB());
            }
        }
    }

    static Color invertRGB(Color c) {
        int red = 255 - c.getRed();
        int green = 255 - c.getGreen();
        int blue = 255 - c.getBlue();
        return new Color(red, green, blue);
    }
}
