package ro.example.proiect.api;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;

public interface ApiService {

    @Headers({
            "x-rapidapi-key: 5f039c207amsh8f7b336d1cc67f4p1994b3jsn38d317c71f81",
            "x-rapidapi-host: hotels4.p.rapidapi.com"
    })

    @GET("locations/search")
    Call<GetLocationModel> getLocation(
            @Query("query") String query,
            @Query("locale") String locale
    );

    @GET("properties/list")
    Call<GetLocationModel> getHotels(
            @Query("destinationId") String destinationId,
            @Query("pageNumber") String pageNumber,
            @Query("checkIn") String checkIn,
            @Query("checkOut") String checkOut,
            @Query("pageSize") String pageSize,
            @Query("adults1") String adults1
    );
}
