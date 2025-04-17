package lbm;

public class Main {
    public static void main(String[] args) {
        System.out.println("Starting Parallel LBM Simulation");
        System.out.println("Grid size: " + Constants.NX + "x" + Constants.NY);
        System.out.println("Threads: " + Constants.NUM_THREADS);
        
        // Warm-up run
        new ParallelGrid().simulate(10);
        
        // Timed run
        ParallelGrid grid = new ParallelGrid();
        long startTime = System.nanoTime();
        grid.simulate(1000000);
        long endTime = System.nanoTime();
        
        double duration = (endTime - startTime) / 1e9;
        System.out.printf("Simulation completed in %.4f seconds%n", duration);
        
        // Verify results
        double[][] u = grid.getU();
        System.out.println("First velocity value: " + u[0][0]);
        System.out.println("Middle velocity value: " + u[Constants.NX/2][Constants.NY/2]);
        
        Visualization.saveVelocityField(u, "output/velocity.csv");
        System.out.println("Results saved to output/velocity.csv");
    }
}