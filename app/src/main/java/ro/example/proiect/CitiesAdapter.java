package ro.example.proiect;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import ro.example.proiect.database.CityModel;

public class CitiesAdapter extends RecyclerView.Adapter<CitiesAdapter.CityViewHolder> {

    private List<CityModel> localDataSet;
    public CitiesAdapter(List<CityModel> dataSet){
        localDataSet = dataSet;
    }

    public class CityViewHolder extends RecyclerView.ViewHolder{
        public TextView nameTextView;
        public TextView countryTextView;
        public ImageView imageView;
        Resources res;
        Context context;

        public CityViewHolder(View itemView){
            super(itemView);

            nameTextView = (TextView) itemView.findViewById(R.id.city_name);
            countryTextView = (TextView) itemView.findViewById(R.id.city_country);
            imageView = (ImageView) itemView.findViewById(R.id.city_image);
            res = itemView.getResources();
            context = itemView.getContext();
        }

        public void bind(CityModel cityModel) {
            nameTextView.setText(cityModel.getName());
            countryTextView.setText(cityModel.getCountry());

            String uri = "@drawable/"+cityModel.getName().toLowerCase();
            int id = res.getIdentifier(uri, null, context.getPackageName());
            Drawable drawable = res.getDrawable(id, context.getTheme());
            imageView.setImageDrawable(drawable);
        }
    }

    @NonNull
    @Override
    public CitiesAdapter.CityViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_city, parent, false);
        return new CityViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CityViewHolder viewHolder, final int position) {
        viewHolder.bind(localDataSet.get(position));
    }

    @Override
    public int getItemCount() {
        return localDataSet.size();
    }
}
