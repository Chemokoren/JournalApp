package msomihub.com.journalapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Timer;
import java.util.TimerTask;

public class JournalEntry extends AppCompatActivity{
    EditText editText;
    Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_journal_entry);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        editText= (EditText)findViewById(R.id.etJournalEntry);
        editText.setSelection(0);

        editText.addTextChangedListener(new TextWatcher() {

            boolean isTyping = false;

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            private Timer timer = new Timer();
            private final long DELAY = 5000; // milliseconds

            @Override
            public void afterTextChanged(final Editable s) {
                Log.d("", "");
                if(!isTyping) {
                    Toast.makeText(JournalEntry.this, "started typing", Toast.LENGTH_SHORT).show();
                    // Send notification for start typing event
                    isTyping = true;
                }
                timer.cancel();
                timer = new Timer();
                timer.schedule(
                        new TimerTask() {
                            @Override
                            public void run() {
                                isTyping = false;
                                Toast.makeText(JournalEntry.this, "stopped typing", Toast.LENGTH_SHORT).show();
                                //send notification for stopped typing event
                            }
                        },
                        DELAY
                );
            }
        });


    }

//    @Override
//    public void onClick(View v) {
//        switch (v.getId()){
//            case R.id.etJournalEntry:
//                if(isEmpty(editText)){
////                    toolbar.setNavigationIcon(R.drawable.save);
////
////                    toolbar.setNavigationOnClickListener(new View.OnClickListener() {
////                        @Override
////                        public void onClick(View v) {
////                            Toast.makeText(JournalEntry.this, "I hope it works", Toast.LENGTH_SHORT).show();
////                    Intent notificationsIntent = new Intent(getApplicationContext(), MainDrawerActivity.class);
////                    notificationsIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK
////                            | Intent.FLAG_ACTIVITY_CLEAR_TOP);
////                    startActivity(notificationsIntent);
////                    finish();
////                        }
////                    });
//                }
//
//
//                break;
//        }
//    }

    private boolean isEmpty(EditText etText) {
        if (etText.getText().toString().trim().length() > 0)
            return false;

        return true;
    }
}
