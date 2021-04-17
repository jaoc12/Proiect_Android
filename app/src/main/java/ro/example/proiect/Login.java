package ro.example.proiect;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class Login extends AppCompatActivity {
    SharedPreferences sharedPref;
    SharedPreferences.Editor sharedPrefEditor;
    EditText edtUserName, edtPassword;
    Button btnLogin;
    TextView txtInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        edtUserName = findViewById(R.id.edtUserName);
        edtPassword = findViewById(R.id.edtPassword);
        edtUserName.setText("");
        edtPassword.setText("");
        btnLogin = findViewById(R.id.btnLogin);
        txtInfo = findViewById(R.id.txtInfo);
        sharedPref = getSharedPreferences(getString(R.string.loginPref), MODE_PRIVATE);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (edtUserName.getText().toString().equals("")
                        || edtPassword.getText().toString().equals("")) {
                    txtInfo.setText("Please insert username and password");
                }
                else
                    DoLogin(edtUserName.getText().toString(), edtPassword.getText().toString());
            }
        });
    }

    private void DoLogin(String username, String password) {
        try {
            if (password.equals("Test")) {

                if (sharedPref == null)
                    sharedPref = getSharedPreferences(getString(R.string.loginPref), MODE_PRIVATE);

                sharedPrefEditor = sharedPref.edit();
                sharedPrefEditor.putString("userName", username);
                sharedPrefEditor.apply();

                Intent i = new Intent(Login.this, MainActivity.class);
                startActivity(i);
                finish();
            } else
                txtInfo.setText("Wrong username or password");
        } catch (Exception ex) {
            txtInfo.setText(ex.getMessage());
        }
    }
}
