package dx.queen.popmovapp.model.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import dx.queen.popmovapp.IDetailListener;
import dx.queen.popmovapp.R;
import dx.queen.popmovapp.model.Movie;

public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.MyViewHolder> {

     IDetailListener listener;
    private Context context;
    private List<Movie> movieList;

    public MoviesAdapter(Context context, List<Movie> movieList) {
        this.context = context;
        this.movieList = movieList;
    }



    @NonNull
    @Override
    public MoviesAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.model, parent, false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MoviesAdapter.MyViewHolder holder, int position) {
        holder.bind(position, listener);

    }

    @Override
    public int getItemCount() {
        return movieList.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title, user_rating;
        public ImageView thumbnail;

        public MyViewHolder(View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.tv_title);
            user_rating = itemView.findViewById(R.id.tv_user_rating);
            thumbnail = itemView.findViewById(R.id.iv_thumbnail);

        }

        private void bind(final int position, final IDetailListener listener) {
            title.setText(movieList.get(position).getOriginalTitle());
            String vote = Double.toString(movieList.get(position).getVoteAverage());
            user_rating.setText(vote);
            Glide.with(context)
                    .load(movieList.get(position).getPosterPath())
                    .placeholder(R.drawable.load)
                    .into(thumbnail);


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.openDetailFragment(position);

                }
            });

        }
    }
}

