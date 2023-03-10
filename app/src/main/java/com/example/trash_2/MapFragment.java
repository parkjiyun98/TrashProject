package com.example.trash_2;

import android.Manifest;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import net.daum.mf.map.api.MapPOIItem;
import net.daum.mf.map.api.MapPoint;
import net.daum.mf.map.api.MapView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.Charset;

public class MapFragment extends Fragment implements View.OnClickListener{
    static final int PERMISSIONS_REQUEST = 0x0000001;//요청 권한이 한개인 경우.끝

    MapView mapView;
    MapPoint mapPoint;
    ViewGroup mapViewContainer;
    Button buttonSearch;
    Boolean test;
    EditText editText;
    FloatingActionButton location_button;

    String result1 = null;   //카카오에서 응답으로 받아온 결과
    String result2 = null;  //StringBuffer에 있는 값을 JSON으로 변환한 결과
    String coordinate = null;
    String x = null;
    String y = null;

    private static String GEOCODE_URL="http://dapi.kakao.com/v2/local/search/keyword.json?query=";
    private static String GEOCODE_USER_INFO="KakaoAK privateKey";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_map, container, false);


        //지도 표현
        mapView = new MapView(getActivity());
        mapViewContainer = (ViewGroup) rootView.findViewById(R.id.map_view);
        mapViewContainer.addView(mapView);
        //mapView.setCurrentLocationTrackingMode(MapView.CurrentLocationTrackingMode.TrackingModeOnWithoutHeading); //나침반 모드 꺼

        //검색 버튼
        buttonSearch = (Button) rootView.findViewById(R.id.buttonSearch);
        editText = (EditText)rootView.findViewById(R.id.editTextQuery);
        buttonSearch.setOnClickListener(this);
        editText.setOnClickListener(this);

        //플로팅 버튼
        location_button = (FloatingActionButton) rootView.findViewById(R.id.location_button);
        location_button.setOnClickListener(this);

        ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION);

        //데이터 입력
        AssetManager assetManager = getActivity().getAssets();
        try {
            InputStream is = assetManager.open("trashcan.json");
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
            String s = "";
            test = true; //쓰레기통 마커 표시를 위해 true 설정

            for(int i=0; i<jsonArray.length(); i++) {
                s = "";
                JSONObject jo = jsonArray.getJSONObject(i);

                String add1 = jo.getString("num");
                String add2 = jo.getString("gu");
                String add3 = jo.getString("detail");

                s += add1 + " " + add2 + " " + add3;
                addToCoordinate(s, test);

                /*
                JSONObject jo = jsonArray.getJSONObject(i);
                s = jo.getString("detail");
                addToCoordinate(s, test);

                 */
            }
        } catch(IOException e) {
            e.printStackTrace();
        } catch(JSONException e) {
            e.printStackTrace();
        }

        return rootView;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,@NonNull String[] permissions, @NonNull int[] grantResults){
        switch (requestCode){ //실행 후 전달받을 코드!
            case PERMISSIONS_REQUEST:
                if (grantResults.length>0 && grantResults[0]==PackageManager.PERMISSION_GRANTED){//권한이 granted
                    Toast.makeText(getActivity(), "위치 권한이 부여되었습니다.", Toast.LENGTH_LONG).show();
                }//권한 허가된 경우
                else{
                    Toast.makeText(getActivity(), "위치 권한이 취소되었습니다.", Toast.LENGTH_LONG).show();}//권한 거절된 경우
                break;//switch문

        }
    }


    public void onClick(View view) {
        switch (view.getId()){
            case R.id.buttonSearch:
                if(ContextCompat.checkSelfPermission(getActivity(),Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
                        || ContextCompat.checkSelfPermission(getActivity(),Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED){
                    test = false;
                    addToCoordinate(editText.getText().toString(), test);
                    mapView.setCurrentLocationTrackingMode(MapView.CurrentLocationTrackingMode.TrackingModeOff);
                    break;
                }
                else{
                    test = false;
                    addToCoordinate(editText.getText().toString(), test);
                    break;

                }

            case R.id.location_button:
                if(ContextCompat.checkSelfPermission(getActivity(),Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
                        || ContextCompat.checkSelfPermission(getActivity(),Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED){
                    test = false;
                    addToCoordinate(editText.getText().toString(), test);
                    mapView.setCurrentLocationTrackingMode(MapView.CurrentLocationTrackingMode.TrackingModeOnWithoutHeading);
                    break;
                }
                else{
                    test = false;
                    addToCoordinate(editText.getText().toString(), test);
                    Toast.makeText(getActivity(), "위치 권한을 거부하여 사용할 수 없는 기능입니다.\n [설정]에서 위치 권한을 허용해주세요.", Toast.LENGTH_LONG).show();
                    break;

                }

        }
    }


    public void addToCoordinate(String s, Boolean test) {
        Thread thread = new Thread() {
            @Override
            public void run() {
                URL obj;
                try{
                    String address = URLEncoder.encode(s, "UTF-8");
                    obj = new URL(GEOCODE_URL+address);
                    HttpURLConnection con = (HttpURLConnection)obj.openConnection();
                    con.setRequestMethod("GET");
                    con.setRequestProperty("Authorization",GEOCODE_USER_INFO);
                    con.setRequestProperty("content-type", "application/json");
                    con.setDoOutput(true);
                    con.setUseCaches(false);
                    con.setDefaultUseCaches(false);
                    Charset charset = Charset.forName("UTF-8");
                    BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream(), charset));
                    String inputLine;
                    StringBuffer response = new StringBuffer();
                    while ((inputLine = in.readLine()) != null) { response.append(inputLine); }
                    result1 = response.toString();
                    JSONObject json1 = new JSONObject(result1);
                    result2 = json1.getString("documents");

                    JSONArray jsonArray = new JSONArray(result2);
                    for(int i = 0; i<jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        x = jsonObject.getString("x");
                        y = jsonObject.getString("y");
                        coordinate = x + " " + y;
                        if(test){ //쓰레기통 마커를 위해 bol이 true라면 (기본으로 생성, true로 설정했기 때문에)
                            marker(coordinate);
                        } else { //검색한 장소 x ,y 좌표를 이용하여 찾아서 지도 이동
                            mapView.setMapCenterPoint(MapPoint.mapPointWithGeoCoord(Double.valueOf(y), Double.valueOf(x)), true);
                        }
                    }
                } catch (Exception e) { e.printStackTrace(); }
            }
        };
        thread.setDaemon(true);  // 메인스레드와 종료 동기화
        thread.start();     // 작업스레드 시작 -> run() 이 작업스레드로 실행됨
    }

    public void marker(String s) {
        String array[] = s.split(" ");
        mapPoint = MapPoint.mapPointWithGeoCoord(Double.valueOf(array[1]), Double.valueOf(array[0]));
        mapView.setMapCenterPoint(mapPoint, true);

        MapPOIItem mapPOIItem = new MapPOIItem();
        mapPOIItem.setItemName(s);
        mapPOIItem.setTag(0);
        mapPOIItem.setMapPoint(mapPoint);
        mapPOIItem.setMarkerType(MapPOIItem.MarkerType.BluePin);
        mapPOIItem.setSelectedMarkerType(MapPOIItem.MarkerType.RedPin);
        mapView.addPOIItem(mapPOIItem);
    }

}

