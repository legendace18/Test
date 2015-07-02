package com.ace.legend.test;


import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.ace.legend.test.adapters.CustomCardAdapter;
import com.ace.legend.test.model.Flower;
import com.ace.legend.test.utils.NetworkHandler;

import java.util.ArrayList;
import java.util.List;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;


/**
 * A simple {@link Fragment} subclass.
 */
public class CardViewFragment extends Fragment {

    private RecyclerView recyclerView;
    private List<Flower> flowerList;
    private ProgressBar progressBar;
    NetworkHandler networkHandler;

    public static final String ENDPOINT = "http://services.hanselandpetal.com";
    public static final String PHOTO_BASE_URL = "http://services.hanselandpetal.com/photos/";

    public CardViewFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View layout =  inflater.inflate(R.layout.fragment_card_view, container, false);

        setHasOptionsMenu(true);
        networkHandler = new NetworkHandler(getActivity());

        progressBar = (ProgressBar) layout.findViewById(R.id.progressBar);
        recyclerView = (RecyclerView) layout.findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        flowerList = new ArrayList<>();

        return layout;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if(networkHandler.isOnline()){
            requestDataFromServer();
        }else{
            Toast.makeText(getActivity(), "Not connected to network.", Toast.LENGTH_LONG).show();
        }
    }

    private void requestDataFromServer() {

        progressBar.setVisibility(View.VISIBLE);
        RestAdapter adapter = new RestAdapter.Builder()
                .setEndpoint(ENDPOINT)
                .build();

        ServerCalls api = adapter.create(ServerCalls.class);
        api.getFeed(new Callback<List<Flower>>() {
            @Override
            public void success(List<Flower> flowers, Response response) {
                progressBar.setVisibility(View.GONE);
                flowerList = flowers;
                updateViews();
            }

            @Override
            public void failure(RetrofitError error) {
                progressBar.setVisibility(View.GONE);
                Snackbar.make(recyclerView, "" + error, Snackbar.LENGTH_LONG)
                        .setAction("Try Agaig", new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                requestDataFromServer();
                            }
                        })
                        .show();
            }
        });
    }

    private void updateViews() {
        CustomCardAdapter adapter = new CustomCardAdapter(getActivity(), flowerList);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.fragment_card_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id){
            case R.id.action_refresh:
                if(networkHandler.isOnline()){
                    requestDataFromServer();
                }else{
                    Toast.makeText(getActivity(), "Not connected to network.", Toast.LENGTH_LONG).show();
                }
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
