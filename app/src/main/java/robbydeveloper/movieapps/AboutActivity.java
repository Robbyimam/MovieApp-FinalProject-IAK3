package robbydeveloper.movieapps;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.util.Linkify;
import android.widget.TextView;

public class AboutActivity extends AppCompatActivity {
    private TextView tvLinkify;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setTitle("Tentang");
            }
        tvLinkify = (TextView) findViewById(R.id.tv_Linkify);
        tvLinkify.setText("robbyimam31@gmail.com\n");
        }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
