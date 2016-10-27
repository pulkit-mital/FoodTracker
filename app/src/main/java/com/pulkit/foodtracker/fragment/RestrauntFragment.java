package com.pulkit.foodtracker.fragment;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.pulkit.foodtracker.R;
import com.pulkit.foodtracker.activities.RestrauntDetailActivity;
import com.pulkit.foodtracker.adapter.RestrauntAdapter;
import com.pulkit.foodtracker.pojo.Restraunts;
import com.pulkit.foodtracker.presenter.RestrauntContract;

import java.util.ArrayList;

/**
 * Created by pulkitmital on 27/10/16.
 */
public class RestrauntFragment extends BaseFragment implements RestrauntContract.View, AdapterView.OnItemClickListener {


    private RestrauntContract.Presenter mPresenter;
    private ActionBar mActionBar;
    private RestrauntAdapter restrauntAdapter;
    private ArrayList<Restraunts> restraunts;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        restrauntAdapter = new RestrauntAdapter(new ArrayList<Restraunts>(0), getActivity());
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
        listView.setAdapter(restrauntAdapter);
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
    public void showRestraunt(ArrayList<Restraunts> restraunts) {
        this.restraunts = restraunts;
        restrauntAdapter.replaceRestraunt(restraunts);
    }

    @Override
    public void showDataNotAvailableMessage(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();

    }

    @Override
    public void setPresenter(RestrauntContract.Presenter presenter) {
        mPresenter = presenter;
    }

    public static RestrauntFragment newInstance() {
        return new RestrauntFragment();
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

        Restraunts restraunt = restraunts.get(i);
        Intent intent = new Intent(getActivity(), RestrauntDetailActivity.class);
        intent.putExtra("restraunt_pojo", restraunt);
        startActivity(intent);

    }
}
