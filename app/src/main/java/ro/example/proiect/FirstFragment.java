package ro.example.proiect;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class FirstFragment extends Fragment {
    public static List<CityModel> citiesList = new ArrayList<>();

    public FirstFragment() {
        super(R.layout.fragment_first);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initializeCitiesList();

        CitiesAdapter adapter = new CitiesAdapter(citiesList);
        RecyclerView rv = view.findViewById(R.id.rvCities);
        rv.setAdapter(adapter);
        rv.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    private void initializeCitiesList() {
        citiesList.add(new CityModel(
                "Bucuresti",
                "Romania"));

        citiesList.add(new CityModel(
                "Paris",
                "Franta"));
    }
}
