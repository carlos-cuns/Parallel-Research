package Resizing;

import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.awt.Image;
import javax.imageio.ImageIO;

import javax.imageio.ImageIO;

public class ResizingTask implements Runnable {
    private BufferedImage originalImage;
    //private BufferedImage resizedImage;
    private int newWidth;
    private int newHeight;

    public ResizingTask(BufferedImage originalImage, int newWidth, int newHeight) {
        this.originalImage = originalImage;
        //this.resizedImage = resizedImage;
        this.newWidth = newWidth;
        this.newHeight = newHeight;
    }

    private static BufferedImage resizeImage(BufferedImage originalImage, int newWidth, int newHeight) {
        BufferedImage resizedImage = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = resizedImage.createGraphics();
        g2d.drawImage(originalImage, 0, 0, newWidth, newHeight, null);
        g2d.dispose();
        return resizedImage;
    }

    @Override
    public void run() {
        String outputFile = "images/output5.png";

        try {
            BufferedImage resizedImage = resizeImage(originalImage, newWidth, newHeight);
            
            
            ImageIO.write(resizedImage, "png", new File(outputFile));

            // Assign the resized image to the instance variable
            //this.resizedImage = resizedImage;
        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
