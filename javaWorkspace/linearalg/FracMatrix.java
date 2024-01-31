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

    public FracMatrix(int m, int n) throws IllegalArgumentException, NullPointerException{
        this.m = m;
        this.n = n;

        matrix = new Fraction[m][n];
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                matrix[i][j] = new Fraction();
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

    public List<Fraction> getRow(int i) {
        List<Fraction> row = new ArrayList<>();
        for (int j = 0; j < n; j++) {
            row.add(matrix[i][j]);
        }
        return row;
        //return rows.get(i);
    }

    public List<Fraction> getCol(int i) {
        List<Fraction> col = new ArrayList<>();
        for (int j = 0; j < m; j++) {
            col.add(matrix[j][i]);
        }
        return col;
        //return columns.get(i);
    }

    public void setVal(int i, int j, Fraction val) {
        matrix[i][j] = val;
    }

    public Fraction getVal(int i, int j) {
        return matrix[i][j];
    }

    public static Fraction dotProd(List<Fraction> a, List<Fraction> b) {
        Fraction res = new Fraction();

        for (int i = 0; i < a.size(); i++) {
            res.add(Fraction.multiply(a.get(i), b.get(i)));
        }
        return res;
    }

    public static FracMatrix multiply(FracMatrix first, FracMatrix second) {
        // (mxn) (mxn)
        // axb bxc
        if (first.n != second.m) {
            throw new IllegalArgumentException("incompatabile diemensions");
        }
        FracMatrix result = new FracMatrix(first.m, second.n);

        int a = first.m;
        int b = first.n;
        int c = second.n;
        for (int i = 0; i < a; i++) {
            for (int j = 0; j < c; j++) {
                result.setVal(i, j, FracMatrix.dotProd(first.getRow(i), second.getCol(j)));
            }
        }
        return result;

    }

    //TODO: multiply matracies
    //TODO: multiply matrix by vector
    //TODO: calculate row reduced echelon form


    public void swapRows(int i, int j) {
        for (int k = 0; k < n; k++) {
            Fraction temp = matrix[i][k];
            matrix[i][k] = matrix[j][k];
            matrix[j][k] = temp; 
        }
    }

    public void swapCols(int i, int j) {
        for (int k = 0; k < m; k++) {
            Fraction temp = matrix[k][i];
            matrix[k][i] = matrix[k][j];
            matrix[k][j] = temp; 
        }
    }

    public void scaleRow(int rowIndx, Fraction scale) {
        for (int k = 0; k < n; k++) {
            matrix[rowIndx][k].multiply(scale);
        }
    }

    public void scaleCol(int colIndx, Fraction scale) {
        for (int k = 0; k < m; k++) {
            matrix[k][colIndx].multiply(scale);
        }
    }

    public void subtractRowFrom(int subtrahend, int minuend) {
        for (int k = 0; k < n; k++) {
            matrix[minuend][k].sub(matrix[subtrahend][k]);
        }
    }
    
    public void subtractColFrom(int subtrahend, int minuend) {
        for (int k = 0; k < m; k++) {
            matrix[k][minuend].sub(matrix[k][subtrahend]);
        }
    }

    public void reduce() {
        for (int i = 0; i < m; i++) {
            
        }
    }


}
