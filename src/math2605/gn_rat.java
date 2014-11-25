/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package math2605;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import org.apache.commons.math.linear.AbstractRealMatrix;
import org.apache.commons.math.linear.Array2DRowRealMatrix;
import org.apache.commons.math.linear.RealMatrix;

/**
 *
 * @author nkaru_000
 */
public class gn_rat {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        //get file name
        System.out.println("Please enter a file name:");
        Scanner scanner = new Scanner(System.in);
        String fileName = scanner.nextLine();
        List<String []> pairs = new ArrayList<>();
        //get coordinate pairs and add to arraylist
        try{
            BufferedReader br = new BufferedReader(new FileReader(fileName));
            String line;
            while ( (line = br.readLine()) != null) {
                String[] pair = line.split(",");
                pairs.add(pair);
            }
            br.close();
        } catch (Exception e) {}
        
        System.out.println("Please enter the value of a:");
        double a = scanner.nextInt();
        System.out.println("Please enter the value of b:");
        double b = scanner.nextInt();
        System.out.println("Please enter the value of c:");
        double c = scanner.nextInt();
        //init B, vector with 3 coordinates
        RealMatrix B = new Array2DRowRealMatrix(3, 1);
        B.setEntry(0, 0, a);
        B.setEntry(1, 0, b);
        B.setEntry(2, 0, c);
        
        System.out.println("Please enter the number of iteration for the Gauss-newton:");
        //init N, number of iterations
        int N = scanner.nextInt();
        
        //init r, vector of residuals
        RealMatrix r = new Array2DRowRealMatrix();
        setR(pairs, a, b, c, r);
        
        //init J, Jacobian of r
        RealMatrix J = new Array2DRowRealMatrix();
        setJ(pairs, a, b, c, r, J);
        
        System.out.println("J");
        System.out.println(J);
        System.out.println("r");
        System.out.println(r);
        
        RealMatrix sub = findQR(J, r);
        
        for (int i = N; i > 0; i--) {            
            B = B.subtract(sub);
            double B0 = B.getEntry(0, 0);
            double B1 = B.getEntry(1, 0);
            double B2 = B.getEntry(2, 0);
            //CHANGE ABC TO USE B0, B1, B2
            setR(pairs, B0, B1, B2, r);
            setJ(pairs, B0, B1, B2, r, J);
        }
        System.out.println("B");
        System.out.println(B.toString());
    }
    
    private static void setR(List<String []> pairs, double a, double b, double c, RealMatrix r) {
        int row = 0;
        for (String[] p : pairs) {
            double x = Double.parseDouble(p[0]);
            double fx = (a * x) / (x + b) + c;
            double y = Double.parseDouble(p[1]);
            double resid = y - fx;
            r.setEntry(row, 0, resid);
            row++;
        }
    }
    
    private static void setJ(List<String[]> pairs, double a, double b, double c, RealMatrix r, RealMatrix J) {
        for (int i = 0; i < r.getRowDimension(); i++) {
            double x = Double.parseDouble(pairs.get(i)[0]);
            for (int j = 0; j < 3; j++) {
                double entry = -1;
                if (j == 0) {
                    entry = -x / (x + b);
                } else if (j == 1) {
                    entry = (a * x) / Math.pow(x + b, 2);
                }
                J.setEntry(i, j, entry); 
            } 
        }
    }
        
    private static RealMatrix findQR(RealMatrix J, RealMatrix r) {
        qr_fact_househ m = new qr_fact_househ(J);
        RealMatrix Q = m.getQ();
        RealMatrix qTranspose = Q.transpose();
        System.out.println("qtranspose");
        System.out.println(qTranspose);
        RealMatrix R = m.getR();
        MatrixMethods temp = new MatrixMethods(R);
        RealMatrix rInverse = temp.inverseMatrix();
        System.out.println("rInverse");
        System.out.println(rInverse);
        RealMatrix sub = rInverse.multiply(qTranspose).multiply(r);
        return sub;
    }
}
