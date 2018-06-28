package msomihub.com.journalapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Timer;
import java.util.TimerTask;

import msomihub.com.journalapp.JournalDb.JournalDbAdapter;

public class JournalEntry extends AppCompatActivity implements View.OnClickListener {
    EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_journal_entry);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        editText = (EditText) findViewById(R.id.etJournalEntry);
        editText.setSelection(0);


        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
            }

            @Override
            public void onTextChanged(final CharSequence s, int start, int before,
                                      int count) {
                getSupportActionBar().setHomeAsUpIndicator(R.drawable.save);
//                Toast.makeText(JournalEntry.this, "I have changed", Toast.LENGTH_SHORT).show();

            }

            @Override
            public void afterTextChanged(final Editable s) {
//                Toast.makeText(JournalEntry.this, "I have changed", Toast.LENGTH_SHORT).show();


            }
        });

        toolbar.setNavigationOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        if (editText.getText().toString().length() > 0) {
//            Toast.makeText(this, "Data: "+editText.getText(), Toast.LENGTH_SHORT).show();

            new JournalDbAdapter(getApplicationContext()).insertData(editText.getText().toString(),"1");
        }
    }
}
