package Grayscale;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;


class ImageParts implements Runnable {
    BufferedImage image;

    public ImageParts(BufferedImage image)
    {
        this.image = image;
    }
    
    public void run() {
        int height = image.getHeight();
        int width = image.getWidth();

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {

                Color color = new Color(image.getRGB(x, y));

                int grayColorValue = (color.getRed() + color.getGreen() + color.getBlue()) / 3;

                Color newColor = new Color(grayColorValue, grayColorValue, grayColorValue);

                image.setRGB(x, y, newColor.getRGB());
            }
        }
    }
}
public class Grayscale {

    public static BufferedImage[] splitImage(BufferedImage image, int numThreads) {
        int width = image.getWidth();
        int height = image.getHeight();
        int partHeight = height / numThreads;

        BufferedImage[] parts = new BufferedImage[numThreads];

        for (int i = 0; i < numThreads; i++) {
            parts[i] = image.getSubimage(0, i * partHeight, width, partHeight);
        }
        return parts;
    }

    public static void main(String[] args) throws IOException
    {
        int numThreads = 4;
        BufferedImage image = ImageIO.read(new File("images/input.png")); 

        try {
            BufferedImage[] parts = splitImage(image, numThreads);

            // Create an array of threads
            Thread[] threads = new Thread[numThreads];

            for (int i = 0; i < numThreads; i++) {
                Thread t = new Thread(new ImageParts(parts[i]));
                threads[i] = t;
                t.start();
            }
            for (Thread t : threads) {
                t.join();
            }
            // Write the inverted image to a file
            ImageIO.write(image, "png", new File("images/output.png"));
        } catch (IOException | InterruptedException e) {
            System.out.println("Unable to save grayscaled image");
            System.err.println(e);
        }
    }

}
