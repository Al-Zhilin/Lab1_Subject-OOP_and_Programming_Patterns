public class Main {

    public static void main(String[] args) {

        // ================= 1. Корректные операции =================
        System.out.println("=== 1. Корректные операции ===");

        Route krasnodarMoscow = new Route("Краснодар", "Москва");
        Flight f1 = new Flight("SU-1001", krasnodarMoscow, 180, 100);          // полный конструктор
        System.out.println("Создан: " + f1);
        System.out.println("Счётчик рейсов: " + Flight.getFlightsCount());

        Flight f2 = new Flight("SU-1002", new Route("Москва", "Санкт-Петербург"), 150); // упрощённый
        System.out.println("Создан: " + f2);
        System.out.println("Счётчик рейсов: " + Flight.getFlightsCount());

        Flight f3 = new Flight("S7-2003", new Route("Краснодар", "Казань"), 120, 120);
        Flight f4 = new Flight("DP-3004", new Route("Сочи", "Екатеринбург"), 200, 50);
        System.out.println("Счётчик общий для всех объектов: создано " + Flight.getFlightsCount()
                + " рейса (по одному счётчику на весь класс)");

        // В расписании начальная ёмкость 2, поэтому на третьем рейсе массив вырастет
        Schedule schedule = new Schedule();
        System.out.println("Добавлен SU-1001: " + schedule.addFlight(f1));
        System.out.println("Добавлен SU-1002: " + schedule.addFlight(f2));
        System.out.println("Добавлен S7-2003: " + schedule.addFlight(f3));
        System.out.println("Добавлен DP-3004: " + schedule.addFlight(f4));
        System.out.println("Повторное добавление SU-1001: " + schedule.addFlight(f1) + " (дубликат)");
        System.out.println("Рейсов в расписании: " + schedule.getCount());

        f2.sellTicket();        // перегрузка без параметра: 1 билет
        f2.sellTicket(5);       // перегрузка с параметром: 5 билетов
        System.out.println("После продажи 1 + 5 билетов: " + f2);

        System.out.println("Продажа 20 билетов на SU-1001: " + schedule.sellTickets("SU-1001", 20));
        System.out.println("Продажа на несуществующий XX-000: " + schedule.sellTickets("XX-000", 1));
        System.out.println("Состояние SU-1001: " + f1);

        System.out.println("Полностью заполненные рейсы:");
        for (Flight f : schedule.getFullFlights()) {
            System.out.println("  " + f);
        }
        System.out.printf("Средняя загрузка по расписанию: %.1f%%%n", schedule.getAverageLoadPercent());

        // ================= 2. Попытки нарушить правила =================
        System.out.println();
        System.out.println("=== 2. Некорректные попытки ===");

        // 2.1 Продать больше, чем свободно
        System.out.println("[1] Продать 1000 билетов на " + f1.getNumber());
        try {
            f1.sellTicket(1000);
            System.out.println("  ОШИБКА: нарушение не обработалось!");
        } catch (IllegalArgumentException | IllegalStateException e) {
            System.out.println("  Обработано исключение: " + e.getMessage());
        }
        System.out.println("  Состояние: " + f1);

        // 2.2 Рейс, где вылет равен назначению (исключение бросает Route)
        System.out.println("[2] Создать рейс Краснодар -> Краснодар");
        int before = Flight.getFlightsCount();
        try {
            Flight bad = new Flight("SU-9999", new Route("Краснодар", "Краснодар"), 100);
            System.out.println("  ОШИБКА: нарушение не обработалось! " + bad);
        } catch (IllegalArgumentException e) {
            System.out.println("  Обработано исключение: " + e.getMessage());
        }
        System.out.println("  Счётчик рейсов не изменился: " + before + " -> "
                + Flight.getFlightsCount() + "; в расписании рейсов: " + schedule.getCount());

        // 2.3 Продать -3 билета
        System.out.println("[3] Продать -3 билета на " + f1.getNumber());
        try {
            f1.sellTicket(-3);
            System.out.println("  ОШИБКА: нарушение не обработалось!");
        } catch (IllegalArgumentException | IllegalStateException e) {
            System.out.println("  Обработано исключение: " + e.getMessage());
        }
        System.out.println("  Состояние: " + f1);

        // 2.4 Дополнительные проверки конструктора Flight
        System.out.println("[4] Создать рейс, где продано больше, чем всего мест (300 из 100)");
        try {
            new Flight("SU-8888", new Route("Сочи", "Москва"), 100, 300);
            System.out.println("  ОШИБКА: нарушение не обработалось!");
        } catch (IllegalArgumentException e) {
            System.out.println("  Обработано исключение: " + e.getMessage());
        }
        System.out.println("  Счётчик рейсов: " + Flight.getFlightsCount());

        System.out.println("[5] Создать рейс с пустым номером");
        try {
            new Flight("   ", new Route("Сочи", "Москва"), 100);
            System.out.println("  ОШИБКА: нарушение не обработалось!");
        } catch (IllegalArgumentException e) {
            System.out.println("  Обработано исключение: " + e.getMessage());
        }
        System.out.println("  Счётчик рейсов: " + Flight.getFlightsCount());

        System.out.println("[6] Создать рейс на " + (Flight.MAX_SEATS + 1) + " мест (MAX_SEATS = "
                + Flight.MAX_SEATS + ")");
        try {
            new Flight("SU-7777", new Route("Сочи", "Москва"), Flight.MAX_SEATS + 1);
            System.out.println("  ОШИБКА: нарушение не обработалось!");
        } catch (IllegalArgumentException e) {
            System.out.println("  Обработано исключение: " + e.getMessage());
        }
        System.out.println("  Счётчик рейсов: " + Flight.getFlightsCount());

        // 2.5 Продажа через расписание: рейс есть, но билетов не хватает
        System.out.println("[7] Продать 500 билетов через расписание на SU-1002");
        try {
            schedule.sellTickets("SU-1002", 500);
            System.out.println("  ОШИБКА: нарушение не обработалось!");
        } catch (IllegalArgumentException | IllegalStateException e) {
            System.out.println("  Обработано исключение: " + e.getMessage());
        }
        System.out.println("  Состояние: " + f2);

        // ================= 3. Неизменяемый Route (задание 5) =================
        System.out.println();
        System.out.println("=== 3. Неизменяемость Route ===");

        Route r = f1.getRoute();                       // получили маршрут геттером
        System.out.println("Маршрут, полученный геттером: " + r);

        // r.departure = "Москва";       // НЕ КОМПИЛИРУЕТСЯ: поле private final
        // r.setDeparture("Москва");     // НЕ КОМПИЛИРУЕТСЯ: такого метода нет

        Route changed = r.withDestination("Сочи");     // «правка» = новый объект
        System.out.println("«Изменённый» маршрут (новый объект): " + changed);
        System.out.println("Маршрут рейса после «правки»: " + f1.getRoute());
        System.out.println("Это один и тот же объект? " + (changed == f1.getRoute()));

        System.out.println("Попытка сделать маршрут с пустым пунктом назначения:");
        try {
            r.withDestination("  ");
            System.out.println("  ОШИБКА: нарушение не обработалось!");
        } catch (IllegalArgumentException e) {
            System.out.println("  Обработано исключение: " + e.getMessage());
        }
        System.out.println("  Маршрут рейса по-прежнему: " + f1.getRoute());

        // ================= 4. Защита внутреннего хранилища =================
        System.out.println();
        System.out.println("=== 4. Контейнер не отдаёт внутренний массив ===");

        Flight[] copy = schedule.getFlights();
        System.out.println("Получена копия списка, рейсов в ней: " + copy.length);
        copy[0] = null;                                // портим КОПИЮ
        System.out.println("Подменили copy[0] на null. Рейсов в расписании: " + schedule.getCount()
                + ", SU-1001 по-прежнему находится: " + (schedule.findByNumber("SU-1001") != null));
    }
}