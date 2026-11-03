
public class TreatmentRoomAllocator {

    private semaphore spaces;

    public TreatmentRoomAllocator(int size) {
        spaces = new semaphore(size);
    }

    public boolean allocateRoom(int time) {
        return spaces.acquire(time);
    }

    public void releaseRoom() {
        spaces.release();
    }

}
