package ro.example.proiect.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import ro.example.proiect.database.CityDAO;
import ro.example.proiect.database.CityModel;

@Database(entities = {CityModel.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {

    public abstract CityDAO cityDAO();
}
