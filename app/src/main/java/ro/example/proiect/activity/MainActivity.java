package ro.example.proiect.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;

import ro.example.proiect.R;

public class MainActivity extends AppCompatActivity {

    private static final String CHANNEL_ID = "Canal notificare";
    static int notificationId = 5;

    TextView txtInfo;
    Button btnLogout;
    Button btnShowCities;

    SharedPreferences sharedPref;
    SharedPreferences.Editor sharedPrefEditor;

    GoogleSignInOptions gso;
    GoogleSignInClient googleSignInClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtInfo = findViewById(R.id.txtInfo);
        btnLogout = findViewById(R.id.btnLogOut);
        btnShowCities = findViewById(R.id.btnShowCities);

        sharedPref = getSharedPreferences(getString(R.string.loginPref), MODE_PRIVATE);
        String loginMode = sharedPref.getString("loginMode", "");

        checkLogin();

        setNotification();

        btnLogout.setOnClickListener(v -> {
            if(loginMode.equals("google")) {
                googleLogout();
            }
            else{
                if(loginMode.equals("custom")){
                    logout();
                }
            }
        });

        btnShowCities.setOnClickListener(v -> {
            Intent i = new Intent(MainActivity.this, CitiesActivity.class);
            startActivity(i);
        });
    }

    private void setNotification() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = getString(R.string.channel_name);
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name,
                    importance);
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }

        Intent intent = new Intent(this, CitiesActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);

        NotificationCompat.Builder builder =
                new NotificationCompat.Builder(this, CHANNEL_ID)
                        .setSmallIcon(R.drawable.ic_launcher_foreground)
                        .setContentTitle("See new cities")
                        .setContentText("Why not discover your new favourite city?")
                        .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                        .setContentIntent(pendingIntent)
                        .setAutoCancel(true);

        NotificationManagerCompat notificationManager =
                NotificationManagerCompat.from(this);

        // notificationId is a unique int for each notification that you must define
        notificationManager.notify(notificationId, builder.build());
    }

    private void logout() {
        try {
            if (sharedPref == null)
                sharedPref = getSharedPreferences(getString(R.string.loginPref), MODE_PRIVATE);

            sharedPrefEditor = sharedPref.edit();
            sharedPrefEditor.putString("name", "");
            sharedPrefEditor.apply();

            Intent i = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(i);
            finish();

        } catch (Exception ex) {
            Toast.makeText(MainActivity.this, ex.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    private void googleLogout() {
        gso = new GoogleSignInOptions
                .Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        googleSignInClient = GoogleSignIn.getClient(this, gso);

        googleSignInClient.signOut()
                .addOnCompleteListener(this, task -> {
                    // [START_EXCLUDE]
                    if (sharedPref == null)
                        sharedPref = getSharedPreferences(getString(R.string.loginPref), MODE_PRIVATE);

                    sharedPrefEditor = sharedPref.edit();
                    sharedPrefEditor.putString("name", "");
                    sharedPrefEditor.apply();

                    Intent i = new Intent(MainActivity.this, LoginActivity.class);
                    startActivity(i);
                    // [END_EXCLUDE]
                });
    }

    private void checkLogin() {
        if (sharedPref == null)
            sharedPref = getSharedPreferences(getString(R.string.loginPref), MODE_PRIVATE);


        String userName = sharedPref.getString("userName", "");
        txtInfo.setText(userName);

        if (userName != null && !userName.equals("")) {
            txtInfo.setText("Welcome " + userName);

        } else
        {
            Intent i = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(i);
        }
    }
}