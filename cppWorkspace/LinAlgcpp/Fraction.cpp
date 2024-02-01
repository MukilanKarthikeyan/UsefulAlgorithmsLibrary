#include <iostream>

using namespace std;

class Fraction {
    private:

    void simplify() {
        long long com = gcd(num, denom);
        num /= com;
        denom /= denom;
        correctSign();
    }

    void correctSign() {
        if (denom < 0) {
            num *= -1;
            denom *= -1;
        }
    }
    
    public:
    long long num;
    long long denom;
    Fraction() {
        num = 0;
        denom = 1;
    }
    // there has got to be a better way than to use underscores
    Fraction(long long _num, long long _denom) {
        if (_denom == 0) {
            cout << "invalid _denominator. can not be 0" << endl;
            return;
        } else if (_num = 0) {
            _num = 0;
            _denom = 1;
        } else {
            int sign = 1;
            if (_num < 0) {
                sign *= -1;
                _num *= -1;
            }
            if (_denom < 0) {
                sign *= -1;
                _denom *= -1;
            }
            long long common = gcd(_num, _denom);
            num = _num / common * sign;
            denom = _denom / common;
        }

    }

    long long gcd(long long a, long long b) {
        a *= (a < 0) ? -1 : 1;
        b *= (b < 0) ? -1 : 1;

        if (b == 0) {
            return a;
        }
        return gcd(b, a % b);

    }


    Fraction operator+=(Fraction const& a) {
        num = (num * a.denom) + (a.num * denom);
        denom = (denom * a.denom);
        simplify();
        Fraction res(num, denom);
        return res;
    }

    Fraction operator-=(Fraction const& a) {
        num = (num * a.denom) - (a.num * denom);
        denom = (denom * a.denom);
        simplify();
        Fraction res(num, denom);
        return res;
    }

    Fraction operator*=(Fraction const& a) {
        num = (num * a.num);
        denom = (denom * a.denom);
        simplify();
        Fraction res(num, denom);
        return res;
    }

    Fraction operator/=(Fraction const& a) {
        num = (num * a.denom);
        denom = (denom * a.num);
        simplify();
        Fraction res(num, denom);
        return res;
    }

    Fraction operator+(Fraction const& a) {
        num = (num * a.denom) + (a.num * denom);
        denom = (denom * a.denom);

        Fraction res(num, denom);
        return res;
    }

    Fraction operator-(Fraction const& a) {
        num = (num * a.denom) - (a.num * denom);
        denom = (denom * a.denom);

        Fraction res(num, denom);
        return res;
    }

    Fraction operator*(Fraction const& a) {
        num = num * a.num;
        denom = denom * a.denom;

        Fraction res(num, denom);
        return res;
    }

    Fraction operator/(Fraction const& a) {
        num = num * a.denom;
        denom = denom * a.num;

        Fraction res(num, denom);
        return res;
    }

};