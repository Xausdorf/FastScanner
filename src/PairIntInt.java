public class PairIntInt {    
    private int first;
    private int second;

    PairIntInt() {
        first = 0;
        second = 0;
    }

    PairIntInt(int first, int second) {
        this.first = first;
        this.second = second;
    }

    int getFirst() {
        return this.first;
    }

    int getSecond() {
        return this.second;
    }

    void newFirst(int value) {
        this.first = value;
    }

    void newSecond(int value) {
        this.second = value;
    }
}