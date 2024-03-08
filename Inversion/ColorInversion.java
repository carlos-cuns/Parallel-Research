package Inversion;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.awt.Color;
import java.io.File;
import java.io.IOException;

class Invert {

    static Color invertRGB(Color c) {
        int red = 255 - c.getRed();
        int green = 255 - c.getGreen();
        int blue = 255 - c.getBlue();
        return new Color(red, green, blue);
    }

    // returns an array of subimages from an original image
    static BufferedImage[] splitImage(BufferedImage image, int numThreads) {
        int width = image.getWidth();
        int height = image.getHeight();
        int partHeight = height / numThreads;

        BufferedImage[] parts = new BufferedImage[numThreads];

        for (int i = 0; i < numThreads; i++) {
            parts[i] = image.getSubimage(0, i * partHeight, width, partHeight);
        }
        return parts;
    }

    // takes a number of threads and an image and inverts the colors
    static void InvertMulti(BufferedImage image, int numThreads) {
        try {
            // Divide the image into 8 parts and process each part in a separate thread
            BufferedImage[] parts = splitImage(image, numThreads);

            // Create an array of threads
            Thread[] threads = new Thread[numThreads];

            for (int i = 0; i < numThreads; i++) {
                Thread t = new Thread(new InvertRunnable(parts[i]));
                threads[i] = t;
                t.start();
            }
            for (Thread t : threads) {
                t.join();
            }
            // Write the inverted image to a file
            ImageIO.write(image, "png", new File("images/output.png"));
        } catch (IOException | InterruptedException e) {
            System.out.println("Unable to save inverted image");
            System.err.println(e);
        }
    }
}

// Runnable based off of the approach that a single thread takes to invert an
// image
class InvertRunnable implements Runnable {
    BufferedImage image;

    InvertRunnable(BufferedImage image) {
        this.image = image;
    }

    @Override
    public void run() {
        int width = image.getWidth();
        int height = image.getHeight();
        // Iterate through each pixel in the given part of the image
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                // Get pixel color
                Color color = new Color(image.getRGB(x, y));
                // Invert the RGB values
                Color newColor = Invert.invertRGB(color);
                // Set the new RGB value
                image.setRGB(x, y, newColor.getRGB());
            }
        }
    }
}

public class ColorInversion {
    static final String imgPath = "images/input.png";
    final static int MAXTHREADS = 8;
    final static int NUMTRIALS = 500;

    static long getAvgTimeMS(long time) {
        return (long) ((long) time / 1e6 / NUMTRIALS);
    }

    public static void main(String[] args) {
        try {
            // Read the image into two copies
            final BufferedImage image = ImageIO.read(new File(imgPath));

            for (int NUMTHREADS = 1; NUMTHREADS <= MAXTHREADS; NUMTHREADS++) {
                // invert using several threads
                long start = System.nanoTime();
                for (int i = 0; i < NUMTRIALS; i++)
                    Invert.InvertMulti(image, NUMTHREADS);
                long time = System.nanoTime() - start;

                System.out.println(" Number of threads: " + NUMTHREADS + ", AVG: " + getAvgTimeMS(time) + "ms");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}