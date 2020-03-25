package com.fordoctors.fordoctors;

import android.app.SearchManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.fordoctors.fordoctors.adapter.PrescriptionsAdapter;
import com.fordoctors.fordoctors.model.Prescription;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;

public class MainActivity extends AppCompatActivity implements PrescriptionsAdapter.SearchAdapterListener {

    private DatabaseReference mUsersDatabase;
    private FloatingActionButton Add, Profile;
    private RecyclerView mPatientsLists;
    private PrescriptionsAdapter prescriptionsAdapter;
    private List<Prescription> prescriptionList;
    private FirebaseAuth mAuth;
    private FirebaseUser mFirebaseUser;
    private RelativeLayout mNoPatients;
    private String mCurrentUserId;

    private DatabaseReference mPatientsDatabase;
    private FirebaseUser mCurrentUser;

    private SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAuth = FirebaseAuth.getInstance();
        Add = findViewById(R.id.add);
        Profile = findViewById(R.id.profile);
        mAuth = FirebaseAuth.getInstance();
        mCurrentUser = mAuth.getCurrentUser();
        mNoPatients = findViewById(R.id.no_patients);
        mPatientsLists =findViewById(R.id.patients_list);



        if (mAuth.getCurrentUser() != null) {
            mCurrentUserId = mCurrentUser.getUid();
            mUsersDatabase = FirebaseDatabase.getInstance().getReference().child("Users").child(mAuth.getCurrentUser().getUid());
            mUsersDatabase.keepSynced(true);
            mPatientsDatabase = FirebaseDatabase.getInstance().getReference().child("Patients").child(mCurrentUserId);
            mPatientsDatabase.keepSynced(true);

            mPatientsLists.setHasFixedSize(true);
            LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
            mPatientsLists.setLayoutManager(mLayoutManager);
            prescriptionList = new ArrayList<>();
            mPatientsLists.setItemAnimator(new DefaultItemAnimator());
            prescriptionsAdapter = new PrescriptionsAdapter(this, prescriptionList, this);
            mPatientsLists.setAdapter(prescriptionsAdapter);

            readPatients();

            Add.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (isNetworkConnected()) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                        builder.setMessage("Scan your Patient's QR").setTitle("Scan");
                        builder.setMessage("Click to scan your Patient's QR")
                                .setCancelable(false)
                                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        Intent intent = new Intent(MainActivity.this, QRScannerActivity.class);
                                        startActivity(intent);
                                        dialog.dismiss();
                                    }
                                })
                                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        dialog.cancel();
                                    }
                                });
                        AlertDialog alert = builder.create();
                        alert.setTitle("QR Scan");
                        alert.show();
                    }else{
                        Toast.makeText(MainActivity.this, "Connect to Internet", Toast.LENGTH_SHORT).show();
                    }
                }
            });

            Profile.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent intent = new Intent(MainActivity.this, ProfileActivity.class);
                    intent.putExtra("access","true");
                    startActivity(intent);

                }
            });


        }


    }

    @Override
    protected void onStart() {
        super.onStart();

        FirebaseUser mCurrentUser = mAuth.getCurrentUser();
        if (mCurrentUser == null) {
            sendToLogin();
        }else{
            FirebaseAuth auth = FirebaseAuth.getInstance();
            mUsersDatabase = FirebaseDatabase.getInstance().getReference("Users").child(auth.getCurrentUser().getUid().toString());
            mUsersDatabase.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    Users user = dataSnapshot.getValue(Users.class);
                    if (user.getVerified().equals("false")) {
                        Intent setupIntent = new Intent(MainActivity.this, ProfileActivity.class);
                        setupIntent.putExtra("access","false");
                        setupIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(setupIntent);
                        finish();
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }


    }

    private void readPatients() {
        mPatientsDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    mPatientsLists.setVisibility(View.VISIBLE);
                    mNoPatients.setVisibility(View.GONE);
                    prescriptionList.clear();
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        Prescription myStatus = snapshot.getValue(Prescription.class);
                        prescriptionList.add(myStatus);
                    }
                    Collections.reverse(prescriptionList);
                    prescriptionsAdapter.notifyDataSetChanged();

                }else {
                    mNoPatients.setVisibility(View.VISIBLE);
                    mPatientsLists.setVisibility(View.GONE);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void sendToLogin() {

        Intent loginIntent = new Intent(MainActivity.this, Login.class);
        startActivity(loginIntent);
        finish();

    }


    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.main_menu, menu);
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        searchView = (SearchView) menu.findItem(R.id.action_search)
                .getActionView();
        searchView.setSearchableInfo(searchManager
                .getSearchableInfo(getComponentName()));
        searchView.setMaxWidth(Integer.MAX_VALUE);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                prescriptionsAdapter.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String query) {
                prescriptionsAdapter.getFilter().filter(query);
                return false;
            }
        });
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id){
            case R.id.logout:
                FirebaseAuth.getInstance().signOut();
                sendToLogin();
                return true;
            case R.id.source:
                Intent intent1 = new Intent(MainActivity.this, SourceActivity.class);
                startActivity(intent1);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        return cm.getActiveNetworkInfo() != null && cm.getActiveNetworkInfo().isConnected();
    }

    @Override
    public void onSearchSelected(Prescription prescription) {

    }
}
