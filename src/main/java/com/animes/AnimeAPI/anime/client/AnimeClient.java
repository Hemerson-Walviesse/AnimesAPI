package com.animes.AnimeAPI.anime.client;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.springframework.stereotype.Component;

import java.io.IOException;
@Component
public class AnimeClient {
    public String chamarApiExternamente(String endpoint){
        OkHttpClient client = new OkHttpClient();

        String url = "https://api.jikan.moe/v4/anime" + endpoint;
        System.out.println(url);

        Request request = new Request.Builder()
                .url(url)
                .build();
        try (Response response = client.newCall(request).execute()) {
            if(!response.isSuccessful()){
                throw new IOException("Erro na chamada da API" + response.code());
            }
            return response.body().string();
        }catch (IOException e){
            return e.getMessage();
        }


    }
}
