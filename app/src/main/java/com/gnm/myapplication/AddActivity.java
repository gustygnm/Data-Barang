package com.gnm.myapplication;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.gnm.myapplication.api.Api;
import com.gnm.myapplication.api.ApiInterface;
import com.gnm.myapplication.model.Result;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.Base64;
import com.loopj.android.http.RequestParams;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;

import cz.msebera.android.httpclient.Header;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.gnm.myapplication.BuildConfig.BASE_URL;


@SuppressLint("NewApi")
public class AddActivity extends AppCompatActivity {

    ProgressBar prgDialog;
    Button btnSimpan;
    ImageView imgBarang;
    RelativeLayout mLayout;
    EditText txtNama, txtStok;

    String encodedString;
    String id, nama, stok, image;
    RequestParams params = new RequestParams();
    String imgPath, fileName;
    Bitmap bitmap;
    ApiInterface Service;
    retrofit2.Call<Result> Call;
    private static int RESULT_LOAD_IMG = 1;
    private static final int PERMISSION_REQUEST_CAMERA = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        btnSimpan = findViewById(R.id.btnSimpan);
        imgBarang = findViewById(R.id.imgBarang);
        prgDialog = findViewById(R.id.loading);
        mLayout = findViewById(R.id.mLayout);
        txtNama = findViewById(R.id.txtNama);
        txtStok = findViewById(R.id.txtStok);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        if (getIntent().getExtras() != null) {
            //do here
            getData();
            getSupportActionBar().setTitle("Edit Data");
        } else {
            getSupportActionBar().setTitle("Tambah Data");
        }

        btnSimpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (getIntent().getExtras() != null) {
                    updateBarang();
                } else {
                    saveBarang();
                }
            }
        });

        imgBarang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showCameraPreview();
            }
        });
    }

    public void getData() {
        Intent intent = getIntent();
        id = intent.getStringExtra("id");
        nama = intent.getStringExtra("nama");
        stok = intent.getStringExtra("stok");
        image = intent.getStringExtra("image");

        txtNama.setText(nama);
        txtStok.setText(stok);
        Picasso.get()
                .load(BASE_URL + image)
                .placeholder(R.mipmap.ic_launcher)
                .error(R.mipmap.ic_launcher)
                .into(imgBarang);
    }

    public void updateBarang() {
        prgDialog.setVisibility(View.VISIBLE);
        Service = Api.getApi().create(ApiInterface.class);
        if (txtNama.getText().toString().trim().equals("")) {
            txtNama.setError("Masukkan nama barang");
            txtNama.requestFocus();
        } else if (txtStok.getText().toString().trim().equals("")) {
            txtStok.setError("Masukkan stok barang");
            txtStok.requestFocus();
        } else {
            uploadImage();
            Gson gson = new GsonBuilder()
                    .setLenient()
                    .create();

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build();

            ApiInterface api = retrofit.create(ApiInterface.class);
            String url;
            if (fileName != null) {
                url = "/barang/img/" + fileName;
            } else {
                url = image;
            }
            Call<Result> Call = api.updateBarang(Integer.valueOf(id), txtNama.getText().toString(), Integer.valueOf(txtStok.getText().toString()), url);
            Call.enqueue(new Callback<Result>() {
                @Override
                public void onResponse(retrofit2.Call<Result> call, Response<Result> response) {
                    String value = response.body().getValue();
                    String message = response.body().getMessage();
                    if (value.equals("1")) {
                        Toast.makeText(AddActivity.this, message, Toast.LENGTH_SHORT).show();
                        finish();
                    } else {
                        Toast.makeText(AddActivity.this, message, Toast.LENGTH_SHORT).show();
                    }

                    prgDialog.setVisibility(View.GONE);
                }

                @Override
                public void onFailure(Call<Result> call, Throwable t) {
                    t.printStackTrace();
                    Log.e("Error", String.valueOf(t));
                    Toast.makeText(AddActivity.this, "Jaringan Error!", Toast.LENGTH_SHORT).show();
                    prgDialog.setVisibility(View.GONE);
                }
            });
        }
    }

    public void saveBarang() {
        prgDialog.setVisibility(View.VISIBLE);
        Service = Api.getApi().create(ApiInterface.class);
        if (txtNama.getText().toString().trim().equals("")) {
            txtNama.setError("Masukkan nama barang");
            txtNama.requestFocus();
        } else if (txtStok.getText().toString().trim().equals("")) {
            txtStok.setError("Masukkan stok barang");
            txtStok.requestFocus();
        } else {
            uploadImage();
            Call = Service.insertBarang(txtNama.getText().toString(), Integer.valueOf(txtStok.getText().toString()), "/barang/img/" + fileName);
            Call.enqueue(new Callback<Result>() {
                @Override
                public void onResponse(Call<Result> call, Response<Result> response) {
                    String value = response.body().getValue();
                    String message = response.body().getMessage();
                    if (value.equals("1")) {
                        Toast.makeText(AddActivity.this, message, Toast.LENGTH_SHORT).show();
                        finish();
                    } else {
                        Toast.makeText(AddActivity.this, message, Toast.LENGTH_SHORT).show();
                    }
                    prgDialog.setVisibility(View.GONE);
                }

                @Override
                public void onFailure(Call<Result> call, Throwable t) {
                    t.printStackTrace();
                    prgDialog.setVisibility(View.GONE);
                    Toast.makeText(AddActivity.this, "Jaringan Error!", Toast.LENGTH_SHORT).show();
                    prgDialog.setVisibility(View.GONE);
                }
            });
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        // BEGIN_INCLUDE(onRequestPermissionsResult)
        if (requestCode == PERMISSION_REQUEST_CAMERA) {
            // Request for camera permission.
            if (grantResults.length == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission has been granted. Start camera preview Activity.
                loadImagefromGallery();
            } else {
                // Permission request was denied.
                Log.e("Error", "Permission request was denied.");
            }
        }
        // END_INCLUDE(onRequestPermissionsResult)
    }

    private void showCameraPreview() {
        // BEGIN_INCLUDE(startCamera)
        // Check if the Camera permission has been granted
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
                == PackageManager.PERMISSION_GRANTED) {
            // Permission is already available, start camera preview
            loadImagefromGallery();
        } else {
            // Permission is missing and must be requested.
            requestCameraPermission();
        }
        // END_INCLUDE(startCamera)
    }

    private void requestCameraPermission() {
        // Permission has not been granted and must be requested.
        if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                Manifest.permission.READ_EXTERNAL_STORAGE)) {
            // Provide an additional rationale to the user if the permission was not granted
            // and the user would benefit from additional context for the use of the permission.
            // Display a SnackBar with cda button to request the missing permission.
            Snackbar.make(mLayout, R.string.camera_access_required,
                    Snackbar.LENGTH_INDEFINITE).setAction("OK", new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // Request the permission
                    ActivityCompat.requestPermissions(AddActivity.this,
                            new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                            PERMISSION_REQUEST_CAMERA);
                }
            }).show();

        } else {
//            Snackbar.make(mLayout, R.string.camera_unavailable, Snackbar.LENGTH_SHORT).show();
            // Request the permission. The result will be received in onRequestPermissionResult().
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, PERMISSION_REQUEST_CAMERA);
        }
    }

    public void loadImagefromGallery() {
        // Create intent to Open Image applications like Gallery, Google Photos
        Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        // Start the Intent
        startActivityForResult(galleryIntent, RESULT_LOAD_IMG);
    }

    // When Image is selected from Gallery
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
            // When an Image is picked
            if (requestCode == RESULT_LOAD_IMG && resultCode == RESULT_OK
                    && null != data) {
                // Get the Image from data
                Uri selectedImage = data.getData();
                String[] filePathColumn = {MediaStore.Images.Media.DATA};

                // Get the cursor
                Cursor cursor = getContentResolver().query(selectedImage,
                        filePathColumn, null, null, null);
                // Move to first row
                cursor.moveToFirst();

                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                imgPath = cursor.getString(columnIndex);
                cursor.close();
                // Set the Image in ImageView
                imgBarang.setImageBitmap(BitmapFactory
                        .decodeFile(imgPath));
                // Get the Image's file name
                String fileNameSegments[] = imgPath.split("/");
                fileName = fileNameSegments[fileNameSegments.length - 1];
                // Put file name in Async Http Post Param which will used in Php web app
                params.put("filename", fileName);

            } else {
                Toast.makeText(this, "You haven't picked Image",
                        Toast.LENGTH_LONG).show();
            }
        } catch (
                Exception e) {
            Toast.makeText(this, "Something went wrong", Toast.LENGTH_LONG)
                    .show();
        }

    }

    // When Upload button is clicked
    public void uploadImage() {
        // When Image is selected from Gallery
        if (imgPath != null && !imgPath.isEmpty()) {
            prgDialog.setVisibility(View.VISIBLE);
            // Convert image to String using Base64
            encodeImagetoString();
            // When Image is not selected from Gallery
        } else {
            Log.e("Error", "You must select image from gallery before you try to upload");
        }
    }

    // AsyncTask - To convert Image to String
    public void encodeImagetoString() {
        new AsyncTask<Void, Void, String>() {

            protected void onPreExecute() {

            }

            @Override
            protected String doInBackground(Void... params) {
                BitmapFactory.Options options = null;
                options = new BitmapFactory.Options();
                options.inSampleSize = 3;
                bitmap = BitmapFactory.decodeFile(imgPath,
                        options);
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                // Must compress the Image to reduce image size to make upload easy
                bitmap.compress(Bitmap.CompressFormat.PNG, 50, stream);
                byte[] byte_arr = stream.toByteArray();
                // Encode Image to String
                encodedString = Base64.encodeToString(byte_arr, 0);
                return "";
            }

            @Override
            protected void onPostExecute(String msg) {
                // Put converted Image string into Async Http Post param
                params.put("image", encodedString);
                // Trigger Image upload
                triggerImageUpload();
            }
        }.execute(null, null, null);
    }

    public void triggerImageUpload() {
        makeHTTPCall();
    }

    // Make Http call to upload Image to Php server
    public void makeHTTPCall() {
        AsyncHttpClient client = new AsyncHttpClient();
        // Don't forget to change the IP address to your LAN address. Port no as well.
        client.post("http://192.168.43.4/barang/uploadImage.php",
                params, new AsyncHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                        prgDialog.setVisibility(View.GONE);
//                        Toast.makeText(getApplicationContext(), String.valueOf(statusCode),
//                                Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                        // Hide Progress Dialog
                        prgDialog.setVisibility(View.GONE);
                        // When Http response code is '404'
                        if (statusCode == 404) {
                            Toast.makeText(getApplicationContext(),
                                    "Requested resource not found",
                                    Toast.LENGTH_LONG).show();
                        }
                        // When Http response code is '500'
                        else if (statusCode == 500) {
                            Toast.makeText(getApplicationContext(),
                                    "Something went wrong at server end",
                                    Toast.LENGTH_LONG).show();
                        }
                        // When Http response code other than 404, 500
                        else {
                            Toast.makeText(
                                    getApplicationContext(),
                                    "Error Occured n Most Common Error: n1. Device not connected to Internetn2. Web App is not deployed in App servern3. App server is not runningn HTTP Status code : "
                                            + statusCode, Toast.LENGTH_LONG)
                                    .show();
                        }
                    }

                });
    }

    @Override
    protected void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();
        // Dismiss the progress bar when application is closed
        if (prgDialog != null) {
            prgDialog.setVisibility(View.GONE);
        }
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
