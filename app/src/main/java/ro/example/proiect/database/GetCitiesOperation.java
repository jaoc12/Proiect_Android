package ro.example.proiect.database;

import android.os.AsyncTask;
import android.util.Log;

import java.util.List;

public class GetCitiesOperation extends AsyncTask<Void, Void, List<CityModel>> {
    CityOperations listener;
    public GetCitiesOperation(CityOperations listener){
        this.listener = listener;
    }
    
    @Override
    protected List<CityModel> doInBackground(Void ... voids) {
        try{
            Log.i("AlMeu", "Merge");
            List<CityModel> citiesList= AppApplication.getAppDatabase().cityDAO().getAll();
            return citiesList;
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    protected void onPostExecute(List<CityModel> citiesList) {
        listener.getCities(citiesList);
    }
}
