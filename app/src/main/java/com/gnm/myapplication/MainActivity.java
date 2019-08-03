package com.gnm.myapplication;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.gnm.myapplication.adapter.AdapterBarang;
import com.gnm.myapplication.api.Api;
import com.gnm.myapplication.api.ApiInterface;
import com.gnm.myapplication.model.Barang;
import com.gnm.myapplication.model.Result;
import com.gnm.myapplication.utils.RecyclerTouchListener;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.gnm.myapplication.BuildConfig.BASE_URL;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.rv)
    RecyclerView recyclerView;

    @BindView(R.id.btnAdd)
    FloatingActionButton btnAdd;

    AdapterBarang Adapter;
    java.util.List<Barang> List = new ArrayList<>();
    ApiInterface Service;
    retrofit2.Call<Result> Call;
    AlertDialog.Builder alertDialogBuilder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initView();
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AddActivity.class);
                startActivity(intent);
            }
        });
        getBarang();
    }

    void initView() {
        alertDialogBuilder = new AlertDialog.Builder(this);
        Adapter = new AdapterBarang(this);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        recyclerView.setHasFixedSize(true);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (dy > 0 && btnAdd.getVisibility() == View.VISIBLE) {
                    btnAdd.hide();
                } else if (dy < 0 && btnAdd.getVisibility() != View.VISIBLE) {
                    btnAdd.show();
                }
            }
        });
        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(this,
                recyclerView, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, final int position) {
                showActionsDialog(position, List.get(position).getmId());
            }

            @Override
            public void onLongClick(View view, int position) {
                showActionsDialog(position, List.get(position).getmId());
            }
        }));
    }

    private void showActionsDialog(final int position, final int id) {
        CharSequence colors[] = new CharSequence[]{"Ubah", "Hapus"};

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Pilih Aksi: ");
        builder.setItems(colors, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (which == 0) {
                    Intent intent = new Intent(MainActivity.this, AddActivity.class);
                    intent.putExtra("id", String.valueOf(List.get(position).getmId()));
                    intent.putExtra("nama", List.get(position).getmNama());
                    intent.putExtra("stok", String.valueOf(List.get(position).getmStok()));
                    intent.putExtra("image", List.get(position).getmImage());
                    startActivity(intent);
                } else {
                    alertDialogBuilder.setTitle("Hapus");
                    alertDialogBuilder
                            .setMessage("Data akan dihapus?")

                            .setCancelable(false)
                            .setPositiveButton("Ya",
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialogInterface, int ids) {
                                            Gson gson = new GsonBuilder()
                                                    .setLenient()
                                                    .create();

                                            Retrofit retrofit = new Retrofit.Builder()
                                                    .baseUrl(BASE_URL)
                                                    .addConverterFactory(GsonConverterFactory.create(gson))
                                                    .build();

                                            ApiInterface api = retrofit.create(ApiInterface.class);
                                            Call<Result> Call = api.deleteBarang(id);
                                            Call.enqueue(new Callback<Result>() {
                                                @Override
                                                public void onResponse(retrofit2.Call<Result> call, Response<Result> response) {
                                                    String value = response.body().getValue();
                                                    String message = response.body().getMessage();
                                                    if (value.equals("1")) {
                                                        Toast.makeText(MainActivity.this, message, Toast.LENGTH_SHORT).show();
                                                        getBarang();

//                                                        finish();
                                                    } else {
                                                        Toast.makeText(MainActivity.this, message, Toast.LENGTH_SHORT).show();
                                                        getBarang();
                                                    }
                                                }

                                                @Override
                                                public void onFailure(Call<Result> call, Throwable t) {
                                                    t.printStackTrace();
                                                    Log.e("Error", String.valueOf(t));
                                                    Toast.makeText(MainActivity.this, "Jaringan Error!", Toast.LENGTH_SHORT).show();
                                                }
                                            });
                                            getBarang();
                                        }
                                    })
                            .setNegativeButton("Tidak",
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialogInterface, int id) {
                                            dialogInterface.cancel();
                                        }
                                    }).create().show();
                }
            }
        });
        builder.show();
    }

    @Override
    protected void onResume() {
        super.onResume();
        getBarang();
    }

    private void getBarang() {
        Service = Api.getApi().create(ApiInterface.class);
        Call = Service.getBarang();
        Call.enqueue(new Callback<Result>() {
            @Override
            public void onResponse(Call<Result> call, Response<Result> response) {
                List.clear();
                if (response.body() != null) {
                    List = response.body().getmResultBarang();
                }
                Adapter.setDetail(List);
                recyclerView.setAdapter(Adapter);
            }

            @Override
            public void onFailure(Call<Result> call, Throwable t) {
                Log.e("Errorrr", t.toString());
            }
        });

    }
}
