package com.example.rodryvazquez.buildlogionapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.example.rodryvazquez.buildlogionapp.Helpers.ApplicationConstants;

public class IntentReceiverActivity extends AppCompatActivity {

    //
    TextView txtUserNameLabel, txtPasswordLabel;

    /**
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intent_receiver);

        txtUserNameLabel = (TextView)findViewById(R.id.txtUserNameReceiverValue);
        txtPasswordLabel = (TextView)findViewById(R.id.txtPasswordReceiverValue);

        //Obtenemos los datos del intent
        Bundle bundle = getIntent().getExtras();
        String result1 = bundle.getString(ApplicationConstants.KEY_USERNAME);
        String result2 = bundle.getString(ApplicationConstants.KEY_PASSWORD);

        //Mostramos las credenciales en el layout
        txtUserNameLabel.setText(result1);
        txtPasswordLabel.setText(result2);

        Log.d(ApplicationConstants.TAG_RECEIVER_ACTIVITY,result1);
        Log.d(ApplicationConstants.TAG_RECEIVER_ACTIVITY,result2);
    }
}
