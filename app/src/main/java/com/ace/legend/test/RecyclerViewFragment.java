package com.ace.legend.test;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.ace.legend.test.adapters.CustomRecyclerAdapter;
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
public class RecyclerViewFragment extends Fragment implements CustomRecyclerAdapter.TouchListener {

    private RecyclerView recyclerView;
    private List<Flower> flowerList;
    private ProgressBar progressBar;
    private CustomRecyclerAdapter adapter;
    NetworkHandler networkHandler;

    public static final String ENDPOINT = "http://services.hanselandpetal.com";
    public static final String PHOTO_BASE_URL = "http://services.hanselandpetal.com/photos/";

    public RecyclerViewFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View layout = inflater.inflate(R.layout.fragment_recycler_view, container, false);
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
        recyclerView.addOnItemTouchListener(new RecyclerViewTouchListener(getActivity(), recyclerView, new ClickListener() {
            @Override
            public void onClick(View v, int position) {
                //Toast.makeText(getActivity(), "clicked at: " + position, Toast.LENGTH_LONG).show();
                showDetailActivity(position);
            }

            @Override
            public void onLongClick(View v, int position) {

            }
        }));
        if (networkHandler.isOnline()) {
            requestDataFromServer();
        } else {
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
        adapter = new CustomRecyclerAdapter(getActivity(), flowerList);
        adapter.setTouchListener(this);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.fragment_recycler_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.action_refresh:
                if (networkHandler.isOnline()) {
                    requestDataFromServer();
                } else {
                    Toast.makeText(getActivity(), "Not connected to network.", Toast.LENGTH_LONG).show();
                }
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void itemTouched(View v, final int position) {
        PopupMenu pMenu = new PopupMenu(getActivity(), v);
        pMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                int id = item.getItemId();
                switch (id) {
                    case R.id.action_open:
                        showDetailActivity(position);
                        break;
                    case R.id.action_delete:
                        flowerList.remove(position);
                        adapter.notifyItemRemoved(position);
                        break;
                }
                return true;
            }
        });
        pMenu.inflate(R.menu.popup_menu);
        pMenu.show();
    }

    private void showDetailActivity(int position) {
        Flower flower = flowerList.get(position);
        Intent intent = new Intent(getActivity(), DetailActivity.class);
        intent.putExtra("NAME", flower.getName());
        intent.putExtra("CATEGORY", flower.getCategory());
        intent.putExtra("PRICE",String.valueOf(flower.getPrice()));
        intent.putExtra("INSTRUCTIONS", flower.getInstructions());
        intent.putExtra("PHOTO", flower.getPhoto());
        startActivity(intent);
    }

    class RecyclerViewTouchListener implements RecyclerView.OnItemTouchListener {

        private GestureDetector detector;
        private ClickListener clickListener;

        public RecyclerViewTouchListener(Context context, RecyclerView recyclerView, ClickListener clickListener) {
            this.clickListener = clickListener;
            detector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {
                @Override
                public boolean onSingleTapUp(MotionEvent e) {
                    return true;
                }

                @Override
                public void onLongPress(MotionEvent e) {
                    super.onLongPress(e);
                }
            });
        }

        @Override
        public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
            View child = rv.findChildViewUnder(e.getX(), e.getY());
            if (child != null && clickListener != null && detector.onTouchEvent(e)) {
                clickListener.onClick(child, rv.getChildPosition(child));
            }
            return false;
        }

        @Override
        public void onTouchEvent(RecyclerView rv, MotionEvent e) {

        }
    }

    public interface ClickListener {
        void onClick(View v, int position);

        void onLongClick(View v, int position);
    }
}
