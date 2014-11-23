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
        qr = new Array2DRowRealMatrix(m.getRowDimension(), m.getColumnDimension());
        row = m.getRowDimension();
        col = m.getColumnDimension();
        dig = new double[col];
        for (int k = 0; k < col; k++) {
         // Compute 2-norm of k-th column without under/overflow.
            double nrm = 0;
            for (int i = k; i < row; i++) {
               nrm = Math.hypot(nrm,qr.getEntry(i, k));
            }

            if (nrm != 0.0) {
                // Form k-th Householder vector.
                if (qr.getEntry(k, k) < 0) {
                   nrm = -nrm;
                }
                for (int i = k; i < row; i++) {
                    qr.setEntry(i, k, (qr.getEntry(i, k)/nrm));
                }
                qr.setEntry(k, k, qr.getEntry(k, k)+1);

                for (int j = k+1; j < col; j++) {
                   double s = 0.0; 
                   for (int i = k; i < row; i++) {
                      s += qr.getEntry(i, k)*qr.getEntry(i, j);
                   }
                   s = -s/qr.getEntry(k, k);
                   for (int i = k; i < row; i++) {
                       qr.setEntry(i, j, ((s*qr.getEntry(i, k)) + qr.getEntry(i, j)));
                   }
                }
            }
            dig[k] = -nrm;
        }
    }
    
   
    public RealMatrix getR () {
     RealMatrix R = new Array2DRowRealMatrix(qr.getColumnDimension(), qr.getColumnDimension());
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
      RealMatrix Q = new Array2DRowRealMatrix(qr.getRowDimension(), qr.getColumnDimension());
      for (int k = col-1; k >= 0; k--) {
         for (int i = 0; i < row; i++) {
             qr.setEntry(i, k, 0);
         }
         Q.setEntry(k, k, 1.0);
         for (int j = k; j < col; j++) {
            if (Q.getEntry(k, k) != 0) {
               double s = 0;
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