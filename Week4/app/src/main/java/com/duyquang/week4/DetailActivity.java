package com.duyquang.week4;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.parceler.Parcels;

public class DetailActivity extends AppCompatActivity {

    private int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ImageView imageBackDrop=(ImageView)findViewById(R.id.imageBackDrop);
        TextView title=(TextView)findViewById(R.id.title);
        TextView releaseDate=(TextView)findViewById(R.id.releaseDate);
        RatingBar ratingBar=(RatingBar)findViewById(R.id.ratingBar);
        TextView overview=(TextView)findViewById(R.id.overview);

        //use parcel to get an object from another activity
        Result result=(Result) Parcels.unwrap(getIntent().getParcelableExtra("result"));

        //backdrop
        String backDropURL="https://image.tmdb.org/t/p/w500/"+result.getBackdropPath();
        Picasso.with(this).load(backDropURL).into(imageBackDrop);

        //title
        title.setText(result.getTitle());

        //release date
        releaseDate.setText("Release Date: "+result.getReleaseDate());

        //rating bar
        ratingBar.setRating(result.getVoteAverage()/2);
        ratingBar.setIsIndicator(true);

        //overview
        overview.setText(result.getOverview());


    }
}
