package comp.example.suutmulyono.latihan4;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import comp.example.suutmulyono.latihan4.adapter.Adapter;

public class EmailFragment extends Fragment {

    private RecyclerView rv;
    private RecyclerView.LayoutManager layoutManager;
    private ArrayList<String> dataSet;

    public EmailFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_email, container, false);
        rv = (RecyclerView) view.findViewById(R.id.email_list);

        layoutManager = new LinearLayoutManager(this.getActivity());
        rv.setLayoutManager(layoutManager);

        initDataset();

        RecyclerView.Adapter adapter = new Adapter(dataSet);
        rv.setAdapter(adapter);

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        getActivity().setTitle("Utama");
    }

    private void initDataset(){

        //data yang akan di tampilkan
        dataSet = new ArrayList<>();
        dataSet.add("Berkas 1");
        dataSet.add("Berkas 2");
        dataSet.add("Berkas 3");
        dataSet.add("Berkas 4");
        dataSet.add("Berkas 5");

    }
}
