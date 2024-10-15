package lalitjain.com;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AdminLogin extends AppCompatActivity {
    EditText etAdminUserName,etAdminPassword;
    Button btnAdminLogin;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_login);

        etAdminUserName=findViewById(R.id.etAdminUserName);
        etAdminPassword=findViewById(R.id.etAdminPassword);
        btnAdminLogin=findViewById(R.id.btnAdminLogin);

        btnAdminLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username =  etAdminUserName.getText().toString();
                String password  =  etAdminPassword.getText().toString();

                if (username.equals("admin") && password.equals("admin"))
                {
                    Toast.makeText(AdminLogin.this, "Welcome", Toast.LENGTH_SHORT).show();
                    Intent a = new Intent(AdminLogin.this,AdminLoggedInMenu.class);
                    startActivity(a);
                }
                else
                {
                    Toast.makeText(AdminLogin.this, "Enter Valid Credentials", Toast.LENGTH_SHORT).show();
                }


            }
        });



    }
}