package ro.example.proiect.database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

import ro.example.proiect.database.CityModel;

@Dao
public interface CityDAO {

    @Insert
    void insertAll(CityModel... cities);

    @Query("SELECT * FROM citymodel")
    List<CityModel> getAll();
}
