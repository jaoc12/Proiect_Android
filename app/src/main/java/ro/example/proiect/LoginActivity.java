package ro.example.proiect;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;

public class LoginActivity extends AppCompatActivity {
    SharedPreferences sharedPref;
    SharedPreferences.Editor sharedPrefEditor;
    EditText edtUserName, edtPassword;
    Button btnLogin;
    SignInButton btnGoogleLogin;
    TextView txtInfo;
    GoogleSignInOptions gso;
    GoogleSignInClient googleSignInClient;

    static final int RC_SIGN_IN = 100;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        gso = new GoogleSignInOptions
                .Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        googleSignInClient = GoogleSignIn.getClient(this, gso);

        edtUserName = findViewById(R.id.edtUserName);
        edtPassword = findViewById(R.id.edtPassword);
        edtUserName.setText("");
        edtPassword.setText("");
        btnLogin = findViewById(R.id.btnLogin);
        btnGoogleLogin = findViewById(R.id.sign_in_button);
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

        btnGoogleLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                googleSignIn();
            }
        });
    }

    @Override
    protected void onStart(){
        super.onStart();
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);

        if(account != null){
            if (sharedPref == null)
                sharedPref = getSharedPreferences(getString(R.string.loginPref), MODE_PRIVATE);

            sharedPrefEditor = sharedPref.edit();
            sharedPrefEditor.putString("userName", "Google");
            sharedPrefEditor.apply();

            Intent i = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(i);
            finish();
        }
    }

    private void googleSignIn() {
        Intent signInIntent = googleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            // The Task returned from this call is always completed, no need to attach
            // a listener.
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }
    }

    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);

            // Signed in successfully, show authenticated UI.
            String username = account.getDisplayName();

            if (sharedPref == null)
                sharedPref = getSharedPreferences(getString(R.string.loginPref), MODE_PRIVATE);

            sharedPrefEditor = sharedPref.edit();
            sharedPrefEditor.putString("userName", username);
            sharedPrefEditor.putString("loginMode", "google");
            sharedPrefEditor.apply();

            Intent i = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(i);
            finish();
        } catch (ApiException e) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            txtInfo.setText(e.getStatusCode());
        }
    }

    private void DoLogin(String username, String password) {
        try {
            if (password.equals("Test")) {

                if (sharedPref == null)
                    sharedPref = getSharedPreferences(getString(R.string.loginPref), MODE_PRIVATE);

                sharedPrefEditor = sharedPref.edit();
                sharedPrefEditor.putString("userName", username);
                sharedPrefEditor.putString("loginMode", "custom");
                sharedPrefEditor.apply();

                Intent i = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(i);
                finish();
            } else
                txtInfo.setText("Wrong username or password");
        } catch (Exception ex) {
            txtInfo.setText(ex.getMessage());
        }
    }
}
