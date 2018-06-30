package msomihub.com.journalapp.Adapters;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;

import msomihub.com.journalapp.JournalEntry;
import msomihub.com.journalapp.JournalModel;
import msomihub.com.journalapp.R;

public class JournalAdapter extends RecyclerView.Adapter<JournalAdapter.JournalHolder> {
    private static Context context;
    private static ArrayList<JournalModel> mData;


    public JournalAdapter(ArrayList<JournalModel> data, Context context) {
        mData = data;
        JournalAdapter.context = context;
    }

    @Override
    public JournalHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView;


        itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item, parent, false);
        JournalHolder vh = new JournalHolder(itemView, new JournalHolder.IMyViewHolderClicks() {

        });
        return vh;

    }

    @Override
    public void onBindViewHolder(JournalHolder holder, final int position) {
        JournalModel tasks = mData.get(position);
        holder.setNumberVal("Id: "+Integer.toString(tasks.getNo()));
        holder.setDateVal("Date: "+tasks.getDesc());
        holder.setDescription(tasks.getDat());
    }

    @Override
    public int getItemCount() {
        if (mData == null)
            return 0;
        return mData.size();
    }


    public static class JournalHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView tv_description, tv_number, tv_date;

        public JournalHolder(View itemView, IMyViewHolderClicks listener) {
            super(itemView);
            tv_description = itemView.findViewById(R.id.tv_desc);
            tv_number =itemView.findViewById(R.id.tv_no);
            tv_date =itemView.findViewById(R.id.tv_date);
            LinearLayout linearLayout = itemView.findViewById(R.id.layoutJournal);
            linearLayout.setOnClickListener(this);

        }

        public void setDescription(String name) {
            tv_description.setText(name);
        }

        public void setDateVal(String name) {
            tv_date.setText(name);
        }
        public void setNumberVal(String name) {
            tv_number.setText(name);
        }




        @Override
        public void onClick(View view) {
            int position = getLayoutPosition();
            JournalModel tasks = mData.get(position);
            String currUser = tasks.getDat();
            String currNo =Integer.toString(tasks.getNo());
            Toast.makeText(view.getContext(), "" + currUser, Toast.LENGTH_SHORT).show();
            Intent intentLogPeriod = new Intent(context, JournalEntry.class);
            intentLogPeriod.putExtra("journal_val", currUser);
            intentLogPeriod.putExtra("journal_id",currNo);
            view.getContext().startActivity(intentLogPeriod);
//            switch (currUser) {
//                case "Preferences":
//                    Intent intentLogPeriod = new Intent(context, PreferencesActivity.class);
//                    intentLogPeriod.putExtra("pref_val", 0);
//                    view.getContext().startActivity(intentLogPeriod);
//                    break;
//                case "Notifications":
//                    view.getContext().startActivity(new Intent(context, FlowNotifications.class));
//                    break;
//                case "FAQ":
//                    view.getContext().startActivity(new Intent(context, MyFlowFAQ.class));
//                    break;
//                case "Support":
//                    view.getContext().startActivity(new Intent(context, FlowSupport.class));
//                    break;
//                case "Reset App":
//                    AlertDialog.Builder alert = new AlertDialog.Builder(context);
//                    final TextView input = new TextView(context);
//                    alert.setTitle("Reset App");
//                    alert.setMessage("Are you sure you want to reset your App?");
//                    alert.setView(input);
//                    alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
//                        public void onClick(DialogInterface dialog, int whichButton) {
//                            SharedPreferences app_preferences = PreferenceManager.getDefaultSharedPreferences(context);
//                            SharedPreferences.Editor editor = app_preferences.edit();
//                            final Calendar c = Calendar.getInstance();
//                            int mYear = c.get(Calendar.YEAR); // current year
//                            int mMonth = c.get(Calendar.MONTH); // current month
//                            int mDay = c.get(Calendar.DAY_OF_MONTH); // current day
//                            editor.putString("menstruation_length", "4");
//                            editor.putString("period_length", "28");
//                            editor.putString("last_period_date", mDay + "/" + (mMonth + 1) + "/" + mYear);
//                            editor.commit();
//                        }
//
//                    });
//                    alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
//                        public void onClick(DialogInterface dialog, int whichButton) {
//                        }
//                    });
//                    alert.show();
//
//
//                    break;
//            }
        }


        public interface IMyViewHolderClicks {

        }


    }



    }