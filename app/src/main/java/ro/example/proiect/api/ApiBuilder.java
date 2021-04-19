package ro.example.proiect.api;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.moshi.MoshiConverterFactory;

public class ApiBuilder {
    private static ApiService apiBuilder;
    private final static String BASE_URL = "https://hotels4.p.rapidapi.com/";
    public final static String API_KEY = "5f039c207amsh8f7b336d1cc67f4p1994b3jsn38d317c71f81";

    public static ApiService getInstance(){
        if(apiBuilder == null){
            HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

            OkHttpClient client = new
                    OkHttpClient.Builder().addInterceptor(interceptor).build();

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .client(client)
                    .addConverterFactory(MoshiConverterFactory.create().asLenient())
                    .build();
            apiBuilder = retrofit.create(ApiService.class);
        }
        return apiBuilder;
    }
}
