/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package math2605;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.Scanner;

/**
 *
 * @author nkaru_000
 */
public class gn_qua {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        System.out.println("Please enter a file name:\n");
        Scanner scanner = new Scanner(System.in);
        String fileName = scanner.nextLine();
        try{
            BufferedReader br = new BufferedReader(new FileReader(fileName));
            String line;
            while ( (line = br.readLine()) != null) {
                String[] pair = line.split(",");
            }
            br.close();
        }catch(Exception e){} //or write your own exceptions
        
       
        System.out.println("Please enter the value of a:\n");
        int a = scanner.nextInt();
        System.out.println("Please enter the value of b:\n");
        int b = scanner.nextInt();
        System.out.println("Please enter the value of c:\n");
        int c = scanner.nextInt();
        
        System.out.println("Please enter the number of iteration for the Gauss-newton:\n");
        int N = scanner.nextInt();
        
    }
        
    
}
