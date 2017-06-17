package dylan.grabadora_de_video;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class Activity_One extends AppCompatActivity {

    private static final int IMAGE_CAPTURE=115;
    private static final int VIDEO_CAPTURE=101;
    Button video, camara;
    ConstraintLayout cons;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity__one);

        video=(Button)findViewById(R.id.BtnVideo);
        camara=(Button)findViewById(R.id.BtnPhoto);


        int permission = ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA);
        if(permission != PackageManager.PERMISSION_GRANTED){

            makeRequest();
        }if(!hasCamera()){
            video.setEnabled(false);
        }

        video.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RecordVideo();
            }
        });

        camara.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TakePhoto();
            }
        });
    }

    protected void makeRequest(){
        ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.CAMERA},IMAGE_CAPTURE);
    }
    private boolean hasCamera(){
        boolean has= getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_ANY);
        return has;
    }
    public void TakePhoto(){
        Intent intentphoto=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intentphoto,IMAGE_CAPTURE);
    }
    public void RecordVideo(){
        Intent intentvideo=new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
        startActivityForResult(intentvideo, VIDEO_CAPTURE);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data){

        Uri VideoUri= data.getData();
        if(requestCode==VIDEO_CAPTURE){
            if(resultCode==RESULT_OK){
                Toast.makeText(this, "Tu Video en:\n"+VideoUri, Toast.LENGTH_SHORT).show();
            }
            else if(resultCode==RESULT_CANCELED){
                Toast.makeText(this,"Grabación cancelada por puto",Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(this,"algo fallo vlv", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,String Permissions[],int[]grantResults){
        switch(requestCode){
            case IMAGE_CAPTURE:{
                if(grantResults.length==0||grantResults[0]!=PackageManager.PERMISSION_GRANTED){
                    cons=(ConstraintLayout)findViewById(R.id.idcons);
                    Toast.makeText(this,"Denegado",Toast.LENGTH_SHORT).show();
                    video.setEnabled(false);
                    camara.setEnabled(false);
                    //Snackbar.make(cons,"compra un cel wey",Snackbar.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(this,"aprobado",Toast.LENGTH_SHORT).show();
                }
            }
        }
    }



}
