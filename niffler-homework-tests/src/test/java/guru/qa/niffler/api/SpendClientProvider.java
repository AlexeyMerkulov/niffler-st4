package guru.qa.niffler.api;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

public class SpendClientProvider {

    private final OkHttpClient httpClient = new OkHttpClient.Builder().build();
    private final Retrofit retrofit = new Retrofit.Builder()
            .client(httpClient)
            .baseUrl("http://127.0.0.1:8093")
            .addConverterFactory(JacksonConverterFactory.create())
            .build();

    public Retrofit getSpendClient() {
        return retrofit;
    }
}
