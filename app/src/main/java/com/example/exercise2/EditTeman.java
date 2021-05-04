package com.example.exercise2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.exercise2.database.DBController;
import com.google.android.material.textfield.TextInputEditText;

import java.util.HashMap;

public class EditTeman extends AppCompatActivity {
    private TextInputEditText edNama, edTelpon;
    private Button btnSimpan;
    String id, nama, telpon;

    DBController controller = new DBController(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_teman);

        edNama = findViewById(R.id.tEditNama);
        edTelpon = findViewById(R.id.tEditTelepon);
        btnSimpan = findViewById(R.id.btnEdit);

        Bundle bundle = getIntent().getExtras();

        id = bundle.getString("id");
        nama = bundle.getString("nama");
        telpon =bundle.getString("telpon");

        edNama.setText(nama);
        edTelpon.setText(telpon);

        btnSimpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nama = edNama.getText().toString();
                telpon = edTelpon.getText().toString();

                if(nama.isEmpty() || telpon.isEmpty()){
                    Toast.makeText(getApplicationContext(), "Data Belum Lengkap", Toast.LENGTH_SHORT).show();
                }
                else {
                    HashMap<String , String > nilai = new HashMap<>();
                    nilai.put("id", id);
                    nilai.put("nama", nama);
                    nilai.put("telpon", telpon);

                    controller.editData(nilai);

                    callHome();
                }
            }
        });
    }
    public void callHome() {
        Intent i = new Intent(EditTeman.this, MainActivity.class);
        startActivity(i);
        finish();
    }
}