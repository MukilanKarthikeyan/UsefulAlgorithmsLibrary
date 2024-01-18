package useful;

public class Duo<T> {
    T one;
    T two;
    
    public Duo(T one, T two) {
        this.one = one;
        this.two = two;
    }

    public T getOne() {
        return one;
    }
    public T getTwo() {
        return two;
    }

    public void setOne(T one) {
        this.one = one;
    }
    public void setTwo(T two) {
        this.two = two;
    }

}
