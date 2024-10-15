package lalitjain.com;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class HospitalLogin extends AppCompatActivity {
    EditText etHospitalUserName,etHospitalPassword;
    Button btnHospitalLogin,btnHospitalSignup;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    ArrayList<String>  h_username = new ArrayList<>();
    ArrayList<String>  h_password = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hospital_login);
        etHospitalUserName=findViewById(R.id.etHospitalUserName);
        etHospitalPassword=findViewById(R.id.etHospitalPassword);
        btnHospitalLogin=findViewById(R.id.btnHospitalLogin);
        btnHospitalSignup=findViewById(R.id.btnHospitalSignup);
        firebaseDatabase= FirebaseDatabase.getInstance();
        databaseReference=firebaseDatabase.getReference("Hospital");

        btnHospitalLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (etHospitalUserName.getText().toString().equals("")) {
                    etHospitalUserName.setError("Please enter UserName");
                } else if (etHospitalPassword.getText().toString().equals("")) {
                    etHospitalUserName.setError("Please Enter Password ");
                } else {
                    String un = etHospitalUserName.getText().toString();
                    String pw  =  etHospitalPassword.getText().toString();
                    databaseReference.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            h_username.clear();
                            h_password.clear();

                            for (DataSnapshot d : snapshot.getChildren()) {
                                Hospital hosp = d.getValue(Hospital.class);
                                String username = hosp.getUn();
                                String password = hosp.getPw();
                                h_username.add(username);
                                h_password.add(password);
                            }

                            if (h_username.contains((Object)(un)))
                            {
                                int index = h_username.indexOf(un);
                                if (h_password.get(index).equals(pw))
                                {
                                    Intent a = new Intent(HospitalLogin.this, HospitalLoginMenu.class);
                                    a.putExtra("username",etHospitalUserName.getText().toString());
                                    startActivity(a);
                                }
                                else
                                {
                                    etHospitalPassword.setError("Wrong password");
                                }
                            }
                            else
                            {
                                etHospitalUserName.setError("Username does not exists");
                            }

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }


                }

        });

        btnHospitalSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent a = new Intent(HospitalLogin.this,HospitalSignup.class);
                startActivity(a);
            }
        });
    }
}