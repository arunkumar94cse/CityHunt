package in.cityhunt.cityhunt;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import in.cityhunt.cityhunt.datas.EventStorage;

public class Popular extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.fragment_popular, container, false);

        EventStorage storage = new EventStorage(getActivity());

        List<Data> datas = new ArrayList<>();

        Cursor cursor = storage.getDataRecent();

        cursor.moveToFirst();

        for (int i=0;i<cursor.getCount();i++){
            Data data = new Data();
            data.setId(cursor.getInt(0));
            data.setName(cursor.getString(1));
            data.setCity(cursor.getString(3));
            data.setStart_date(cursor.getString(14));
            data.setPoster_url(cursor.getString(16));
            datas.add(data);
            cursor.moveToNext();
        }

        layout.findViewById(R.id.fab).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(getActivity(),Filters.class),1);
            }
        });

        RecyclerView popular_list = (RecyclerView) layout.findViewById(R.id.popular_view);
        popular_list.setHasFixedSize(true);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        popular_list.setLayoutManager(mLayoutManager);

        RecyclerView.Adapter mAdapter = new CustomListAdapter(datas,getActivity());
        popular_list.setAdapter(mAdapter);
        return layout;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }
}
