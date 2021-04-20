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

        CityModel city3 = new CityModel(
                3,
                "London",
                "United Kingdom",
                "London is one of the world's most important global cities. It exerts a considerable impact upon the arts, commerce, education, entertainment, fashion, finance, healthcare, media, professional services, research and development, tourism and transportation."
        );

        CityModel city4 = new CityModel(
                4,
                "New York",
                "United States of America",
                "New York is the most photographed city in the world. Home to the headquarters of the United Nations, New York is an important center for international diplomacy, and has sometimes been called the capital of the world. As many as 800 languages are spoken in New York."
        );

        CityModel city5 = new CityModel(
                5,
                "Tokyo",
                "Japan",
                "Tokyo is the political and economic center of the country, as well as the seat of the Emperor of Japan. Originally a fishing village, named Edo, the city became a prominent political center in 1603. By the mid-18th century, Tokyo was one of the most populous cities in the world at over one million."
        );

        new InsertCitiesOperation(this).execute(city1, city2, city3, city4, city5);
    }

    @Override
    public void insertCities(String result) {
    }

    @Override
    public void getCities(List<CityModel> citiesList) { }
}
