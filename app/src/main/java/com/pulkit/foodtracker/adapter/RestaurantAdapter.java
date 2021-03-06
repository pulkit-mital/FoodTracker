package com.pulkit.foodtracker.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.pulkit.foodtracker.R;
import com.pulkit.foodtracker.Utils.RatingComparator;
import com.pulkit.foodtracker.pojo.Restaurants;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by pulkitmital on 27/10/16.
 */

public class RestaurantAdapter extends BaseAdapter {

    ArrayList<Restaurants> restrauntList;
    private Context context;

    public RestaurantAdapter(ArrayList<Restaurants> restrauntList, Context context) {
        this.restrauntList = restrauntList;
        this.context = context;
    }

    public void replaceRestraunt(ArrayList<Restaurants> restrauntList) {
        this.restrauntList = restrauntList;
        Collections.sort(this.restrauntList, new RatingComparator());
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return restrauntList.size();
    }

    @Override
    public Object getItem(int i) {
        return restrauntList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return Long.valueOf(((Restaurants) restrauntList.get(i)).getId());
    }

    @Override
    public View getView(int i, View converterView, ViewGroup viewGroup) {
        View view = converterView;
        if (view == null) {
            LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
            view = inflater.inflate(R.layout.single_restraunt_list, viewGroup, false);
        }
        TextView restrauntNameTextView = (TextView) view.findViewById(R.id.restraunt_name);
        TextView restrauntRatingTextView = (TextView) view.findViewById(R.id.restraunt_rating);
        TextView restrauntLocalityTextView = (TextView) view.findViewById(R.id.restraunt_locality);
        ImageView restrauntImageView = (ImageView) view.findViewById(R.id.restraunt_image);


        Restaurants restaurants = restrauntList.get(i);
        restrauntNameTextView.setText(restaurants.getName());
        restrauntLocalityTextView.setText(restaurants.getLocality());
        restrauntRatingTextView.setText(restaurants.getRating());

        Picasso.with(context).load(restaurants.getImage()).fit().error(R.mipmap.ic_launcher).placeholder(R.mipmap.ic_launcher).into(restrauntImageView);
        return view;
    }
}
