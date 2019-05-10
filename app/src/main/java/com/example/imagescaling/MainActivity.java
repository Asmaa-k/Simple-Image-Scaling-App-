package com.example.imagescaling;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.SparseIntArray;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

public class MainActivity extends AppCompatActivity {
    private static final int PICK_IMAGE_REQUEST = 1;
    private Uri mImageUri;
    ImageView mImageView;
    Button  mirrorHor,mirrorVer;
    ImageView btnLeft, btnRight;
    Bitmap mCapturedBitmap;
    static int ROTATION_0 = 0;
    static int ROTATION_90 = 90;
    static int ROTATION_180 = 180;
    static int ROTATION_270 = 270;
    int glob = 0;
    float flip = -1.0f;

    private static final SparseIntArray ORIENTATIONS_RIGHT = new SparseIntArray();

    static {
        ORIENTATIONS_RIGHT.append(ROTATION_0, 270);
        ORIENTATIONS_RIGHT.append(ROTATION_90, 0);
        ORIENTATIONS_RIGHT.append(ROTATION_180, 90);
        ORIENTATIONS_RIGHT.append(ROTATION_270, 180);
    }

    private static final SparseIntArray ORIENTATIONS_LIFT = new SparseIntArray();

    static {
        ORIENTATIONS_LIFT.append(ROTATION_0, 90);
        ORIENTATIONS_LIFT.append(ROTATION_90, 180);
        ORIENTATIONS_LIFT.append(ROTATION_180, 270);
        ORIENTATIONS_LIFT.append(ROTATION_270, 0);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mImageView = findViewById(R.id.output_image);
        btnLeft = findViewById(R.id.button);
        btnRight = findViewById(R.id.button2);
        mirrorHor = findViewById(R.id.butto3);
        mirrorVer = findViewById(R.id.button4);

        btnRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rightRot();
            }
        });
        btnLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                leftRot();
            }
        });
        mirrorHor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                flippingHorizontal();
            }
        });

        mirrorVer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                flippingVertical();
            }
        });

    }

    private void flippingVertical() {
        // Vertical mirroring
        try {
            if (mImageUri != null) {
                Context context = getApplicationContext();
                Bitmap bMap = MediaStore.Images.Media.getBitmap(context.getContentResolver(), mImageUri);

                //Create object of new Matrix.
                Matrix matrix = new Matrix();
                if (flip == -1.0f) {
                    matrix.postScale(1.0f, flip);
                    flip = 1.0f;
                } else {
                    matrix.postScale(1.0f, flip);
                    flip = -1.0f;
                }
                matrix.postSkew(19,10);
                matrix.postRotate(glob);
                Bitmap bMapRotate = Bitmap.createBitmap(bMap, 0, 0, bMap.getWidth(), bMap.getHeight(), matrix, true);
                mImageView.setImageBitmap(bMapRotate);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void flippingHorizontal() {
        // Horizontal mirroring
        try {
            if (mImageUri != null) {
                Context context = getApplicationContext();
                Bitmap bMap = MediaStore.Images.Media.getBitmap(context.getContentResolver(), mImageUri);

                //Create object of new Matrix.
                Matrix matrix = new Matrix();
                if (flip == -1.0f) {
                    matrix.postScale(flip, 1.0f);
                    flip = 1.0f;
                } else {
                    matrix.postScale(flip, 1.0f);
                    flip = -1.0f;
                }
                matrix.postRotate(glob);
                Bitmap bMapRotate = Bitmap.createBitmap(bMap, 0, 0, bMap.getWidth(), bMap.getHeight(), matrix, true);
                mImageView.setImageBitmap(bMapRotate);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void leftRot() {
        try {
            if (mImageUri != null) {
                Context context = getApplicationContext();
                Bitmap bMap = MediaStore.Images.Media.getBitmap(context.getContentResolver(), mImageUri);

                //Create object of new Matrix.
                Matrix matrix = new Matrix();

                //set image rotation value to 90 degrees in matrix.
                switch (glob) {
                    case 0:
                        matrix.postRotate(ORIENTATIONS_LIFT.get(ROTATION_0));
                        glob = 90;
                        break;
                    case 90:
                        matrix.postRotate(ORIENTATIONS_LIFT.get(ROTATION_90));
                        glob = 180;
                        break;
                    case 180:
                        matrix.postRotate(ORIENTATIONS_LIFT.get(ROTATION_180));
                        glob = 270;
                        break;
                    case 270:
                        matrix.postRotate(ORIENTATIONS_LIFT.get(ROTATION_270));
                        glob = 0;
                        break;
                    default:
                        matrix.postRotate(0);
                        glob = 0;

                }
                //Create bitmap with new values.
                Bitmap bMapRotate = Bitmap.createBitmap(bMap, 0, 0, bMap.getWidth(), bMap.getHeight(), matrix, true);

                //put rotated image in ImageView.
                mImageView.setImageBitmap(bMapRotate);
            } else Toast.makeText(this, "Choose Image", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void rightRot() {

        try {
            if (mImageUri != null) {


                //Bitmap bMap;
                Context context = getApplicationContext();

                //Decode Image using Bitmap factory.
                Bitmap bMap = MediaStore.Images.Media.getBitmap(context.getContentResolver(), mImageUri);

                //Create object of new Matrix.
                Matrix matrix = new Matrix();

                //set image rotation value to 90 degrees in matrix.
                switch (glob) {
                    case 0:
                        matrix.postRotate(ORIENTATIONS_RIGHT.get(ROTATION_0));
                        glob = 270;
                        break;
                    case 90:
                        matrix.postRotate(ORIENTATIONS_RIGHT.get(ROTATION_90));
                        glob = 0;
                        break;
                    case 180:
                        matrix.postRotate(ORIENTATIONS_RIGHT.get(ROTATION_180));
                        glob = 90;
                        break;
                    case 270:
                        matrix.postRotate(ORIENTATIONS_RIGHT.get(ROTATION_270));
                        glob = 180;
                        break;
                    default:
                        matrix.postRotate(0);
                        glob = 0;

                }
                //Create bitmap with new values.
                Bitmap bMapRotate = Bitmap.createBitmap(bMap, 0, 0, bMap.getWidth(), bMap.getHeight(), matrix, true);

                //put rotated image in ImageView.
                mImageView.setImageBitmap(bMapRotate);
            } else Toast.makeText(this, "Choose Image", Toast.LENGTH_SHORT).show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void openFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK
                && data != null && data.getData() != null) {
            mImageUri = data.getData();

            Picasso.get()
                    .load(mImageUri)
                    .placeholder(R.mipmap.ic_launcher)
                    .into(mImageView);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.open_file_images, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.open_file) {
            openFileChooser();
        }
        return super.onOptionsItemSelected(item);
    }
}