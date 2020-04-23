package task_b;

public class Switcher {
    private volatile boolean isLastElementCounted;

    synchronized boolean getIsLastElementCounted() {
        return isLastElementCounted;
    }

    synchronized void setIsLastElementCounted(boolean value) {
        isLastElementCounted = value;
    }
}
