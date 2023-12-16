package com.virosms.pokeapi_pmm.repository;

import com.virosms.pokeapi_pmm.entity.PokeList;
import com.virosms.pokeapi_pmm.entity.Pokemon;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface PokeApiService {

    String BASE_URL = "https://pokeapi.co/api/v2/";

    @GET("pokemon/{id}")
    Call<Pokemon> getPokemon(@Path("id") String id);

    @GET("pokemon")
    Call<PokeList> getPokemonList(@Query("limit") int limit, @Query("offset") int offset);
}
