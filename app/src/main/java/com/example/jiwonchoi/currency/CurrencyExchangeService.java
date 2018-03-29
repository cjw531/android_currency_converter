package com.example.jiwonchoi.currency;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by jiwonchoi on 2018. 3. 22..
 */

public interface CurrencyExchangeService {
    //Receive currency rates that are latest from Fixor.io HTTP call
    @GET("latest?")
    Call<CurrencyExchange> loadCurrencyExchange(@Query("base") String base);
}
