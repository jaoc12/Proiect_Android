package ro.example.proiect.activity;

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

import ro.example.proiect.R;

public class LoginActivity extends AppCompatActivity {
    SharedPreferences sharedPref;
    SharedPreferences.Editor sharedPrefEditor;

    EditText edtUserName, edtPassword;
    TextView txtInfo;

    Button btnLogin;
    Button btnRegister;

    SignInButton btnGoogleLogin;
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
        btnRegister = findViewById(R.id.btnRegister);
        btnGoogleLogin = findViewById(R.id.sign_in_button);

        txtInfo = findViewById(R.id.txtInfo);
        sharedPref = getSharedPreferences(getString(R.string.loginPref), MODE_PRIVATE);

        btnLogin.setOnClickListener(v -> {
            if (edtUserName.getText().toString().equals("")
                    || edtPassword.getText().toString().equals("")) {
                txtInfo.setText("Please insert username and password");
            }
            else
                doLogin(edtUserName.getText().toString(), edtPassword.getText().toString());
        });

        btnRegister.setOnClickListener(v -> doRegister());

        btnGoogleLogin.setOnClickListener(v -> googleSignIn());
    }

    @Override
    protected void onStart(){
        super.onStart();
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);

        if(account != null){
            if (sharedPref == null)
                sharedPref = getSharedPreferences(getString(R.string.loginPref), MODE_PRIVATE);

            sharedPrefEditor = sharedPref.edit();
            sharedPrefEditor.putString("userName", account.getDisplayName());
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

    private void doLogin(String username, String password) {
        try {
            if (sharedPref == null)
                sharedPref = getSharedPreferences(getString(R.string.loginPref), MODE_PRIVATE);
            String loginPassword = sharedPref.getString("password", "");
            String loginUsername = sharedPref.getString("userName", "");

            if (password.equals(loginPassword) && !password.equals("")
            && username.equals(loginUsername) && !username.equals("")) {

                sharedPrefEditor = sharedPref.edit();
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

    private void doRegister() {
        String username = edtUserName.getText().toString();
        String password = edtPassword.getText().toString();

        if (sharedPref == null)
            sharedPref = getSharedPreferences(getString(R.string.loginPref), MODE_PRIVATE);

        sharedPrefEditor = sharedPref.edit();
        sharedPrefEditor.putString("userName", username);
        sharedPrefEditor.putString("password", password);
        sharedPrefEditor.putString("loginMode", "custom");
        sharedPrefEditor.apply();

        Intent i = new Intent(LoginActivity.this, MainActivity.class);
        startActivity(i);
        finish();
    }
}
