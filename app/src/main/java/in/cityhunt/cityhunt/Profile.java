package in.cityhunt.cityhunt;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;

import org.json.JSONObject;

import in.cityhunt.cityhunt.datas.SharedPreferencesData;

public class Profile extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_profile, container, false);
        TextView name = (TextView)root.findViewById(R.id.name);
        ImageView profile_pic = (ImageView)root.findViewById(R.id.profile_pic);
        SharedPreferencesData data = new SharedPreferencesData(getActivity());
        JSONObject jdata = data.getLoginDetails();
        name.setText(jdata.optString(SharedPreferencesData.NAME));
        ImageLoader mImageLoader = Mysingleton.getInstance(getActivity()).getImageLoader();
        mImageLoader.get(jdata.optString(SharedPreferencesData.PROFILE_PIC_PATH),
                ImageLoader.getImageListener(profile_pic,R.drawable.com_facebook_profile_picture_blank_square,
                        R.drawable.com_facebook_profile_picture_blank_square));
        Button logout = (Button)root.findViewById(R.id.logout);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new SharedPreferencesData(getActivity()).logout();
                startActivity(new Intent(getActivity(),LoginActivity.class));
                getActivity().finish();
            }
        });
        return root;
    }
}
