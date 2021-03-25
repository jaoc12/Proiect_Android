package ro.example.proiect;

public class CityModel {
    private String Name;
    private String Country;

    public CityModel(String name, String country) {
        Name = name;
        Country = country;
    }

    public String getName() {
        return Name;
    }

    public String getCountry() {
        return Country;
    }
}
