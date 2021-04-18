package ro.example.proiect.database;

import android.os.AsyncTask;

public class InsertCitiesOperation extends AsyncTask<CityModel, Void, String> {
    CityOperations listener;
    public InsertCitiesOperation(CityOperations listener){
        this.listener = listener;
    }

    @Override
    protected String doInBackground(CityModel... cityModels) {
        try{
            AppApplication.getAppDatabase().cityDAO().insertAll(cityModels);
        } catch (Exception e){
            return "error";
        }
        return "succes";
    }

    @Override
    protected void onPostExecute(String result) {
        listener.insertCities(result);
    }
}
