package comp.example.suutmulyono.latihan4;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.OpenableColumns;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;

import comp.example.suutmulyono.latihan4.adapter.AdapterFile;

public class TulisActivity extends AppCompatActivity {

    AlertDialog.Builder dialog;

    private static int RESULT_LOAD_IMG = 1;
    private static final int READ_REQUEST_CODE = 42;

    private static final String TAG = TulisActivity.class.getSimpleName();

    private ArrayList<String> dataSet;
    ListView listView;
    AdapterFile adapterFile;

    String[] cities = {
            "suut.sukses@gmail.com",
            "suut@rockdut.com"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tulis);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Spinner element
        final Spinner spinner = (Spinner) findViewById(R.id.spinner);
        ArrayAdapter<String> adapter= new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, cities);

        spinner.setAdapter(adapter);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_setting, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home){
            startActivity(new Intent(this, MainActivity.class));
        }else if (id == R.id.action_search){
            Toast.makeText(getBaseContext(),"On going,,, ", Toast.LENGTH_SHORT).show();
        }else if (id == R.id.action_file){
            ChoseFile();
        }else if (id == R.id.action_send){
            Toast.makeText(getBaseContext(),"On going,,, ",Toast.LENGTH_SHORT).show();
        }

        return super.onOptionsItemSelected(item);
    }

    private void ChoseFile() {
        final CharSequence[] dialogitem = {"Lampirkan File","Sisipkan dari Drive"};
        dialog = new AlertDialog.Builder(TulisActivity.this);
        dialog.setCancelable(true);
        dialog.setItems(dialogitem, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which){
                    case  0 :
                        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
                        intent.addCategory(Intent.CATEGORY_OPENABLE);
                        intent.setType("*/*");

                        startActivityForResult(intent, READ_REQUEST_CODE);
                        break;
                    case 1:
                        Toast.makeText(getBaseContext(),"On going,,, ",Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        });
        AlertDialog builder = dialog.create();
        WindowManager.LayoutParams ml = builder.getWindow().getAttributes();
        ml.gravity = Gravity.TOP | Gravity.RIGHT;
        builder.getWindow().setLayout(60,60);
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
                showImage(uri);
            }
        }    
    }

    private String showImage(Uri uri) {
        String result = null;
        if (uri.getScheme().equals("content")){
            Cursor cursor = getContentResolver().query(uri, null, null, null, null);

            try {
                if (cursor != null && cursor.moveToFirst()) {
                    result = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));
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

        Log.e("name ",result);
        nameFile(result);
        return result;
    }

    private void nameFile(String result) {
        dataSet = new ArrayList<>();
        dataSet.add(result);

        listView = (ListView) findViewById(R.id.data_file);
        adapterFile = new AdapterFile(TulisActivity.this, dataSet);
        listView.setAdapter(adapterFile);
        Log.e("array ", String.valueOf(dataSet));
    }

}
