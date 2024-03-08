import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;
import javax.imageio.ImageIO;

//Import the package names here: Compression, Inversion, Flipping etc. 
import ImageFlip.ImageFlipTask;

public class Project {
    public static void main(String[] args) throws IOException, InterruptedException {
        try {

            // Start the timer
            long startTime = System.currentTimeMillis();
            // Load the original image
            BufferedImage originalImage = ImageIO.read(new File("images/input.png"));

            // Define the number of threads to use
            int numThreads = 8;
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

            // Menu for image processing actions
            System.out.println("Choose an action:");
            System.out.println("1. Inverse Transformation");
            System.out.println("2. Horizontal Flip");
            System.out.println("3. Vertical Flip");
            System.out.println("4. Compress");

            Scanner scanner = new Scanner(System.in);
            int choice = scanner.nextInt();

            // Depending on the user's choice, execute the corresponding image processing operation in a separate thread
            switch (choice) {
                case 1:
                    System.out.println("Function not yet implemented.");
                    break;
                case 2:
                    // Horizontal Flip
                    executeFlip(originalImage, flippedImage, threads, numThreads, segmentHeight, remainingHeight, startIndex, 1);
                    System.out.println("Image flipped horizontally successfully.");
                    break;
                case 3:
                    // Vertical Flip
                    executeFlip(originalImage, flippedImage, threads, numThreads, segmentHeight, remainingHeight, startIndex, -1);
                    System.out.println("Image flipped vertically successfully.");
                    break;
                case 4:
                    System.out.println("Compress functionality is not yet available.");
                    break;
                default:
                    System.out.println("Invalid choice!");
            }

            // Wait for all threads to finish
            for (Thread thread : threads) {
                thread.join();
            }

            // Stop the timer
          long stopTime = System.currentTimeMillis();

          // Calculate elapsed time
          long elapsedTime = stopTime - startTime;

            // Save the flipped image
            File output = new File("images/output.png");
            ImageIO.write(flippedImage, "png", output);
            // Print the time taken
            System.out.println("Image size Height: "+height+" Width: "+width+".  "+" Time taken: " + elapsedTime + " milliseconds.");
    

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
      //Implement Call functions here
     // Function to execute image Inversion  operation

    // Function to execute image flipping operation
    private static void executeFlip(BufferedImage originalImage, BufferedImage flippedImage, Thread[] threads, int numThreads,
                                     int segmentHeight, int remainingHeight, int startIndex, int flipDirection) {
        // Create and start threads for flipping
        for (int i = 0; i < numThreads; i++) {
            int segmentEndIndex = startIndex + segmentHeight + (i == numThreads - 1 ? remainingHeight : 0);
            threads[i] = new Thread(new ImageFlipTask(originalImage, flippedImage, startIndex, segmentEndIndex, flipDirection));
            threads[i].start();
            startIndex = segmentEndIndex;
        }
    }
     // Function to execute image Resize  operation
}
