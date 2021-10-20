package com.romdon.formsregistrasi.ui;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.romdon.formsregistrasi.R;
import com.romdon.formsregistrasi.db.DbHelper;
import com.romdon.formsregistrasi.model.RegisterData;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public class FormActivity extends AppCompatActivity {
    private TextView tvLocation;
    private EditText ednama, edalamat, ednohp;
    private ImageView imgFoto;
    private RadioGroup rgGender;

    private Uri imageUri;

    private final DbHelper dbHelper = DbHelper.getInstance(this);

    private final String[] locationPerms = new String[]{
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION
    };

    private final String openGalleryPerms = Manifest.permission.READ_EXTERNAL_STORAGE;

    private FusedLocationProviderClient locationClient;

    ActivityResultLauncher<String> mGetContent = registerForActivityResult(new ActivityResultContracts.GetContent(),
            uri -> {
                if (imgFoto != null) {
                    this.imageUri = uri;

                    Glide.with(this)
                            .load(uri)
                            .into(imgFoto);
                }
            });

    ActivityResultLauncher<String[]> mGetLocation = registerForActivityResult(new ActivityResultContracts.RequestMultiplePermissions(),
            permissions -> {
                for (Map.Entry<String, Boolean> entry : permissions.entrySet()) {
                    if (entry.getKey().equals(Manifest.permission.ACCESS_FINE_LOCATION) && entry.getValue()
                            || entry.getKey().equals(Manifest.permission.ACCESS_COARSE_LOCATION) && entry.getValue()) {
                        getCurrentLocation();
                        return;
                    }
                }
            });

    ActivityResultLauncher<String> mGetImagePermission = registerForActivityResult(new ActivityResultContracts.RequestPermission(),
            granted -> {
                if (granted) {
                    this.mGetContent.launch("image/*");
                }
            });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form);

        ednama = findViewById(R.id.Ednama);
        edalamat = findViewById(R.id.Edalamat);
        ednohp = findViewById(R.id.ednomor);
        imgFoto = findViewById(R.id.imgupload);
        rgGender = findViewById(R.id.jeniskelamin);
        tvLocation = findViewById(R.id.tvresultlocation);

        Button btnlokasi = findViewById(R.id.btnlokasi);
        Button btnupload = findViewById(R.id.btnupload);
        Button btnsubmit = findViewById(R.id.btnsubmit);

        locationClient = LocationServices.getFusedLocationProviderClient(this);

        btnlokasi.setOnClickListener(v -> mGetLocation.launch(locationPerms));
        btnupload.setOnClickListener(v -> mGetImagePermission.launch(openGalleryPerms));
        btnsubmit.setOnClickListener(v -> submitForm());
    }

    private void submitForm() {
        String name = ednama.getText().toString().trim();
        String address = edalamat.getText().toString().trim();
        String phoneNumber = ednohp.getText().toString().trim();
        String location = tvLocation.getText().toString().trim();
        String photo = imageUri.toString();

        int genderId = rgGender.getCheckedRadioButtonId();
        RadioButton rb = findViewById(genderId);
        String gender = rb.getText().toString().trim();

        RegisterData registerData = new RegisterData(
                name,
                address,
                photo,
                location,
                gender,
                phoneNumber
        );

        dbHelper.insert(registerData);

        navigateToResult();
    }

    private void navigateToResult() {
        Intent intent = new Intent(FormActivity.this, ResultActivity.class);
        startActivity(intent);
    }

    @SuppressLint("MissingPermission")
    private void getCurrentLocation() {
        locationClient.getLastLocation().addOnSuccessListener(location -> {
            try {
                Geocoder geocoder = new Geocoder(this);
                List<Address> currentLocation = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
                String local = currentLocation.get(0).getSubLocality();
                tvLocation.setText(local);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }
}