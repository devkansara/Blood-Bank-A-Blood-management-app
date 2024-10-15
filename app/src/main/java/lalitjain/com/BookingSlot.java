package lalitjain.com;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.data.DataBufferSafeParcelable;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.itextpdf.barcodes.BarcodeQRCode;
import com.itextpdf.io.image.ImageData;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.io.source.ByteArrayOutputStream;
import com.itextpdf.kernel.colors.ColorConstants;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.pdf.xobject.PdfFormXObject;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.property.HorizontalAlignment;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.Calendar;
import java.util.Random;

public class BookingSlot extends AppCompatActivity {
    EditText etQuantity;
    //    CalendarView calenderView;
    ImageView ivCalender;
    int y, m, d1;
    TextView tvReceiverName, tvReceiverEmail, tvHospitalName, tvDate,tvBloodGroup;
    Button btnBookSlot;
    ChipGroup chipGroup;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking_slot);

        tvDate = findViewById(R.id.tvDate);
        ivCalender = findViewById(R.id.ivCalender);
        tvHospitalName = findViewById(R.id.tvHospitalName);
        etQuantity = findViewById(R.id.etQuantity);
        tvReceiverName = findViewById(R.id.tvReceiverName);
        tvReceiverEmail = findViewById(R.id.tvReceiverEmail);
        btnBookSlot = findViewById(R.id.btnBookSlot);
        chipGroup = findViewById(R.id.chipGroup);
        tvBloodGroup=findViewById(R.id.tvBloodGroup);
        firebaseDatabase= FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("Appointments");

        Intent a = getIntent();
        String receiver_name = a.getStringExtra("receiver_name");
        String receiver_email = a.getStringExtra("receiver_email");
        String h_name = a.getStringExtra("h_name");
        String available_quantity = a.getStringExtra("qtyy");
        String bloodgrp = a.getStringExtra("bloodgrp");
        String table_name = a.getStringExtra("table_name");

        Toast.makeText(BookingSlot.this, "in" + table_name, Toast.LENGTH_SHORT).show();

        tvHospitalName.setText(h_name);
        tvReceiverName.setText(receiver_name);
       tvReceiverEmail.setText(receiver_email);
        tvBloodGroup.setText(bloodgrp);

//        etQuantity.setText(available_quantity);

        Calendar c1 = Calendar.getInstance();
                y = c1.get(Calendar.YEAR);
                m = c1.get(Calendar.MONTH);
                d1 = c1.get(Calendar.DAY_OF_MONTH);
                int m2 =m+1;
                String date = d1 + "/" + m2 + "/" +y;
                tvDate.setText(date);





        Calendar c = Calendar.getInstance();
        ivCalender.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                y = c.get(Calendar.YEAR);
                m = c.get(Calendar.MONTH);
                d1 = c.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog g = new DatePickerDialog(BookingSlot.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                        int m1 = i1 + 1;
                        String d = i2 + "/" + m1 + "/" + i;
                        tvDate.setText(d);
                    }
                }, y, m, d1);
                g.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
                g.show();
            }
        });


        btnBookSlot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String entered_qty = etQuantity.getText().toString();
                if (entered_qty.equals(""))
                    etQuantity.setError("Enter amount in ml");
                else if ((Double.parseDouble(entered_qty)) > (Double.parseDouble(available_quantity)))
                    etQuantity.setError("Must be less than" + available_quantity);
//                else if(tvDate.getText().toString().equals(""))
//                    tvDate.setError("Please Enter Date");

                else {
                    String date_booked = tvDate.getText().toString();
                    String quantity = etQuantity.getText().toString();
                    String time;

                    int id = chipGroup.getCheckedChipId();
                    Chip chip = chipGroup.findViewById(id);
                    time = chip.getText().toString();

                    // Toast.makeText(BookingSlot.this, ""+time, Toast.LENGTH_SHORT).show();

                    String path = "Appointments/" + h_name + "/" +
                            receiver_email.replace(".", "").replace("@", "") + "/" +
                            date.replace("/","")+"/"+
                            bloodgrp;

                    firebaseDatabase = FirebaseDatabase.getInstance();
                    databaseReference = firebaseDatabase.getReference(path);

                    databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot)
                        {
                            if (snapshot.hasChildren() == true)
                            {
                                Toast.makeText(BookingSlot.this, "Already Booked", Toast.LENGTH_SHORT).show();
                            }
                            else
                            {
                                Appointments a = new Appointments(receiver_name,receiver_email,quantity,date,h_name,time,bloodgrp);

                                String receiver_mail_new = receiver_email.replace(".", "").replace("@", "");
                                //    databaseReference.child(String.valueOf(receiver_mail_new)).setValue(a);

                                DatabaseReference db = FirebaseDatabase.getInstance().getReference("Appointments");

                                db.child(h_name).child(receiver_mail_new).
                                        child(date.replace("/","")).child(bloodgrp).setValue(a);
                                updateBloodQuantity(quantity,bloodgrp,table_name);

                                Toast.makeText(BookingSlot.this, "Booking ", Toast.LENGTH_SHORT).show();

                                try {
                                    createPdf(tvReceiverName.getText().toString(), tvReceiverEmail.getText().toString(), tvHospitalName.getText().toString(), tvBloodGroup.getText().toString(), etQuantity.getText().toString(), tvDate.getText().toString(), time);
                                } catch (FileNotFoundException e) {
                                    e.printStackTrace();
                                }


                                Intent a1 = new Intent(BookingSlot.this, RecieverLoggedIn.class);
                                startActivity(a1);
                                finish();
                            }

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error)
                        {

                        }
                    });
                }


                }

        });





    }

    private void updateBloodQuantity(String quantity, String bloodgrp, String table_name)
    {
        DatabaseReference dbrf = FirebaseDatabase.getInstance().getReference(table_name);
        dbrf.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
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

                if (bloodgrp.equalsIgnoreCase("A-"))
                {
                    a_neg = String.valueOf(Double.parseDouble(a_neg) - Double.parseDouble(quantity)) ;
                }
                if (bloodgrp.equalsIgnoreCase("A+"))
                {
                    a_pos = String.valueOf(Double.parseDouble(a_pos) - Double.parseDouble(quantity)) ;
                }
                if (bloodgrp.equalsIgnoreCase("B-"))
                {
                    b_neg = String.valueOf(Double.parseDouble(b_neg) - Double.parseDouble(quantity)) ;
                }
                if (bloodgrp.equalsIgnoreCase("B+"))
                {
                    b_pos = String.valueOf(Double.parseDouble(a_pos) - Double.parseDouble(quantity)) ;
                }
                if (bloodgrp.equalsIgnoreCase("AB-"))
                {
                    ab_neg = String.valueOf(Double.parseDouble(ab_neg) - Double.parseDouble(quantity)) ;
                }
                if (bloodgrp.equalsIgnoreCase("AB+"))
                {
                    ab_pos = String.valueOf(Double.parseDouble(ab_pos) - Double.parseDouble(quantity)) ;
                }
                if (bloodgrp.equalsIgnoreCase("O-"))
                {
                    o_neg = String.valueOf(Double.parseDouble(o_neg) - Double.parseDouble(quantity)) ;
                }
                if (bloodgrp.equalsIgnoreCase("O+"))
                {
                    o_pos = String.valueOf(Double.parseDouble(o_pos) - Double.parseDouble(quantity)) ;
                }

                BloodGroupDetails bgd_updated = new BloodGroupDetails(a_neg, a_pos, b_neg, b_pos,
                        ab_neg, ab_pos, o_neg, o_pos);
                dbrf.setValue(bgd_updated);

                Toast.makeText(BookingSlot.this, "Blood Group Table Updated", Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void createPdf(String receiver_name, String receiver_email, String hospital_name, String blood_group, String blood_quantity, String date, String time) throws FileNotFoundException {

        String pdfPath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).toString();
        Random random = new Random();

// generate a random integer from 0 to 899, then add 100
        int x = random.nextInt(900) + 100;
        File file = new File(pdfPath,x + " Appointment.pdf");
        OutputStream outputStream = new FileOutputStream(file);
//        Toast.makeText(BookingSlot.this, "here" + outputStream, Toast.LENGTH_SHORT).show();

        PdfWriter writer = new PdfWriter(file);

        PdfDocument pdfDocument = new PdfDocument(writer);
        Document document = new Document(pdfDocument);


        pdfDocument.setDefaultPageSize(PageSize.TABLOID);
        document.setMargins(0,0,0,0);

        Drawable d = getDrawable(R.drawable.ff);
        Bitmap bitmap = ((BitmapDrawable)d).getBitmap();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] bitmapData = stream.toByteArray();


        ImageData imageData = ImageDataFactory.create(bitmapData);
        Image image = new Image(imageData);

//        Paragraph p11 = new Paragraph("Appointment"+).setHorizontalAlignment(HorizontalAlignment.CENTER);
        float[] width = {180f, 260f};
//        float[] height= {200f};
        Paragraph p = new Paragraph("                   ");
        Paragraph p1 = new Paragraph("                   ");
        Table table = new Table(width);
        table.setHeight(380);
        table.setFontSize(20);
        table.setHorizontalAlignment(HorizontalAlignment.CENTER);

        table.addCell(new Cell().add(new Paragraph("Name")));
        table.addCell(new Cell().add(new Paragraph(receiver_name)));

        table.addCell(new Cell().add(new Paragraph("Email")));
        table.addCell(new Cell().add(new Paragraph(receiver_email)));

        table.addCell(new Cell().add(new Paragraph("Hospital Name")));
        table.addCell(new Cell().add(new Paragraph(hospital_name)));

        table.addCell(new Cell().add(new Paragraph("Blood Group")));
        table.addCell(new Cell().add(new Paragraph(blood_group)));

        table.addCell(new Cell().add(new Paragraph("Quantity")));
        table.addCell(new Cell().add(new Paragraph(blood_quantity + " ml")));

        table.addCell(new Cell().add(new Paragraph("Date")));
        table.addCell(new Cell().add(new Paragraph(date)));

        table.addCell(new Cell().add(new Paragraph("Time Slot")));
        table.addCell(new Cell().add(new Paragraph(time)));

        BarcodeQRCode qrCode = new BarcodeQRCode(receiver_name+"\n"+receiver_email+"\n"
                +hospital_name+"\n"+blood_group+"\n"+blood_quantity+"\n"+date+"\n"+time);
        PdfFormXObject qrCodeObject = qrCode.createFormXObject(ColorConstants.RED, pdfDocument);
        Image qrCodeImage = new Image(qrCodeObject).setWidth(200).setHeight(200).setHorizontalAlignment(HorizontalAlignment.CENTER);

      //  Drawable d1 = getDrawable(R.drawable.gg);

        Drawable d1 = getDrawable(R.drawable.gg);
        Bitmap bitmap1 = ((BitmapDrawable)d1).getBitmap();
        ByteArrayOutputStream stream1 = new ByteArrayOutputStream();
        bitmap1.compress(Bitmap.CompressFormat.PNG, 100, stream1);
        byte[] bitmapData1 = stream1.toByteArray();


        ImageData imageData1 = ImageDataFactory.create(bitmapData1);

        Image image1 = new Image(imageData1);
        document.add(image);

        document.add(p).add(p1);
        document.add(p).add(p1);
        document.add(table);
        document.add(p).add(p1);
        document.add(p).add(p1);
        document.add(qrCodeImage).setBottomMargin(0);
        document.add(p).add(p1);
        document.add(p).add(p1);

        document.add(image1).setTopMargin(0);

        document.close();
        Toast.makeText(BookingSlot.this, "Pdf created at " + pdfPath, Toast.LENGTH_SHORT).show();
    }


}