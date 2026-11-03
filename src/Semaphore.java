
public class semaphore {

    private int value;

    public semaphore(int value) {
        this.value = value;
    }

    public synchronized void acquire() {
        value--;
        if (value < 0) {
            try {
                wait();
            } catch (InterruptedException e) {
                System.err.println(e.getMessage());
            }
        }

    }

    public synchronized void acquire(Runnable callback) {
        value--;
        if (value < 0) {
            try {
                callback.run();
                wait();
            } catch (InterruptedException e) {
                System.err.println(e.getMessage());
            }
        }

    }

    public synchronized boolean acquire(int time) {
        --value;
        if (value < 0) {
            try {
                long startTime = System.nanoTime();
                wait(time);
                boolean something = System.nanoTime() - startTime < (time * 1000000l);
                if (!something) {
                    Thread rollbacker = new Thread(() -> {
                        this.rollBack();
                    });
                    rollbacker.start();
                }
                return something;
            } catch (InterruptedException e) {
                System.err.println(e.getMessage());
            }
        }
        return true;
    }

    private synchronized void rollBack() {
        ++value;
    }

    public synchronized void release() {
        value++;
        if (value <= 0) {
            notify();
        }
    }

}
