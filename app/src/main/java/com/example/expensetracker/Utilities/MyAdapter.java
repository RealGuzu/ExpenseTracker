package com.example.expensetracker.Utilities;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.expensetracker.Activities.DetailedView;
//import com.example.expensetracker.Activities.update_activity;
import com.example.expensetracker.R;

import java.util.ArrayList;
import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyViewHolder> {
    private Context context;
    private List<DataClass> dataList;

    public MyAdapter(Context context, List<DataClass> dataList) {
        this.context = context;
        this.dataList = dataList;
    }

    @NonNull
        @Override
        public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_item, parent, false);
                return new MyViewHolder(view);
            }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        DataClass data = dataList.get(position);
        holder.recTitle.setText(data.getTitle());
//        holder.recDesc.setText(data.getDescription());
//        holder.recCategory.setText(data.getCategory());
        holder.recAmount.setText("$" + data.getAmount());

        // Concatenate dollar sign with amount

        holder.recCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, DetailedView.class);

                intent.putExtra("Key",dataList.get(holder.getAdapterPosition()).getKey());

                context.startActivity(intent);
            }
        });
    }

        @Override
        public int getItemCount() {
            return dataList.size();
        }


    public void searchDataList(ArrayList<DataClass> searchList){
    dataList = searchList;
    notifyDataSetChanged();
    }
}


class
MyViewHolder extends  RecyclerView.ViewHolder{
    TextView recTitle,recDesc,recAmount,recCategory;
    CardView recCard;
    public MyViewHolder(@NonNull View itemView){
        super(itemView);
        recCard = itemView.findViewById(R.id.recCard);
//        recDesc = itemView.findViewById(R.id.recDesc);
        recTitle = itemView.findViewById(R.id.recTitle);
        recAmount = itemView.findViewById(R.id.recAmount);
//        recCategory = itemView.findViewById(R.id.recCategory);

    }
}