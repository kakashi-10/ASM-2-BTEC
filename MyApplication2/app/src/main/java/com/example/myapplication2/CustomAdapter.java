package com.example.myapplication2;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.MyViewHolder> {

    private Context context;
    private Activity activity;
    private ArrayList budget_id, budget_name, budget_type, budget_amount;


    CustomAdapter(Activity activity, Context context, ArrayList budget_id, ArrayList budget_name, ArrayList budget_type,
                  ArrayList budget_amount){
        this.activity = activity;
        this.context = context;
        this.budget_id = budget_id;
        this.budget_name = budget_name;
        this.budget_type = budget_type;
        this.budget_amount = budget_amount;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.my_row, parent, false);
        return new MyViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, final int position) {
        holder.budget_id_txt.setText(String.valueOf(budget_id.get(position)));
        holder.budget_name_txt.setText(String.valueOf(budget_name.get(position)));
        holder.budget_type_txt.setText(String.valueOf(budget_type.get(position)));
        holder.budget_amount_txt.setText(String.valueOf(budget_amount.get(position)));

        holder.mainLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int currentPosition = holder.getAdapterPosition();
                if (currentPosition != RecyclerView.NO_POSITION) { // Check if position is valid
                    Intent intent = new Intent(context, UpdateActivity.class);
                    intent.putExtra("id", (String) budget_id.get(currentPosition));
                    intent.putExtra("name", (String) budget_name.get(currentPosition));
                    intent.putExtra("type", (String) budget_type.get(currentPosition));
                    intent.putExtra("amount", (String) budget_amount.get(currentPosition));
                    activity.startActivityForResult(intent, 1);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return budget_id.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView budget_id_txt, budget_name_txt, budget_type_txt, budget_amount_txt;
        LinearLayout mainLayout;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            budget_id_txt = itemView.findViewById(R.id.budget_id_txt);
            budget_name_txt = itemView.findViewById(R.id.budget_name_txt);
            budget_type_txt = itemView.findViewById(R.id.budget_type_txt);
            budget_amount_txt = itemView.findViewById(R.id.budget_amount_txt);
            mainLayout = itemView.findViewById(R.id.mainLayout);
        }
    }
}
