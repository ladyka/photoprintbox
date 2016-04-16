package com.printapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.printapp.R;
import com.printapp.adapters.HorizontalViewAdapter;
import com.printapp.adapters.SectionsPagerAdapter;
import com.printapp.fragments.PhotoSelectDialogFragment;
import com.printapp.models.Photo;
import com.printapp.models.SearchGroups;
import com.printapp.models.SearchPhotos;
import com.printapp.models.SearchUsers;
import com.printapp.network.VkApi;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.printapp.network.ServiceGenerator.ACCESS_TOKEN;
import static com.printapp.network.ServiceGenerator.API_VERSION;
import static com.printapp.network.ServiceGenerator.USER_FIELDS;
import static com.printapp.network.ServiceGenerator.createService;

public class MainActivity extends AppCompatActivity implements PhotoSelectDialogFragment.PhotoSelectListener {
    private SectionsPagerAdapter pagerAdapter;
    private ViewPager mViewPager;
    private SearchView search;
    private RecyclerView recview;
    private HorizontalViewAdapter hva;
    private VkApi vk;
    private Call call;
    public static String TAG = "PRINT";

    @Override
    public void OnPhotoSelectListener(Photo photo) {
        hva.remove(photo);
        if(photo.count!=0){
            hva.add(photo);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //new AccessTask().execute();

        ACCESS_TOKEN = getIntent().getExtras().getString("TOKEN");

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");


        recview = (RecyclerView) findViewById(R.id.recview);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.HORIZONTAL);
        recview.setLayoutManager(llm);
        hva = HorizontalViewAdapter.getHorizontalViewAdapter();
        hva.setFragmentManager(getSupportFragmentManager());
        recview.setAdapter(hva);


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(),"Распечатано",Toast.LENGTH_SHORT).show();
            }
        });

        vk = createService(VkApi.class);
        call = vk.searchUsers("", API_VERSION, ACCESS_TOKEN, USER_FIELDS );

        pagerAdapter = new SectionsPagerAdapter(this);
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(pagerAdapter);


        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);

        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                Log.d(TAG, "onPageSelected: "+position);
                call.cancel();
                switch (position){
                    case 0: {
                        call = vk.searchUsers(search.getQuery().toString(), API_VERSION, ACCESS_TOKEN, USER_FIELDS );
                        call.enqueue(new Callback<SearchUsers>() {
                            @Override
                            public void onResponse(Call<SearchUsers> call, Response<SearchUsers> response) {
                                System.out.println("LIST LENGTH IN MAIN  "+hva.getData().size());
                                pagerAdapter.update(mViewPager.getCurrentItem(), response);
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
                        call = vk.searchGroups(search.getQuery().toString(), API_VERSION, ACCESS_TOKEN );
                        call.enqueue(new Callback<SearchGroups>() {
                            @Override
                            public void onResponse(Call<SearchGroups> call, Response<SearchGroups> response) {
                                pagerAdapter.update(mViewPager.getCurrentItem(), response);
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
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main,menu);
        MenuItem item = menu.findItem(R.id.action_search);
        search = (android.support.v7.widget.SearchView) MenuItemCompat.getActionView(item);

        search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            @Override
            public boolean onQueryTextSubmit(String query) {
                call.cancel();
                if(mViewPager.getCurrentItem()==2){
                    call = vk.searchPhotos(query, API_VERSION, ACCESS_TOKEN);
                    call.enqueue(new Callback<SearchPhotos>() {
                        @Override
                        public void onResponse(Call<SearchPhotos> call, Response<SearchPhotos> response) {
                            Log.d("onResponse: ", String.valueOf(response.body().response.count));
                            pagerAdapter.update(mViewPager.getCurrentItem(),response);
                        }

                        @Override
                        public void onFailure(Call<SearchPhotos> call, Throwable t) {

                        }
                    });
                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                call.cancel();
                switch (mViewPager.getCurrentItem()){
                    case 0: {

                        call = vk.searchUsers(newText, API_VERSION, ACCESS_TOKEN, USER_FIELDS );
                        call.enqueue(new Callback<SearchUsers>() {
                            @Override
                            public void onResponse(Call<SearchUsers> call, Response<SearchUsers> response) {
                                System.out.println("LIST LENGTH IN MAIN  "+hva.getData().size());
                                pagerAdapter.update(mViewPager.getCurrentItem(), response);
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
                        call = vk.searchGroups(newText, API_VERSION, ACCESS_TOKEN );
                        call.enqueue(new Callback<SearchGroups>() {
                            @Override
                            public void onResponse(Call<SearchGroups> call, Response<SearchGroups> response) {
                                pagerAdapter.update(mViewPager.getCurrentItem(), response);
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
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        hva.setFragmentManager(getSupportFragmentManager());
        super.onActivityResult(requestCode, resultCode, data);
    }
}
