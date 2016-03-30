package com.printapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.SearchView;

import com.printapp.adapters.HorizontalViewAdapter;
import com.printapp.adapters.SectionsPagerAdapter;
import com.printapp.models.Photo;
import com.printapp.models.SearchGroups;
import com.printapp.models.SearchUsers;
import com.printapp.models.ServiceGenerator;
import com.printapp.models.VkApi;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    private SectionsPagerAdapter mSectionsPagerAdapter;
    private ViewPager mViewPager;
    private SearchView search;
    private RecyclerView recview;
    private HorizontalViewAdapter hva;
    private VkApi vk;
    private Call call;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //new AccessTask().execute();

        ServiceGenerator.ACCESS_TOKEN = getIntent().getExtras().getString("TOKEN");

        recview = (RecyclerView) findViewById(R.id.recview);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.HORIZONTAL);
        recview.setLayoutManager(llm);
        hva = new HorizontalViewAdapter();
        recview.setAdapter(hva);

        search = (SearchView) findViewById(R.id.search);
        search.setIconified(false);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hva.add(new Photo());
                search.setVisibility(View.VISIBLE);
            }
        });


        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);


        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);

        vk = ServiceGenerator.createService(VkApi.class);
        call = vk.searchUsers("",ServiceGenerator.API_VERSION,ServiceGenerator.ACCESS_TOKEN,ServiceGenerator.USER_FIELDS );
        search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                call.cancel();
                switch (mViewPager.getCurrentItem()){
                    case 0: {

                        call = vk.searchUsers(newText,ServiceGenerator.API_VERSION,ServiceGenerator.ACCESS_TOKEN,ServiceGenerator.USER_FIELDS );
                        call.enqueue(new Callback<SearchUsers>() {
                            @Override
                            public void onResponse(Call<SearchUsers> call, Response<SearchUsers> response) {
                                System.out.println("LIST LENGTH IN MAIN  "+hva.getData().size());
                                mSectionsPagerAdapter.update(mViewPager.getCurrentItem(), response, hva);
                            }

                            @Override
                            public void onFailure(Call<SearchUsers> call, Throwable t) {
                                System.out.println(call.request().url());
                                t.printStackTrace();
                            }
                        });
                        break;
                    }
                    case 1: {
                        call = vk.searchGroups(newText,ServiceGenerator.API_VERSION,ServiceGenerator.ACCESS_TOKEN );
                        call.enqueue(new Callback<SearchGroups>() {
                            @Override
                            public void onResponse(Call<SearchGroups> call, Response<SearchGroups> response) {
                                mSectionsPagerAdapter.update(mViewPager.getCurrentItem(), response,hva);
                            }

                            @Override
                            public void onFailure(Call<SearchGroups> call, Throwable t) {
                                System.out.println(call.request().url());
                                t.printStackTrace();
                            }
                        });
                        break;
                    }
                }
                return true;
            }
        });

        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                call.cancel();
                switch (position){
                    case 0: {
                        call = vk.searchUsers(search.getQuery().toString(),ServiceGenerator.API_VERSION,ServiceGenerator.ACCESS_TOKEN, ServiceGenerator.USER_FIELDS );
                        call.enqueue(new Callback<SearchUsers>() {
                            @Override
                            public void onResponse(Call<SearchUsers> call, Response<SearchUsers> response) {
                                System.out.println("LIST LENGTH IN MAIN  "+hva.getData().size());
                                mSectionsPagerAdapter.update(mViewPager.getCurrentItem(), response, hva);
                            }

                            @Override
                            public void onFailure(Call<SearchUsers> call, Throwable t) {
                                System.out.println(call.request().url());
                                t.printStackTrace();
                            }
                        });
                        break;
                    }
                    case 1: {
                        call = vk.searchGroups(search.getQuery().toString(),ServiceGenerator.API_VERSION,ServiceGenerator.ACCESS_TOKEN );
                        call.enqueue(new Callback<SearchGroups>() {
                            @Override
                            public void onResponse(Call<SearchGroups> call, Response<SearchGroups> response) {
                                mSectionsPagerAdapter.update(mViewPager.getCurrentItem(), response,hva);
                            }

                            @Override
                            public void onFailure(Call<SearchGroups> call, Throwable t) {
                                System.out.println(call.request().url());
                                t.printStackTrace();
                            }
                        });
                        break;
                    }
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(data.hasExtra("LIST")){
            Log.d("onActivityResult: ", String.valueOf(((ArrayList<Photo>) data.getSerializableExtra("LIST")).size()));
            hva.setData((ArrayList<Photo>) data.getSerializableExtra("LIST"));
            Log.d("onActivityResult:", String.valueOf(hva.getData()));
        } else{
            Log.d("onActivityResult: ","Extra not found");
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}
