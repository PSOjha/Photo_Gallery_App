package com.piyushmaheswari.photogalleryapp.Adapter;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.piyushmaheswari.photogalleryapp.Model.ModelRecord;
import com.piyushmaheswari.photogalleryapp.R;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class ModelAdapter extends RecyclerView.Adapter<ModelAdapter.HolderRecord> {

    private Context context;
    private ArrayList<ModelRecord> recordsList;

    public ModelAdapter(Context context, ArrayList<ModelRecord> recordsList) {
        this.context = context;
        this.recordsList = recordsList;
    }

    @NonNull
    @Override
    public HolderRecord onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.row_record,parent,false);
        return new HolderRecord(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HolderRecord holder, int position) {
        ModelRecord record=recordsList.get(position);

        holder.datetv.setText(record.getDate());
        holder.nametv.setText(record.getName());
        holder.imageView.setImageURI(Uri.parse(record.getImage()));

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        holder.imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }

    @Override
    public int getItemCount() {
        return recordsList.size();
    }


    class HolderRecord extends RecyclerView.ViewHolder
    {

        CircleImageView imageView;
        ImageButton imageButton;
        TextView nametv,datetv;

        public HolderRecord(@NonNull View itemView) {
            super(itemView);

            imageView=itemView.findViewById(R.id.imageNotes);
            imageButton=itemView.findViewById(R.id.more);
            nametv=itemView.findViewById(R.id.nameTv);
            datetv=itemView.findViewById(R.id.dateTv);
        }
    }
}
