package lbm;

public class Constants {
    public static final int NX = 200;
    public static final int NY = 200;
    public static final int NUM_THREADS = Math.min(Runtime.getRuntime().availableProcessors(), 8);
    public static final double TAU = 0.6;
    public static final double U_INLET = 0.1;
    public static final int Q = 9;
    
    public static final int[][] E = {
        {0,0}, {1,0}, {0,1}, {-1,0}, {0,-1}, 
        {1,1}, {-1,1}, {-1,-1}, {1,-1}
    };
    
    public static final double[] W = {
        4.0/9, 1.0/9, 1.0/9, 1.0/9, 1.0/9,
        1.0/36, 1.0/36, 1.0/36, 1.0/36
    };
}