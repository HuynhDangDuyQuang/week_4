package com.duyquang.week4;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.Gson;

import java.io.IOException;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class TopRateFragment extends Fragment  {
    final String urlTopRate="https://api.themoviedb.org/3/movie/top_rated?api_key=a07e22bc18f5cb106bfe4cc1f83ad8ed";
    private SwipeRefreshLayout swipeContainer;


    List<Result> results;
    String movieData;
    Adapter adapter;



    public TopRateFragment() { }

    public static TopRateFragment newInstance() {
        TopRateFragment fragment = new TopRateFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) { super.onCreate(savedInstanceState); }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_top_rate, container, false);

        final RecyclerView rvMovie = view.findViewById(R.id.recycler_view);

        swipeContainer = (SwipeRefreshLayout) view.findViewById(R.id.swipeContainer);

        fetchData(rvMovie);

        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                fetchData(rvMovie);


            }
        });

        return view;
    }

    public void fetchData(RecyclerView rvM){
        final RecyclerView rvMovie=rvM;
        OkHttpClient client = new OkHttpClient();
        Request request=new Request.Builder()
                .url(urlTopRate)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

                movieData=response.body().string();
                Gson gson = new Gson();
                Movie movie = (Movie) gson.fromJson(movieData, Movie.class);
                results=movie.getResults();

                adapter=new Adapter(TopRateFragment.this.getActivity(), results);

                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        rvMovie.setAdapter(adapter);
                        rvMovie.setLayoutManager(new LinearLayoutManager(TopRateFragment.this.getActivity()));
                        swipeContainer.setRefreshing(false);
                    }
                });

            }

        });
    }

}