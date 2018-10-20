package comp.example.suutmulyono.latihan4.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import comp.example.suutmulyono.latihan4.R;

/**
 * Created by suut mulyono on 9/29/2018.
 */

public class AdapterFile extends BaseAdapter{

    private List<String> data;
    private Activity activity;
    private LayoutInflater inflater;

    public AdapterFile(Activity activity, ArrayList<String> data) {
        this.activity = activity;
        this.data = data;
    }


    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int location) {
        return data.get(location);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (inflater == null)
            inflater = (LayoutInflater) activity
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (convertView == null)
            convertView = inflater.inflate(R.layout.file, null);

        TextView file = (TextView) convertView.findViewById(R.id.name_file);
        file.setText(data.get(position));
        return convertView;
    }
}
