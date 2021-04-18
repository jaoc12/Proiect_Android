package ro.example.proiect.database;

import java.util.List;

public interface CityOperations {

    public void insertCities(String result);
    public void getCities(List<CityModel> citiesList);
}
