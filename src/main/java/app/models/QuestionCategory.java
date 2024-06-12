package app.models;

public enum QuestionCategory {
    AlgemeneVragen("Algemene vragen"),
    StrategischPerspectief("Strategisch perspectief"),
    Organisatie("Organisatie"),
    Toekomstbestendigheid("Toekomstbestendigheid"),
    Data("Data"),
    Processen("Processen"),
    Sector("Sector");

    private final String value;

    QuestionCategory(String value){
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
