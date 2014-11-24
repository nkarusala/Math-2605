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
        double[][] Q ;

    public qr_fact_househ(RealMatrix m){
        qr = new Array2DRowRealMatrix(m.getRowDimension(), m.getColumnDimension());
        row = m.getRowDimension();
        col = m.getColumnDimension();
        dig = new double[col];
        qr = m.copy();
        for (int k = 0; k < col; k++) {
            double normal = 0;
            for (int i = k; i < row; i++) {
               normal = Math.hypot(normal,qr.getEntry(i, k));
              
            }
           

            if (normal != 0.0) {
               
            	if (qr.getEntry(k, k) < 0) {
                   normal = -normal;
                }
                for (int i = k; i < row; i++) {
                    qr.setEntry(i, k, (qr.getEntry(i, k)/normal));
                    
                }
                qr.setEntry(k, k, qr.getEntry(k, k)+1.0);
              //System.out.println("hh kk" + qr.getEntry(k, k));

                for (int j = k+1; j < col; j++) {
                   double s = 0.0; 
                   for (int i = k; i < row; i++) {
                      s = s +( qr.getEntry(i, k)*qr.getEntry(i, j));
                   }
                   s = -s/qr.getEntry(k, k);
                   //System.out.println("qr s" + j + " " + s);
                   for (int i = k; i < row; i++) {
                	   double temp = ((s*qr.getEntry(i, k)) + qr.getEntry(i, j));
                       qr.setEntry(i, j, temp);
                     //System.out.println("qr ij" + i + " " + qr.getEntry(i, j));
                   }
                }
            }
            dig[k] = -normal;
            
        }
        //System.out.println("qr");
        //System.out.println(qr.toString());
      
    }
    
    

   
    public RealMatrix getR () {
     RealMatrix R = new Array2DRowRealMatrix(qr.getColumnDimension(), qr.getColumnDimension());
      for (int out = 0; out < col; out++) {
         for (int j = 0; j < col; j++) {
            if (out < j) {
               R.setEntry(out, j, qr.getEntry(out, j));
              // System.out.println(R.getEntry(i, j));
            } else if (out == j) {
               R.setEntry(out, j, dig[out]);
            // System.out.println(R.getEntry(i, j));
            } else {
                R.setEntry(out, j, 0);
             // System.out.println(R.getEntry(i, j));
            }
            System.out.println("R " + out + j + " " + R.getEntry(out, j));
         }
      }
      return R;
   }
    
    


    
    
    public RealMatrix getQ () {
      RealMatrix Q = new Array2DRowRealMatrix(qr.getRowDimension(), qr.getColumnDimension());
      for (int g = col-1; g >= 0; g--) {
         for (int i = 0; i < row; i++) {
             Q.setEntry(i, g, 0.0);
         }
         Q.setEntry(g, g, 1.0);
         //System.out.println(Q.getEntry(k, k));
         for (int j = g; j < col; j++) {
            if (qr.getEntry(g,g) != 0) {
               double s = 0.0;
               for (int i = g; i < row; i++) {
                   s = s + (qr.getEntry(i, g) * Q.getEntry(i, j));
                  // System.out.println(s);
               }
               s = (-s) / qr.getEntry(g, g);
               //System.out.println();
               for (int i = g; i < row; i++) {
                   Q.setEntry(i, j, ((s* qr.getEntry(i, g)) + Q.getEntry(i, j)));
               }
            }
           
         }
         //System.out.println(k);
         //System.out.println(" " + Q.toString()); 
      }
      
      return Q;
   }
    
    
}