import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;
import javax.imageio.ImageIO;

//Import the package names here: Compression, Inversion, Flipping etc. 
import ImageFlip.ImageFlipTask;
import Inversion.InversionTask;
import Grayscale.GrayscaleTask;

public class Testing {
    public static void Test(BufferedImage originalImage, int choice, int numThreads) {
        long startTime = System.currentTimeMillis();

        Thread[] threads = new Thread[numThreads];

        // Get image dimensions
        int width = originalImage.getWidth();
        int height = originalImage.getHeight();

        // Create a new image for the flipped result
        BufferedImage flippedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

        // Divide the image into equal parts for each thread
        int segmentHeight = height / numThreads;
        int remainingHeight = height % numThreads;
        int startIndex = 0;
        switch (choice) {
            case 1:
                executeInvert(originalImage, flippedImage, threads, numThreads, segmentHeight, remainingHeight,
                        startIndex);
                System.out.println("Image inverted successfully.");
                break;
            case 2:
                // Horizontal Flip
                executeFlip(originalImage, flippedImage, threads, numThreads, segmentHeight, remainingHeight,
                        startIndex, 1);
                System.out.println("Image flipped horizontally successfully.");
                break;
            case 3:
                // Vertical Flip
                executeFlip(originalImage, flippedImage, threads, numThreads, segmentHeight, remainingHeight,
                        startIndex, -1);
                System.out.println("Image flipped vertically successfully.");
                break;
            case 4:
                executeGrayscale(originalImage, flippedImage, threads, numThreads, segmentHeight, remainingHeight,
                        startIndex);
                System.out.println("Image grayscale successfully.");
                break;
            // break
            default:
                System.out.println("Invalid choice!");
        }
        // Wait for all threads to finish
        for (Thread thread : threads) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        // Stop the timer
        long stopTime = System.currentTimeMillis();

        // Calculate elapsed time
        long elapsedTime = stopTime - startTime;

        // Save the flipped image
        File output = new File("images/output" + choice + ".png");
        try {
            ImageIO.write(flippedImage, "png", output);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        // Print the time taken
        System.out.println("Image size Height: " + height + " Width: " + width + ".  " + " Time taken: "
                + elapsedTime + " milliseconds.");

    }

    public static void main(String[] args) throws IOException, InterruptedException {

        // Start the timer
        // Load the original image
        BufferedImage originalImageS = ImageIO.read(new File("images/small.png"));
        BufferedImage originalImageM = ImageIO.read(new File("images/medium.png"));
        BufferedImage originalImageL = ImageIO.read(new File("images/large.png"));

        // Menu for image processing actions
        System.out.println("Choose an action:");
        System.out.println("1. Inverse Transformation");
        System.out.println("2. Horizontal Flip");
        System.out.println("3. Vertical Flip");
        System.out.println("4. Grayscale");
        System.out.println("5. Resize");
        // add resizing to whole project.java
        Scanner scanner = new Scanner(System.in);
        int choice = scanner.nextInt();

        Test(originalImageS, choice, 1);
        Test(originalImageM, choice, 1);
        Test(originalImageL, choice, 1);
        Test(originalImageS, choice, 8);
        Test(originalImageM, choice, 8);
        Test(originalImageL, choice, 8);
        Test(originalImageS, choice, 16);
        Test(originalImageM, choice, 16);
        Test(originalImageL, choice, 16);
    }

    // Implement Call functions here
    // Function to execute image Inversion operation
    private static void executeInvert(BufferedImage originalImage, BufferedImage flippedImage, Thread[] threads,
            int numThreads,
            int segmentHeight, int remainingHeight, int startIndex) {
        // Create and start threads for flipping
        for (int i = 0; i < numThreads; i++) {
            int segmentEndIndex = startIndex + segmentHeight + (i == numThreads - 1 ? remainingHeight : 0);
            threads[i] = new Thread(
                    new InversionTask(originalImage, flippedImage, startIndex, segmentEndIndex));
            threads[i].start();
            startIndex = segmentEndIndex;
        }
    }

    // Function to execute image flipping operation
    private static void executeFlip(BufferedImage originalImage, BufferedImage flippedImage, Thread[] threads,
            int numThreads,
            int segmentHeight, int remainingHeight, int startIndex, int flipDirection) {
        // Create and start threads for flipping
        for (int i = 0; i < numThreads; i++) {
            int segmentEndIndex = startIndex + segmentHeight + (i == numThreads - 1 ? remainingHeight : 0);
            threads[i] = new Thread(
                    new ImageFlipTask(originalImage, flippedImage, startIndex, segmentEndIndex, flipDirection));
            threads[i].start();
            startIndex = segmentEndIndex;
        }
    }

    private static void executeGrayscale(BufferedImage originalImage, BufferedImage flippedImage, Thread[] threads,
            int numThreads,
            int segmentHeight, int remainingHeight, int startIndex) {
        // Create and start threads for flipping
        for (int i = 0; i < numThreads; i++) {
            int segmentEndIndex = startIndex + segmentHeight + (i == numThreads - 1 ? remainingHeight : 0);
            threads[i] = new Thread(
                    new GrayscaleTask(originalImage, flippedImage, startIndex, segmentEndIndex));
            threads[i].start();
            startIndex = segmentEndIndex;
        }
    }

    // Function to execute image Resize operation
}
