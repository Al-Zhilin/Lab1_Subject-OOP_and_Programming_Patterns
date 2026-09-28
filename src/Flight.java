public class Flight {

    // ---------- Статические члены (задание 2) ----------

    /** Максимально допустимое число мест на одном рейсе. */
    public static final int MAX_SEATS = 850;

    /** Сколько рейсов создано. Общее для ВСЕХ объектов класса. */
    private static int flightsCount = 0;

    // ---------- Поля объекта: все private (задание 1) ----------

    private final String number;     // final: задаётся один раз
    private final Route route;       // Route неизменяем, поэтому final + геттер безопасны
    private final int totalSeats;    // общее число мест не меняется
    private int soldSeats;           // меняется только через sellTicket

    // ---------- Конструкторы ----------

    /** Полный конструктор: здесь проверяются ВСЕ правила. */
    public Flight(String number, Route route, int totalSeats, int soldSeats) {
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

        this.number = number.trim();
        this.route = route;
        this.totalSeats = totalSeats;
        this.soldSeats = soldSeats;

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
        return totalSeats;
    }

    public int getSoldSeats() {
        return soldSeats;
    }

    // ---------- Методы предметной области (вместо сеттеров) ----------

    public int getFreeSeats() {
        return totalSeats - soldSeats;
    }

    public boolean isFull() {
        return soldSeats == totalSeats;
    }

    public double getLoadPercent() {
        return (double) soldSeats * 100 / totalSeats;
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
        soldSeats += count;
    }

    // ---------- Статический геттер счётчика ----------

    /** Статический метод вызывается через класс: Flight.getFlightsCount(). */
    public static int getFlightsCount() {
        return flightsCount;
    }

    // ---------- toString ----------

    @Override
    public String toString() {
        // %s - строка, %d - целое, %.1f - дробное с 1 знаком, %% - символ процента
        return String.format("Рейс %s [%s]: продано %d из %d (%.1f%%)",
                number, route, soldSeats, totalSeats, getLoadPercent());
    }
}
