package dataTypes;

public class Fraction implements Comparable<Fraction>{
    public long num;
    public long denom;    

    public Fraction(long num, long denom) throws IllegalArgumentException{
        if (denom == 0) {
            throw new IllegalArgumentException("invalid denominator: can not be 0");
        } 
        
        this.num = num;
        this.denom = denom;
        simplifiy();
    }

    public Fraction(long num) {
        this(num, 1);
    }

    public Fraction() {
        this(0, 1);
    }



    public void simplifiy() {
        if (num == 0) {
            num = 0;
            denom = 1;
        } else {
            long common = MathTools.gcd(num, denom); 
            num = num / common;
            denom = denom / common;
        }
        correctSign();
    }
    
    public void correctSign() {
        if (denom < 0) {
            num *= -1;
            denom *= -1;
        }
    }

    public long getSign() {
        correctSign();
        return num;  
    }

    public boolean eq(int a) {
        simplifiy();
        if (denom != 1) {
            return false;
        }
        return num == a;
    }

    public Fraction reciprcal() {
        if (num == 0) {
            return new Fraction();
        }
        return new Fraction(this.denom, this.num);
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


    public static Fraction add(Fraction a, long b) {
        long n = a.num + (b * a.denom);
        long d = a.denom;
        return new Fraction(n, d);
    }

    public static Fraction sub(Fraction a, long b) {
        long n = a.num - (b * a.denom);
        long d = a.denom;
        return new Fraction(n, d);
    }

    public static Fraction multiply(Fraction a, long b) {
        long n = a.num * b;
        long d = a.denom;
        return new Fraction(n, d);
    }

    public static Fraction divide(Fraction a, long b) {
        long n = a.num;
        long d = a.denom * b;
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

    @Override
    public int compareTo(Fraction o) {
        // TODO Auto-generated method stub
        long n = Fraction.sub(this, o).num;
        if (n == 0) {
            return 0;
        } else if (n > 0) {
            return 1;
        } 
        return -1;
    }
    
}
