package ro.example.proiect;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

import ro.example.proiect.R;
import ro.example.proiect.database.CityModel;
import ro.example.proiect.database.CityOperations;
import ro.example.proiect.database.InsertCitiesOperation;

public class CitiesActivity extends AppCompatActivity implements CityOperations {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cities);

        insertCities();
    }

    private void insertCities(){
        CityModel city1 = new CityModel(
                1,
                "Bucuresti",
                "Romania");

        CityModel city2 = new CityModel(
                2,
                "Paris",
                "Franta");

        new InsertCitiesOperation(this).execute(city1, city2);
    }

    @Override
    public void insertCities(String result) {
        Toast.makeText(this, result, Toast.LENGTH_LONG).show();
    }

    @Override
    public void getCities(List<CityModel> citiesList) { }
}
