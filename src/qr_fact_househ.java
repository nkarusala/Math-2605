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
        qr = m.copy();
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
            dig[k] = -nrm;
            
        }
        //System.out.println("qr");
        //System.out.println(qr.toString());
      
    }
    public RealMatrix getQ () {
      RealMatrix Q = new Array2DRowRealMatrix(qr.getRowDimension(), qr.getColumnDimension());
      for (int k = col-1; k >= 0; k--) {
         for (int i = 0; i < row; i++) {
             Q.setEntry(i, k, 0.0);
         }
         Q.setEntry(k, k, 1.0);
         //System.out.println(Q.getEntry(k, k));
         for (int j = k; j < col; j++) {
            if (qr.getEntry(k, k) != 0) {
               double s = 0.0;
               for (int i = k; i < row; i++) {
                   s = s + (qr.getEntry(i, k) * Q.getEntry(i, j));
                  // System.out.println(s);
               }
               s = (-s) / qr.getEntry(k, k);
               //System.out.println();
               for (int i = k; i < row; i++) {
                   Q.setEntry(i, j, ((s* qr.getEntry(i, k)) + Q.getEntry(i, j)));
               }
            }
           
         }
         //System.out.println(k);
         //System.out.println(" " + Q.toString()); 
      }
      
      return Q;
   }
    
}