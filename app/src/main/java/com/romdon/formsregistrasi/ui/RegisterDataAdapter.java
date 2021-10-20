package com.romdon.formsregistrasi.ui;

import android.annotation.SuppressLint;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.romdon.formsregistrasi.R;
import com.romdon.formsregistrasi.model.RegisterData;

import java.util.ArrayList;
import java.util.List;

public class RegisterDataAdapter extends RecyclerView.Adapter<RegisterDataAdapter.DataViewHolder> {

    private final ArrayList<RegisterData> list = new ArrayList<>();

    @SuppressLint("NotifyDataSetChanged")
    public void setData(List<RegisterData> list) {
        this.list.clear();
        this.list.addAll(list);
        notifyDataSetChanged();
    }

    static class DataViewHolder extends RecyclerView.ViewHolder {
        private final ImageView imgPhoto;
        private final TextView tvId, tvName, tvAddress, tvLocation, tvPhoneNumber, tvGender;

        public DataViewHolder(View itemView) {
            super(itemView);
            imgPhoto = itemView.findViewById(R.id.imgfoto);
            tvId = itemView.findViewById(R.id.txtresultid);
            tvName = itemView.findViewById(R.id.txtresultname);
            tvAddress = itemView.findViewById(R.id.txtresultalamat);
            tvLocation = itemView.findViewById(R.id.txtresultlocation);
            tvPhoneNumber = itemView.findViewById(R.id.txtresultnohp);
            tvGender = itemView.findViewById(R.id.txtresultgender);
        }

        public void bind(RegisterData data) {
            tvId.setText(String.valueOf(data.getId()));
            tvName.setText(data.getName());
            tvAddress.setText(data.getAddress());
            tvLocation.setText(data.getLocation());
            tvPhoneNumber.setText(data.getPhoneNumber());
            tvGender.setText(data.getGender());

            Glide.with(itemView.getContext())
                    .load(Uri.parse(data.getPhoto()))
                    .into(imgPhoto);
        }
    }

    @NonNull
    @Override
    public DataViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item_data, parent, false);
        return new DataViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DataViewHolder holder, int position) {
        holder.bind(list.get(position));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
