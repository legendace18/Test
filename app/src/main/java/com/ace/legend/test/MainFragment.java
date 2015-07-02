package com.ace.legend.test;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


/**
 * A simple {@link Fragment} subclass.
 */
public class MainFragment extends Fragment {

    private FragmentManager fm;
    private Button btn_recyclerView, btn_cardView;
    private int Flag = 0;

    public MainFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View layout = inflater.inflate(R.layout.fragment_main, container, false);
        fm = getChildFragmentManager();
        btn_recyclerView = (Button) layout.findViewById(R.id.btn_recyclerView);
        btn_cardView = (Button) layout.findViewById(R.id.btn_cardView);
        return layout;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if (savedInstanceState != null) {
            Flag = savedInstanceState.getInt("Flag");
            setButtonState();
        } else {
            RecyclerViewFragment frag = new RecyclerViewFragment();
            fm.beginTransaction().add(R.id.fragment_container, frag).commit();
            setButtonState();
        }

        btn_recyclerView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Flag != 0) {
                    Flag = 0;
                    RecyclerViewFragment frag = new RecyclerViewFragment();
                    fm.beginTransaction().replace(R.id.fragment_container, frag).commit();
                    setButtonState();
                }
            }
        });

        btn_cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Flag != 1) {
                    Flag = 1;
                    CardViewFragment frag = new CardViewFragment();
                    fm.beginTransaction().replace(R.id.fragment_container, frag).commit();
                    setButtonState();
                }
            }
        });
    }

    private void setButtonState() {
        if (Flag == 0) {
            btn_recyclerView.setBackgroundResource(R.drawable.btn_recycler_view_pressed);
            btn_cardView.setBackgroundResource(R.drawable.btn_card_view);
            btn_recyclerView.setTextColor(getResources().getColor(R.color.white));
            btn_cardView.setTextColor(getResources().getColor(R.color.grey));
        } else if (Flag == 1) {
            btn_cardView.setBackgroundResource(R.drawable.btn_card_view_pressed);
            btn_recyclerView.setBackgroundResource(R.drawable.btn_recycler_view);
            btn_cardView.setTextColor(getResources().getColor(R.color.white));
            btn_recyclerView.setTextColor(getResources().getColor(R.color.grey));
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("Flag", Flag);
    }
}
