package com.evatar.blackjack.views;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.evatar.blackjack.R;
import com.sxh.bcllib.IBclCallBack;
import com.sxh.bcllib.RpcConnectionStatusCallBack;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;


public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button playButton = (Button) findViewById(R.id.button_login);
        playButton.setOnClickListener(v -> login());

        Button registerButton = (Button) findViewById(R.id.button_register);
        registerButton.setOnClickListener(v -> register());

        if( !GlobalClass.getInstance().isBclSDKInit ) {
            GlobalClass.getInstance().bclSDK.init(this, "wss://zeus-api-xj30f.motion.one", "MTN", "MTN", "https://zeus-faucet-xj30f.motion.one", "b0a0f57617e69b033a23f9934e43c3a650974d4eace42d2251c2583c238b7ae2", new IBclCallBack() {
                @Override
                public void onReceiveValue(String s) {
                    Toast.makeText(MainActivity.this.getApplicationContext(), "BCL SDK Init Success.", Toast.LENGTH_SHORT).show();
                    GlobalClass.getInstance().isBclSDKInit = true;
                }
            }, new RpcConnectionStatusCallBack() {
                @Override
                public void callBack(String s) {
                    Toast.makeText(MainActivity.this.getApplicationContext(), "BCL SDK Init Success.", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    private void login() {

        EditText account = (EditText) findViewById(R.id.editText_act);
        EditText password = (EditText) findViewById(R.id.editText_pwd);

        GlobalClass.getInstance().bclSDK.login(account.getText().toString(), password.getText().toString(), new IBclCallBack() {
            @Override
            public void onReceiveValue(String s) {
                try {
                    JSONObject jsonObj = (JSONObject) new JSONTokener(s).nextValue();
                    int status = jsonObj.getInt("status");

                    if(status > 0) {
                        sendLoginMsg();
                    }else {
                        String statusText = jsonObj.getString("statusText");
                        Toast.makeText(getApplicationContext(), statusText, Toast.LENGTH_SHORT).show();
                    }
                }catch (JSONException e) {

                }
            }
        });

//        Intent intent = new Intent(MainActivity.this, GameActivity.class);
//        startActivity(intent);
        //MainActivity.this.finish();

    }

    private void sendLoginMsg(){
        GlobalClass.getInstance().bclSDK.transferAsset("jojojoey", "1.3.0","0","login", new IBclCallBack() {
            @Override
            public void onReceiveValue(String s) {
                Toast.makeText(MainActivity.this.getApplicationContext(), "LoginMsg sended.", Toast.LENGTH_SHORT).show();

                EditText account = (EditText) findViewById(R.id.editText_act);
                Intent intent = new Intent(MainActivity.this, GameActivity.class);
                startActivity(intent);
                MainActivity.this.finish();
                GlobalClass.instance.playerName = account.getText().toString();
            }
        });
    }

    private void register(){
        Intent intent = new Intent(this, RegisterActivity.class);
        startActivity(intent);
        this.finish();
    }
}
