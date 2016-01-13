package in.cityhunt.cityhunt;

import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

public class Poster extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_poster);
        Intent intent = getIntent();
        Bitmap bitmap = intent.getParcelableExtra(EventDetails.bitmapString);
        ImageView poster = (ImageView)findViewById(R.id.poster);
        poster.setImageBitmap(bitmap);
    }
}
