/**
 * Место в самолёте. Часть рейса (композиция): создаётся только внутри Flight
 * и наружу не отдаётся, поэтому без рейса не существует.
 */
public class Seat {

    private final int number;
    private boolean occupied;

    // package-private: создать место может только код внутри пакета (Flight)
    Seat(int number) {
        this.number = number;
        this.occupied = false;
    }

    public int getNumber() {
        return number;
    }

    public boolean isOccupied() {
        return occupied;
    }

    void occupy() {
        occupied = true;
    }
}
