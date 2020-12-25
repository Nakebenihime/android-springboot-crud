package org.androidspringbootfrontend.api;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static org.androidspringbootfrontend.constant.Constant.BASE_URL;

public class Retrofit2Client {

    public static Retrofit.Builder builder = new Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create());

    private static final Retrofit retrofit = builder.build();

    public static <S> S createService(Class<S> serviceClass) {
        return retrofit.create(serviceClass);
    }

}
