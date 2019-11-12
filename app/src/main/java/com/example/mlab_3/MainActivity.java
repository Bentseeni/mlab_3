package com.example.mlab_3;

import android.content.Intent;
import android.media.MediaSync;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ArrayList<JSONObject> arrayList = new ArrayList<>();
    ArrayList<String> arrayListName = new ArrayList<>();
    JSONArray jsonArray = new JSONArray();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        final ListView listView = findViewById(R.id.area_list);
        final ArrayAdapter <String> arrayAdapter = new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,arrayListName);
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        String urlSring = "http://api.football-data.org/v2/areas";

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, urlSring, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {

                         jsonArray = response.getJSONArray("areas");
                        //array.getJSONObject(0).getString("name");

                        for(int i = 0;i<jsonArray.length();i++){
                            arrayList.add(jsonArray.getJSONObject(i));
                            arrayListName.add(jsonArray.getJSONObject(i).getString("name"));
                        }
                       // arrayList.add(array.getJSONObject(0));
                        //JSONObject c = array.getJSONObject(0);
                        //String areaName = c.getString("name");
                        //Toast.makeText(getApplicationContext(),areaName+"asd",Toast.LENGTH_SHORT).show();



                }catch (JSONException e)
                {
                    e.printStackTrace();
                }
                listView.setAdapter(arrayAdapter);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        requestQueue.add(jsonObjectRequest);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                int x = parent.indexOfChild(view);
                Intent intent = new Intent(MainActivity.this,competitions.class);

                try {
                    intent.putExtra("id",jsonArray.getJSONObject(position).getString("id"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                startActivity(intent);
            }
        });

    }
}