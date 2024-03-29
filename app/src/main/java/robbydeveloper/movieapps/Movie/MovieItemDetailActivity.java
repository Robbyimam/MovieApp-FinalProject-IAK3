package robbydeveloper.movieapps.Movie;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.NavUtils;
import android.view.MenuItem;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.List;

import robbydeveloper.movieapps.Entity.Favorite;
import robbydeveloper.movieapps.R;

/**
 * An activity representing a single MovieItem detail screen. This
 * activity is only used narrow width devices. On tablet-size devices,
 * item details are presented side-by-side with a list of items
 * in a {@link MovieItemListActivity}.
 */
public class MovieItemDetailActivity extends AppCompatActivity {

    public static final String MOVIE_ID = "id";
    public static final String POSTER_PATH = "poster_path";
    public static final String BACKDROP_PATH = "backdrop_path";
    public static final String YEAR = "year";
    public static final String RELEASE = "release";
    public static final String SINOPSIS = "sinopsis";
    public static final String TITLE = "title";
    public static final String DURATION = "duration";
    ImageView imageView;
    int movie_id = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movieitem_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.detail_toolbar);
        setSupportActionBar(toolbar);

        final FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own detail action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        // Show the Up button in the action bar.
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        if (savedInstanceState == null) {
            Bundle arguments = new Bundle();
            movie_id = getIntent().getIntExtra(MovieItemDetailActivity.MOVIE_ID,0);

            arguments.putInt(MovieItemDetailActivity.MOVIE_ID,
                    getIntent().getIntExtra(MovieItemDetailActivity.MOVIE_ID,0));
            arguments.putString(MovieItemDetailActivity.POSTER_PATH,
                    getIntent().getStringExtra(MovieItemDetailActivity.POSTER_PATH));
            arguments.putString(MovieItemDetailActivity.BACKDROP_PATH,
                    getIntent().getStringExtra(MovieItemDetailActivity.BACKDROP_PATH));
            arguments.putString(MovieItemDetailActivity.RELEASE,
                    getIntent().getStringExtra(MovieItemDetailActivity.RELEASE));
            arguments.putString(MovieItemDetailActivity.SINOPSIS,
                    getIntent().getStringExtra(MovieItemDetailActivity.SINOPSIS));
            arguments.putString(MovieItemDetailActivity.TITLE,
                    getIntent().getStringExtra(MovieItemDetailActivity.TITLE));
            arguments.putFloat(MovieItemDetailActivity.DURATION,
                    getIntent().getFloatExtra(MovieItemDetailActivity.DURATION,0));

            arguments.putString(MovieItemDetailActivity.YEAR,
                    getIntent().getStringExtra(MovieItemDetailActivity.YEAR));

            imageView = (ImageView) findViewById(R.id.img_background);

            if(imageView!=null){
                Picasso.with(this) //
                        .load(getIntent().getStringExtra("backdrop_path")) //
                        .fit() //
                        .into(imageView);
            }

            final List<Favorite> favorite = Favorite.find(Favorite.class, "movie_id = ?", String.valueOf(movie_id));

            if (favorite.size() > 0) {
                if (fab != null) {
                    fab.setImageResource(R.drawable.ic_favorite_white_24dp);

                }
            }

            if (fab != null) {
                fab.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        List<Favorite> favoriteList = Favorite.find(Favorite.class, "movie_id = ?", String.valueOf(movie_id));
                        if (favoriteList.size() <= 0) {
                            fab.setImageResource(R.drawable.ic_favorite_white_24dp);
                            Favorite favorite_store = new Favorite();
                            favorite_store.setMovieId(movie_id);
                            favorite_store.save();
                            Snackbar.make(view, "Film ini sudah ditambahkan ke daftar favorite", Snackbar.LENGTH_LONG)
                                    .setAction("Action", null).show();
                        } else {
                            Favorite single_favorite = favoriteList.get(0);
                            single_favorite.delete();
                            fab.setImageResource(R.drawable.ic_favorite_border_white_24dp);
                            Snackbar.make(view, "Film ini sudah dihapuskan ke daftar favorite", Snackbar.LENGTH_LONG)
                                    .setAction("Action", null).show();
                        }
                    }
                });
            }

            MovieDetailFragment fragment = new MovieDetailFragment();
            fragment.setArguments(arguments);
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.movieitem_detail_container, fragment)
                    .commit();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            NavUtils.navigateUpTo(this, new Intent(this, MovieItemListActivity.class));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
