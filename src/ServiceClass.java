// Класс обслуживания: название и во сколько раз он дороже базовой цены
public class ServiceClass {

    private final String name;
    private final double priceMultiplier;

    public ServiceClass(String name, double priceMultiplier) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Название класса обслуживания не может быть пустым");
        }
        if (priceMultiplier <= 0) {
            throw new IllegalArgumentException("Множитель цены должен быть больше нуля: " + priceMultiplier);
        }
        this.name = name.trim();
        this.priceMultiplier = priceMultiplier;
    }

    public String getName() {
        return name;
    }

    public double getPriceMultiplier() {
        return priceMultiplier;
    }
}
