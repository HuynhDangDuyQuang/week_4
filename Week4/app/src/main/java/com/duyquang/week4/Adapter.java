package com.duyquang.week4;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.List;



/**
 * Created by me Phuong on 13/03/2018.
 */

public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder>{

    // Store a member variable for the contacts
    private List<Result> mResults=new ArrayList<>();
    // Store the context for easy access
    private Context mContext;


    // Pass in the contact array into the constructor
    public Adapter(Context context, List<Result> contacts) {
        mResults = contacts;
        mContext = context;
    }

    // Easy access to the context object in the recyclerview
    private Context getContext() {
        return mContext;
    }

    // Usually involves inflating a layout from XML and returning the holder
    @Override
    public Adapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate the custom layout
        View movieView = inflater.inflate(R.layout.item_contact, parent, false);



        // Return a new holder instance
        ViewHolder viewHolder = new ViewHolder(movieView);
        return viewHolder;
    }

    // Involves populating data into the item through holder
    @Override
    public void onBindViewHolder(final Adapter.ViewHolder viewHolder, final int position) {
        // Get the data model based on position
        final Result result = mResults.get(position);

        // Set item views based on your views and data model
        //title
        TextView title = viewHolder.movieTitle;
        title.setText(result.getTitle());

        //overview
        TextView description = viewHolder.movieOverview;
        description.setText(result.getOverview());

        //poster
        ImageView poster=viewHolder.moviePoster;
        String posterURL="https://image.tmdb.org/t/p/w500/"+result.getPosterPath();
        Picasso.with(mContext).load(posterURL).into(poster);

        //play button
        ImageView buttonPlay=viewHolder.imageButton;
        if(result.getVideo()) buttonPlay.setVisibility(View.VISIBLE);
        else buttonPlay.setVisibility(View.INVISIBLE);

        //on click listener
        viewHolder.parentLayout.setOnClickListener(new View.OnClickListener(){
                                                       @Override
                                                       public void onClick(View view) {
//                   listener.onLayoutClick(result);
                                                           Intent detailActivity= new Intent(mContext,DetailActivity.class);
                                                           detailActivity.putExtra("result", Parcels.wrap(result));    // use Parcel to send an object to another activity
                                                           mContext.startActivity(detailActivity);

//                   Intent detailActivity=new Intent (mContext,DetailActivity.class);
//                   mContext.startActivity(detailActivity);
                                                       }
                                                   }
        );

    }

    // Returns the total count of items in the list
    @Override
    public int getItemCount() {
        return mResults.size();
    }

    // Provide a direct reference to each of the views within a data item
    // Used to cache the views within the item layout for fast access
    public class ViewHolder extends RecyclerView.ViewHolder{
        // Your holder should contain a member variable
        // for any view that will be set as you render a row
        TextView movieTitle;
        TextView movieOverview;
        ImageView moviePoster;
        ImageView imageButton;
        LinearLayout parentLayout;


        // We also create a constructor that accepts the entire item row
        // and does the view lookups to find each subview
        public ViewHolder(View itemView) {
            // Stores the itemView in a public final member variable that can be used
            // to access the context from any ViewHolder instance.
            super(itemView);

            movieTitle=(TextView) itemView.findViewById(R.id.movieTitle);
            movieOverview=(TextView) itemView.findViewById(R.id.movieOverview);
            moviePoster=(ImageView) itemView.findViewById(R.id.moviePoster);
            imageButton=(ImageView) itemView.findViewById(R.id.imageButton);
            parentLayout=(LinearLayout) itemView.findViewById(R.id.linearLayout);

        }

    }

}
