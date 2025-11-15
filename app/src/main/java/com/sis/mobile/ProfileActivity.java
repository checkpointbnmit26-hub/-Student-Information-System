package com.sis.mobile;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class ProfileActivity extends AppCompatActivity {
    EditText etName, etEmail, etPhone, etAddress;
    Button btnEdit, btnSave;
    boolean editing = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        etName = findViewById(R.id.etName);
        etEmail = findViewById(R.id.etEmailP);
        etPhone = findViewById(R.id.etPhone);
        etAddress = findViewById(R.id.etAddress);
        btnEdit = findViewById(R.id.btnEdit);
        btnSave = findViewById(R.id.btnSave);

        SharedPreferences prefs = getSharedPreferences("SIS_PREF", MODE_PRIVATE);
        String name = prefs.getString("NAME", "John Doe");
        String email = prefs.getString("EMAIL", "student@test.com");
        String phone = prefs.getString("PHONE", "+1234567890");
        String addr = prefs.getString("ADDRESS", "123 Main St");

        etName.setText(name);
        etEmail.setText(email);
        etPhone.setText(phone);
        etAddress.setText(addr);

        setEditing(false);

        btnEdit.setOnClickListener(v -> setEditing(true));
        btnSave.setOnClickListener(v -> {
            SharedPreferences.Editor e = prefs.edit();
            e.putString("NAME", etName.getText().toString());
            e.putString("PHONE", etPhone.getText().toString());
            e.putString("ADDRESS", etAddress.getText().toString());
            e.apply();
            setEditing(false);
            Toast.makeText(ProfileActivity.this, "Profile updated", Toast.LENGTH_SHORT).show();
        });
    }

    private void setEditing(boolean val) {
        editing = val;
        etName.setEnabled(val);
        etPhone.setEnabled(val);
        etAddress.setEnabled(val);
        btnEdit.setVisibility(val ? View.GONE : View.VISIBLE);
        btnSave.setVisibility(val ? View.VISIBLE : View.GONE);
    }
}
