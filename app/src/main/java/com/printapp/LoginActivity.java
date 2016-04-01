package com.printapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.vk.sdk.VKAccessToken;
import com.vk.sdk.VKCallback;
import com.vk.sdk.VKSdk;
import com.vk.sdk.api.VKError;

public class LoginActivity extends AppCompatActivity {
    boolean expired = true;
    String token;
    Button loginButton;
    Button logoutButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        loginButton = (Button) findViewById(R.id.loginButton);
        logoutButton = (Button) findViewById(R.id.logoutButton);

        if(VKAccessToken.tokenFromSharedPreferences(getApplicationContext(),"TOKEN")!=null) {
            System.out.println("! " + (token = VKAccessToken.tokenFromSharedPreferences(getApplicationContext(), "TOKEN").accessToken));
            System.out.println("! " + (expired = VKAccessToken.tokenFromSharedPreferences(getApplicationContext(), "TOKEN").isExpired()));
        }
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!expired){
                    Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                    intent.putExtra("TOKEN",token);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                }else {
                    VKSdk.login((Activity) v.getContext(), "photos", "offline");
                }
            }
        });
        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                VKSdk.logout();
                VKAccessToken.removeTokenAtKey(getApplicationContext(),"TOKEN");
                expired = true;
                token = null;
                Toast.makeText(getApplicationContext(),"Пользователь вышел из системы",Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(!VKSdk.onActivityResult(requestCode, resultCode, data, new VKCallback<VKAccessToken>() {
            @Override
            public void onResult(VKAccessToken res) {
                Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                intent.putExtra("TOKEN",res.accessToken);
                res.saveTokenToSharedPreferences(getApplicationContext(),"TOKEN");
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }

            @Override
            public void onError(VKError error) {

            }
        }))
        super.onActivityResult(requestCode, resultCode, data);

    }
}
