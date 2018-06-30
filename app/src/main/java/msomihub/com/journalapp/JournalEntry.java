package msomihub.com.journalapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import msomihub.com.journalapp.JournalDb.JournalDb;

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
        checkForJournalVal();
    }

    public void checkForJournalVal(){
        Intent journIntent =getIntent();
        String journVal =journIntent.getStringExtra("journal_val");
        String journId =journIntent.getStringExtra("journal_id");
//        String journVal="I am coming home";
        if(journVal != null && !journVal.isEmpty()){
            editText.setText(journVal +" "+journId);
        }else{

        }
//        if(journVal.length()>0){
//            editText.setText(journVal);
//        }
    }


    @Override
    public void onClick(View v) {
        if (editText.getText().toString().length() > 0) {
            Intent journIntent =getIntent();
            String journId =journIntent.getStringExtra("journal_id");
            if(journId != null && !journId.isEmpty()){
                new JournalDb(getApplicationContext()).updateJournalDate(editText.getText().toString(),journId);
                Toast.makeText(this, "Journal Updated Successfully ", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getApplicationContext(), MainActivity.class));

            }else {
                new JournalDb(getApplicationContext()).insertData(editText.getText().toString(), "1");
                Toast.makeText(this, "Journal Saved Successfully ", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
            }
        }else{
            Intent notificationsIntent = new Intent(getApplicationContext(), MainActivity.class);
            notificationsIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK
                    | Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(notificationsIntent);
            finish();

        }
    }
}
