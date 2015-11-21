package it.jaschke.alexandria;

import android.content.Intent;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;

import com.google.zxing.Result;

import it.jaschke.alexandria.CameraPreview.ZXingScannerView;

/**
 * Created by Bin Li on 2015/11/20.
 */
public class BarcodeActivity extends ActionBarActivity implements ZXingScannerView.ResultHandler{

    private ZXingScannerView mScannerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mScannerView = new ZXingScannerView(this);
        setContentView(mScannerView);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mScannerView.setResultHandler(this);
        mScannerView.startCamera();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mScannerView.stopCamera();
    }

    @Override
    public void handleResult(Result rawResult) {
        try {
            Uri notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
            Ringtone r = RingtoneManager.getRingtone(getApplicationContext(), notification);
            r.play();

            setResult(RESULT_OK, new Intent().putExtra("content", rawResult.getText()));
            finish();
        } catch (Exception e) {}
        Log.e("test", "Contents = " + rawResult.getText() + ", Format = " + rawResult.getBarcodeFormat().toString());
    }
}