package ab.appdev.drivemaster;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Point;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.google.zxing.WriterException;

import androidmads.library.qrgenearator.QRGContents;
import androidmads.library.qrgenearator.QRGEncoder;

public class DetectionSetupActivity extends AppCompatActivity {
    private SharedPreferences sharedpreferences;
    private SeekBar sensitivityBar;
    private TextView sensitivityShowingLabel;

    private ImageView qrCodeIV;
    private Bitmap bitmap;
    private QRGEncoder qrgEncoder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setup);


        sensitivityBar = findViewById(R.id.sensitivityBar);
        sensitivityShowingLabel = findViewById(R.id.sensitivity);

        sharedpreferences = getSharedPreferences(Configurable.SHAREDNAME, Context.MODE_PRIVATE);
        sensitivityShowingLabel.setText(sharedpreferences.getString(Configurable.SENSITIVITY, Configurable.DEFAULT_SENSITIVITY));


        sensitivityBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                sharedpreferences.edit().putString(Configurable.SENSITIVITY, progress + "").apply();

                if (0 <= progress & progress <= 3) {
                    //sensitivityBar.color
                    sensitivityShowingLabel.setText("BAJO ES MAS SEGURO");
                    sensitivityShowingLabel.setBackgroundColor(Color.GREEN);
                    sensitivityShowingLabel.setTextColor(Color.BLACK);
                } else if (4 <= progress && progress <= 8) {
                    sensitivityShowingLabel.setText("MEDIANAMENTE SEGURO");
                    sensitivityShowingLabel.setBackgroundColor(Color.YELLOW);
                    sensitivityShowingLabel.setTextColor(Color.BLACK);
                } else {
                    sensitivityShowingLabel.setText("NO SEGURO DEL TODO");
                    sensitivityShowingLabel.setBackgroundColor(Color.RED);
                    sensitivityShowingLabel.setTextColor(Color.WHITE);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                Toast.makeText(getApplicationContext(), "Elige la Sensibilidad", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        sensitivityBar.setProgress(Integer.parseInt(sharedpreferences.getString(Configurable.SENSITIVITY, Configurable.DEFAULT_SENSITIVITY)));


        qrCodeIV = findViewById(R.id.idIVQrcode);

        WindowManager manager = (WindowManager) getSystemService(WINDOW_SERVICE);

        // initializing a variable for default display.
        Display display = manager.getDefaultDisplay();

        // creating a variable for point which
        // is to be displayed in QR Code.
        Point point = new Point();
        display.getSize(point);

        // getting width and
        // height of a point
        int width = point.x;
        int height = point.y;

        // generating dimension from width and height.
        int dimen = Math.min(width, height);
        dimen = dimen * 3 / 4;

        // setting this dimensions inside our qr code
        // encoder to generate our qr code.
        qrgEncoder = new QRGEncoder(sharedpreferences.getString(Configurable.BRODCASTID, ""), null, QRGContents.Type.TEXT, dimen);
        try {
            // getting our qrcode in the form of bitmap.
            bitmap = qrgEncoder.encodeAsBitmap();
            // the bitmap is set inside our image
            // view using .setimagebitmap method.
            qrCodeIV.setImageBitmap(bitmap);
        } catch (WriterException e) {
            // this method is called for
            // exception handling.
            Log.e("Tag", e.toString());
        }

    }


    public void deRegister(View view) {
        sharedpreferences.edit().putString(Configurable.BRODCASTID, AESUtils.encrypt("trusttext" + AESUtils.sizedString(30))).apply();
        sharedpreferences.edit().putString(Configurable.APPMODE, "").apply();
        Intent intent = new Intent(getApplicationContext(), StartupActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        finishAffinity();
        startActivity(intent);
        finish();
    }
}