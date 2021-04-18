package ro.example.proiect;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;

public class MainActivity extends AppCompatActivity {

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
            finish();
        });
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
                    finish();
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
            finish();
        }
    }
}