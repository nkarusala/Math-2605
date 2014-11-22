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
        System.out.println("Please enter a file name:\n");
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
        
        System.out.println("Please enter the value of a:\n");
        double a = scanner.nextInt();
        System.out.println("Please enter the value of b:\n");
        double b = scanner.nextInt();
        System.out.println("Please enter the value of c:\n");
        double c = scanner.nextInt();
        //init B, vector with 3 coordinates
        AbstractRealMatrix B = new Array2DRowRealMatrix(3, 1);
        B.setEntry(0, 0, a);
        B.setEntry(1, 0, b);
        B.setEntry(2, 0, c);
        
        System.out.println("Please enter the number of iteration for the Gauss-newton:\n");
        //init N, number of iterations
        int N = scanner.nextInt();
        
        //init r, vector of residuals
        AbstractRealMatrix r = new Array2DRowRealMatrix();
        setR(pairs, a, b, c, r);
        
        //init J, Jacobian of r
        AbstractRealMatrix J = new Array2DRowRealMatrix();
        setJ(pairs, a, b, c, r, J);
    }
    
    private static void setR(List<String []> pairs, double a, double b, double c, AbstractRealMatrix r) {
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
    
    private static void setJ(List<String[]> pairs, double a, double b, double c, AbstractRealMatrix r, AbstractRealMatrix J) {
        for (int i = 0; i < r.getRowDimension(); i++) {
            double x = Double.parseDouble(pairs.get(i)[0]);
            for (int j = 0; j < 3; j++) {
                double entry = -1;
                if (j == 0) {
                    entry = -x / (x + b);
                } else if (j == 1) {
                    entry = a * x / Math.pow(x + b, 2);
                }
                J.setEntry(i, j, entry); 
            } 
        }
    }
        
    
}
