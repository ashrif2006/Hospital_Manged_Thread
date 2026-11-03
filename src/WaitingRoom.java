
public class WaitingRoom {

    private int capacity;
    private Patient patients[];
    private int inPtr = 0;
    private int outPtr = 0;

    private semaphore elements;
    private semaphore spaces;
    private semaphore mutex;

    public WaitingRoom(int size) {
        capacity = size;
        patients = new Patient[size];
        elements = new semaphore(0);
        spaces = new semaphore(size);
        mutex = new semaphore(1);
    }

    public void addPatient(Patient p) {
        Thread th = new Thread(() -> {
            spaces.acquire(() -> {
                System.out.println("Waiting room is full.");
                System.out.println("Patient " + p.getId() + " is waiting outside for an available seat");
            });
            System.out.println("Patient " + p.getId() + " entered the waiting room.");
            mutex.acquire();
            patients[inPtr] = p;
            inPtr = (inPtr + 1) % capacity;
            mutex.release();
            elements.release();
        });
        th.start();
    }

    public Patient getPatient() {
        elements.acquire();
        mutex.acquire();
        Patient p = patients[outPtr];
        outPtr = (outPtr + 1) % capacity;
        mutex.release();
        spaces.release();
        return p;
    }

}
