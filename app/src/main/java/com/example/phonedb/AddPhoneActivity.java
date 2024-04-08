package com.example.phonedb;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AddPhoneActivity extends AppCompatActivity {
    private Button websiteButton, cancelButton, saveButton;
    private EditText brandET, modelET, androidVerET, websiteET;
    private long id;
    private boolean isEditMode = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_phone);

        initializeButtons();
        initializeEditTexts();

        Bundle pack = getExtras();
        if (pack != null) {
            isEditMode = true;
            id = pack.getLong("id");
            fillEditTexts(pack);
        }

        setButtonListeners();
    }

    private Bundle getExtras() {
        return getIntent().getExtras();
    }

    private void fillEditTexts(Bundle pack) {
        brandET.setText(pack.getString("brand"));
        modelET.setText(pack.getString("model"));
        androidVerET.setText(String.valueOf(pack.getDouble("android_version")));
        websiteET.setText(pack.getString("website"));
    }

    private void setButtonListeners() {
        websiteButton.setOnClickListener(view -> {
            String address = websiteET.getText().toString();
            if (address.isEmpty()) {
                websiteET.setError(getString(R.string.error_empty));
            } else {
                try {
                    Intent browser = new Intent("android.intent.action.VIEW", Uri.parse(address));
                    startActivity(browser);
                } catch (Exception ex) {
                    Toast.makeText(this, getString(R.string.toast_invalid_website), Toast.LENGTH_SHORT).show();
                }
            }
        });

        cancelButton.setOnClickListener(view -> {
            setResult(RESULT_CANCELED);
            finish();
        });

        saveButton.setOnClickListener(view -> {
            if (areSomeFieldsEmpty()) {
                Toast.makeText(AddPhoneActivity.this, getString(R.string.toast_fields_empty), Toast.LENGTH_SHORT).show();
                checkAndSetErrorForEmptyEditText(brandET);
                checkAndSetErrorForEmptyEditText(modelET);
                checkAndSetErrorForEmptyEditText(androidVerET);
                checkAndSetErrorForEmptyEditText(websiteET);
            }
            else {
                Bundle pack = new Bundle();
                pack.putBoolean("mode", isEditMode);
                pack.putLong("id", id);
                pack.putString("brand", brandET.getText().toString());
                pack.putString("model", modelET.getText().toString());
                pack.putDouble("android_version", Double.parseDouble(androidVerET.getText().toString()));
                pack.putString("website", websiteET.getText().toString());
                Intent intention = new Intent();
                intention.putExtras(pack);

                setResult(RESULT_OK, intention);
                finish();
            }

        });
    }

    private void checkAndSetErrorForEmptyEditText(EditText editText) {
        if (editText.getText().toString().equals(""))
            editText.setError(getString(R.string.error_empty));
    }

    private void initializeEditTexts() {
        brandET = findViewById(R.id.et_brand);
        modelET = findViewById(R.id.et_model);
        androidVerET = findViewById(R.id.et_android_version);
        websiteET = findViewById(R.id.et_website);
    }

    private void  initializeButtons() {
        websiteButton = findViewById(R.id.btn_website);
        cancelButton = findViewById(R.id.btn_cancel);
        saveButton = findViewById((R.id.btn_save));
    }

    private boolean areSomeFieldsEmpty() {
        return brandET.getText().toString().isEmpty() ||
                modelET.getText().toString().isEmpty() ||
                androidVerET.getText().toString().isEmpty() ||
                websiteET.getText().toString().isEmpty();
    }
}