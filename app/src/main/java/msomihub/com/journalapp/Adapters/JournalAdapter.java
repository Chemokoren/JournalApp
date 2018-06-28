package msomihub.com.journalapp.Adapters;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import msomihub.com.journalapp.ListItem;
import msomihub.com.journalapp.R;

import static android.content.ContentValues.TAG;

public class JournalAdapter extends RecyclerView.Adapter<JournalAdapter.PaymentsHolder> {
    private static Context context;
    private static ArrayList<ListItem> mData;


    public JournalAdapter(ArrayList<ListItem> data, Context context) {
        mData = data;
        JournalAdapter.context = context;
    }

    @Override
    public PaymentsHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView;


        itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item, parent, false);
        PaymentsHolder vh = new PaymentsHolder(itemView, new PaymentsHolder.IMyViewHolderClicks() {

        });
        return vh;

    }

    @Override
    public void onBindViewHolder(PaymentsHolder holder, final int position) {
        ListItem tasks = mData.get(position);

        holder.setDescription(tasks.getHead());
    }

    @Override
    public int getItemCount() {
        if (mData == null)
            return 0;
        return mData.size();
    }


    public static class PaymentsHolder extends RecyclerView.ViewHolder {

        TextView tv_description;

        public PaymentsHolder(View itemView, IMyViewHolderClicks listener) {
            super(itemView);

            tv_description = itemView.findViewById(R.id.tv_head);

        }

        public void setDescription(String name) {
            tv_description.setText(name);
        }


        public interface IMyViewHolderClicks {

        }


    }




}





