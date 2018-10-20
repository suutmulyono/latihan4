package comp.example.suutmulyono.latihan4.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;

import comp.example.suutmulyono.latihan4.R;

/**
 * Created by suut mulyono on 9/27/2018.
 */

public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder>{

    private ArrayList<String> data;

    public Adapter(ArrayList<String> data) {
        this.data = data;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // membuat view baru
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_email, parent, false);
        // mengeset ukuran view, margin, padding, dan parameter layout lainnya
        ViewHolder vh = new ViewHolder(view);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.email.setText(data.get(position));
        holder.icon.setText("B");
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView email, icon;

        public ViewHolder(View itemView) {
            super(itemView);

            icon  = (TextView) itemView.findViewById(R.id.icon);
            email = (TextView) itemView.findViewById(R.id.job);
        }
    }
}
