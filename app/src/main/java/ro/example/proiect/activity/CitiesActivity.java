package ro.example.proiect.activity;

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
                "Paris",
                "France",
                "Paris is the capital and most populous city of France, with an estimated population of 2,175,601 residents as of 2018, in an area of more than 105 square kilometres. Since the 17th century, Paris has been one of Europe's major centres of finance, diplomacy, commerce, fashion, gastronomy, science and arts."
        );

        CityModel city2 = new CityModel(
                2,
                "Rome",
                "Italy",
                "Rome is located in the central-western portion of the Italian Peninsula, within Lazio(Latium), along the shores of the Tiber. Vatican City (the smallest country in the world) is an independent country inside the city boundaries of Rome, the only existing example of a country within a city; for this reason Rome has sometimes been defined as the capital of two states."
        );

        new InsertCitiesOperation(this).execute(city1, city2);
    }

    @Override
    public void insertCities(String result) {
    }

    @Override
    public void getCities(List<CityModel> citiesList) { }
}
