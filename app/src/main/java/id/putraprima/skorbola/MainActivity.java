package id.putraprima.skorbola;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.IOException;
import java.time.Instant;

public class MainActivity extends AppCompatActivity {

    private EditText HomeTeamInput;
    private EditText AwayTeamInput;

    private static final String TAG=MainActivity.class.getCanonicalName();
    private static final int GALLERY_REQUEST_CODE=1;
    private boolean isHome = true;
    private ImageView homelogo, awaylogo;
    private Uri homeUri, awayUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //TODO
        //Fitur Main Activity
        //1. Validasi Input Home Team
        //2. Validasi Input Away Team
        //3. Ganti Logo Home Team
        //4. Ganti Logo Away Team
        //5. Next Button Pindah Ke MatchActivity

        HomeTeamInput = findViewById(R.id.home_team);
        AwayTeamInput = findViewById(R.id.away_team);
        homelogo = findViewById(R.id.home_logo);
        awaylogo = findViewById(R.id.away_logo);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode == RESULT_CANCELED){
            return;
        }

        if(requestCode == GALLERY_REQUEST_CODE){
            if(isHome){
                if(data !=  null){
                    try{
                        homeUri = data.getData();
                        Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), homeUri);
                        homelogo.setImageBitmap(bitmap);
                    }catch (IOException e){
                        Toast.makeText(this,"Can't load Image", Toast.LENGTH_SHORT).show();
                        Log.e(TAG, e.getMessage());
                    }
                }
            }else{
                if(data != null){
                    try{
                        awayUri = data.getData();
                        Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), awayUri);
                        awaylogo.setImageBitmap(bitmap);
                    }catch (IOException e){
                        Toast.makeText(this, "Can't loa Image", Toast.LENGTH_SHORT).show();
                        Log.e(TAG, e.getMessage());
                    }
                }
            }
        }
    }

    private void selectImage(Context context){
        Intent pickPhoto = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(pickPhoto, GALLERY_REQUEST_CODE);
    }

    public void handlehomelogo(View view) {
        isHome = true;
        selectImage(this);
    }

    public void handleawaylogo(View view) {
        isHome = false;
        selectImage(this);
    }

    public void handlenext(View view) {
        String homeTeamName = HomeTeamInput.getText().toString();
        String awayTeamName = AwayTeamInput.getText().toString();
        String homeUriSrting = homeUri.toString();
        String awayUriString = awayUri.toString();

        Intent iten = new Intent(MainActivity.this,MatchActivity.class);
        iten.putExtra("homeTeam", homeTeamName);
        iten.putExtra("awayTeam", awayTeamName);
        iten.putExtra("homeUri", homeUriSrting);
        iten.putExtra("awayUri", awayUriString);

        startActivity(iten);
    }
}
