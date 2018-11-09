package efx.com.GroupLink;

import android.app.Activity;
import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Arrays;
import java.util.List;

public class LoginActivity extends AppCompatActivity {

    private FirebaseAuth auth;
    private static int RC_SIGN_IN = 123;
    private EditText email, pass;

    DatabaseReference databaseRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        databaseRef = database.getReference("users");

        auth = FirebaseAuth.getInstance(); //This determines whether or not the user is signed in
        email = findViewById(R.id.editText);
        pass = findViewById(R.id.editText2);

        if (auth.getCurrentUser() != null){
            Intent intent = new Intent(LoginActivity.this, MainScreenActivity.class);
            Toast.makeText(this, "DEBUG: Welcome back to GroupLink! We have signed you in", Toast.LENGTH_SHORT).show();
            //databaseRef.child(auth.getUid()).child("username").setValue("Default Username");
            startActivity(intent);
            finish();
        } else {

            //Declaring a List of the possible ways users can sign in [Email and Phone Number]
            List<AuthUI.IdpConfig> signInProviders = Arrays.asList(
                    new AuthUI.IdpConfig.EmailBuilder().build(),
                    new AuthUI.IdpConfig.GoogleBuilder().build()
            );

            //startSignIn(signInProviders);
        }
    }

    //Start Login Activity
    void startSignIn(List<AuthUI.IdpConfig> providers){
        //An activity is a new page, this method opens a new screen which is the login page
        startActivityForResult(
            AuthUI.getInstance() //Pulls Up Authentication Page
                    .createSignInIntentBuilder()
                    .setAvailableProviders(providers) //Get providers from the list declared in onCreate fx
                    .setLogo(R.drawable.app_icon_bold_2x)
                    .setTheme(R.style.AppTheme)
                    .build(),
            RC_SIGN_IN);
    }

    @Override
    protected void onActivityResult (int requestCode, int resultCode, Intent data){
        if (resultCode == RESULT_CANCELED) {
            Activity myActivity = this;
            myActivity.finish();
            System.exit(0);
        }
        Log.i("This is the users ID", auth.getUid());
        Intent intent = new Intent(LoginActivity.this, MainScreenActivity.class);
        databaseRef.child(auth.getUid()).child("username").setValue("Default Username");
        startActivity(intent);
        finish();
    }

    //Logout Button
    public void logOut(View logOut) {
       FirebaseAuth.getInstance().signOut();
    }

    //DEBUG FUNCTION
    public void login(View loginBtn) {

        auth.signInWithEmailAndPassword(email.getText().toString(), pass.getText().toString())
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Intent intent = new Intent(LoginActivity.this, MainScreenActivity.class);
                            Toast.makeText(getApplicationContext(), "DEBUG: Welcome back to GroupLink! We have signed you in", Toast.LENGTH_SHORT).show();
                            databaseRef.child(auth.getUid()).child("username").setValue("Default Username");
                            startActivity(intent);
                            finish();
                        }else{
                            Log.e("FAILURE", "Sign-in Failed: " + task.getException().getMessage());

                            Toast.makeText(LoginActivity.this,"Error Login",Toast.LENGTH_LONG).show();
                        }
                    }
                });

    }

}
