package dx.queen.popmovapp;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity implements IDetailListener {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        openListFragment();

    }

//      public  static Activity getActivity(){
//        Context context = getActivity();
//      while (context instanceof ContextWrapper){
//            if(context instanceof Activity){
//                return (Activity)context;
//            }
//            context = ((ContextWrapper)context).getBaseContext();
//        }
//        return null;
//    }


    private void openListFragment() {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_holder , FragmentList.newInstance(this))
                .commit() ;
    }




    @Override
    public void openDetailFragment(int i) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id. fragment_holder , DetailFragment.newInstance(i))
                .addToBackStack(null)
                .commit() ;

    }
}
