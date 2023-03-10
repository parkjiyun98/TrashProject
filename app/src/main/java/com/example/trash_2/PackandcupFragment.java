package com.example.trash_2;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class PackandcupFragment extends Fragment {
    public static PackandcupFragment newInstance() {
        return new PackandcupFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_packandcup, container, false);

        Button button=(Button) rootView.findViewById(R.id.back);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //getActivity()로 MainActivity의 replaceFragment를 불러온다.
                ((MainActivity)getActivity()).replaceFragment(Fragment2.newInstance());
            }
        });

        return rootView;
    }

}
