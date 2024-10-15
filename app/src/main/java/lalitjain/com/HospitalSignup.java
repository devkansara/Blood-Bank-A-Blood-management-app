package lalitjain.com;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class HospitalSignup extends AppCompatActivity {
    EditText etHospitalName,etHospitalUserName,etHospitalCity,etHospitalPassword1,etHospitalPassword2;
    Button btnHospitalSignup;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hospital_signup);

        etHospitalName=findViewById(R.id.etHospitalName);
        etHospitalUserName=findViewById(R.id.etHospitalUserName);
        etHospitalCity=findViewById(R.id.etHospitalCity);
        etHospitalPassword1=findViewById(R.id.etHospitalPassword1);
        etHospitalPassword2=findViewById(R.id.etHospitalPassword2);
        btnHospitalSignup=findViewById(R.id.btnHospitalSignup);

        firebaseDatabase= FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("Hospital");

        btnHospitalSignup.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                if (etHospitalName.getText().toString().equals("")) {
                    etHospitalName.setError("Please Enter Name");
                } else if (etHospitalName.getText().toString().length() < 2) {
                    etHospitalName.setError("Minimum 2 Characters");
                } else if (etHospitalUserName.getText().toString().equals("")) {
                    etHospitalUserName.setError("Please Enter UserName");
                } else if (etHospitalUserName.getText().toString().length() < 2) {
                    etHospitalUserName.setError("Minimum 2 Characters");
                } else if (etHospitalCity.getText().toString().equals("")) {
                    etHospitalCity.setError("Please Enter city");
                } else if (etHospitalCity.getText().toString().length() < 2) {
                    etHospitalCity.setError("Minimum 2 Characters");
                } else if (etHospitalPassword1.getText().toString().equals("")) {
                    etHospitalPassword1.setError("Please Enter Password");
                } else if (etHospitalPassword1.getText().toString().length() < 2) {
                    etHospitalPassword1.setError("Minimum 2 Characters");
                } else if (etHospitalPassword2.getText().toString().length() < 2) {
                    etHospitalPassword2.setError("Minimum 2 Characters");
                } else if (etHospitalPassword2.getText().toString().equals("")) {
                    etHospitalPassword2.setError("Please Confirm Password");
                } else if (etHospitalPassword1.getText().toString().equals(etHospitalPassword2.getText().toString()) == false) {
                    etHospitalPassword2.setError("Please Enter Correct Password");
                }
                else {

                    String un = etHospitalUserName.getText().toString();
                    String name = etHospitalName.getText().toString();
                    String city = etHospitalCity.getText().toString();
                    String pw = etHospitalPassword1.getText().toString();

                    databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {

                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {


                            if (dataSnapshot.hasChild(String.valueOf(un))){
                                Toast.makeText(HospitalSignup.this, "Already exists", Toast.LENGTH_SHORT).show();
                            }
                            else
                            {
                                Hospital h = new Hospital(un, name, city, pw);
                                databaseReference.child(String.valueOf(un)).setValue(h);
                                Toast.makeText(HospitalSignup.this, "Done", Toast.LENGTH_SHORT).show();
                                BloodGroupDetails(un);
                                etHospitalName.setText("");
                                etHospitalUserName.setText("");
                                etHospitalCity.setText("");
                                etHospitalPassword1.setText("");
                                etHospitalPassword2.setText("");




                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });

                }
            }

        });


        }

        private void BloodGroupDetails(String un)
        {
            FirebaseDatabase fbdb;
            DatabaseReference dbrf;

            String table_name = un + "_BloodGroupDetails";

            fbdb = FirebaseDatabase.getInstance();
            dbrf = fbdb.getReference(table_name);

            dbrf.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    BloodGroupDetails bg  = new BloodGroupDetails("0.0","0.0","0.0","0.0","0.0","0.0","0.0","0.0");
                    dbrf.setValue(bg);
                    Toast.makeText(HospitalSignup.this, "Done", Toast.LENGTH_SHORT).show();


                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });

        }
    }
