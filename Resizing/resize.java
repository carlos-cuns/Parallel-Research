// included libraries
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class ImageResizer {

    public static void main(String[] args) {
        String inputFile = "images/input.png"; 
        String outputFile = "images/output2.png";
        int newWidth = 200; // desired width
        int newHeight = 150; // desired height

        try {
            BufferedImage originalImage = ImageIO.read(new File(inputFile));
            BufferedImage resizedImage = resizeImage(originalImage, newWidth, newHeight);
            ImageIO.write(resizedImage, "png", new File(outputFile));
            System.out.println("Image resized successfully.");
        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static BufferedImage resizeImage(BufferedImage originalImage, int newWidth, int newHeight) {
        BufferedImage resizedImage = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = resizedImage.createGraphics();
        g2d.drawImage(originalImage, 0, 0, newWidth, newHeight, null);
        g2d.dispose();
        return resizedImage;
    }
}
