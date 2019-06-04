package dx.queen.popmovapp.controller;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import dx.queen.popmovapp.IDetailListener;
import dx.queen.popmovapp.R;

public class MainActivity extends AppCompatActivity implements IDetailListener {




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        openListFragment();

    }

    private void openListFragment() {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_holder, FragmentList.newInstance(this))
                .commit();
    }


    @Override
    public void openDetailFragment(int position) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_holder, DetailFragment.newInstance(position))
                .addToBackStack(null)
                .commit();

    }
}
