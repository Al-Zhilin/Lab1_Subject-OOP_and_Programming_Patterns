public class Flight {

    // ---------- Статические члены (задание 2) ----------

    /** Максимально допустимое число мест на одном рейсе. */
    public static final int MAX_SEATS = 850;

    /** Сколько рейсов создано. Общее для ВСЕХ объектов класса. */
    private static int flightsCount = 0;

    // ---------- Поля объекта: все private ----------

    private final String number;     // final: задаётся один раз
    private final Route route;       // Route неизменяем, поэтому final + геттер безопасны
    private final Seat[] seats;      // композиция: места создаются здесь и наружу не отдаются
    private final BaggagePolicy baggagePolicy;   // правило багажа приходит извне

    // ---------- Конструкторы ----------

    public Flight(String number, Route route, int totalSeats, int soldSeats) {
        this(number, route, totalSeats, soldSeats, new NoBaggagePolicy());
    }

    public Flight(String number, Route route, int totalSeats, int soldSeats,
                  BaggagePolicy baggagePolicy) {
        if (number == null || number.isBlank()) {
            throw new IllegalArgumentException("Номер рейса не может быть пустым");
        }
        if (route == null) {
            throw new IllegalArgumentException("Маршрут рейса не задан");
        }
        if (totalSeats < 1 || totalSeats > MAX_SEATS) {
            throw new IllegalArgumentException(
                    "Общее число мест должно быть от 1 до " + MAX_SEATS
                            + ", получено: " + totalSeats);
        }
        if (soldSeats < 0) {
            throw new IllegalArgumentException(
                    "Число проданных мест не может быть отрицательным: " + soldSeats);
        }
        if (soldSeats > totalSeats) {
            throw new IllegalArgumentException(
                    "Продано мест (" + soldSeats + ") больше, чем всего мест ("
                            + totalSeats + ")");
        }

        if (baggagePolicy == null) {
            throw new IllegalArgumentException("Правило багажа не задано");
        }

        this.number = number.trim();
        this.route = route;
        this.baggagePolicy = baggagePolicy;

        // Места создаются вместе с рейсом; первые soldSeats из них уже заняты
        this.seats = new Seat[totalSeats];
        for (int i = 0; i < totalSeats; i++) {
            seats[i] = new Seat(i + 1);
            if (i < soldSeats) {
                seats[i].occupy();
            }
        }

        // Счётчик увеличиваем В САМОМ КОНЦЕ: если любая проверка выше
        // бросила исключение, объект не создан и считаться не должен.
        flightsCount++;
    }

    public Flight(String number, Route route, int totalSeats) {
        this(number, route, totalSeats, 0);
    }

    // ---------- Геттеры (только то, что нужно читать снаружи) ----------

    public String getNumber() {
        return number;
    }

    /** Безопасно: Route неизменяем, испортить его через ссылку нельзя. */
    public Route getRoute() {
        return route;
    }

    public int getTotalSeats() {
        return seats.length;
    }

    public int getSoldSeats() {
        int sold = 0;
        for (Seat seat : seats) {
            if (seat.isOccupied()) {
                sold++;
            }
        }
        return sold;
    }

    // ---------- Методы предметной области (вместо сеттеров) ----------

    public int getFreeSeats() {
        return getTotalSeats() - getSoldSeats();
    }

    public boolean isFull() {
        return getFreeSeats() == 0;
    }

    public double getLoadPercent() {
        return (double) getSoldSeats() * 100 / getTotalSeats();
    }

    public void sellTicket() {
        sellTicket(1);
    }

    public void sellTicket(int count) {
        if (count <= 0) {
            throw new IllegalArgumentException(
                    "Количество билетов должно быть больше нуля, получено: " + count);
        }
        if (count > getFreeSeats()) {
            throw new IllegalStateException(
                    "Недостаточно свободных мест на рейсе " + number
                            + ": запрошено " + count + ", свободно " + getFreeSeats());
        }
        // занимаем первые свободные места
        int left = count;
        for (Seat seat : seats) {
            if (left == 0) {
                break;
            }
            if (!seat.isOccupied()) {
                seat.occupy();
                left--;
            }
        }
    }

    /** Доплата за багаж считается по правилу, которое рейсу передали при создании. */
    public double getBaggageSurcharge(double weightKg) {
        if (weightKg < 0) {
            throw new IllegalArgumentException("Вес багажа не может быть отрицательным: " + weightKg);
        }
        return baggagePolicy.surcharge(weightKg);
    }

    /** Статический метод вызывается через класс: Flight.getFlightsCount(). */
    public static int getFlightsCount() {
        return flightsCount;
    }

    @Override
    public String toString() {
        return String.format("Рейс %s [%s]: продано %d из %d (%.1f%%)",
                number, route, getSoldSeats(), getTotalSeats(), getLoadPercent());
    }
}
