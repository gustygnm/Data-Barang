package com.gnm.myapplication.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.gnm.myapplication.BuildConfig;
import com.gnm.myapplication.R;
import com.gnm.myapplication.api.Api;
import com.gnm.myapplication.api.ApiInterface;
import com.gnm.myapplication.model.Barang;
import com.gnm.myapplication.model.Result;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.gnm.myapplication.BuildConfig.BASE_URL;

public class AdapterBarang extends RecyclerView.Adapter<AdapterBarang.ItemDetailHolder> {

    private List<Barang> detailList = new ArrayList<>();
    private Context context;

    public AdapterBarang(Context context) {
        this.context = context;
    }

    @Override
    public ItemDetailHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ItemDetailHolder(
                LayoutInflater.from(parent.getContext()).inflate(R.layout.item_barang, parent, false)
        );
    }

    public void setDetail(List<Barang> detail) {
        this.detailList = detail;
    }

    public List<Barang> getList() {
        return detailList;
    }

    public void refresh() {
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(ItemDetailHolder holder, int position) {
        holder.bindView(detailList.get(position));
    }

    @Override
    public int getItemCount() {
        return detailList.size();
    }

    class ItemDetailHolder extends RecyclerView.ViewHolder {

//        ApiInterface Service;
//        retrofit2.Call<Result> Call;

        @BindView(R.id.imgCover)
        ImageView imgCover;

        @BindView(R.id.txtNama)
        TextView txtNama;

        @BindView(R.id.txtStok)
        TextView txtStok;
        AlertDialog.Builder alertDialogBuilder;

        ItemDetailHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            alertDialogBuilder = new AlertDialog.Builder(context);
        }

        int v;

        public void bindView(final Barang detail) {
            txtNama.setText(detail.getmNama());
            txtStok.setText("Stok : " + String.valueOf(detail.getmStok()));
            Picasso.get()
                    .load(BASE_URL + detail.getmImage())
                    .placeholder(R.mipmap.ic_launcher)
                    .error(R.mipmap.ic_launcher)
                    .into(imgCover);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                }
            });
        }
    }
}
