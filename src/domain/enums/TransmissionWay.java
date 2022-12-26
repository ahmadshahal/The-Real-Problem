package domain.enums;

public enum TransmissionWay {
    Walk("Walk"),
    Bus("Bus"),
    Taxi("Taxi");

    private final String name;

    TransmissionWay(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
