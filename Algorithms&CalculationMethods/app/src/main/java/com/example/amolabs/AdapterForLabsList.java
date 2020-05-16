package com.example.amolabs;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;


public class AdapterForLabsList extends RecyclerView.Adapter {
    private List<String> labsCollection;
    private List<String> labsDescriptionCollection;
    Context context;
    public AdapterForLabsList(List<String> labs, List<String> labsDescription, Context context){
        labsCollection = labs;
        labsDescriptionCollection = labsDescription;
        this.context = context;
    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        Context context = viewGroup.getContext();
        int layoutForListItem = R.layout.lab_list_item;
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(layoutForListItem, viewGroup, false);
        LabViewHolder labViewHolder = new LabViewHolder(view);
        return labViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        ((LabViewHolder)viewHolder).labNumber.setText(labsCollection.get(i));
        ((LabViewHolder)viewHolder).labDescription.setText(labsDescriptionCollection.get(i));
    }

    @Override
    public int getItemCount() {
        return labsCollection.size();
    }


    class LabViewHolder extends RecyclerView.ViewHolder{
        TextView labNumber;
        TextView labDescription;
        public LabViewHolder(@NonNull View itemView) {
            super(itemView);
            labNumber = itemView.findViewById(R.id.labNumber);
            labDescription = itemView.findViewById(R.id.labDescription);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent;
                    switch (getAdapterPosition()){
                        case 0:
                            intent = new Intent("com.example.amolabs.Lab1Main");
                            context.startActivity(intent);
                            break;
                        case 1:
                            intent = new Intent(v.getContext(), Lab2Main.class);
                            context.startActivity(intent);
                            break;
                        case 2:
                            intent = new Intent(v.getContext(), Lab3Main.class);
                            context.startActivity(intent);
                            break;
                        case 3:
                            intent = new Intent(v.getContext(), Lab4Main.class);
                            context.startActivity(intent);
                            break;
                        case 4:
                            intent = new Intent(v.getContext(), Lab5Main.class);
                            context.startActivity(intent);
                            break;
                    }
                }
            });
        }
    }
}
