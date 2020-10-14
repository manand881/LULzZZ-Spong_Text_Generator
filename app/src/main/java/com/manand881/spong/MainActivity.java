package com.manand881.spong;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.play.core.appupdate.AppUpdateInfo;
import com.google.android.play.core.appupdate.AppUpdateManager;
import com.google.android.play.core.appupdate.AppUpdateManagerFactory;
import com.google.android.play.core.install.model.AppUpdateType;
import com.google.android.play.core.install.model.UpdateAvailability;
import com.google.android.play.core.tasks.Task;

import androidx.appcompat.app.AppCompatActivity;

import static android.content.ClipDescription.MIMETYPE_TEXT_PLAIN;

public class MainActivity extends AppCompatActivity {

    String Input_Text="";
    String Output_Text="";
    String pasteData = "";
    int Random_No;
    Character temp;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final EditText Input_Field   = (EditText)findViewById(R.id.firstinputedittext);
        final EditText Output_Text_Field   = (EditText)findViewById(R.id.Secondinputedittext);

        copyfromclipboard();
        Input_Field.setText(pasteData);


//        final WebView webview=(WebView) findViewById(R.id.memegen);
//        webview.getSettings().setJavaScriptEnabled(true);
////        webview.getSettings().setBuiltInZoomControls(true);
//
////        webview.loadUrl("file:///android_asset/spongebob.html");

        TextView txt=(TextView) findViewById(R.id.textView2);

        txt.setOnClickListener(new View.OnClickListener(){

            @Override
            //On click function
            public void onClick(View view) {
                String url = "https://github.com/manand881/";
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
            }
        });

        Button button = (Button) findViewById(R.id.cleartext);

        button.setOnClickListener(new View.OnClickListener(){
            @Override
            //On click function
            public void onClick(View view) {
                Input_Field.setText("");
                Output_Text_Field.setText("");
                Input_Text="";
                Output_Text="";
                Toast.makeText(getApplicationContext(),"Text Cleared",Toast.LENGTH_SHORT).show();
            }
        });

        Button button2 = (Button) findViewById(R.id.copytext);

        button2.setOnClickListener(new View.OnClickListener(){
            @Override
            //On click function
            public void onClick(View view) {

                ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clip = ClipData.newPlainText("Label",Output_Text);
                clipboard.setPrimaryClip(clip);
                Toast.makeText(getApplicationContext(),"Text Copied",Toast.LENGTH_SHORT).show();

            }

        });

        Button button3 = (Button) findViewById(R.id.generate);

        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            //On click function
            public void onClick(View view) {

                Input_Text = Input_Field.getText().toString();
                Output_Text = "";
                int stringlen = Input_Text.length();
                for (int i = 0; i < stringlen; i++) {

                    temp = Input_Text.charAt(i);
                    Random_No = coinToss();

                    if (Random_No == 1) {

                        temp = Character.toUpperCase(temp);
                        Output_Text = Output_Text + temp;

                    } else {

                        temp = Character.toLowerCase(temp);
                        Output_Text = Output_Text + temp;

                    }

                }

                Output_Text_Field.setText(Output_Text);

            }

    });

        Button button4 = (Button) findViewById(R.id.clearclipboard);

        button4.setOnClickListener(new View.OnClickListener(){
            @Override
            //On click function
            public void onClick(View view) {

                ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clip = ClipData.newPlainText("Label","");
                clipboard.setPrimaryClip(clip);
                Toast.makeText(getApplicationContext(),"Clipboard Cleared",Toast.LENGTH_SHORT).show();

            }

        });

        // Creates instance of the manager.
        AppUpdateManager appUpdateManager = AppUpdateManagerFactory.create(this);

        // Returns an intent object that you use to check for an update.
        Task<AppUpdateInfo> appUpdateInfoTask = appUpdateManager.getAppUpdateInfo();

        // Checks that the platform will allow the specified type of update.
        appUpdateInfoTask.addOnSuccessListener(appUpdateInfo -> {
            if (appUpdateInfo.updateAvailability() == UpdateAvailability.UPDATE_AVAILABLE
                    // For a flexible update, use AppUpdateType.FLEXIBLE
                    && appUpdateInfo.isUpdateTypeAllowed(AppUpdateType.IMMEDIATE)) {
                // Request the update.
            }
        });



}

//    @Override
//    public void onResume(){
//
//        WebView webview=(WebView) findViewById(R.id.memegen);
//        webview.loadUrl("file:///android_asset/spongebob.html");
//        super.onResume();
//
//    }


    @Override
    public void onBackPressed() {

        finishAffinity();
        System.exit(0);

    }

    public static int coinToss(){
        return (int)Math.round(Math.random());
    }

    @Override
    public void onResume(){
        super.onResume();
        final EditText Input_Field   = (EditText)findViewById(R.id.firstinputedittext);
        Button button3 = (Button) findViewById(R.id.generate);
        copyfromclipboard();
        Input_Field.setText(pasteData);
        button3.performClick();

    }

    public void copyfromclipboard(){
        ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);

        // If it does contain data, decide if you can handle the data.
        if (!(clipboard.hasPrimaryClip())) {

        } else if (!(clipboard.getPrimaryClipDescription().hasMimeType(MIMETYPE_TEXT_PLAIN))) {

            // since the clipboard has data but it is not plain text

        } else {

            //since the clipboard contains plain text.
            ClipData.Item item = clipboard.getPrimaryClip().getItemAt(0);

            // Gets the clipboard as text.
            pasteData = item.getText().toString();
            ClipData clip = ClipData.newPlainText("Label","");
            clipboard.setPrimaryClip(clip);

        }

    }
}
