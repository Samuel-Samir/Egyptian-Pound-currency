package com.example.android.dollar.Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.android.dollar.R;
import com.example.android.dollar.Utilities;

import java.io.IOException;

/**
 * A simple {@link Fragment} subclass.
 */
public class ChatFragment extends Fragment {

    Button button ;
    TextView textView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
         View rootView =inflater.inflate(R.layout.fragment_chat, container, false);
          button = (Button) rootView.findViewById(R.id.buttonShow);
          textView =(TextView) rootView.findViewById(R.id.textshow) ;
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        return rootView ;
    }

}
