package dx.queen.popmovapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import dx.queen.popmovapp.model.Movie;

public class DetailFragment extends Fragment {

    public static DetailFragment newInstance(int i) {
        Bundle args = new Bundle();
        args.putInt(Movie.ARG_MOVIE_ID, i);
        DetailFragment fragment = new DetailFragment();
        fragment.setArguments(args);
        return fragment;
    }

    TextView nameOfMovie, plotSynopsis, userRating, releaseDate;
    ImageView imageView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.content_detail, container, false);
         initViews(v);
         return v;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        int i =  getArguments().getInt(Movie.ARG_MOVIE_ID,-1);
        if (i == -1)
            throw new RuntimeException("Wrong boy id");
        Movie movie = FragmentList.movieList.get(i);
        fillViews(movie);

    }

    public void initViews(View v){
        imageView = v.findViewById(R.id.thumbnail_image_header);
        nameOfMovie = v.findViewById(R.id.tv_title);
        plotSynopsis = v.findViewById(R.id.tv_plot_synopsis);
        userRating = v.findViewById(R.id.tv_user_rating);
        releaseDate = v.findViewById(R.id.tv_release_date);
    }

    private void fillViews(Movie movie){

        String thumbnail = movie.getPosterPath();
        String movieName = movie.getOriginalTitle();
        String synopsis = movie.getOverview();
        String rating = Double.toString(movie.getVoteAverage());
        String dateOfRelease = movie.getReleaseDate();


        Glide.with(this)
                .load("http://api.themoviedb.org/3/" + thumbnail)
                .placeholder(R.drawable.load)
                .into(imageView);

        nameOfMovie.setText(movieName);
        plotSynopsis.setText(synopsis);
        userRating.setText(rating);
        releaseDate.setText(dateOfRelease);




    }


//        Toolbar toolbar = findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
////
     // getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//
//       // initCollapsingToolbar();
////




//    private void initCollapsingToolbar(){
//        final CollapsingToolbarLayout collapsingToolbarLayout = findViewById(R.id.collapsing_toolbar);
//        collapsingToolbarLayout.setTitle("");
//        AppBarLayout appBarLayout =  findViewById(R.id.appbar);
//        appBarLayout.setExpanded(true);
//        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
//
//            boolean isShow = false;
//            int scrollRange = -1;
//
//            @Override
//            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
//                if(scrollRange == -1){
//                    scrollRange = appBarLayout.getTotalScrollRange();
//
//                }
//                if (scrollRange + verticalOffset == 0){
//                    collapsingToolbarLayout.setTitle(getString(R.string.movie_details));
//                    isShow = true;
//                }else if(isShow){
//                    collapsingToolbarLayout.setTitle("");
//                    isShow = false;
//                }
//
//
//
//            }
//        });
//
//    }

    }
