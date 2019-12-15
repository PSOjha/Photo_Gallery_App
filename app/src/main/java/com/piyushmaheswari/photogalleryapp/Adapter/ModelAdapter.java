package com.piyushmaheswari.photogalleryapp.Adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.piyushmaheswari.photogalleryapp.AddUpdatePhotosActivity;
import com.piyushmaheswari.photogalleryapp.Database.SqLiteHelper;
import com.piyushmaheswari.photogalleryapp.MainActivity;
import com.piyushmaheswari.photogalleryapp.Model.ModelRecord;
import com.piyushmaheswari.photogalleryapp.R;
import com.piyushmaheswari.photogalleryapp.RecordDetailActivity;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class ModelAdapter extends RecyclerView.Adapter<ModelAdapter.HolderRecord> {

    private Context context;
    SqLiteHelper sqLiteHelper;
    private ArrayList<ModelRecord> recordsList;

    public ModelAdapter(Context context, ArrayList<ModelRecord> recordsList) {
        this.context = context;
        this.recordsList = recordsList;

        sqLiteHelper=new SqLiteHelper(context);
    }

    @NonNull
    @Override
    public HolderRecord onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.row_record,parent,false);
        return new HolderRecord(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HolderRecord holder, final int position) {

        final ModelRecord record=recordsList.get(position);
        final String id=record.getId();
        final String date=record.getDate();
        String name=record.getName();
        String addedTime=record.getAddedTime();
        String updatedTime=record.getUpdateTime();
        String image=record.getImage();

        holder.datetv.setText(date);
        holder.nametv.setText(name);
        if(record.getImage().equals("null"))
        {
            holder.imageView.setImageResource(R.drawable.ic_action_name_black);
        }
        else
        {
            holder.imageView.setImageURI(Uri.parse(image));
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(context, RecordDetailActivity.class);
                intent.putExtra("RECORD_ID",id);
                context.startActivity(intent);
            }
        });

        holder.imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showMoreDialog(""+position,
                                ""+id,
                                ""+record.getName(),
                                ""+record.getImage(),
                                ""+date,
                                ""+record.getAddedTime(),
                                ""+record.getUpdateTime()
                                );
            }
        });
    }

    private void showMoreDialog(String position, final String id, final String name, final String image, final String date, final String addedTime, final String updatedTime)
    {
        String[] options={"Edit","Delete"};
        AlertDialog.Builder builder=new AlertDialog.Builder(context);
        builder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                if(i==0)
                {
                    Intent intent=new Intent(context, AddUpdatePhotosActivity.class);
                    intent.putExtra("ID",id);
                    intent.putExtra("NAME",name);
                    intent.putExtra("DATE",date);
                    intent.putExtra("IMAGE",image);
                    intent.putExtra("ADDED_TIME",addedTime);
                    intent.putExtra("UPDATED_TIME",updatedTime);
                    intent.putExtra("isEditMode",true);
                    context.startActivity(intent);

                }
                else if(i==1)
                {
                    sqLiteHelper.deleteData(id);
                    ((MainActivity)context).onResume();
                }

            }
        });
        builder.create().show();
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
