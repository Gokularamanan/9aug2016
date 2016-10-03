package com.example.rbp687.a9aug2016;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.rbp687.a9aug2016.mFireBase.FireBaseClient;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;


import com.google.firebase.*;
import com.google.firebase.database.snapshot.LongNode;
import com.google.firebase.internal.NonNull;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import static com.example.rbp687.a9aug2016.Constants.fireBase;

public class Userinput extends AppCompatActivity {

    //TODO: Gokul:- Permission handle: Camera, Storage

    private static final int REQUEST_IMAGE_CAMERA = 101;
    private static final int REQUEST_IMAGE_GALLERY = 102;
    private static final int PLACE_PICKER_REQUEST = 103;
    private final String TAG = this.getClass().getName();

    ImageView imageButton;
    EditText statusEditText;
    EditText editTextTitle;
    EditText editTextDesc;
    String picturePath;
    Bitmap bitmap;
    Uri downloadUrl;
    FireBaseClient fireBaseClient;
    EditText editTextMap;
    Button editTextMapAddress;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_userinput);

        if(BuildConfig.DEBUG) {
            TextInputLayout textInputLayout = (TextInputLayout)findViewById(R.id.statusLayoutt);
            textInputLayout.setVisibility(View.VISIBLE);
            statusEditText = (EditText)findViewById(R.id.statusEditText);
            statusEditText.setText(Integer.toString(Constants.publishReviewPending));
        }

        Log.d(TAG, "onCreate");

        fireBaseClient = new FireBaseClient(this,Constants.fireBaseBaseUrl);

        editTextTitle = (EditText) findViewById(R.id.editTextTitle);
        imageButton = (ImageView) findViewById(R.id.imageButton);
        editTextDesc = (EditText) findViewById(R.id.editTextDesc);
        editTextMap = (EditText) findViewById(R.id.editTextMap);
        editTextMapAddress = (Button) findViewById(R.id.editTextMapAddress);


        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectImage();
            }
        });

        editTextMapAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick-Map button");
                PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();
                try {
                    startActivityForResult(builder.build(Userinput.this), PLACE_PICKER_REQUEST);
                } catch (GooglePlayServicesRepairableException e) {
                    e.printStackTrace();
                } catch (GooglePlayServicesNotAvailableException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main_user, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            if (!TextUtils.isEmpty(editTextTitle.getText().toString()) &&
                    downloadUrl != null &&
                    !TextUtils.isEmpty(editTextTitle.getText().toString())) {
                int status = Constants.publishReviewPending;
                try {
                    status = Integer.parseInt(statusEditText.getText().toString());
                } catch (Exception e) {
                    Log.d(TAG, e.toString());
                }
                fireBaseClient.saveOnline(editTextTitle.getText().toString(),
                        downloadUrl.toString(),
                        editTextDesc.getText().toString(), status);
                return true;
            }else {
                Log.e(TAG, "Field not populated");
            }
        }
        return super.onOptionsItemSelected(item);
    }

    private void saveInFireBase() {
        Log.d(TAG, "saveInFireBase");
        // Create a storage reference from our app
        StorageReference storageRef = FirebaseStorage.getInstance().getReference().child(Constants.userId);
        Log.d(TAG, "toString:"  + storageRef.toString());
        Log.d(TAG, "getBucket:"  + storageRef.getBucket());
        Log.d(TAG, "getName:"  + storageRef.getName());
        Log.d(TAG, "getPath:"  + storageRef.getPath());


        // Create a reference to "chicken.jpg"
        StorageReference mountainsRef = storageRef.child("images/"+ String.valueOf(System.currentTimeMillis()));

/*        // Create a reference to 'images/chicken.jpg'
        StorageReference mountainImagesRef = storageRef.child("images/chicken.jpg");

        // While the file names are the same, the references point to different files
        mountainsRef.getName().equals(mountainImagesRef.getName());    // true
        mountainsRef.getPath().equals(mountainImagesRef.getPath());    // false*/

        // Get the data from an ImageView as bytes
        imageButton.setDrawingCacheEnabled(true);
        imageButton.buildDrawingCache();
        Bitmap bitmap = imageButton.getDrawingCache();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] data = baos.toByteArray();

        UploadTask uploadTask = mountainsRef.putBytes(data);
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                Log.d(TAG, "uploadTask-onFailure:" + exception.toString());
                // Handle unsuccessful uploads
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Log.d(TAG, "uploadTask-onSuccess");
                // taskSnapshot.getMetadata() contains file metadata such as size, content-type, and download URL.
                downloadUrl = taskSnapshot.getDownloadUrl();
                Log.d(TAG, downloadUrl.toString());
            }
        });
    }

    private void selectImage() {

        final CharSequence[] options = {"Take Photo", "Choose from Gallery", "Cancel"};

        AlertDialog.Builder builder = new AlertDialog.Builder(Userinput.this);
        builder.setTitle("Add Photo!");
        builder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (options[item].equals("Take Photo")) {
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    File f = new File(android.os.Environment.getExternalStorageDirectory(), "temp.jpg");
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(f));
                    startActivityForResult(intent, REQUEST_IMAGE_CAMERA);
                } else if (options[item].equals("Choose from Gallery")) {
                    Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(intent, REQUEST_IMAGE_GALLERY);

                } else if (options[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            Log.d(TAG, "onActivityResult-requestCode:" + String.valueOf(requestCode));
            if (requestCode == REQUEST_IMAGE_CAMERA) {
                File f = new File(Environment.getExternalStorageDirectory().toString());
                for (File temp : f.listFiles()) {
                    if (temp.getName().equals("temp.jpg")) {
                        f = temp;
                        break;
                    }
                }
                try {
                    BitmapFactory.Options bitmapOptions = new BitmapFactory.Options();

                    bitmap = BitmapFactory.decodeFile(f.getAbsolutePath(),
                            bitmapOptions);

                    imageButton.setImageBitmap(bitmap);

                    String path = android.os.Environment
                            .getExternalStorageDirectory()
                            + File.separator
                            + "Phoenix" + File.separator + "default";
                    f.delete();
                    OutputStream outFile = null;
                    File file = new File(path, String.valueOf(System.currentTimeMillis()) + ".jpg");
                    Log.d(TAG, "picturePath Camera: " + file.getAbsolutePath());
                    picturePath = file.getAbsolutePath();
                    saveInFireBase();
                    try {
                        outFile = new FileOutputStream(file);
                        bitmap.compress(Bitmap.CompressFormat.JPEG, 85, outFile);
                        outFile.flush();
                        outFile.close();
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else if (requestCode == REQUEST_IMAGE_GALLERY) {

                Uri selectedImage = data.getData();
                String[] filePath = {MediaStore.Images.Media.DATA};
                Cursor c = getContentResolver().query(selectedImage, filePath, null, null, null);
                c.moveToFirst();
                int columnIndex = c.getColumnIndex(filePath[0]);
                picturePath = c.getString(columnIndex);
                c.close();
                Bitmap thumbnail = (BitmapFactory.decodeFile(picturePath));
                Log.w(TAG, "picturePath Gallery: " + picturePath);
                imageButton.setImageBitmap(thumbnail);
                saveInFireBase();
            } else if (requestCode == PLACE_PICKER_REQUEST) {
                Place place = PlacePicker.getPlace(data, this);
                String toastMsg = String.format("Place: %s", place.getName());
                Log.d(TAG, toastMsg);
                final LatLng location = place.getLatLng();
                Log.d(TAG, "latitude:" + String.valueOf(location.latitude) + "longitude:" + String.valueOf(location.longitude));
                Toast.makeText(this, toastMsg, Toast.LENGTH_LONG).show();
            }
        }
    }
}
