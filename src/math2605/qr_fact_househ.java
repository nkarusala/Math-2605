/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package math2605;

import static java.lang.Math.sqrt;
import org.apache.commons.math.linear.AbstractRealMatrix;
import org.apache.commons.math.linear.Array2DRowRealMatrix;
import org.apache.commons.math.linear.RealMatrix;

/**
 *
 * @author anjali
 */
public class qr_fact_househ {
        private RealMatrix qr;
        private int row, col;
        private double[] dig;

    public qr_fact_househ(RealMatrix m){
        qr = m.copy();
        row = m.getRowDimension();
        col = m.getColumnDimension();
        for(int i = 0; i < col; i++){
            double normal = 0;
            for(int j = i; j < row; j++){
                double num = qr.getEntry(j, i);
                normal = sqrt((normal *normal) + (num*num));     
            }
            
            if(normal != 0){
                if(qr.getEntry(i, i) < 0){
                    normal = normal * (-1);
                }
                for(int j = i ; j < row; j++ ){
                    qr.setEntry(j, i, (qr.getEntry(j, i) / normal));
                }
                qr.setEntry(i,i,(qr.getEntry(i, i)+ 1));
            }
            
            for(int j = i+1; j < col; j++){
                double x = 0;
                for(int k = i; k < row; k++){
                    x = x + (qr.getEntry(k, i)*qr.getEntry(k, i));
                }
                x = (x*(-1))/qr.getEntry(i, i);
                for(int k = i; k<row;k++){
                    qr.setEntry(k, j, (qr.getEntry(k, i)*x));
                }
            }
            
            dig[i] = -normal;
        }
    }
    
    
    public int checkFull(){
        int full = 0;
        for(int i = 0; i < col;i++){
            if(dig[i] == 0){
                full = 1;
            }
        }
        return full;
    }
    
    
    public RealMatrix getHouseHolder(){
        AbstractRealMatrix H = new Array2DRowRealMatrix();
        for (int i = 0; i < row; i++) {
         for (int j = 0; j < col; j++) {
            if (i >= j) {
               H.setEntry(i, j, qr.getEntry(i, j));
            } else {
                H.setEntry(i, j, 0);
            }
         }
      }
      return H;
    }
    
    public RealMatrix getR () {
     AbstractRealMatrix R = new Array2DRowRealMatrix();
      for (int i = 0; i < col; i++) {
         for (int j = 0; j < col; j++) {
            if (i < j) {
               R.setEntry(i, j, qr.getEntry(i, j));
            } else if (i == j) {
               R.setEntry(i, j, dig[i]);
            } else {
                R.setEntry(i, j, 0);
            }
         }
      }
      return R;
   }
    
    
    public RealMatrix getQ () {
      AbstractRealMatrix Q = new Array2DRowRealMatrix();
      for (int k = col-1; k >= 0; k--) {
         for (int i = 0; i < row; i++) {
             qr.setEntry(i, k, 0);
         }
         Q.setEntry(k, k, 1);
         for (int j = k; j < col; j++) {
            if (Q.getEntry(k, k) != 0) {
               double s = 0.0;
               for (int i = k; i < row; i++) {
                   s = s + (qr.getEntry(i, k) * Q.getEntry(i, j));
               }
               s = (s*(-1)) / qr.getEntry(k, k);
               for (int i = k; i < row; i++) {
                   Q.setEntry(i, j, (s* qr.getEntry(i, k)));
               }
            }
         }
      }
      return Q;
   }
    
}