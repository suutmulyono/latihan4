package comp.example.suutmulyono.latihan4.adapter;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import comp.example.suutmulyono.latihan4.R;
import comp.example.suutmulyono.latihan4.data.Data;
import comp.example.suutmulyono.latihan4.sqliteDatabase.DbHelper;

/**
 * Created by suut mulyono on 9/29/2018.
 */

public class AdapterFile extends BaseAdapter {

    private List<Data> data;
    private Activity activity;
    private LayoutInflater inflater;

    DbHelper SQlite;

    public AdapterFile(Activity activity, List<Data> data) {
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
    public View getView(final int position, View convertView, ViewGroup parent) {
        if (inflater == null)
            inflater = (LayoutInflater) activity
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (convertView == null)
            convertView = inflater.inflate(R.layout.file, null);

        final TextView id = convertView.findViewById(R.id.id_file);
        final TextView file = convertView.findViewById(R.id.name_file);
        final TextView size = convertView.findViewById(R.id.size_file);
        Button btn_del = convertView.findViewById(R.id.delete_file);

        final Data name = data.get(position);
        int sz = Integer.parseInt(name.getSize());
        double m = sz/1024.0;
        DecimalFormat dec = new DecimalFormat("0.00");

        if (m > 1024){
            String mg = dec.format(m/1024.0).concat(" MB");
            size.setText(mg);
        }else {
            String kb = dec.format(m).concat(" KB");
            size.setText(kb);
        }

        id.setText(name.getId());
        file.setText(name.getName());

        final View finalConvertView = convertView;
        final DbHelper SQlite = new DbHelper(convertView.getContext());

        btn_del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(finalConvertView.getContext(), "test" + name.getId(), Toast.LENGTH_SHORT).show();
                Log.e("id", String.valueOf(name.getId()));
                data.remove(position);
                SQlite.delete(Integer.parseInt(String.valueOf(name.getId())));
                notifyDataSetChanged();
            }
        });

        return convertView;
    }
}
