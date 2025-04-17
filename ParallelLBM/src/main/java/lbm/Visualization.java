package lbm;

import java.io.FileWriter;
import java.io.IOException;

public class Visualization {
    public static void saveVelocityField(double[][] u, String filename) {
        try {
            // Create output directory if it doesn't exist
            new java.io.File(filename).getParentFile().mkdirs();
            
            try (FileWriter writer = new FileWriter(filename)) {
                for (int j = Constants.NY-1; j >= 0; j--) {
                    for (int i = 0; i < Constants.NX; i++) {
                        writer.write(String.format("%.6f", u[i][j]));
                        if (i < Constants.NX - 1) writer.write(",");
                    }
                    writer.write("\n");
                }
            }
        } catch (IOException e) {
            System.err.println("Error saving data: " + e.getMessage());
        }
    }
}