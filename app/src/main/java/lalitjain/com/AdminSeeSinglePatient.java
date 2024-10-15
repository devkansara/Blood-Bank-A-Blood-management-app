package lalitjain.com;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class AdminSeeSinglePatient extends AppCompatActivity {
    TextView tvname,tvemail,tvphone,tvaadhar,tvbldgrp,tvbldqty;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_see_single_patient);

        tvname=findViewById(R.id.tvname);
        tvemail=findViewById(R.id.tvemail);
        tvphone=findViewById(R.id.tvphone);
        tvaadhar=findViewById(R.id.tvaadhar);
        tvbldgrp=findViewById(R.id.tvbldgrp);
        tvbldqty=findViewById(R.id.tvbldqty);

        Intent a = getIntent();
        String name = a.getStringExtra("name");
        String email = a.getStringExtra("email");
        String phone = a.getStringExtra("phone");
        String quantity = a.getStringExtra("quantity");
        String grp = a.getStringExtra("group");
        String id = a.getStringExtra("aadhar");

        tvname.setText("Name : "+name);
        tvemail.setText("Email : "+email);
        tvphone.setText("Phone : "+phone);
        tvbldqty.setText("Quantity : "+quantity);
        tvbldgrp.setText("Blood grp : "+grp);
        tvaadhar.setText("Aadhar no : "+id);


    }
}