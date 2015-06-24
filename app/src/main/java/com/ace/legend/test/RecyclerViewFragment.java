package com.ace.legend.test;


import android.app.ProgressDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.ace.legend.test.adapters.CustomAdapter;
import com.ace.legend.test.model.Flower;

import java.util.ArrayList;
import java.util.List;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;


/**
 * A simple {@link Fragment} subclass.
 */
public class RecyclerViewFragment extends Fragment {

    private RecyclerView recyclerView;
    private List<Flower> flowerList;
    private ProgressDialog pDialog;

    public static final String ENDPOINT = "http://services.hanselandpetal.com";
    public static final String PHOTO_BASE_URL = "http://services.hanselandpetal.com/photos/";

    public RecyclerViewFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View layout =  inflater.inflate(R.layout.fragment_recycler_view, container, false);

        recyclerView = (RecyclerView) layout.findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        flowerList = new ArrayList<>();


        return layout;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if(isOnline()){
            requestDataFromServer();
        }else{
            Toast.makeText(getActivity(), "Not connected to network.", Toast.LENGTH_LONG).show();
        }
    }

    private void requestDataFromServer() {
        pDialog = new ProgressDialog(getActivity());
        pDialog.setCancelable(false);
        pDialog.setIndeterminate(true);
        pDialog.setMessage("Loading...");
        pDialog.show();
        RestAdapter adapter = new RestAdapter.Builder()
                .setEndpoint(ENDPOINT)
                .build();

        ServerCalls api = adapter.create(ServerCalls.class);
        api.getFeed(new Callback<List<Flower>>() {
            @Override
            public void success(List<Flower> flowers, Response response) {
                pDialog.dismiss();
                flowerList = flowers;
                updateViews();
            }

            @Override
            public void failure(RetrofitError error) {
                pDialog.dismiss();
            }
        });
    }

    private void updateViews() {
        CustomAdapter adapter = new CustomAdapter(getActivity(), flowerList);
        recyclerView.setAdapter(adapter);
    }

    protected boolean isOnline() {
        ConnectivityManager cm = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        if(netInfo != null && netInfo.isConnectedOrConnecting()){
            return true;
        }else{
            return false;
        }
    }


}
