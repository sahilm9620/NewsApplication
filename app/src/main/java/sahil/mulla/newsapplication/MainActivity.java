package sahil.mulla.newsapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {
    RecyclerView mRecyclerview;
    RecyclerView.LayoutManager mLayoutManager;
    ArrayList<HashMap<String, String>> arrayListNews;
    RecyclerView.Adapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRecyclerview = findViewById(R.id.recycleview);
        mLayoutManager = new LinearLayoutManager(MainActivity.this);
        mRecyclerview.setLayoutManager(mLayoutManager);
        callServerData();

    }

    private void callServerData() {

// Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "https://chandansatyendraprasad.000webhostapp.com/tests";

// Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Display the first 500 characters of the response string.
                        Toast.makeText(MainActivity.this, "Response is: " + response.substring(0, 500), Toast.LENGTH_SHORT).show();
                        parseing_Server_Response(response);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity.this, "That didn't work!", Toast.LENGTH_SHORT).show();

            }
        });

// Add the request to the RequestQueue.
        queue.add(stringRequest);
    }

    private void parseing_Server_Response(String response) {
        try {
            JSONObject jsonObject = new JSONObject(response);
            JSONArray arrayHeadlines = jsonObject.getJSONArray("headlines");
            arrayListNews = new ArrayList<>();
            for (int i = 0; i < arrayHeadlines.length(); i++) {
                JSONObject objItem = arrayHeadlines.getJSONObject(i);

                String title = objItem.getString("title");
                String imgUrl = objItem.getString("imgUrl");
                String description = objItem.getString("description");

                HashMap<String, String> map = new HashMap<>();
                map.put("title", title);
                map.put("url", imgUrl);
                map.put("detail", description);
                arrayListNews.add(map);
            }
            //set adapter
            mAdapter = new HomeListAdapter(MainActivity.this, arrayListNews);
            mRecyclerview.setAdapter(mAdapter);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}