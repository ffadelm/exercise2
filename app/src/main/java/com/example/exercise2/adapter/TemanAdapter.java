package com.example.exercise2.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.exercise2.EditTeman;
import com.example.exercise2.LihatTeman;
import com.example.exercise2.MainActivity;
import com.example.exercise2.R;
import com.example.exercise2.database.Teman;
import com.example.exercise2.database.DBController;
import com.google.android.material.transition.Hold;

import java.util.ArrayList;
import java.util.HashMap;

public class TemanAdapter extends RecyclerView.Adapter<TemanAdapter.TemanViewHolder> {

    private ArrayList<Teman> listData;
    public TemanAdapter(ArrayList<Teman> listData) {
        this.listData = listData;
    }

    @Override
    public TemanViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInf = LayoutInflater.from(parent.getContext());
        View view = layoutInf.inflate(R.layout.row_data_teman, parent, false);
        return new TemanViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TemanViewHolder holder, int position) {
        String nama, telpon;

        nama = listData.get(position).getNama();
        telpon = listData.get(position).getTelpon();

        holder.tvNama.setTextColor(Color.BLACK);
        holder.tvNama.setText(nama);
        holder.tvNama.setTextSize(20);

        holder.tvTelpon.setText(telpon);
    }

    @Override
    public int getItemCount() {
        return (listData != null)?listData.size() : 0;
    }

    public class TemanViewHolder extends RecyclerView.ViewHolder {
        private CardView cardKu;
        private TextView tvNama, tvTelpon;
        private Context context;
        String id, nama, telpon;
        Bundle bundle = new Bundle();
        DBController controller;
        public TemanViewHolder(View view) {
            super(view);
            cardKu = itemView.findViewById(R.id.kartuKu);
            tvNama = itemView.findViewById(R.id.textNama);
            tvTelpon = itemView.findViewById(R.id.textTelpon);
            context = itemView.getContext();
            controller = new DBController(context);

            cardKu.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    nama = listData.get(getAdapterPosition()).getNama();
                    telpon = listData.get(getAdapterPosition()).getTelpon();

                    bundle.putString("nama", nama.trim());
                    bundle.putString("telpon", telpon.trim());

                    Intent intent = new Intent(context, LihatTeman.class);
                    intent.putExtras(bundle);
                    context.startActivity(intent);
                }
            });

            cardKu.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {

                    PopupMenu pop = new PopupMenu(context, view);
                    pop.inflate(R.menu.popup_menu);

                    pop.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {

                        @Override
                        public boolean onMenuItemClick(MenuItem item) {
                            switch (item.getItemId()) {
                                case R.id.mnEdit:
                                    id = listData.get(getAdapterPosition()).getId();
                                    nama = listData.get(getAdapterPosition()).getNama();
                                    telpon = listData.get(getAdapterPosition()).getTelpon();

                                    bundle.putString("id", id.trim());
                                    bundle.putString("nama", nama.trim());
                                    bundle.putString("telpon", telpon.trim());

                                    Intent intent = new Intent(context, EditTeman.class);
                                    intent.putExtras(bundle);
                                    context.startActivity(intent);
                                    break;
                                case R.id.mnDelete:
                                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                                    builder.setTitle("Hapus Data");
                                    builder.setMessage("Yakin ingin di hapus?");
                                    builder.setCancelable(true);
                                    builder.setPositiveButton("Ya",
                                            new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialogInterface, int i) {
                                                    id = listData.get(getAdapterPosition()).getId();
                                                    controller.hapusData(id);

                                                    Intent intent = new Intent(context, MainActivity.class);
                                                    context.startActivity(intent);
                                                }
                                            }
                                    );
                                    builder.setNegativeButton("Batal",
                                            new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialogInterface, int i) {
                                                    dialogInterface.cancel();
                                                }
                                            }
                                    );
                                    AlertDialog alertDialog = builder.create();
                                    alertDialog.show();
                                    break;
                            }
                            return false;
                        }
                    });
                    pop.show();
                    return false;
                }
            });
        }
    }
}
