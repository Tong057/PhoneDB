package com.example.phonedb;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainActivity extends AppCompatActivity implements PhoneListAdapter.OnItemClickListener {
    private PhoneViewModel mPhoneViewModel;
    private PhoneListAdapter mAdapter;
    private RecyclerView mRecyclerView;
    private FloatingActionButton fabButton;
    private ItemTouchHelper itemTouchHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initializeRecycleView();
        initializeViewModel();
        setFABButton();
        initializeItemTouchHelper();
    }

    private void initializeItemTouchHelper() {
        itemTouchHelper = new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(
                0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView,
                                  @NonNull RecyclerView.ViewHolder viewHolder,
                                  @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                int adapterPosition = viewHolder.getAdapterPosition();
                Phone phoneToDelete = mAdapter.getPhoneAtPosition(adapterPosition);
                mPhoneViewModel.delete(phoneToDelete);
            }
        });
        itemTouchHelper.attachToRecyclerView(mRecyclerView);
    }

    private void setFABButton() {
        fabButton = findViewById(R.id.fab_main);
        fabButton.setOnClickListener(view -> openAddPhoneActivityForResult(
                new Intent(MainActivity.this, AddPhoneActivity.class))
        );
    }

    private void initializeViewModel() {
        mPhoneViewModel = new ViewModelProvider(this).get(PhoneViewModel.class);
        mPhoneViewModel.getAllPhones().observe(this, phones -> mAdapter.setPhoneList(phones));
    }

    private void initializeRecycleView() {
        mRecyclerView = findViewById(R.id.recycler_view);
        mAdapter = new PhoneListAdapter(this);
        mRecyclerView.setAdapter(mAdapter);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    public void openAddPhoneActivityForResult(Intent intent) {
        addPhoneActivityResultLauncher.launch(intent);
    }

    ActivityResultLauncher<Intent> addPhoneActivityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        Intent data = result.getData();
                        Bundle pack = data.getExtras();

                        boolean isEditMode = pack.getBoolean("mode");

                        String brand = pack.getString("brand");
                        String model = pack.getString("model");
                        double androidVersion = pack.getDouble("android_version");
                        String website = pack.getString("website");

                        if (isEditMode) {
                            long id = pack.getLong("id");
                            mPhoneViewModel.update(new Phone(id, brand, model, androidVersion, website));
                        }
                        else {
                            mPhoneViewModel.insert(new Phone(brand, model, androidVersion, website));
                        }

                    }
                }
            });

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case 0:
                mPhoneViewModel.deleteAll();
                Toast.makeText(this, getString(R.string.toast_database_deleted), Toast.LENGTH_SHORT).show();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onItemClickListener(Phone phone) {
        Intent intent = new Intent(MainActivity.this, AddPhoneActivity.class);
        intent.putExtra("id", phone.getId());
        intent.putExtra("brand", phone.getBrand());
        intent.putExtra("model", phone.getModel());
        intent.putExtra("android_version", phone.getAndroidVersion());
        intent.putExtra("website", phone.getWebsite());
        openAddPhoneActivityForResult(intent);
    }
}