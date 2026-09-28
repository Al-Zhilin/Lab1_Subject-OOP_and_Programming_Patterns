public final class Route {

    private final String departure;    // пункт вылета
    private final String destination;  // пункт назначения

    public Route(String departure, String destination) {
        if (departure == null || departure.isBlank()) {
            throw new IllegalArgumentException("Пункт вылета не может быть пустым");
        }
        if (destination == null || destination.isBlank()) {
            throw new IllegalArgumentException("Пункт назначения не может быть пустым");
        }

        String from = departure.trim();
        String to = destination.trim();

        if (from.equalsIgnoreCase(to)) {
            throw new IllegalArgumentException(
                    "Пункты вылета и назначения не должны совпадать: " + from);
        }

        this.departure = from;
        this.destination = to;
    }

    public String getDeparture() {
        return departure;
    }

    public String getDestination() {
        return destination;
    }

    public Route withDestination(String newDestination) {
        return new Route(departure, newDestination);
    }

    @Override
    public String toString() {
        return departure + " -> " + destination;
    }
}
