package testcases;

import java.util.Scanner;

import dataTypes.Fraction;
import linearalg.FracMatrix;

public class MatrixInteraction {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        /*
        System.out.println("Welcome to the LinearAlgebra interface. what would you like to do?");
        System.out.println("0: create empty matrix with diemensions");
        int option = scan.nextInt();

        switch(option) {
            case 0:
                //create a new matrtix
                break;
        }
         */
        

        System.out.print("input matrix diemensions: ");
        int m = scan.nextInt();
        int n = scan.nextInt();

        FracMatrix mat = new FracMatrix(m, n);

        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                // for now only supports longs
                //TODO: add support for fraction inputs
                mat.setVal(i, j, new Fraction(scan.nextLong()));
            }
            scan.nextLine();
        }
    }
}
