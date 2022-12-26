package domain.enums;

public enum TransmissionWay {
    Walk("Walked Here"),
    Bus("Took a Bus"),
    Taxi("Took a Taxi");

    private final String message;

    TransmissionWay(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
