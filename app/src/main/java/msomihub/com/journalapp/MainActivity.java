package msomihub.com.journalapp;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;

import java.util.ArrayList;

import msomihub.com.journalapp.Adapters.JournalAdapter;
import msomihub.com.journalapp.JournalDb.JournalDb;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    RecyclerView journalView;

    private RecyclerView.Adapter adapter;
    private ArrayList<JournalModel> listItems;
    GoogleApiClient mGoogleApiClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent journalEntryIntent = new Intent(getApplicationContext(), JournalEntry.class);
                startActivity(journalEntryIntent);
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        initialize();
        returnCompletedTasks();
        returnActiveAccount();

    }

    private void initialize() {
        journalView = findViewById(R.id.rvJournal);
        journalView.setHasFixedSize(true);
        journalView.setLayoutManager(new LinearLayoutManager(this));
        listItems = new ArrayList<>();
        adapter = new JournalAdapter(listItems, this);
        journalView.setAdapter(adapter);
    }
    public void returnActiveAccount(){

        try {
            Intent getLoggedInAccount = getIntent();
            String logMail = getLoggedInAccount.getStringExtra("login_account").toString();
            Log.e("login_account",""+logMail);

            NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
            View headerView = navigationView.getHeaderView(0);
            TextView navUsername = (TextView) headerView.findViewById(R.id.tvEmail);
            navUsername.setText(logMail);

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void returnCompletedTasks() {

        ArrayList<JournalModel> returned_journ = new JournalDb(getApplicationContext()).getAllJournals();
        JournalModel tasks = new JournalModel();

        for (JournalModel customer : returned_journ) {
            listItems.add(customer);
            if (listItems != null) {
                adapter.notifyDataSetChanged();
            }
        }

    }
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.nav_support) {
            try{
                Intent intent = new Intent (Intent.ACTION_VIEW , Uri.parse("mailto:" + "humphreykibet@gmail.com"));
                intent.putExtra(Intent.EXTRA_SUBJECT,"Subject...");
                intent.putExtra(Intent.EXTRA_TEXT, "");
                startActivity(intent);
            }catch(ActivityNotFoundException e){
                e.printStackTrace();
            }

        } else if (id == R.id.nav_share) {
            try {
                Intent i = new Intent(Intent.ACTION_SEND);
                i.setType("text/plain");
                i.putExtra(Intent.EXTRA_SUBJECT, "JournalApp");
                String sAux = "\nLet me recommend you this application\n\n";
                sAux = sAux + "Journal App \n\n";
                i.putExtra(Intent.EXTRA_TEXT, sAux);
                startActivity(Intent.createChooser(i, "choose one"));
            }catch (Exception e){
                e.printStackTrace();
            }

        } else if (id == R.id.nav_logout) {
            signOut();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    public void signOut() {
        Auth.GoogleSignInApi.signOut(mGoogleApiClient).setResultCallback(
                new ResultCallback<Status>() {
                    @Override
                    public void onResult(Status status) {
                        Toast.makeText(getApplicationContext(), "Logged Out", Toast.LENGTH_SHORT).show();
                        Intent i = new Intent(getApplicationContext(), JournalLogin.class);
                        startActivity(i);
                    }
                });
    }

    @Override
    protected void onStart() {
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();
        mGoogleApiClient.connect();
        super.onStart();
    }




}
