/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package PowerMethod;

/**
 *
 * @author nkaru_000
 */
public class trace_det {
    private double trace;
    private double det;
    private int iterationsA;
    private int iterationsInverseA;
    
    public trace_det(double trace, double det, int iterationsA, int iterationsInverseA) {
        this.trace = trace;
        this.det = det;
        this.iterationsA = iterationsA;
        this.iterationsInverseA = iterationsInverseA;
    }
    
    public double getTrace() {
        return this.trace;
    }
    
    public double getDet() {
        return this.det;
    }
    
    public int getIterA() {
        return this.iterationsA;
    }
    
    public int getIterInverseA() {
        return this.iterationsInverseA;
    }
}
