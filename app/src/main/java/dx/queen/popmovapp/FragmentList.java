package dx.queen.popmovapp;

import android.app.ProgressDialog;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import dx.queen.popmovapp.adapter.MoviesAdapter;
import dx.queen.popmovapp.api.Client;
import dx.queen.popmovapp.api.Service;
import dx.queen.popmovapp.model.Movie;
import dx.queen.popmovapp.model.MoviesResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FragmentList extends Fragment {
     RecyclerView recyclerView;
     MoviesAdapter adapter;
    public static List<Movie> movieList;
    ProgressDialog progressDialog;
    private SwipeRefreshLayout swipeContainer;
    IDetailListener listener;

    public static FragmentList newInstance(IDetailListener listener) {
        FragmentList fragmentList = new FragmentList();
        fragmentList.setListener(listener);
        return fragmentList;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_list, container, false);
        recyclerView = v.findViewById(R.id.recycler_view);

        initViews();
        swipeContainer = v.findViewById(R.id.fragment_list);
        swipeContainer.setColorSchemeColors(getResources().getColor(android.R.color.holo_orange_dark));
        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                initViews();
                Toast.makeText(getContext() , "Movies Refreshed", Toast.LENGTH_SHORT).show();
            }
        });
        return v;


        }

    private void initViews(){
        progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage("Fetching movies");
        progressDialog.setCancelable(false);
        progressDialog.show();

        movieList = new ArrayList<>();
        adapter = new MoviesAdapter(getContext(),movieList);

        if(getActivity().getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT){
            recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
        }else{
            recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 4));
        }

        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        loadJSON();
    }


    private void loadJSON() {

        try {
            if (BuildConfig.THE_MOVIE_DB_API_TOKEN.isEmpty()){
                Toast.makeText(getContext(), "please obtain API key firstly from themoviedr.org", Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
                return;
            }

            Client client = new Client();
            Service apiService =
                    client.getClient().create(Service.class);
            Call<MoviesResponse> call = apiService.getPopularMovies(BuildConfig.THE_MOVIE_DB_API_TOKEN);
            call.enqueue(new Callback<MoviesResponse>() {
                @Override
                public void onResponse(Call<MoviesResponse> call, Response<MoviesResponse> response) {
                    List<Movie> movies = response.body().getResults();
                    recyclerView.setAdapter(new  MoviesAdapter(getContext(),movies));
                    recyclerView.smoothScrollToPosition(0);

                    if(swipeContainer.isRefreshing()){
                        swipeContainer.setRefreshing(false);
                    }
                    progressDialog.dismiss();

                }

                @Override
                public void onFailure(Call<MoviesResponse> call, Throwable t) {
                    Log.d("Error", t.getMessage());
                    Toast.makeText(getContext(), "Error Fetching Data!", Toast.LENGTH_SHORT).show();

                }
            });
        }catch (Exception e){
            Log.d("Error", e.getMessage());
            Toast.makeText(getContext(), e.toString(), Toast.LENGTH_SHORT).show();
        }
    }

    public void setListener(IDetailListener listener) {
        this.listener = listener;
    }
}
