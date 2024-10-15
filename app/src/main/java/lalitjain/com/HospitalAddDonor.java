package lalitjain.com;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class HospitalAddDonor extends AppCompatActivity {
    EditText etDonorName,etDonorEmail,etDonorPhone,etDonorAadhar,etBloodAmount;
    Button btnAddDonor;
    Spinner spnBloodGroup;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hospital_add_donor);

        etDonorName=findViewById(R.id.etDonorName);
        etDonorEmail=findViewById(R.id.etDonorEmail);
        etDonorPhone=findViewById(R.id.etDonorPhone);
        etDonorAadhar=findViewById(R.id.etDonorAadhar);
        etBloodAmount=findViewById(R.id.etBloodAmount);
        btnAddDonor=findViewById(R.id.btnAddDonor);
        spnBloodGroup=findViewById(R.id.spnBloodGroup);
        firebaseDatabase= FirebaseDatabase.getInstance();


        Intent a = getIntent() ;
        final String username = a.getStringExtra("username");
        databaseReference = firebaseDatabase.getReference(username);
        Toast.makeText(HospitalAddDonor.this, "Welcome " + username, Toast.LENGTH_SHORT).show();

        ArrayList<String> Blood_group =  new ArrayList<>();
        Blood_group.add("A-");
        Blood_group.add("A+");
        Blood_group.add("B-");
        Blood_group.add("B+");
        Blood_group.add("AB-");
        Blood_group.add("AB+");
        Blood_group.add("O-");
        Blood_group.add("O+");

        // Array Adapter
        ArrayAdapter arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1,Blood_group);

        spnBloodGroup.setAdapter(arrayAdapter);


        btnAddDonor.setOnClickListener(new View.OnClickListener() {
            String email = etDonorEmail.getText().toString().trim();
            String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
            boolean checker = email.matches(emailPattern);

            @Override
            public void onClick(View view) {

                if(etDonorName.getText().toString().equals(""))
                {
                    etDonorName.setError("Please Enter Name");
                }

                else if (etDonorName.getText().toString().length()<2)
                {
                    etDonorName.setError("Please Enter Proper Name");
                }

                else if (etDonorEmail.getText().toString().equals(""))
                {
                    etDonorEmail.setError("Please Enter Email");
                }

                else if (etDonorPhone.getText().toString().length()!=10)
                {
                    etDonorPhone.setError("Please Enter Proper Phone");
                }
                else if (etBloodAmount.getText().toString().equals(""))
                {
                    etBloodAmount.setError("Please Enter Quantity of Blood");
                }
                else if (etDonorAadhar.getText().toString().equals(""))
                {
                    etDonorAadhar.setError("Please Enter Aadhar");
                }
                else if (HospitalAddDonor.isValid((etDonorEmail.getText().toString())) == false)
                {
                    etDonorEmail.setError("Please enter valid email");
                }

                else if(etDonorAadhar.getText().toString().length()!=12)
                {
                    etDonorAadhar.setError("Please Enter Proper Aadhar");
                }
                else
                {
                    String name = etDonorName.getText().toString();
                    String email =etDonorEmail.getText().toString();
                    String phone =  etDonorPhone.getText().toString();
                    String id  = etDonorAadhar.getText().toString();
                    String qty = etBloodAmount.getText().toString();
                    int bg  = spnBloodGroup.getSelectedItemPosition();
                    String grp = Blood_group.get(bg);
                    databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {

                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {


                            if (dataSnapshot.hasChild(String.valueOf(name))){
                                Toast.makeText(HospitalAddDonor.this, "Already exists", Toast.LENGTH_SHORT).show();
                            }
                            else
                            {
                                Donor d = new Donor(name,email,phone, id, qty,grp);
                                databaseReference.child(String.valueOf(name)).setValue(d);
                                Toast.makeText(HospitalAddDonor.this, "Done", Toast.LENGTH_SHORT).show();
                                etDonorName.setText("");
                                etDonorEmail.setText("");
                                etDonorPhone.setText("");
                                etBloodAmount.setText("");
                                etDonorAadhar.setText("");
                                updateBloodGroupTable(username,grp,qty);


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
    private static boolean isValid(String email) {
        String regex = "^[\\w-\\.+]*[\\w-\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$";
        return email.matches(regex);
    }
    private void updateBloodGroupTable(String username, String bg, String bq)
    {
        FirebaseDatabase fbdb;
        DatabaseReference dbrf;

        String table_name = username + "_BloodGroupDetails";

        fbdb = FirebaseDatabase.getInstance();
        dbrf = fbdb.getReference(table_name);

        dbrf.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot)
            {
                String a_pos = "", a_neg = "", b_pos = "", b_neg = "", ab_pos = "", ab_neg = "", o_pos = "", o_neg = "";

                DataSnapshot d = snapshot;

                BloodGroupDetails bgd = d.getValue(BloodGroupDetails.class);
                a_pos = bgd.getA_positive();
                a_neg = bgd.getA_negative();
                o_pos = bgd.getO_positive();
                o_neg = bgd.getO_negative();
                ab_pos = bgd.getAB_positive();
                ab_neg = bgd.getAB_negative();
                b_pos = bgd.getB_positive();
                b_neg = bgd.getB_negative();

                if (bg.equalsIgnoreCase("A-"))
                {
                    a_neg = String.valueOf(Double.parseDouble(a_neg) + Double.parseDouble(bq)) ;
                }
                if (bg.equalsIgnoreCase("A+"))
                {
                    a_pos = String.valueOf(Double.parseDouble(a_pos) + Double.parseDouble(bq)) ;
                }
                if (bg.equalsIgnoreCase("B-"))
                {
                    b_neg = String.valueOf(Double.parseDouble(b_neg) + Double.parseDouble(bq)) ;
                }
                if (bg.equalsIgnoreCase("B+"))
                {
                    b_pos = String.valueOf(Double.parseDouble(a_pos) + Double.parseDouble(bq)) ;
                }
                 if (bg.equalsIgnoreCase("AB-"))
                {
                    ab_neg = String.valueOf(Double.parseDouble(ab_neg) + Double.parseDouble(bq)) ;
                }
                 if (bg.equalsIgnoreCase("AB+"))
                {
                    ab_pos = String.valueOf(Double.parseDouble(ab_pos) + Double.parseDouble(bq)) ;
                }
                 if (bg.equalsIgnoreCase("O-"))
                {
                    o_neg = String.valueOf(Double.parseDouble(o_neg) + Double.parseDouble(bq)) ;
                }
                if (bg.equalsIgnoreCase("O+"))
                {
                    o_pos = String.valueOf(Double.parseDouble(o_pos) + Double.parseDouble(bq)) ;
                }

                BloodGroupDetails bgd_updated = new BloodGroupDetails(a_neg, a_pos, b_neg, b_pos,
                        ab_neg, ab_pos, o_neg, o_pos);
                dbrf.setValue(bgd_updated);

                Toast.makeText(HospitalAddDonor.this, "Blood Group Table Updated", Toast.LENGTH_SHORT).show();

            } //end of updateBloodGroupTable() method

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }




}