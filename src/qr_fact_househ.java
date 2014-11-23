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
        double[][] QR ;
        double[] Rdiag;

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
    
    public void QRDecomposition (RealMatrix a) {
        // Initialize.
    	RealMatrix A  = a.copy();
        QR = A.getData();
       int  m = A.getRowDimension();
        int n = A.getColumnDimension();
         Rdiag = new double[n];

        // Main loop.
        for (int k = 0; k < n; k++) {
           // Compute 2-norm of k-th column without under/overflow.
           double nrm = 0;
           for (int i = k; i < m; i++) {
              nrm = Math.hypot(nrm,QR[i][k]);
           }
           if (nrm != 0.0) {
              // Form k-th Householder vector.
              if (QR[k][k] < 0) {
                 nrm = -nrm;
              }
              for (int i = k; i < m; i++) {
                 QR[i][k] /= nrm;
                 //System.out.println("qr ik" + QR[i][k]);
              }
              QR[k][k] += 1.0;
            
              // Apply transformation to remaining columns.
              for (int j = k+1; j < n; j++) {
                 double s = 0.0; 
                 for (int i = k; i < m; i++) {
                    s += QR[i][k]*QR[i][j];
                 }
                 s = -s/QR[k][k];
                 //System.out.println("hh s" + j + " " + s);
                 for (int i = k; i < m; i++) {
                    QR[i][j] += s*QR[i][k];
                  //System.out.println("ij " + i + " " + QR[i][j]);
                 }
              }
           }
           Rdiag[k] = -nrm;
        }
        /*System.out.println("A");
        for(int i = 0; i < m; i++) {
        	for(int j = 0 ; j <n; j++){
        		System.out.println(QR[i][j]);
        	}
        	System.out.println();
        	}*/
       
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
            System.out.println("R " + i + j + " " + R.getEntry(i, j));
         }
      }
      return R;
   }
    
    


    
    
    public RealMatrix getQ () {
      RealMatrix Q = new Array2DRowRealMatrix(qr.getRowDimension(), qr.getColumnDimension());
      for (int k = col-1; k >= 0; k--) {
         for (int i = 0; i < row; i++) {
             Q.setEntry(i, k, 0.0);
         }
         Q.setEntry(k, k, 1.0);
         for (int j = k; j < col; j++) {
            if (qr.getEntry(k, k) != 0) {
               double s = 0.0;
               for (int i = k; i < row; i++) {
                   s = s + (qr.getEntry(i, k) * Q.getEntry(i, j));
               }
               s = (-s) / qr.getEntry(k, k);
               for (int i = k; i < row; i++) {
                   Q.setEntry(i, j, ((s* qr.getEntry(i, k)) + Q.getEntry(i, k)));
               }
            }
         }
      }
      return Q;
   }
    
}