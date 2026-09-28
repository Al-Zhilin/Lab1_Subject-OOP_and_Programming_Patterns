import java.util.Arrays;

public class Schedule {

    private static final int INITIAL_CAPACITY = 2;

    private Flight[] flights;   // хранилище (закрыто)
    private int count;          // сколько ячеек реально занято

    public Schedule() {
        // new Flight[2] создаёт 2 пустые ссылки (null), а не 2 объекта Flight
        this.flights = new Flight[INITIAL_CAPACITY];
        this.count = 0;
    }

    public boolean addFlight(Flight flight) {
        if (flight == null) {
            throw new IllegalArgumentException("Нельзя добавить пустой рейс (null)");
        }
        if (findByNumber(flight.getNumber()) != null) {
            return false;
        }
        if (count == flights.length) {
            // Массив заполнен - создаём вдвое больший и копируем в него старый
            flights = Arrays.copyOf(flights, flights.length * 2);
        }
        flights[count] = flight;
        count++;
        return true;
    }

    /** Найти рейс по номеру. Не нашли - возвращаем null. */
    public Flight findByNumber(String number) {
        if (number == null) {
            return null;
        }
        String wanted = number.trim();
        // Идём только до count, дальше в массиве лежат null
        for (int i = 0; i < count; i++) {
            if (flights[i].getNumber().equalsIgnoreCase(wanted)) {
                return flights[i];
            }
        }
        return null;
    }

    public boolean sellTickets(String number, int tickets) {
        Flight flight = findByNumber(number);
        if (flight == null) {
            return false;
        }
        flight.sellTicket(tickets);
        return true;
    }

    public Flight[] getFullFlights() {
        int fullCount = 0;
        for (int i = 0; i < count; i++) {
            if (flights[i].isFull()) {
                fullCount++;
            }
        }
        Flight[] result = new Flight[fullCount];
        int index = 0;
        for (int i = 0; i < count; i++) {
            if (flights[i].isFull()) {
                result[index] = flights[i];
                index++;
            }
        }
        return result;
    }

    public double getAverageLoadPercent() {
        if (count == 0) {
            return 0;
        }
        double sum = 0;
        for (int i = 0; i < count; i++) {
            sum += flights[i].getLoadPercent();
        }
        return sum / count;
    }

    public int getCount() {
        return count;
    }

    public Flight[] getFlights() {
        return Arrays.copyOf(flights, count);
    }
}
