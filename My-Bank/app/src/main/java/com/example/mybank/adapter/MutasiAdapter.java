package com.example.mybank.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mybank.R;
import com.example.mybank.model.Mutasi;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class MutasiAdapter extends RecyclerView.Adapter<MutasiAdapter.MutasiViewHolder> {
    Context context;
    ArrayList<Mutasi> mutasis;

    public MutasiAdapter(Context context, ArrayList<Mutasi> mutasis){
        this.context = context;
        this.mutasis = mutasis;
    }

    @NonNull
    @Override
    public MutasiAdapter.MutasiViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.mutasi_item, parent, false);
        return new MutasiViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MutasiAdapter.MutasiViewHolder holder, int position) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        System.out.println("Isi username di onBindViewHolder: " + mutasis.get(position).getUsername());
        holder.penerimaTV.setText(mutasis.get(position).getRekening_tujuan());
        holder.tanggalTV.setText(dateFormat.format(mutasis.get(position).getTransaction_date()));
        holder.uangTV.setText(String.valueOf(mutasis.get(position).getTrans_money()));
        holder.jenisTV.setText(mutasis.get(position).getTipe_transaksi());
    }

    @Override
    public int getItemCount() {
        return mutasis.size();
    }

    public class MutasiViewHolder extends RecyclerView.ViewHolder {
        TextView penerimaTV;
        TextView tanggalTV;
        TextView uangTV;
        TextView jenisTV;

        public MutasiViewHolder(@NonNull View itemView) {
            super(itemView);
            penerimaTV = itemView.findViewById(R.id.namaTV);
            tanggalTV = itemView.findViewById(R.id.tglMutasiTV);
            uangTV = itemView.findViewById(R.id.uangTV);
            jenisTV = itemView.findViewById(R.id.jenisTV);
        }
    }
}
