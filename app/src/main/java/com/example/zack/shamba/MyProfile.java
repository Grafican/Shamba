package com.example.zack.shamba;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.provider.MediaStore;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TableRow;

import java.io.FileNotFoundException;
import java.io.InputStream;

public class MyProfile extends AppCompatActivity implements View.OnClickListener {

    private static final int CAMERA_PIC_REQUEST = 1;
    PopupWindow popupWindow;
    TableRow tblRowGallery, tblRowCamera, tblRowRemove;
    int PICK_PHOTO_FOR_PROFILE = 0;
    ImageView profPic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_profile);

        profPic = (ImageView)findViewById(R.id.profilePicture);
    }

    public void takePicture(View view) {
chooseImageSource();
    }


    private void chooseImageSource() {

        try {
            LayoutInflater inflater = (LayoutInflater) MyProfile.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View layout = inflater.inflate(R.layout.activity_popup__choose_image_source, (ViewGroup) findViewById(R.id.toPopUpWindow));

            tblRowGallery = (TableRow) layout.findViewById(R.id.galleryPhoto);
            tblRowCamera   = (TableRow) layout.findViewById(R.id.cameraPhoto);
            tblRowRemove = (TableRow) layout.findViewById(R.id.removePic);
            tblRowGallery.setOnClickListener(this);
            tblRowCamera.setOnClickListener(this);
            tblRowRemove.setOnClickListener(this);

            popupWindow = new PopupWindow(layout, CoordinatorLayout.LayoutParams.WRAP_CONTENT, CoordinatorLayout.LayoutParams.WRAP_CONTENT, true);

            popupWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            popupWindow.setOutsideTouchable(true);

            popupWindow.showAtLocation(layout, Gravity.CENTER, 0, 0);
            popupWindow.setFocusable(true);

        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.galleryPhoto:
                ChooseFromGallery();
                break;
            case R.id.cameraPhoto:
openCamera();
                break;
            case R.id.removePic:

                break;

        }
    }




    public  void ChooseFromGallery(){
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        startActivityForResult(intent, PICK_PHOTO_FOR_PROFILE);

    }

    private void openCamera() {

       /* Intent i = new Intent("android.media.action.IMAGE_CAPTURE");
        startActivity(i);*/

        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(cameraIntent, CAMERA_PIC_REQUEST);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_PHOTO_FOR_PROFILE && resultCode == Activity.RESULT_OK){
            popupWindow.dismiss();

            if (data == null){
                //display error

                return;
            }
            try {
                InputStream inputStream = getApplicationContext().getContentResolver().openInputStream(data.getData());


            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }else if (requestCode == CAMERA_PIC_REQUEST && resultCode == Activity.RESULT_OK){

            popupWindow.dismiss();

            Bitmap cameraPic = (Bitmap) data.getExtras().get("data");
            profPic.setImageBitmap(cameraPic);

        }
    }





}
