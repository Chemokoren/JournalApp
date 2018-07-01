package msomihub.com.journalapp.JournalDb;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import msomihub.com.journalapp.JournalModel;

public class JournalDb {
    journalDbHelper myhelper;
    public JournalDb(Context context)
    {
        myhelper = new journalDbHelper(context);
    }

    public long insertData(String name, String pass)
    {

        SQLiteDatabase dbb = myhelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(journalDbHelper.JOURN_NAME, name);
        contentValues.put(journalDbHelper.MyDATE, dateNow());
        long id = dbb.insert(journalDbHelper.TABLE_NAME, null , contentValues);
        return id;
    }

    public String dateNow(){
        Date date =new Date();
        final Calendar c = Calendar.getInstance();
        int mYear = c.get(Calendar.YEAR); // current year
        int mMonth = c.get(Calendar.MONTH); // current month
        int mDay = c.get(Calendar.DAY_OF_MONTH); // current day
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
        String todays_d = mDay + "/" + (mMonth + 1) + "/" + mYear +" "+sdf.format(c.getTime());
        return todays_d;
    }

//    public ArrayList<String> getData()
//    {
//        SQLiteDatabase db = myhelper.getWritableDatabase();
//        String[] columns = {journalDbHelper.UID, journalDbHelper.JOURN_NAME, journalDbHelper.MyDATE};
//        Cursor cursor =db.query(journalDbHelper.TABLE_NAME,columns,null,null,null,null,null);
////        StringBuffer buffer= new StringBuffer();
//        ArrayList<String> my_data = new ArrayList<>();
//        ArrayList<String> my_dt = new ArrayList<>();
//        ArrayList<String> my_gen = new ArrayList<>();
//        String buffer= "";
//        while (cursor.moveToNext())
//        {
//            int cid =cursor.getInt(cursor.getColumnIndex(journalDbHelper.UID));
//            String name =cursor.getString(cursor.getColumnIndex(journalDbHelper.JOURN_NAME));
//            String  my_date =cursor.getString(cursor.getColumnIndex(journalDbHelper.MyDATE));
////            String combined_journal = my_date + "  \n" + name +" \n";
//            String combined_journal = name +" \n";
//            buffer=combined_journal;
//            my_data.add(buffer);
//            my_dt.add(my_date);
////            buffer.append(cid+ "   " + name + "   " + my_date +" \n");
//        }
//        my_gen.addAll(my_data);
//        my_gen.addAll(my_dt);
//        Log.e("buffermanenos",""+my_gen);
//        return my_gen;
//    }

    public ArrayList<JournalModel> getAllJournals(){
        SQLiteDatabase db = myhelper.getWritableDatabase();
        String[] columns = {journalDbHelper.UID, journalDbHelper.JOURN_NAME, journalDbHelper.MyDATE};
        Cursor cursor =db.query(journalDbHelper.TABLE_NAME,columns,null,null,null,null,null);
        ArrayList<JournalModel> journalList = new ArrayList<>();
        while (cursor.moveToNext())
        {
            int cid =cursor.getInt(cursor.getColumnIndex(journalDbHelper.UID));
            String name =cursor.getString(cursor.getColumnIndex(journalDbHelper.JOURN_NAME));
            String  my_date =cursor.getString(cursor.getColumnIndex(journalDbHelper.MyDATE));
            JournalModel customer = new JournalModel(cid, name, my_date);
            journalList.add(customer);
        }

//        Log.e("buffermanenos",""+journalList);
        return journalList;

    }

    public  int delete(String uname)
    {
        SQLiteDatabase db = myhelper.getWritableDatabase();
        String[] whereArgs ={uname};

        int count =db.delete(journalDbHelper.TABLE_NAME , journalDbHelper.JOURN_NAME +" = ?",whereArgs);
        return  count;
    }

    public int updateName(String oldName , String newName)
    {
        SQLiteDatabase db = myhelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(journalDbHelper.JOURN_NAME,newName);
        String[] whereArgs= {oldName};
        int count =db.update(journalDbHelper.TABLE_NAME,contentValues, journalDbHelper.JOURN_NAME +" = ?",whereArgs );
        return count;
    }
    public int updateJournalDate(String journ_name, String journ_id)
    {
        SQLiteDatabase db = myhelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(journalDbHelper.JOURN_NAME,journ_name);
        contentValues.put(journalDbHelper.MyDATE,dateNow());
        String[] whereArgs= {journ_id};
        int count =db.update(journalDbHelper.TABLE_NAME,contentValues, journalDbHelper.UID +" = ?",whereArgs );
        return count;
    }

    static class journalDbHelper extends SQLiteOpenHelper
    {
        private static final String DATABASE_NAME = "JournalDb";    // Database Name
        private static final String TABLE_NAME = "JournalTable";   // Table Name
        private static final int DATABASE_Version = 1;   // Database Version
        private static final String UID="_id";     // Column I (Primary Key)
        private static final String JOURN_NAME = "Name";    //Column II
        private static final String MyDATE= "Date";    // Column III
        private static final String CREATE_TABLE = "CREATE TABLE "+TABLE_NAME+
                " ("+UID+" INTEGER PRIMARY KEY AUTOINCREMENT, "+ JOURN_NAME +" VARCHAR(255) ,"+ MyDATE+" VARCHAR(225));";
        private static final String DROP_TABLE ="DROP TABLE IF EXISTS "+TABLE_NAME;
        private Context context;

        public journalDbHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_Version);
            this.context=context;
        }

        public void onCreate(SQLiteDatabase db) {

            try {
                db.execSQL(CREATE_TABLE);
            } catch (Exception e) {
                Message.message(context,""+e);
            }
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            try {
                Message.message(context,"OnUpgrade");
                db.execSQL(DROP_TABLE);
                onCreate(db);
            }catch (Exception e) {
                Message.message(context,""+e);
            }
        }
    }
}
