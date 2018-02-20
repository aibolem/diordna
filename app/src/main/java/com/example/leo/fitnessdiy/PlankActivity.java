package com.example.leo.fitnessdiy;

import android.content.Intent;
import android.net.Uri;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.leo.fitnessdiy.Utilities.NetworkUtils;
import com.example.leo.fitnessdiy.model.History;
import com.example.leo.fitnessdiy.model.Users;
import com.example.leo.fitnessdiy.routes.api;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

public class PlankActivity extends AppCompatActivity {
    private String LOG_TAG = "TES PLANK ACTIVITY";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plank);
        printUser(1);
    }

    public String milisecondToMinutes(long l){
        long minutes = (l/1000)/60;
        long seconds = (l/1000)%60;
        if (seconds >= 10)
            return minutes+" : "+seconds;
        else
            return minutes+" : 0"+seconds;
    }

    public void countDownPlank(View view) {
        final TextView countText = findViewById(R.id.count_timer);
        new CountDownTimer(120000, 1000){
            @Override
            public void onTick(long l) {
                String waktu = milisecondToMinutes(l);
                countText.setText(waktu);
            }

            @Override
            public void onFinish() {
                countText.setText("BERHASIL");
            }
        }.start();
    }

    public Users initializeData(String data) {
        Users user = new Users();
        try {
            JSONArray parser = new JSONArray(data);
            JSONObject json = parser.getJSONObject(0);

            int id = json.getInt("id");
            String username = json.getString("username");
            String password = json.getString("password");
            String email = json.getString("email");
            String phone_number = json.getString("phone_number");
            int age = json.getInt("age");
            String address = json.getString("address");
            String level = json.getString("level");
            user = new Users(id, username, password, email, phone_number, age, address, level);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return user;
    }

    public void printUser(int user_id){
        try {
            URL url = new URL("http://ekiwae21.000webhostapp.com/fitness-server/users.php?user=1");
            String fetchResults = NetworkUtils.getResponseFromHttpUrl(url);
            Users user = initializeData(fetchResults);

            Log.d(LOG_TAG, Integer.toString(user.getId()));
            Log.d(LOG_TAG, user.getLevel());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void openVideo(View view) {
        String url = (String)view.getTag();

        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_VIEW);
        intent.addCategory(Intent.CATEGORY_BROWSABLE);

        intent.setData(Uri.parse(url));
        startActivity(intent);
    }
}
