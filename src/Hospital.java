
import java.util.Scanner;
import java.util.concurrent.ThreadLocalRandom;

public class Hospital {

    public void start() {

        int nPatients, nDoctors, wRoomSize;

        Scanner input = new Scanner(System.in);
        System.out.println("Enter the number of patients");
        nPatients = input.nextInt();
        System.out.println("Enter the number of doctors");
        nDoctors = input.nextInt();
        System.out.println("Enter the capacity of waiting room");
        wRoomSize = input.nextInt();

        WaitingRoom wr = new WaitingRoom(wRoomSize);
        TreatmentRoomAllocator tr = new TreatmentRoomAllocator(2);

        Thread doctors[] = new Thread[nDoctors];

        for (int i = 0; i < nDoctors; i++) {
            doctors[i] = new Doctor(wr, tr, i + 1);
            doctors[i].start();
        }

        for (int i = 0; i < nPatients; i++) {
            try {
                int delay = ThreadLocalRandom.current().nextInt(100, 500);
                Thread.sleep(delay);
                Patient arriving = new Patient(i + 1);
                System.out.println("Patient " + arriving.getId() + " arrived.");
                wr.addPatient(arriving);
            } catch (InterruptedException e) {
                System.err.println(e.getMessage());
            }
        }
    }
}
