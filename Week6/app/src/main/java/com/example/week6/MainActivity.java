package com.example.week6;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Parcelable;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.FragmentTransaction;

import com.example.week6.MovieFragment;

public class MainActivity extends AppCompatActivity implements MovieFragment.OnMovieSelected {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    @Override
    public void movieSelected(Movie movie) {

        int display_mode = getResources().getConfiguration().orientation;
        if (display_mode == Configuration.ORIENTATION_PORTRAIT) {
            Intent intent = new Intent(this, DetailsActivity.class);
            intent.putExtra("movie", movie);
            startActivity(intent);
        }else{
            DetailsFragment df =
                    (DetailsFragment)getSupportFragmentManager().findFragmentByTag("details");
            if (df == null) {
                FragmentTransaction fts = getSupportFragmentManager().beginTransaction();
                df = DetailsFragment.newInstance(movie);
                fts.add(R.id.container, df, "details");
                fts.commit();
            }else{
                df.setMovie(findViewById(R.id.container), movie);
            }
        }

    }
}