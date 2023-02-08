package com.example.trash_2;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.content.res.AssetManager;
import android.widget.Button;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class SearchFragment extends Fragment {
    TextView nameView, partView, dischargeView;
    String s = "";
    String searchResult;
    Button button;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.search_fragment, container, false);
        nameView = (TextView)rootView.findViewById(R.id.nameView);
        partView = (TextView)rootView.findViewById(R.id.partView);
        dischargeView = (TextView)rootView.findViewById(R.id.dischargeView);

        button = (Button) rootView.findViewById(R.id.back);
        button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                MainActivity mainActivity = (MainActivity) getActivity();
                mainActivity.onFragmentChange(0,null);

            }
        });

        if(getArguments()!=null) {
            searchResult = getArguments().getString("Name");
        }

        AssetManager assetManager = getActivity().getAssets();
        try {
            InputStream is = assetManager.open("recycle.json");
            InputStreamReader isr = new InputStreamReader(is);
            BufferedReader reader = new BufferedReader(isr);

            StringBuffer buffer = new StringBuffer();
            String line = reader.readLine();

            while(line != null) {
                buffer.append(line + "\n");
                line = reader.readLine();
            }
            String jsonData = buffer.toString();

            JSONArray jsonArray = new JSONArray(jsonData);

            for(int i=0; i<jsonArray.length(); i++) {
                JSONObject jo = jsonArray.getJSONObject(i);
                String trashName = jo.getString("품목");
                String part = jo.getString("구분");
                String discharge = jo.getString("배출방법");

                if(trashName.contains(searchResult)) {
                    nameView.setText(trashName);
                    partView.setText(part);
                    dischargeView.setText(discharge);
                }
            }
        } catch(IOException e) {
            e.printStackTrace();
        } catch(JSONException e) {
            e.printStackTrace();
        }

        return rootView;
    }

}