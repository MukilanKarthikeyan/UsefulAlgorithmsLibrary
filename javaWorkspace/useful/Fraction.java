package useful;


public class Fraction {
    public long num;
    public long denom;

    private long gcd(long a, long b) {
        //sanitize input to support negative numbers
        a = Math.abs(a);
        b = Math.abs(b);

        if (b == 0) {
            return a;
        }
        return gcd(b, a % b);
    }

    public Fraction(long num, long denom) throws IllegalArgumentException{
        if (denom == 0) {
            throw new IllegalArgumentException("invalid denominator: can not be 0");
        } else 
        
        this.num = num;
        this.denom = denom;
        simplifiy();
    }

    public Fraction() {
        this(0, 1);
    }

    public void simplifiy() {
        if (num == 0) {
            num = 0;
            denom = 1;
        } else {
            int sign = 1;
            if (num < 0) {
                sign *= -1;
                num *= -1;
            }
            if (denom < 0) {
                sign *= -1;
                denom *= -1;
            }
            long common = gcd(num, denom); 
            num = num / common * sign;
            denom = denom / common;
        }
    }

    public static Fraction add(Fraction a, Fraction b) {
        long n = (a.num * b.denom) + (b.num * a.denom);
        long d = (a.denom * b.denom);
        return new Fraction(n, d);
    }

    public static Fraction sub(Fraction a, Fraction b) {
        long n = (a.num * b.denom) - (b.num * a.denom);
        long d = (a.denom * b.denom);
        return new Fraction(n, d);
    }

    public static Fraction multiply(Fraction a, Fraction b) {
        long n = (a.num * b.num);
        long d = (a.denom * b.denom);
        return new Fraction(n, d);
    }

    public static Fraction divide(Fraction a, Fraction b) {
        long n = (a.num * b.denom);
        long d = (a.denom * b.num);
        return new Fraction(n, d);
    }
    
    // changes current fraction object 
    public void add(Fraction a) {
        this.num = (this.num * a.denom) + (a.num * this.denom);
        this.denom *= a.denom;
        simplifiy();
    }

    public void sub(Fraction a) {
        this.num = (this.num * a.denom) - (a.num * this.denom);
        this.denom *= a.denom;
        simplifiy();
    }

    public void multiply(Fraction a) {
        this.num *= a.num;
        this.denom *= a.denom;
        simplifiy();
    }

    public void divide(Fraction a) {
        this.num *= a.denom;
        this.denom *= a.num;
        simplifiy();
    }

    // work with intergers
    public void add(long a) {
        this.num +=  (a * this.denom);
        simplifiy();
    }

    public void sub(long a) {
        this.num -= (a * this.denom);
        simplifiy();
    }

    public void multiply(long a) {
        this.num *= a;
        simplifiy();
    }

    public void divide(long a) {
        this.denom *= a;
        simplifiy();
    }
    
}
