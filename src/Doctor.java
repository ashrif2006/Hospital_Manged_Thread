
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

            Patient p = r.getPatient(); // if the patient not in wating room element.acquire make the doctor wating
            System.out.println("Doctor: " + doctorId + " is assigned to Patient " + p.getId());
            System.out.println("Doctor: " + doctorId + " is trying to acquire a treatment room");
            //بجاول احجز غرفه وهنتظر لمده 3 ثواني بس
            if (t.allocateRoom(3000)) {
                System.out.println("Doctor: " + doctorId + " acquired a treatment room");
                System.out.println("Doctor: " + doctorId + " begins treating Patient " + p.getId());
                long starting = System.nanoTime();
                int seconds = ThreadLocalRandom.current().nextInt(1, 5);// time to access this patient
                long nano = TimeUnit.SECONDS.toNanos(seconds);
                try {
                    Thread.sleep(seconds * 1000L);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
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
