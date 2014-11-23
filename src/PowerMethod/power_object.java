/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package PowerMethod;

import org.apache.commons.math.linear.RealMatrix;

/**
 *
 * @author nkaru_000
 */
public class power_object {
    private double eVal;
    private RealMatrix eVec;
    private int numN; 
    
    public power_object(double eVal, RealMatrix eVec, int numN) {
        this.eVal = eVal;
        this.eVec = eVec;
        this.numN = numN;
    }
    
    public double getEVal() {
        return this.eVal;
    }
    
    public RealMatrix getEVec() {
        return this.eVec;
    }
    
    public int getNumN() {
        return this.numN;
    }
}
