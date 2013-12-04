import java.io.Serializable;

class Contador implements Serializable {
    private int c = 0;
    private String n;
    Contador(String s) {
        n = s;
    }
    String getN () {
        return n;
    }
    int getC () {
        return c;
    }
    int incC() {
        return ++c;
    }
}

