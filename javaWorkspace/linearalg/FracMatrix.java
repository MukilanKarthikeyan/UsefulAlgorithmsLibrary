package linearalg;

import java.util.ArrayList;
import java.util.List;

import useful.Fraction;

public class FracMatrix {
    
    // should it also be represented as a lsit of lists?
    private int m;
    private int n;
    private Fraction[][] matrix; 
    private List<List<Fraction>> rows;
    private List<List<Fraction>> columns;

    //private Fraction det;
    //private FracMatrix[] luFactor = new FracMatrix[2];


    public FracMatrix(int m, int n, List<Fraction> inputStream) throws IllegalArgumentException, NullPointerException{


        if (inputStream == null) {
            // throwing null pointer or illegal argument?
            throw new NullPointerException("");
        }
        // matrix elements represented left to right, top to bottom
        if (inputStream.size() != m * n) {
            throw new IllegalArgumentException("number of inputs does not match specified diemensions");
            //could say that if smaller will fill remaining with 0s
            //if more elements truncate rest
        }


        this.m = m;
        this.n = n;

        matrix = new Fraction[m][n];
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                matrix[i][j] = inputStream.get(i*m + j);
            }
        }
    }

    public FracMatrix(List<List<Fraction>> structInput) throws IllegalArgumentException{
        // matrix elements represented left to right, top to bottom
        if (structInput == null) {
            // should throw new exception
            return;
        } 
        int m = structInput.size();
        int n = structInput.get(0).size();
        matrix = new Fraction[m][n];
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                matrix[i][j] = structInput.get(i).get(j);
            }
        }
    }


    private void rowsAndColumns() {
        for (int i = 0; i < m; i++) {
            rows.add(new ArrayList<>());
            for (int j = 0; j < n; j++) {
                rows.get(i).add(matrix[i][j]);
            }
        }

        for (int i = 0; i < n; i++) {
            columns.add(new ArrayList<>());
            for (int j = 0; j < m; j++) {
                columns.get(i).add(matrix[j][i]);
            }
        }

    }

    //TODO: multiply matracies
    //TODO: multiply matrix by vector
    //TODO: calculate rwo reduced echelon form
    


}
