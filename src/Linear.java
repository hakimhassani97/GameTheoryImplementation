import java.util.Scanner;
/**
 *
 * @author hakimhassani97
 */
//solveur d'equations lineaires
public class Linear {
    char []var = {'x', 'y', 'z', 'w','a','b','c'};
    int n=1;
    double [][]mat = new double[n][n];
    double [][]constants = new double[n][1];
    public Linear(int nbVars,double[][] m,double[][] c){
        n=nbVars;
        mat = new double[n][n];
        constants = new double[n][1];
        for(int i=0; i<n; i++){
            for(int j=0; j<n; j++){
                mat[i][j] = m[i][j];
            }
            constants[i][0] = c[i][0];
        }
    }
    double[][] mul(double[][] a,double[][] b){
        double result[][] = new double[n][1];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < 1; j++) {
                for (int k = 0; k < n; k++){
                    result[i][j] = result[i][j] + a[i][k] * b[k][j];
                }
            }
        }
        return result;
    }
    public double[][] solve(){
        double inverted_mat[][] = invert(mat);
        double result[][] = mul(inverted_mat,constants);
        return result;
    }
    public static void main(String args[]){
        char []var = {'x', 'y', 'z', 'w','a','b','c'};
        System.out.println("entrez le nombre de variables de l'aquation : ");
        Scanner input = new Scanner(System.in);
        int n = input.nextInt();
        System.out.println("entrez les coefficients :");
        System.out.println("ax + by + cz + ... = d");
        double [][]mat = new double[n][n];
        double [][]constants = new double[n][1];
        //input
        for(int i=0; i<n; i++){
            for(int j=0; j<n; j++){
                mat[i][j] = input.nextDouble();
            }
            constants[i][0] = input.nextDouble();
        }
        //Matrix representation
        for(int i=0; i<n; i++){
            for(int j=0; j<n; j++){
                System.out.print(" "+mat[i][j]);
            }
            System.out.print("  "+ var[i]);
            System.out.print("  =  "+ constants[i][0]);
            System.out.println();
        }
        //inverse de la matrice mat[][]
        double inverted_mat[][] = invert(mat);
        System.out.println("la matrice inverse: ");
        for (int i=0; i<n; ++i) {
            for (int j=0; j<n; ++j){
                System.out.print(inverted_mat[i][j]+"  ");
            }
            System.out.println();
        }
        //Multiplication de la mat inverse et les constantes
        double result[][] = new double[n][1];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < 1; j++) {
                for (int k = 0; k < n; k++){
                    result[i][j] = result[i][j] + inverted_mat[i][k] * constants[k][j];
                }
            }
        }
        System.out.println("la solution est:");
        for(int i=0; i<n; i++){
            System.out.println(var[i]+" = "+result[i][0] + " ");
        }
        input.close();
    }
    public static double[][] invert(double a[][]) {
        int n = a.length;
        double x[][] = new double[n][n];
        double b[][] = new double[n][n];
        int index[] = new int[n];
        for (int i=0; i<n; ++i) b[i][i] = 1;
        // Transformer la matrice en triangulaire sup
        gaussian(a, index);
        // MAJ de la matrice b[i][j] avec les 
        for (int i=0; i<n-1; ++i)
            for (int j=i+1; j<n; ++j)
                for (int k=0; k<n; ++k)
                    b[index[j]][k] -= a[index[j]][i]*b[index[i]][k];
        // substitusion arriere
        for (int i=0; i<n; ++i){
            x[n-1][i] = b[index[n-1]][i]/a[index[n-1]][n-1];
            for (int j=n-2; j>=0; --j){
                x[j][i] = b[index[j]][i];
                for (int k=j+1; k<n; ++k){
                    x[j][i] -= a[index[j]][k]*x[k][i];
                }
                x[j][i] /= a[index[j]][j];
            }
        }
        return x;
    }

// Method to carry out the partial-pivoting Gaussian
// elimination.  Here index[] stores pivoting order.
    public static void gaussian(double a[][], int index[]) {
        int n = index.length;
        double c[] = new double[n];
        // Initialize the index
        for (int i=0; i<n; ++i) 
            index[i] = i;
        // Find the rescaling factors, one from each row
        for (int i=0; i<n; ++i) 
        {
            double c1 = 0;
            for (int j=0; j<n; ++j) 
            {
                double c0 = Math.abs(a[i][j]);
                if (c0 > c1) c1 = c0;
            }
            c[i] = c1;
        }
 
 // Search the pivoting element from each column
        int k = 0;
        for (int j=0; j<n-1; ++j) 
        {
            double pi1 = 0;
            for (int i=j; i<n; ++i) 
            {
                double pi0 = Math.abs(a[index[i]][j]);
                pi0 /= c[index[i]];
                if (pi0 > pi1) 
                {
                    pi1 = pi0;
                    k = i;
                }
            }
            // permuter les lignes selon l'ordre de pivot
            int itmp = index[j];
            index[j] = index[k];
            index[k] = itmp;
            for (int i=j+1; i<n; ++i){
                double pj = a[index[i]][j]/a[index[j]][j];
                // stocker les pivot sous la diagonale
                a[index[i]][j] = pj;
                // modification des elements selon s[]
                for (int l=j+1; l<n; ++l)
                    a[index[i]][l] -= pj*a[index[j]][l];
            }
        }
    }
}
