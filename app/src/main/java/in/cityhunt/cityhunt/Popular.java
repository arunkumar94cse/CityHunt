package in.cityhunt.cityhunt;

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

        RecyclerView popular_list = (RecyclerView) layout.findViewById(R.id.popular_view);
        popular_list.setHasFixedSize(true);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        popular_list.setLayoutManager(mLayoutManager);

        RecyclerView.Adapter mAdapter = new CustomListAdapter(datas,getActivity());
        popular_list.setAdapter(mAdapter);
        return layout;
    }
}
