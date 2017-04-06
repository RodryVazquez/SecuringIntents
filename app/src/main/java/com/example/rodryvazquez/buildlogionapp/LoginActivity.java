package com.example.rodryvazquez.buildlogionapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.example.rodryvazquez.buildlogionapp.Helpers.ApplicationConstants;

public class LoginActivity extends AppCompatActivity {

    /**
     *
     */
    EditText edtUserName, edtPassword;

    /**
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        edtUserName = (EditText)findViewById(R.id.edtInputUserName);
        edtPassword = (EditText) findViewById(R.id.edtInputPassword);
    }

    /**
     *
     * @param v
     */
    public void submitInfo(View v) {
        Intent intent = new Intent();
        intent.putExtra(ApplicationConstants.KEY_USERNAME,edtUserName.getText().toString());
        intent.putExtra(ApplicationConstants.KEY_PASSWORD,edtPassword.getText().toString());
        intent.setAction(ApplicationConstants.KEY_INTENT_ACTION_LOGIN);
        intent.addCategory(Intent.CATEGORY_DEFAULT);
        //Asignamos el className al intent y lo transformamos de Implicito a Explicito por lo que se ignora el Intent-Filter
        //intent.setClassName("com.example.rodryvazquez.buildlogionapp","com.example.rodryvazquez.buildlogionapp.IntentReceiverActivity");
        startActivity(intent);
    }
}
