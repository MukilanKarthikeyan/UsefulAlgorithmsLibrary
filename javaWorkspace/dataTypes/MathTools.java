package dataTypes;
public class MathTools {

    public static long gcd(long a, long b) {
        //sanitize input to support negative numbers
        a = Math.abs(a);
        b = Math.abs(b);

        if (b == 0) {
            return a;
        }
        return gcd(b, a % b);
    }

    public static int gcd(int a, int b) {
        //sanitize input to support negative numbers
        a = Math.abs(a);
        b = Math.abs(b);

        if (b == 0) {
            return a;
        }
        return gcd(b, a % b);
    }
}
