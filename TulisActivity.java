package comp.example.suutmulyono.latihan4;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.OpenableColumns;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import comp.example.suutmulyono.latihan4.adapter.AdapterFile;
import comp.example.suutmulyono.latihan4.data.Data;
import comp.example.suutmulyono.latihan4.sqliteDatabase.DbHelper;

public class TulisActivity extends AppCompatActivity {

    AlertDialog.Builder dialog;

    private static int RESULT_LOAD_IMG = 1;
    private static final int READ_REQUEST_CODE = 42;

    private static final String TAG = TulisActivity.class.getSimpleName();

    private ArrayList<String> dataSet = new ArrayList<>();

    DbHelper SQlite = new DbHelper(this);
    List<Data> fileList = new ArrayList<Data>();
    ListView listView;
    AdapterFile adapterFile;

    String[] cities = {
            "suut.sukses@gmail.com",
            "suut@rockdut.com"
    };

    public static final String TAG_ID = "id";
    public static final String TAG_NAME = "name";
    public static final String TAG_SIZE = "size";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tulis);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        SQlite = new DbHelper(getApplicationContext());

        // Spinner element
        final Spinner spinner = findViewById(R.id.spinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, cities);

        spinner.setAdapter(adapter);

        listView = findViewById(R.id.data_file);
        adapterFile = new AdapterFile(TulisActivity.this, fileList);
        listView.setAdapter(adapterFile);
        Log.e("array ", String.valueOf(dataSet));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_setting, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            startActivity(new Intent(this, MainActivity.class));
        } else if (id == R.id.action_search) {
            Toast.makeText(getBaseContext(), "On going,,, ", Toast.LENGTH_SHORT).show();
        } else if (id == R.id.action_file) {
            ChoseFile();
        } else if (id == R.id.action_send) {
            Toast.makeText(getBaseContext(), "On going,,, ", Toast.LENGTH_SHORT).show();
        }

        return super.onOptionsItemSelected(item);
    }

    private void ChoseFile() {
        final CharSequence[] dialogitem = {"Lampirkan File", "Sisipkan dari Drive"};
        dialog = new AlertDialog.Builder(TulisActivity.this);
        dialog.setCancelable(true);
        dialog.setItems(dialogitem, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case 0:
                        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
                        intent.addCategory(Intent.CATEGORY_OPENABLE);
                        intent.setType("*/*");

                        startActivityForResult(intent, READ_REQUEST_CODE);
                        break;
                    case 1:
                        Toast.makeText(getBaseContext(), "On going,,, ", Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        });
        AlertDialog builder = dialog.create();
        WindowManager.LayoutParams ml = builder.getWindow().getAttributes();
        ml.gravity = Gravity.TOP | Gravity.RIGHT;
        builder.getWindow().setLayout(60, 60);
        builder.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == READ_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            // The document selected by the user won't be returned in the intent.
            // Instead, a URI to that document will be contained in the return intent
            // provided to this method as a parameter.
            // Pull that URI using resultData.getData().
            Uri uri = null;
            if (data != null) {
                uri = data.getData();
                Log.e(TAG, "Uri: " + uri.toString());
                showFile(uri);
            }
        }
    }

    private String showFile(Uri uri) {
        String result = null;
        String nama = null;
        if (uri.getScheme().equals("content")) {
            Cursor cursor = getContentResolver().query(uri, null, null, null, null);

            try {
                if (cursor != null && cursor.moveToFirst()) {
                    result = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));
                    nama = cursor.getString(cursor.getColumnIndex(OpenableColumns.SIZE));
                }
            } finally {
                cursor.close();
            }
        }
        if (result == null) {
            result = uri.getPath();
            int cut = result.lastIndexOf('/');
            if (cut != -1) {
                result = result.substring(cut + 1);
            }
        }

        Log.e("name ", result);
        nameFile(result, nama);
        return result;
    }

    private void nameFile(String result, String nama) {

        dataSet.add(result);
        dataSet.add(nama);

        SQlite.insert(result.trim(), nama.trim());
    }

    private void getAllData() {
        ArrayList<HashMap<String, String>> row = SQlite.getAlldata();

        for (int i = 0; i < row.size(); i++) {
            String id = row.get(i).get(TAG_ID);
            String name = row.get(i).get(TAG_NAME);
            String size = row.get(i).get(TAG_SIZE);

            Data data = new Data();
            data.setId(id);
            data.setName(name);
            data.setSize(size);

            fileList.add(data);
        }
        adapterFile.notifyDataSetChanged();
    }

    @Override
    protected void onResume() {
        super.onResume();
        fileList.clear();
        getAllData();
    }

}
