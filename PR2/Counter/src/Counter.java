public class Counter {

    private  String name;     // counter name
    private  int maxCount;    // maximum value
    private int count;        // current value

    // create a new counter with the given parameters
    public Counter(String id, int max) {
        name = id;
        maxCount = max;
        count = 0;
    }

    // increment the counter by 1
    public void increment() {
        if (count < maxCount) count++;
    }

    // return the current count
    public int value() {
        return count;
    }

    // return a string representation of this counter
    public String toString() {
        return name + ": " + count;
    }

}

