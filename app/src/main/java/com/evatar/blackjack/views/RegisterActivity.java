package com.evatar.blackjack.views;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.evatar.blackjack.R;
import com.sxh.bcllib.IBclCallBack;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

public class RegisterActivity extends AppCompatActivity {

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_create_account);


            Button registerButton = (Button) findViewById(R.id.button_create_account);
            registerButton.setOnClickListener(v -> createAccount());

            Button cancelButton = (Button) findViewById(R.id.button_cancel);
            cancelButton.setOnClickListener(v -> cancel());
        }

        private void createAccount() {
            EditText account = (EditText) findViewById(R.id.editText_account);
            EditText password = (EditText) findViewById(R.id.editText_password);
            EditText passwordAgain = (EditText) findViewById(R.id.editText_password_again);

            String accountStr = account.getText().toString();
            String passwordStr = password.getText().toString();
            String passwordAgainStr = passwordAgain.getText().toString();

            if ( accountStr.equals("") || passwordStr.equals("") || passwordAgain.equals("")) {
                Toast.makeText(getApplicationContext(), "cannot empty", Toast.LENGTH_SHORT).show();
            }
            else if (!passwordStr.equals(passwordAgainStr)) {
                Toast.makeText(getApplicationContext(), "password not the same", Toast.LENGTH_SHORT).show();
            } else {
                GlobalClass.getInstance().bclSDK.signup(accountStr, passwordStr, new IBclCallBack() {
                    @Override
                    public void onReceiveValue(String s) {
                        int status = 0;
                        String statusText = "";
                        try {
                            JSONObject jsonObj = (JSONObject) new JSONTokener(s).nextValue();
                            status = jsonObj.getInt("status");
                            statusText = jsonObj.getString("statusText");
                        }catch (JSONException e) {

                        }

                        if( status < 0) {
                            Toast.makeText(getApplicationContext(), statusText, Toast.LENGTH_SHORT).show();
                        }
                        else {
                            new AlertDialog.Builder(RegisterActivity.this)
                                    .setTitle("Create account")
                                    .setMessage("create success!")
                                    .setPositiveButton("play it", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            Intent intent = new Intent(RegisterActivity.this, GameActivity.class);
                                            startActivity(intent);
                                            RegisterActivity.this.finish();
                                            GlobalClass.instance.playerName = account.getText().toString();
                                        }
                                    })
                                    .show();
                        }
                    }
                });
            }
        }

        private void cancel() {
            Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
            startActivity(intent);
            RegisterActivity.this.finish();
        }
}
