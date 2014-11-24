/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package math2605;

import org.apache.commons.math.linear.Array2DRowRealMatrix;
import org.apache.commons.math.linear.RealMatrix;

/**
 *
 * @author anjali
 */
public class qr_fact_givens {
   RealMatrix R;
	RealMatrix Q;
	public qr_fact_givens(RealMatrix A) {
		int m = A.getRowDimension();
		int n = A.getColumnDimension();
		 R = A.copy();
		RealMatrix a = A.copy();
		 Q = null;
		boolean firstQ = true;
		for(int j = 0 ; j < n ; j++){
			int row = j;
			for(int i = j; i < m-1 ; i++){
				if(a.getEntry(row, j) == 0){
					//System.out.println("in break");
					break;
				} 
				if(upperTriangular(a)){
					//System.out.println("is ut");
					continue;
				}
				
				double[] cs = new double[2];
				
				cs = givensRoatation(a.getEntry(i, j), a.getEntry(i+ 1,j));
				RealMatrix G = createG(i, i+1, cs, n);
				R = G.multiply(R);
				if(firstQ){
					Q = G.transpose();
					firstQ =false;
				} else {
					Q = Q.multiply(G.transpose());
				}
				a = G.multiply(a);
				row++;
			}
		}
	}
	
	private boolean upperTriangular(RealMatrix a) {
		boolean triangular = true;
		for (int i = a.getRowDimension() - 1; i > 0 && triangular; i--){ // rows
		    for (int j = a.getColumnDimension() - 2; j >= 0; j--){ // columns - see example
		        if (a.getEntry(i, j) != 0) {
		            triangular = false;
		            break;
		        }
		    }
		}
		return triangular;
	}

	private double[] givensRoatation(double a, double b) {
		double[] cs = new double[2];
		double t = 0;
		if(b==0 && a==0){
			cs[0] = 1;
			cs[1]= 0;
		} else {
			cs[0] = a/(Math.sqrt(a*a + b*b));
			cs[1] = -b/(Math.sqrt(a*a + b*b));
		}
		return cs;
	}
	
	private RealMatrix createG(int i, int j, double[] cs, int n) {
		RealMatrix G = new Array2DRowRealMatrix(n,n);
		for(int k = 0 ; k < n ; k++){
			for(int l = 0; l < n; l++){
				if(k == l){
					G.setEntry(k, k, 1);
				} else {
					G.setEntry(k, l, 0);
				}
			}
		}
		G.setEntry(i, i, cs[0]);
		G.setEntry(j, j, cs[0]);
		G.setEntry(i, j, -cs[1]);
		G.setEntry(j, i, cs[1]);
		return G;
	}

	public RealMatrix getR(){
		return R;
	}
	
	public RealMatrix getQ(){
		return Q;
	} 
}
