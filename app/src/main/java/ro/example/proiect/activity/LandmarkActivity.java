package ro.example.proiect.activity;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import ro.example.proiect.R;
import ro.example.proiect.api.ApiBuilder;
import ro.example.proiect.api.EntityLocation;
import ro.example.proiect.api.GetLocationModel;
import ro.example.proiect.database.CityModel;
import ro.example.proiect.database.CityOperations;
import ro.example.proiect.database.GetCitiesOperation;

public class LandmarkActivity extends AppCompatActivity implements CityOperations {

    TextView textView;
    Context landmarkContext;
    public List<CityModel> citiesList = new ArrayList<>();
    String cityName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landmark);
        landmarkContext = this.getApplicationContext();

        textView = (TextView) findViewById(R.id.txtCity);
        cityName = getIntent().getExtras().getString("CityName");
        textView.setText(cityName);
        new GetCitiesOperation(this).execute();

        Call<GetLocationModel> call =
                ApiBuilder.getInstance().getLocation(cityName.toLowerCase(), "en_US");

        call.enqueue(new Callback<GetLocationModel>() {
            @Override
            public void onResponse(Call<GetLocationModel> call,
                                   Response<GetLocationModel> response) {

                List<EntityLocation> entities = response.body()
                        .getSuggestions()
                        .get(2)
                        .getEntities();

                setLandmarks(entities);
            }
            @Override
            public void onFailure(Call<GetLocationModel> call, Throwable t) {
                textView.setText("Failure");
            }
        });

    }

    private void setLandmarks(List<EntityLocation> entities) {

        for (int i=0; i<3; i++) {
            EntityLocation location = entities.get(i);
            String txtName = "landmark" + String.valueOf(i+1);

            int txtId = getResources().getIdentifier(txtName,
                    "id", landmarkContext.getPackageName());
            TextView landmarkView = (TextView) findViewById(txtId);

            landmarkView.setText(location.getName());
        }
    }

    @Override
    public void insertCities(String result) {
    }

    @Override
    public void getCities(List<CityModel> citiesList) {
        this.citiesList = citiesList;

        Optional<CityModel> city = citiesList.stream()
                .filter(cityModel -> cityModel.getName().equals(cityName))
                .findFirst();

        TextView detailsView = (TextView) findViewById(R.id.txtDetails);
        if(city.isPresent()) {
            detailsView.setText(city.get().Country);
        }
        else{
            detailsView.setText("Failure");
        }
    }
}
