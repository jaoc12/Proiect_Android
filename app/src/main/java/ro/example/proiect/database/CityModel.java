package ro.example.proiect.database;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class CityModel {
    @PrimaryKey
    public int id;
    public String Name;
    public String Country;

    public CityModel(){
    }

    public CityModel(String name, String country) {
        Name = name;
        Country = country;
    }

    public CityModel(int id,String name, String country) {
        this.id = id;
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
