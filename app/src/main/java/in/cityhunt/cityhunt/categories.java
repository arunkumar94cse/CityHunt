package in.cityhunt.cityhunt;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
public class categories extends Fragment implements View.OnClickListener {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.fragment_categories, container, false);

        layout.findViewById(R.id.nearby).setOnClickListener(this);
        layout.findViewById(R.id.education).setOnClickListener(this);
        layout.findViewById(R.id.business).setOnClickListener(this);
        layout.findViewById(R.id.sports).setOnClickListener(this);
        layout.findViewById(R.id.entertainment).setOnClickListener(this);
        layout.findViewById(R.id.culturals).setOnClickListener(this);
        layout.findViewById(R.id.parties).setOnClickListener(this);
        return layout;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.nearby:
                startActivity(new Intent(getActivity(),NearBy.class));
                break;
            case R.id.education:
                startActivity(new Intent(getActivity(),Category_Events.class).putExtra("type","Education"));
                break;
            case R.id.business:
                startActivity(new Intent(getActivity(),Category_Events.class).putExtra("type","Business"));
                break;
            case R.id.sports:
                startActivity(new Intent(getActivity(),Category_Events.class).putExtra("type","Sports"));
                break;
            case R.id.culturals:
                startActivity(new Intent(getActivity(),Category_Events.class).putExtra("type","Culturals"));
                break;
            case R.id.entertainment:
                startActivity(new Intent(getActivity(),Category_Events.class).putExtra("type","Entertainment"));
                break;
            case R.id.parties:
                startActivity(new Intent(getActivity(),Category_Events.class).putExtra("type","Parties"));
                break;
        }
    }
}
