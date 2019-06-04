package dx.queen.popmovapp.controller;

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
import dx.queen.popmovapp.R;
import dx.queen.popmovapp.model.Movie;

public class DetailFragment extends Fragment {


    public static DetailFragment newInstance(int position) {
        Bundle args = new Bundle();
        args.putInt(Movie.ARG_MOVIE_ID, position);
        DetailFragment fragment = new DetailFragment();
        fragment.setArguments(args);
        return fragment;
    }

    private static final int STATUS_CODE = -1;

    private TextView nameOfMovie, plotSynopsis, userRating, releaseDate;
    private ImageView imageView;

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
        int position = getArguments().getInt(Movie.ARG_MOVIE_ID, STATUS_CODE);
        if (position == STATUS_CODE)
            throw new RuntimeException("Wrong  id");
        Movie movie = FragmentList.movieList.get(position);
        fillViews(movie);

    }

    private void initViews(View v) {
        imageView = v.findViewById(R.id.thumbnail_image_header);
        nameOfMovie = v.findViewById(R.id.tv_title);
        plotSynopsis = v.findViewById(R.id.tv_plot_synopsis);
        userRating = v.findViewById(R.id.tv_user_rating);
        releaseDate = v.findViewById(R.id.tv_release_date);
    }

    private void fillViews(Movie movie) {

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


}
