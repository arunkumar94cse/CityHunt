package in.cityhunt.cityhunt;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class CustomListAdapter extends RecyclerView.Adapter<CustomListAdapter.ViewHolder> {

    private List<Data> datas;
    private Context context;

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView poster;
        private TextView name,city,start_date,id;

        public ViewHolder(View v) {
            super(v);
            this.id = (TextView)v.findViewById(R.id.event_id);
            this.poster = (ImageView)v.findViewById(R.id.poster);
            this.name = (TextView)v.findViewById(R.id.title);
            this.start_date = (TextView)v.findViewById(R.id.date);
            this.city = (TextView)v.findViewById(R.id.city);
        }
    }

    public CustomListAdapter(List<Data> datas,Context context) {
        this.datas = datas;
        this.context = context;
    }

    @Override
    public CustomListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.events_view, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Data data = datas.get(position);
        holder.name.setText(data.getName());
        holder.city.setText(data.getCity());
        holder.id.setText(String.valueOf(data.getId()));
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:MM:SS", Locale.ENGLISH);
        SimpleDateFormat newformat = new SimpleDateFormat("MMM''dd", Locale.ENGLISH);
        try {
            Date date = format.parse(data.getStart_date());
            holder.start_date.setText(newformat.format(date).toUpperCase());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        ImageLoader mImageLoader = Mysingleton.getInstance(context).getImageLoader();
        mImageLoader.get(Utilities.HOME_URL + data.getPoster_url(),
                ImageLoader.getImageListener(holder.poster, R.drawable.posternew, R.drawable.posternew));
    }

    @Override
    public int getItemCount() {
        return datas.size();
    }
}
