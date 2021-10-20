package com.romdon.formsregistrasi.ui;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.romdon.formsregistrasi.R;
import com.romdon.formsregistrasi.db.DbHelper;
import com.romdon.formsregistrasi.model.RegisterData;

import java.util.List;

public class ResultActivity extends AppCompatActivity {
    private RecyclerView listData;
    private final RegisterDataAdapter adapter = new RegisterDataAdapter();
    private final DbHelper dbHelper = DbHelper.getInstance(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        listData = findViewById(R.id.listdata);

        initList();

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("Hasil Registrasi");
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void initList() {
        List<RegisterData> listRegisteredData = dbHelper.findAllData();
        Log.d("ResultActivity", listRegisteredData.get(0).getPhoto());
        listData.setAdapter(adapter);
        adapter.setData(listRegisteredData);
    }
}