package lbm;

import java.util.concurrent.*;

public class ParallelGrid {
    private final LBMSolver[] solvers;
    private final ExecutorService executor;
    private final CyclicBarrier barrier;
    
    public ParallelGrid() {
        this.solvers = new LBMSolver[Constants.NUM_THREADS];
        this.executor = Executors.newFixedThreadPool(Constants.NUM_THREADS);
        this.barrier = new CyclicBarrier(Constants.NUM_THREADS);
        
        int chunkSize = Constants.NX / Constants.NUM_THREADS;
        for (int t = 0; t < Constants.NUM_THREADS; t++) {
            int start = t * chunkSize;
            int end = (t == Constants.NUM_THREADS-1) ? Constants.NX : (t+1)*chunkSize;
            solvers[t] = new LBMSolver(start, end);
        }
    }

    public void simulate(int steps) {
        for (int t = 0; t < Constants.NUM_THREADS; t++) {
            final int threadId = t;
            executor.submit(() -> {
                try {
                    for (int s = 0; s < steps; s++) {
                        solvers[threadId].collideAndStream();
                        barrier.await();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return null;
            });
        }
        executor.shutdown();
    }
    
    public double[][] getU() {
        double[][] u = new double[Constants.NX][Constants.NY];
        for (LBMSolver solver : solvers) {
            double[][] localU = solver.getU();
            System.arraycopy(localU, 0, u, 0, localU.length);
        }
        return u;
    }
}