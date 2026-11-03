
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

public class Doctor extends Thread {

    private WaitingRoom r;
    private TreatmentRoomAllocator t;
    private int doctorId;

    public Doctor(WaitingRoom rm, TreatmentRoomAllocator tr, int id) {
        r = rm;
        t = tr;
        doctorId = id;
    }

    @Override
    public void run() {
        while (true) {
            Patient p = r.getPatient();
            System.out.println("Doctor: " + doctorId + " is assigned to Patient " + p.getId());
            System.out.println("Doctor: " + doctorId + " is trying to acquire a treatment room");
            if (t.allocateRoom(3000)) {
                System.out.println("Doctor: " + doctorId + " acquired a treatment room");
                System.out.println("Doctor: " + doctorId + " begins treating Patient " + p.getId());
                long starting = System.nanoTime();
                int seconds = ThreadLocalRandom.current().nextInt(1, 5);
                long nano = TimeUnit.SECONDS.toNanos(seconds);
                while (System.nanoTime() - starting < nano) {
                    // Treating
                }
                // Finished
                System.out.println("Patient: " + p.getId() + " left.");
                t.releaseRoom();
            } else {
                System.out.println("Doctor: " + doctorId + " couldn't acquire a treatment room");
                System.out.println("Doctor: " + doctorId + " is sending Patient" + p.getId() + " back to the waiting room.");
                r.addPatient(p);
            }
            System.out.println("Doctor: " + doctorId + " is now available");
        }
    }

}
