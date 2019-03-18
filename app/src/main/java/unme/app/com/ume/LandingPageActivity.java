package unme.app.com.ume;


import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.content.SharedPreferences;
import android.widget.Toast;


public class LandingPageActivity extends AppCompatActivity {
    private TextView userLogin;
    private ImageButton btnProfile;
    private SharedPreferences sharedPreferences;
    private ImageButton btnLogOut;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landing_page);
        //Get login user id
        userLogin = (TextView) findViewById(R.id.txtUsername);
        btnProfile = (ImageButton) findViewById(R.id.btnProfile);

        sharedPreferences = getSharedPreferences("USER_LOGIN", MODE_PRIVATE);
        String sessionUserID = sharedPreferences.getString("USER_ID", null);
        String sessionUser = sharedPreferences.getString("USER", null);
        if ((sessionUserID == null) || (sessionUser == null)) {

            Intent intent = new Intent(LandingPageActivity.this, LoginActivity.class);
            finish();
            startActivity(intent);
        }
        userLogin.setText("Hi.." + sessionUser);
        btnProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertModal();
            }
        });


    }

    public void alertModal() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.profile_alert_box, null);
        builder.setView(view);
        btnLogOut = (ImageButton) view.findViewById(R.id.btnLogout);
        btnLogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Logout();
            }
        });

        AlertDialog alert = builder.create();
        alert.show();
    }


    public void Logout() {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.commit();
        Intent intent = getIntent();
        finish();
        startActivity(intent);

    }
}
