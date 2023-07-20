import java.util.Random;

public class Convolution {
    // 1D Convolution
    public static double[] convolve1D(double[] signal, double[] kernel) {
        int kernelSize = kernel.length;
        int signalSize = signal.length;
        int resultSize = signalSize + kernelSize - 1;
        double[] result = new double[resultSize];

        for (int i = 0; i < resultSize; i++) {
            for (int j = 0; j < kernelSize; j++) {
                if (i - j >= 0 && i - j < signalSize) {
                    result[i] += signal[i - j] * kernel[j];
                }
            }
        }

        return result;
    }

    // 4D Convolution
    public static double[][][][] convolve4D(double[][][][] image, double[][][][] kernel) {
        int outputHeight = image.length - kernel.length + 1;
        int outputWidth = image[0].length - kernel[0].length + 1;
        int outputChannels = kernel[0][0][0].length;
        int batchSize = image[0][0][0].length;
        double[][][][] output = new double[outputHeight][outputWidth][outputChannels][batchSize];

        // Perform convolution
        for (int i = 0; i < outputHeight; i++) {
            for (int j = 0; j < outputWidth; j++) {
                for (int outChannel = 0; outChannel < outputChannels; outChannel++) {
                    for (int batch = 0; batch < batchSize; batch++) {
                        for (int inChannel = 0; inChannel < image[0][0].length; inChannel++) {
                            for (int kernelRow = 0; kernelRow < kernel.length; kernelRow++) {
                                for (int kernelCol = 0; kernelCol < kernel[0].length; kernelCol++) {
                                    output[i][j][outChannel][batch] += image[i + kernelRow][j + kernelCol][inChannel][batch]
                                            * kernel[kernelRow][kernelCol][inChannel][outChannel];
                                }
                            }
                        }
                    }
                }
            }
        }

        return output;
    }

    // Helper function to display 4D output tensor (Same as Python implementation)
    // ...

    public static void main(String[] args) {
        // 1D Convolution Example (Same as before)
        // ...

        // 4D Convolution Example
        int height = 5;
        int width = 5;
        int numChannels = 3;
        int batchSize = 2;
        double[][][][] image = new double[height][width][numChannels][batchSize];
        double[][][][] kernel = new double[3][3][numChannels][4];

        Random random = new Random();
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                for (int c = 0; c < numChannels; c++) {
                    for (int b = 0; b < batchSize; b++) {
                        image[i][j][c][b] = random.nextDouble();
                    }
                }
            }
        }

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                for (int c = 0; c < numChannels; c++) {
                    for (int oc = 0; oc < 4; oc++) {
                        kernel[i][j][c][oc] = random.nextDouble();
                    }
                }
            }
        }

        double[][][][] output = convolve4D(image, kernel);
        visualize4DConvolution(output); // Implement the visualization function in Java
    }
}
