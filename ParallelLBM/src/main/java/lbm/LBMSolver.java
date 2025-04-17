package lbm;

public class LBMSolver {
    private final double[][][] f;
    private final double[][][] feq;  // Now properly used
    private final double[][] u, v;
    private final double[][] rho;
    private final int startX, endX;
    
    public LBMSolver(int startX, int endX) {
        this.startX = startX;
        this.endX = endX;
        this.f = new double[Constants.Q][endX - startX][Constants.NY];
        this.feq = new double[Constants.Q][endX - startX][Constants.NY];
        this.u = new double[endX - startX][Constants.NY];
        this.v = new double[endX - startX][Constants.NY];
        this.rho = new double[endX - startX][Constants.NY];
        initialize();
    }

    private void initialize() {
        for (int i = 0; i < endX - startX; i++) {
            for (int j = 0; j < Constants.NY; j++) {
                rho[i][j] = 1.0;
                u[i][j] = (j == Constants.NY-1) ? Constants.U_INLET : 0;
                v[i][j] = 0;
                for (int k = 0; k < Constants.Q; k++) {
                    f[k][i][j] = Constants.W[k];
                }
            }
        }
    }

    public void collideAndStream() {
        // Collision step using feq
        for (int i = 0; i < endX - startX; i++) {
            for (int j = 0; j < Constants.NY; j++) {
                double usqr = u[i][j]*u[i][j] + v[i][j]*v[i][j];
                for (int k = 0; k < Constants.Q; k++) {
                    double eu = Constants.E[k][0]*u[i][j] + Constants.E[k][1]*v[i][j];
                    feq[k][i][j] = Constants.W[k] * rho[i][j] * (1 + 3*eu + 4.5*eu*eu - 1.5*usqr);
                    f[k][i][j] -= (f[k][i][j] - feq[k][i][j]) / Constants.TAU;
                }
            }
        }
        
        // Streaming step
        for (int i = 0; i < endX - startX; i++) {
            for (int j = 0; j < Constants.NY; j++) {
                for (int k = 0; k < Constants.Q; k++) {
                    int nextX = (i + Constants.E[k][0] + (endX - startX)) % (endX - startX);
                    int nextY = (j + Constants.E[k][1] + Constants.NY) % Constants.NY;
                    f[k][nextX][nextY] = f[k][i][j];
                }
            }
        }
    }
    
    public double[][] getU() { return u; }
    public double[][] getV() { return v; }
}