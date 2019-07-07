package com.example.permissiono;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    Button bt;
    ImageView im;
   public static final int c=123;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mypermission();
        bt=findViewById(R.id.button);
        im=findViewById(R.id.action_image);
        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(i,c);

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==RESULT_OK && requestCode==c){
            Bitmap ccc= (Bitmap) data.getExtras().get("data");
            im.setImageBitmap(ccc);
        }
    }
       private boolean mypermission(){
        int lacPerm= ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION);
        int lacCmr = ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA);
        List<String> lstprmneed=new ArrayList<>();
        if(lacPerm!= PackageManager.PERMISSION_GRANTED){
            lstprmneed.add(Manifest.permission.ACCESS_FINE_LOCATION);
        }
        if(lacCmr!= PackageManager.PERMISSION_GRANTED){
               lstprmneed.add(Manifest.permission.CAMERA);
        }
        if(!lstprmneed.isEmpty()){
            ActivityCompat.requestPermissions(this,lstprmneed.toArray(new String[lstprmneed.size()]),c);
            return false;
        }
        return true;
    }
    public void onRequestPermissionResult(int requestcode,String permissions[],int[] grantResult){
       switch (requestcode){
           case c: {
               Map<String,Integer> perms=new HashMap<>();
               perms.put(Manifest.permission.ACCESS_FINE_LOCATION,PackageManager.PERMISSION_GRANTED);
               perms.put(Manifest.permission.CAMERA,PackageManager.PERMISSION_GRANTED);
               if(grantResult.length > 0){
                   for(int i=0;i<permissions.length;i++)
                       perms.put(permissions[i],grantResult[i]);
                   if(perms.get(Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
                           && perms.get(Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED){}
                   else{
                       Toast.makeText(this, "all permission required", Toast.LENGTH_SHORT).show();
                   }
               }
           }
       }
    }
}
