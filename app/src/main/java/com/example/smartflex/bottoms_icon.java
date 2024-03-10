package com.example.smartflex;

import android.content.Intent;
import android.os.Bundle;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link bottoms_icon#newInstance} factory method to
 * create an instance of this fragment.
 */
public class bottoms_icon extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private TextView homeText;
    private ImageView homeIcon;

    private TextView advisorText;
    private ImageView advisorIcon;
    public bottoms_icon() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment bottoms_icon.
     */
    // TODO: Rename and change types and number of parameters
    public static bottoms_icon newInstance(String param1, String param2) {
        bottoms_icon fragment = new bottoms_icon();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
// Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_bottoms_icon, container, false);
        //set the variables
        homeIcon = v.findViewById(R.id.homeIcon);
        homeText = v.findViewById(R.id.homeText);

        advisorIcon = v.findViewById(R.id.advisorIcon);
        advisorText = v.findViewById(R.id.advisorText);

        //set the buttons
        LinearLayout homeButton = v.findViewById(R.id.HomeLayout);
        homeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), MainActivity.class));
            }
        });

        LinearLayout advisorButton = v.findViewById(R.id.AdvisorButton);
        advisorButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), FinancialAdvisor.class));
            }
        });
        return v;
    }

    public void setColorHome(){
        int tintColor = ContextCompat.getColor(getContext(), R.color.iconColorSelected);;
        homeIcon.setColorFilter(tintColor);
        homeText.setTextColor(tintColor);
    }

    public void setColorAdvisor(){
        int tintColor = ContextCompat.getColor(getContext(), R.color.iconColorSelected);;
        advisorIcon.setColorFilter(tintColor);
        advisorText.setTextColor(tintColor);
    }
}