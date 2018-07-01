package msomihub.com.journalapp;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;




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

    public void checkForJournalVal() {
        Intent journIntent = getIntent();
        String journVal = journIntent.getStringExtra("journal_val");
        String journId = journIntent.getStringExtra("journal_id");
//        String journVal="I am coming home";
        if (journVal != null && !journVal.isEmpty()) {
            editText.setText(journVal + " " + journId);
        } else {

        }
//        if(journVal.length()>0){
//            editText.setText(journVal);
//        }
    }


    @Override
    public void onClick(View v) {
        if (editText.getText().toString().length() > 0) {
            Intent journIntent = getIntent();
            String journId = journIntent.getStringExtra("journal_id");
            if (journId != null && !journId.isEmpty()) {
                new JournalDb(getApplicationContext()).updateJournalDate(editText.getText().toString(), journId);
                Toast.makeText(this, "Journal Updated Successfully ", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getApplicationContext(), MainActivity.class));

            } else {
                new JournalDb(getApplicationContext()).insertData(editText.getText().toString(), "1");
                Toast.makeText(this, "Journal Saved Successfully ", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
            }
        } else {
            Intent notificationsIntent = new Intent(getApplicationContext(), MainActivity.class);
            notificationsIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK
                    | Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(notificationsIntent);
            finish();

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.journal_entry_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        Intent journIntent = getIntent();
        String journId = journIntent.getStringExtra("journal_id");
        if (journId != null && !journId.isEmpty()) {
            if (id == R.id.action_delete) {
                Toast.makeText(this, "Are you sure you want to delete", Toast.LENGTH_SHORT).show();
                return true;
            } else if (id == R.id.action_PDF) {
                createPDF();
                return true;
            } else if (id == R.id.action_Share) {
                return true;
            }

        }


        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        if (!(editText.getText().length() > 0)) {

            hideMenuItems(menu);
        }
//        menu.findItem(R.id.action_Share).setVisible(false);
        return super.onPrepareOptionsMenu(menu);
//        menu.findItem(R.id.action_delete).setVisible(false);

    }

    public void hideMenuItems(Menu menu) {
        MenuItem item = menu.findItem(R.id.action_delete);
        item.setVisible(false);
        MenuItem item1 = menu.findItem(R.id.action_Share);
        item1.setVisible(false);
        MenuItem item2 = menu.findItem(R.id.action_PDF);
        item2.setVisible(false);

    }

    public String getJournal() {
        return editText.getText().toString();
    }

    public void createPDF() {
        String FILE = Environment.getExternalStorageDirectory().toString()
                + "/PDF/" + "Name.pdf";

        // Add Permission into Manifest.xml
        // <uses-permission
        // android:name="android.permission.WRITE_EXTERNAL_STORAGE"/>

        // Create New Blank Document
        Document document = new Document(PageSize.A4);

        // Create Directory in External Storage
        String root = Environment.getExternalStorageDirectory().toString();
        File myDir = new File(root + "/PDF");
        myDir.mkdirs();

        Log.e("myDir myDir alc",""+myDir);

        // Create Pdf Writer for Writting into New Created Document
        try {
            PdfWriter.getInstance(document, new FileOutputStream(FILE));

            // Open Document for Writting into document
            document.open();

            // User Define Method
            addMetaData(document);
            addTitlePage(document);
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (DocumentException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        // Close Document after writting all content
        document.close();

        Toast.makeText(this, "PDF File is Created. Location : " + FILE,
                Toast.LENGTH_LONG).show();
        Log.e("file path alc",""+FILE);
    }

    public void addMetaData(Document document)

    {
        document.addTitle("Personal Journal");
        document.addSubject("Person Info");
        document.addKeywords("Personal,	Education, Skills");
        document.addAuthor("TAG");
        document.addCreator("TAG");
    }

    public void addTitlePage(Document document) throws DocumentException {
        // Font Style for Document
        Font catFont = new Font(Font.FontFamily.TIMES_ROMAN, 18, Font.BOLD);
        Font titleFont = new Font(Font.FontFamily.TIMES_ROMAN, 22, Font.BOLD
                | Font.UNDERLINE, BaseColor.GRAY);
        Font smallBold = new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.BOLD);
        Font normal = new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.NORMAL);

        // Start New Paragraph
        Paragraph prHead = new Paragraph();
        // Set Font in this Paragraph
        prHead.setFont(titleFont);
        // Add item into Paragraph
        prHead.add("Personal Journal – Name\n");

        // Create Table into Document with 1 Row
        PdfPTable myTable = new PdfPTable(1);
        // 100.0f mean width of table is same as Document size
        myTable.setWidthPercentage(100.0f);

        // Create New Cell into Table
        PdfPCell myCell = new PdfPCell(new Paragraph(""));
        myCell.setBorder(Rectangle.BOTTOM);

        // Add Cell into Table
        myTable.addCell(myCell);

        prHead.setFont(catFont);
        prHead.add("\nDate today:" + new JournalDb(getApplicationContext()).dateNow());
        prHead.setAlignment(Element.ALIGN_CENTER);

        // Add all above details into Document
        document.add(prHead);
        document.add(myTable);

        document.add(myTable);

        // Now Start another New Paragraph
//        Paragraph prPersinalInfo = new Paragraph();
//        prPersinalInfo.setFont(smallBold);
//        prPersinalInfo.add("Address 1\n");
//        prPersinalInfo.add("Address 2\n");
//        prPersinalInfo.add("City: SanFran. State: CA\n");
//        prPersinalInfo.add("Country: USA Zip Code: 000001\n");
//        prPersinalInfo
//                .add("Mobile: 9999999999 Fax: 1111111 Email: john_pit@gmail.com \n");
//
//        prPersinalInfo.setAlignment(Element.ALIGN_CENTER);

//        document.add(prPersinalInfo);
//        document.add(myTable);

        document.add(myTable);

        Paragraph prProfile = new Paragraph();
        prProfile.setFont(smallBold);
        prProfile.add("\n \n Contents : \n ");
        prProfile.setFont(normal);
        prProfile.add("\n " + new JournalEntry().getJournal());

        prProfile.setFont(smallBold);
        document.add(prProfile);

        // Create new Page in PDF
        document.newPage();
    }

    private void viewPdf(File myFile){
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setDataAndType(Uri.fromFile(myFile), "application/pdf");
        intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
        startActivity(intent);
    }
}

