package ro.example.proiect.recyclerview;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import ro.example.proiect.R;
import ro.example.proiect.database.CityModel;
import ro.example.proiect.database.CityOperations;
import ro.example.proiect.database.GetCitiesOperation;
import ro.example.proiect.recyclerview.CitiesAdapter;

public class FirstFragment extends Fragment implements CityOperations {
    public List<CityModel> citiesList = new ArrayList<>();
    RecyclerView rv;

    public FirstFragment() {
        super(R.layout.fragment_first);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        rv = view.findViewById(R.id.rvCities);
        new GetCitiesOperation(this).execute();
    }

    @Override
    public void insertCities(String result) { }

    @Override
    public void getCities(List<CityModel> citiesList) {
        this.citiesList = citiesList;

        CitiesAdapter adapter = new CitiesAdapter(citiesList);
        rv.setAdapter(adapter);
        rv.addItemDecoration(new DividerItemDecoration(rv.getContext(), DividerItemDecoration.VERTICAL));
        rv.setLayoutManager(new LinearLayoutManager(getContext()));
    }
}
