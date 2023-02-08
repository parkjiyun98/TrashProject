package com.example.trash_2;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.widget.Button;
import android.widget.EditText;

public class Fragment2 extends Fragment implements View.OnClickListener {
    public static Fragment2 newInstance() {
        return new Fragment2();
    }

    Button button;
    EditText editText;
    String s;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment2, container, false);

        button = (Button) rootView.findViewById(R.id.buttonSearch2);
        editText = (EditText) rootView.findViewById(R.id.editTextQuery2);
        button.setOnClickListener(this);
        editText.setOnClickListener(this);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                s = editText.getText().toString();
                MainActivity mainActivity = (MainActivity) getActivity();
                mainActivity.onFragmentChange(1, s);

            }
        });
        Button buttonPaper = (Button) rootView.findViewById(R.id.paper);
        buttonPaper.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //getActivity()로 MainActivity의 replaceFragment를 불러온다.
                ((MainActivity) getActivity()).replaceFragment(PaperFragment.newInstance());
            }
        });

        Button buttonPackandcup = (Button) rootView.findViewById(R.id.packAndCup);
        buttonPackandcup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //getActivity()로 MainActivity의 replaceFragment를 불러온다.
                ((MainActivity) getActivity()).replaceFragment(PackandcupFragment.newInstance());
            }
        });

        Button buttonCanandscrap = (Button) rootView.findViewById(R.id.canAndScrap);
        buttonCanandscrap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //getActivity()로 MainActivity의 replaceFragment를 불러온다.
                ((MainActivity) getActivity()).replaceFragment(CanandscrapFragment.newInstance());
            }
        });

        Button buttonGlass = (Button) rootView.findViewById(R.id.glass);
        buttonGlass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //getActivity()로 MainActivity의 replaceFragment를 불러온다.
                ((MainActivity) getActivity()).replaceFragment(GlassFragment.newInstance());
            }
        });

        Button buttonPetplasticvinyl = (Button) rootView.findViewById(R.id.petPlasticVinyl);
        buttonPetplasticvinyl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //getActivity()로 MainActivity의 replaceFragment를 불러온다.
                ((MainActivity) getActivity()).replaceFragment(PetplasticvinylFragment.newInstance());
            }
        });

        Button buttonStyrofoam = (Button) rootView.findViewById(R.id.styrofoam);
        buttonStyrofoam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //getActivity()로 MainActivity의 replaceFragment를 불러온다.
                ((MainActivity) getActivity()).replaceFragment(StyrofoamFragment.newInstance());
            }
        });

        Button buttonAppliance = (Button) rootView.findViewById(R.id.appliance);
        buttonAppliance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //getActivity()로 MainActivity의 replaceFragment를 불러온다.
                ((MainActivity) getActivity()).replaceFragment(ApplianceFragment.newInstance());
            }
        });

        Button buttonFoodwaste = (Button) rootView.findViewById(R.id.foodWaste);
        buttonFoodwaste.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //getActivity()로 MainActivity의 replaceFragment를 불러온다.
                ((MainActivity) getActivity()).replaceFragment(FoodwasteFragment.newInstance());
            }
        });

        Button buttonHazardouswaste = (Button) rootView.findViewById(R.id.hazardousWaste);
        buttonHazardouswaste.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //getActivity()로 MainActivity의 replaceFragment를 불러온다.
                ((MainActivity) getActivity()).replaceFragment(HazardouswasteFragment.newInstance());
            }
        });

        Button buttonEtc = (Button) rootView.findViewById(R.id.etc);
        buttonEtc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //getActivity()로 MainActivity의 replaceFragment를 불러온다.
                ((MainActivity) getActivity()).replaceFragment(EtcFragment.newInstance());
            }
        });

        return rootView;
    }

    @Override
    public void onClick(View v) {


    }
}
