package com.printapp.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.GridView;
import android.widget.Toast;

import com.printapp.fragments.PhotoSelectDialogFragment;
import com.printapp.R;
import com.printapp.adapters.GridAdapter;
import com.printapp.adapters.HorizontalViewAdapter;
import com.printapp.models.Photo;
import com.printapp.models.SearchPhotos;
import com.printapp.network.ServiceGenerator;
import com.printapp.network.VkApi;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ItemActivity extends AppCompatActivity implements PhotoSelectDialogFragment.PhotoSelectListener {
    private Context context;
    private VkApi vk;
    private Call<SearchPhotos> call;
    private RecyclerView horizontal_recview;
    private GridView gridView;
    private GridAdapter gridAdapter;
    private HorizontalViewAdapter hva;

    @Override
    public void OnPhotoSelectListener(Photo photo) {
        hva.remove(photo);
        if(photo.count!=0){
            hva.add(photo);
        }    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this;
        setContentView(R.layout.activity_item);

        horizontal_recview = (RecyclerView) findViewById(R.id.horizontal_recview);
        hva = HorizontalViewAdapter.getHorizontalViewAdapter();
        hva.setFragmentManager(getSupportFragmentManager());
        //hva.setData((ArrayList<Photo>) getIntent().getExtras().getSerializable("LIST"));
        //System.out.println("LIST LENGTH IN ITEM  "+((ArrayList<Photo>) getIntent().getExtras().getSerializable("LIST")).size());


        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.HORIZONTAL);
        horizontal_recview.setLayoutManager(llm);
        horizontal_recview.setAdapter(hva);
        gridView = (GridView) findViewById(R.id.grid_view);

        Toast.makeText(this, String.valueOf(this.getIntent().getExtras().getLong("ID")),Toast.LENGTH_SHORT).show();

        vk = ServiceGenerator.createService(VkApi.class);
        call = vk.getPhotos(200, String.valueOf(this.getIntent().getExtras().getLong("ID")),
                ServiceGenerator.API_VERSION,
                ServiceGenerator.ACCESS_TOKEN);
        call.enqueue(new Callback<SearchPhotos>() {
            @Override
            public void onResponse(Call<SearchPhotos> call, Response<SearchPhotos> response) {
                Log.d("onResponse: ", String.valueOf(response.body().response.count));
                gridAdapter = new GridAdapter(context);
                gridAdapter.setGridData(response);
                gridView.setAdapter(gridAdapter);
            }

            @Override
            public void onFailure(Call<SearchPhotos> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
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
        Intent result = new Intent();
        result.putExtra("LIST",hva.getData());
        setResult(RESULT_OK,result);
        super.onBackPressed();
    }
}
