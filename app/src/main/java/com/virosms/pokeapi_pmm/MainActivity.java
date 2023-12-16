package com.virosms.pokeapi_pmm;

import static android.app.ProgressDialog.show;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.virosms.pokeapi_pmm.entity.PokeList;
import com.virosms.pokeapi_pmm.entity.Pokemon;
import com.virosms.pokeapi_pmm.repository.PokeApiService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://pokeapi.co/api/v2/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        PokeApiService service = retrofit.create(PokeApiService.class);

        for (int offset = 0; offset <= 1154; offset += 20) {
            getPokemonList(service, 20, offset);
        }
    }


    public void getPokemonById(PokeApiService service, int id) {
        Call<Pokemon> pokemonCall = service.getPokemon(Integer.toString(
                (int) (Math.random() * 807) + 1));
        pokemonCall.enqueue(new Callback<>() {
            @Override
            public void onResponse(Call<Pokemon> call, Response<Pokemon> response) {
                Pokemon pokemon = response.body();

                if (pokemon != null) {
                    System.out.println("POKEMON NAME " + pokemon.getName());
                    System.out.println("POKEMON HEIGHT " + pokemon.getHeight());
                    System.out.println("POKEMON WEIGHT " + pokemon.getWeight());
                }
            }

            @Override
            public void onFailure(Call<Pokemon> call, Throwable t) {
                Toast.makeText(MainActivity.this, t.getMessage(),
                        Toast.LENGTH_SHORT).show();
            }
        });
    }


    public void getPokemonList(PokeApiService service, int limit, int offset) {
        Call<PokeList> pokeListCall = service.getPokemonList(limit, offset);
        pokeListCall.enqueue(new Callback<>() {
            @Override
            public void onResponse(Call<PokeList> call, Response<PokeList> response) {
                PokeList pokeList = response.body();

                if (pokeList != null) {
                    for (Pokemon pokemon : pokeList.getResults()) {
                        System.out.println("POKEMON NAME " + pokemon.getName());
                        System.out.println("POKEMON HEIGHT " + pokemon.getHeight());
                        System.out.println("POKEMON WEIGHT " + pokemon.getWeight());
                    }
                }else {
                    System.err.println("ERROR: " + response.errorBody());
                    assert response.errorBody() != null;
                    response.errorBody().close();
                }
            }

            @Override
            public void onFailure(Call<PokeList> call, Throwable t) {
                Toast.makeText(MainActivity.this, t.getMessage(),
                        Toast.LENGTH_SHORT).show();
            }
        });
    }
}