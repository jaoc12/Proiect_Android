package ro.example.proiect.activity;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import org.w3c.dom.Text;

import java.util.ArrayList;
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

    TextView textCity;
    Context landmarkContext;
    public List<CityModel> citiesList = new ArrayList<>();
    String cityName;
    LinearLayout layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landmark);

        layout = (LinearLayout) findViewById(R.id.layoutLandmark);
        landmarkContext = this.getApplicationContext();
        textCity = (TextView) findViewById(R.id.txtCity);

        cityName = getIntent().getExtras().getString("CityName");
        textCity.setText(cityName);

        /*String uri = "@drawable/"+cityName.toLowerCase();
        int id = getResources().getIdentifier(uri, null, this.getPackageName());
        layout.setBackgroundResource(id);*/

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
                textCity.setText("Failure");
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

        TextView textDetails = (TextView) findViewById(R.id.txtDetails);
        TextView textCountry = (TextView) findViewById(R.id.txtCountry);

        if(city.isPresent()) {
            textCountry.setText(city.get().Country);
            textDetails.setText(city.get().Description);
        }
        else{
            textDetails.setText("Failure");
        }
    }
}
