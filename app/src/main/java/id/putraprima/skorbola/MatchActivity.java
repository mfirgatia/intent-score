package id.putraprima.skorbola;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;

public class MatchActivity extends AppCompatActivity {

    private TextView homeText;
    private TextView awayText;
    private TextView score;

    private static final String TAG = MainActivity.class.getCanonicalName();
    private ImageView homeLogo, awayLogo;

    private int scoreHome, scoreAway;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_match);
        //TODO
        //1.Menampilkan detail match sesuai data dari main activity
        //2.Tombol add score menambahkan satu angka dari angka 0, setiap kali di tekan
        //3.Tombol Cek Result menghitung pemenang dari kedua tim dan mengirim nama pemenang ke ResultActivity, jika seri di kirim text "Draw"

        homeText = findViewById(R.id.txt_home);
        awayText = findViewById(R.id.txt_away);

        homeLogo = findViewById(R.id.home_logo);
        awayLogo = findViewById(R.id.away_logo);

        Bundle extras = getIntent().getExtras();
        Uri homelogoUri = Uri.parse(extras.getString("homeUri"));
        Uri awaylogoUri = Uri.parse(extras.getString("awayUri"));

        if (extras != null){
            homeText.setText(extras.getString("homeTeam"));
            awayText.setText(extras.getString("awayTeam"));
            try{
                Bitmap homeBitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), homelogoUri);
                Bitmap awayBitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), awaylogoUri);
                homeLogo.setImageBitmap(homeBitmap);
                awayLogo.setImageBitmap(awayBitmap);
            }catch (IOException e){
                Toast.makeText(this, "Can't load imgae", Toast.LENGTH_SHORT).show();
                Log.e(TAG, e.getMessage());
            }
        }
    }

    public void addhomescore(View view){
        score = findViewById(R.id.score_home);
        scoreHome = Integer. valueOf(score.getText().toString());
        scoreHome += 1;
        score.setText(String.valueOf(scoreHome));
    }

    public void addawayscore(View view){
        score = findViewById(R.id.score_away);
        scoreAway = Integer. valueOf(score.getText().toString());
        scoreAway += 1;
        score.setText(String.valueOf(scoreAway));
    }

    public void tampilkanhasil (View view){
        Intent itn = new Intent(this, ResultActivity.class);

        if (scoreHome > scoreAway){
            itn.putExtra("result", "Congratulation Winner " + homeText.getText().toString());
        }else if (scoreHome < scoreAway){
            itn.putExtra("result", "Congratulation Winner " + awayText.getText().toString());
        }else {
            itn.putExtra("result", " DRAW ");
        }

        startActivity(itn);
    }
}
