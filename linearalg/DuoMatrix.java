package linearalg;
import java.util.*;

import useful.Duo;

public  class  DuoMatrix {
    // Duo will represent a fraction
    Duo<Integer>[][] matrix;

    public DuoMatrix(int m, int n, Duo<Integer> con) {
        matrix = new Duo<Integer>[m][n];

        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                matrix[i][j] = new Duo<Integer>(con.getOne(), con.getTwo());
            }
        }
    }

    public Duo<Integer>[][][] crDecomp() {
        Duo<Integer>[][][] res = new Duo<Integer>[2][0][0];

    }

    /*

    public <T> Matrix(List<List<Duo>> items) {
        this(items.size(), items.get(0).size());

        for (int i = 0; i < items.size(); i++) {
            for (int j = 0; j < items.get(0).size(); j++) {
                matrix[i][j] = items.get(i).get(j);
            }
        }
    }

    */ 
}
