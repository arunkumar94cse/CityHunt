package in.cityhunt.cityhunt;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class Popular extends Fragment {

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
        return layout;
    }
}
