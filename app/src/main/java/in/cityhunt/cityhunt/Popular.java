package in.cityhunt.cityhunt;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

public class Popular extends Fragment {

    String url = "http://cityhunt.in/cityhunt/events/getEventsList";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.fragment_popular, container, false);

        RecyclerView popular_list = (RecyclerView) layout.findViewById(R.id.popular_view);
        popular_list.setHasFixedSize(true);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        popular_list.setLayoutManager(mLayoutManager);

        String[] arr = new String[]{"one","two","three","four","five"};

        RecyclerView.Adapter mAdapter = new CustomListAdapter(arr);
        popular_list.setAdapter(mAdapter);
        RequestQueue queue = Volley.newRequestQueue(getActivity());
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e("response",response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("resp",error.getMessage());
            }
        });
        queue.add(stringRequest);
        return layout;
    }
}
