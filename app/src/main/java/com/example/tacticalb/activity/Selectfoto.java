package com.example.tacticalb.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.Display;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.tacticalb.R;
import com.example.tacticalb.Service.Save_inf;
import com.example.tacticalb.dialog.Loading;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;

import es.dmoral.toasty.Toasty;


public class Selectfoto extends AppCompatActivity implements View.OnClickListener {
    Button choose_pc,choose_app,save;
    ImageView imageView;

    StorageReference mStorageRef;

    FirebaseStorage storage;
    StorageReference storageReference;

    Loading loading;
    private String mImageUri = "";
    int Type_selection=-1;
    //-1-собственные
    //1-из библиотеки приложения

    static final String backfromlibrary ="comebacktoselect";
    static final String otv="choose";


    private static final int REQUEST_FOTO_LIBRARY = 101;

    Save_inf save_inf;
    DatabaseReference databaseReference;
    FirebaseUser user;

    private Uri filePath;

    private final int PICK_IMAGE_REQUEST = 71;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        setTheme(R.style.AppThemeGame);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.select_activity);
        save_inf= new Save_inf(this);
        choose_app=findViewById(R.id.choose_library);
        choose_pc=findViewById(R.id.choose_from);
        save=findViewById(R.id.save);
        imageView=findViewById(R.id.picture);

        choose_pc.setOnClickListener(this);
        choose_app.setOnClickListener(this);
        save.setOnClickListener(this);
        mImageUri=save_inf.getString(Save_inf.REMEMBER_FOR_URL_AVATAR).toString();

        mStorageRef = FirebaseStorage.getInstance().getReference();

        Glide.with(this).load(mImageUri).into(imageView);
        user = FirebaseAuth.getInstance().getCurrentUser();
        databaseReference= FirebaseDatabase.getInstance().getReference("USER").child(user.getUid());

        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();

        Display display = getWindowManager().getDefaultDisplay();
        DisplayMetrics metricsB = new DisplayMetrics();
        display.getMetrics(metricsB);
        if(metricsB.widthPixels/metricsB.density>700){

            choose_pc.setTextSize(TypedValue.COMPLEX_UNIT_SP,43);
            save.setTextSize(TypedValue.COMPLEX_UNIT_SP,43);
            choose_app.setTextSize(TypedValue.COMPLEX_UNIT_SP,43);
        }
        else if(metricsB.widthPixels/metricsB.density>600){
            choose_pc.setTextSize(TypedValue.COMPLEX_UNIT_SP,37);
            save.setTextSize(TypedValue.COMPLEX_UNIT_SP,37);
            choose_app.setTextSize(TypedValue.COMPLEX_UNIT_SP,37);
        }
        else if(metricsB.widthPixels/metricsB.density>500){
            choose_pc.setTextSize(TypedValue.COMPLEX_UNIT_SP,32);
            save.setTextSize(TypedValue.COMPLEX_UNIT_SP,32);
            choose_app.setTextSize(TypedValue.COMPLEX_UNIT_SP,32);
        }
        else if(metricsB.widthPixels/metricsB.density>400){
            choose_pc.setTextSize(TypedValue.COMPLEX_UNIT_SP,28);
            save.setTextSize(TypedValue.COMPLEX_UNIT_SP,28);
            choose_app.setTextSize(TypedValue.COMPLEX_UNIT_SP,28);
        }
        else{
            choose_pc.setTextSize(TypedValue.COMPLEX_UNIT_SP,25);
            save.setTextSize(TypedValue.COMPLEX_UNIT_SP,25);
            choose_app.setTextSize(TypedValue.COMPLEX_UNIT_SP,25);
        }




    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.save:{

//Сохранение информации о сохраненых аватарках

                switch (Type_selection){
                    case -1:
                        uploadImage();
                        break;
                    case 1: Intent answer= new Intent();
                        DatabaseReference databaseReference1= databaseReference.child("uri_pic");
                       databaseReference1.setValue(mImageUri);  answer.putExtra(Setting.backTypeselection,Type_selection);
                        setResult(RESULT_OK,answer); finish();
                        break;
                }



            }break;
            case R.id.choose_from:{
              chooseImage();
            }break;
            case R.id.choose_library:{
                Intent intent=new Intent(this,Liblarypicture.class);
                startActivityForResult(intent,REQUEST_FOTO_LIBRARY);
            }break;
        }
    }
    //метод открытия изображений устроства для выбора собствееной аватарки  пользователя
    private void chooseImage() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
    }

    @Override
    public void onActivityResult(int requestCode,int resultCode, Intent data){
        super.onActivityResult(requestCode , resultCode , data);
        switch (requestCode){

            //обработка выходов из активностей выхода
            case PICK_IMAGE_REQUEST:
                Type_selection=-1;
                    if( resultCode == RESULT_OK
                    && data != null && data.getData() != null )
            {
                filePath = data.getData();
                try {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                    imageView.setImageBitmap(bitmap);
                }
                catch (IOException e)
                {
                    e.printStackTrace();
                }
            }break;
            case REQUEST_FOTO_LIBRARY:
                if (resultCode==RESULT_OK) {
                    Glide.with(this).load(data.getStringExtra(backfromlibrary)).into(imageView);
                    mImageUri=data.getStringExtra(backfromlibrary);
                    Type_selection=1;
                }break;
        }
    }

//Закачивание собственной картинки  в Firebase Storage
    private void uploadImage() {

        if(filePath != null)
        {
           loading= new Loading(this);

        loading.show();
            mStorageRef = FirebaseStorage.getInstance().getReference();

            StorageReference ref = storageReference.child("images/"+ save_inf.getHero( ).get_ID().toString());
            ref.putFile(filePath)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            mStorageRef.child("images/" + save_inf.getHero( ).get_ID()).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {

                                    DatabaseReference databaseReference1= databaseReference.child("uri_pic");
                                    databaseReference1.setValue(uri.toString()).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()) {
                                                Toasty.success(getApplicationContext(), getResources().getString(R.string.loading), Toasty.LENGTH_LONG, true).show();
                                                Intent answer= new Intent();
                                                 answer.putExtra(Setting.backTypeselection,Type_selection);
                                                setResult(RESULT_OK,answer);   loading.dismiss();
                                                finish();


                                            }else
                                                Toasty.error(getApplicationContext(),getResources().getString(R.string.error), Toasty.LENGTH_LONG,true).show();
                                        }
                                    });

                                   //Toast.makeText(getApplicationContext(), "Uploaded", Toast.LENGTH_SHORT).show();
                                }
                            });

                        }
                    });

        }
    }
}
