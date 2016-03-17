package com.printapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.printapp.adapters.GridViewAdapter;
import com.printapp.models.SearchPhotos;
import com.printapp.models.ServiceGenerator;
import com.printapp.models.VkApi;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ItemActivity extends AppCompatActivity {
    private VkApi vk;
    private Call<SearchPhotos> call;
    private RecyclerView horizontal_recview;
    private RecyclerView grid_recview;
    private GridViewAdapter gva;
    private int type;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item);
        horizontal_recview = (RecyclerView) findViewById(R.id.horizontal_recview);
        grid_recview = (RecyclerView) findViewById(R.id.grid_recview);

        Toast.makeText(this, String.valueOf(this.getIntent().getExtras().getLong("ID")),Toast.LENGTH_SHORT).show();
        //type = getIntent().getExtras().getInt("TYPE");

        vk = ServiceGenerator.createService(VkApi.class);
        call = vk.getPhotos(200, String.valueOf(this.getIntent().getExtras().getLong("ID")),
                ServiceGenerator.API_VERSION,
                ServiceGenerator.ACCESS_TOKEN);
        call.enqueue(new Callback<SearchPhotos>() {
            @Override
            public void onResponse(Call<SearchPhotos> call, Response<SearchPhotos> response) {
                Log.d("onResponse: ", String.valueOf(response.body().response.count));
                gva = new GridViewAdapter(response);
                RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getApplicationContext(),3);
                grid_recview.setLayoutManager(layoutManager);
                grid_recview.setAdapter(gva);
            }

            @Override
            public void onFailure(Call<SearchPhotos> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    public void finish() {
        super.finish();
    }

    @Override
    public void onBackPressed() {
        Intent bundle = new Intent();
        bundle.putExtra("1","Extra message");
        setResult(RESULT_OK,bundle);
        super.onBackPressed();
    }
}
