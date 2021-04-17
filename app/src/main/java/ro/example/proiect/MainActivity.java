package ro.example.proiect;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    TextView txtInfo;
    Button btnLogout;

    SharedPreferences sharedPref;
    SharedPreferences.Editor sharedPrefEditor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtInfo = findViewById(R.id.txtInfo);
        btnLogout = findViewById(R.id.btnLogOut);

        sharedPref = getSharedPreferences(getString(R.string.loginPref), MODE_PRIVATE);

        checkLogin();

        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Logout();
            }
        });
    }

    private void Logout() {
        try {
            if (sharedPref == null)
                sharedPref = getSharedPreferences(getString(R.string.loginPref), MODE_PRIVATE);

            sharedPrefEditor = sharedPref.edit();
            sharedPrefEditor.putString("name", "");
            sharedPrefEditor.apply();

            Intent i = new Intent(MainActivity.this, Login.class);
            startActivity(i);
            finish();

        } catch (Exception ex) {
            Toast.makeText(MainActivity.this, ex.getMessage(), Toast.LENGTH_LONG).show();
        }
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
            Intent i = new Intent(MainActivity.this, Login.class);
            startActivity(i);
            finish();
        }
    }
}