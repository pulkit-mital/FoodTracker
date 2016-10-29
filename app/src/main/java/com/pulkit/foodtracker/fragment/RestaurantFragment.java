package com.pulkit.foodtracker.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.pulkit.foodtracker.R;
import com.pulkit.foodtracker.activities.RestaurantDetailActivity;
import com.pulkit.foodtracker.adapter.RestaurantAdapter;
import com.pulkit.foodtracker.pojo.Restaurants;
import com.pulkit.foodtracker.presenter.RestaurantContract;

import java.util.ArrayList;

/**
 * Created by pulkitmital on 27/10/16.
 */
public class RestaurantFragment extends BaseFragment implements RestaurantContract.View, AdapterView.OnItemClickListener {


    private RestaurantContract.Presenter mPresenter;
    private ActionBar mActionBar;
    private RestaurantAdapter restaurantAdapter;
    private ArrayList<Restaurants> restraunts;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        restaurantAdapter = new RestaurantAdapter(new ArrayList<Restaurants>(0), getActivity());
        setHasOptionsMenu(true);
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.start();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.restrunt_list_fragment_layout, container, false);
        ListView listView = (ListView) view.findViewById(R.id.listView);
        listView.setAdapter(restaurantAdapter);
        listView.setOnItemClickListener(this);
        return view;
    }


    @Override
    public void showProgress() {
        super.showProgress();
    }

    @Override
    public void hideProgress() {
        super.hideProgress();
    }

    @Override
    public void setTitle(String title) {
        ((AppCompatActivity) getContext()).getSupportActionBar().setTitle(title);
    }

    @Override
    public void showRestraunt(ArrayList<Restaurants> restraunts) {
        this.restraunts = restraunts;
        restaurantAdapter.replaceRestraunt(restraunts);
    }

    @Override
    public void showDataNotAvailableMessage(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();

    }

    @Override
    public void setPresenter(RestaurantContract.Presenter presenter) {
        mPresenter = presenter;
    }

    public static RestaurantFragment newInstance() {
        return new RestaurantFragment();
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

        Restaurants restraunt = restraunts.get(i);
        Intent intent = new Intent(getActivity(), RestaurantDetailActivity.class);
        intent.putExtra("restraunt_pojo", restraunt);
        startActivity(intent);

    }
}
