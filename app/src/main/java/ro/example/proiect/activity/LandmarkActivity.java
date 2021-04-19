package ro.example.proiect.activity;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import ro.example.proiect.R;
import ro.example.proiect.api.ApiBuilder;
import ro.example.proiect.api.EntityLocation;
import ro.example.proiect.api.GetLocationModel;

public class LandmarkActivity extends AppCompatActivity {

    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landmark);

        textView = (TextView) findViewById(R.id.txtHotel);
        String cityName = getIntent().getExtras().getString("CityName");
        textView.setText(cityName);

        Call<GetLocationModel> call =
                ApiBuilder.getInstance().getLocation(cityName.toLowerCase(), "en_US");

        call.enqueue(new Callback<GetLocationModel>() {
            @Override
            public void onResponse(Call<GetLocationModel> call,
                                   Response<GetLocationModel> response) {

                List<EntityLocation> destinationId = response.body()
                        .getSuggestions()
                        .get(2)
                        .getEntities();

                for (EntityLocation entityLocation : destinationId) {
                    Log.i("AlMeu", entityLocation.getName());
                }
            }
            @Override
            public void onFailure(Call<GetLocationModel> call, Throwable t) {
                textView.setText("Failure");
            }
        });

    }
}
