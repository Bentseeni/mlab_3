package com.example.mlab_3;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class competitions extends AppCompatActivity {

    ArrayList<String> arrayList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_competitions);

        String id = getIntent().getStringExtra("id");
        Toast.makeText(getApplicationContext(),id, Toast.LENGTH_LONG).show();


        final ListView listView = findViewById(R.id.competition_list);
        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,arrayList);

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        String urlSring = "http://api.football-data.org/v2/competitions?areas="+id;
        Toast.makeText(getApplicationContext(),urlSring, Toast.LENGTH_LONG).show();


        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, urlSring, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray jsonArray = response.getJSONArray("competitions");

                    for (int i = 0;i<jsonArray.length();i++)
                    {
                        arrayList.add(jsonArray.getJSONObject(i).getString("name"));
                       // Toast.makeText(getApplicationContext(),jsonArray.getJSONObject(i).getString("name"), Toast.LENGTH_LONG).show();

                    }
                } catch (JSONException e) {
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
    }
}
