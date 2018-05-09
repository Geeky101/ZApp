package com.justinmutsito.zapp.ui;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.justinmutsito.zapp.R;
import com.justinmutsito.zapp.objects.Payment;
import com.justinmutsito.zapp.preferences.Preferences;
import com.justinmutsito.zapp.util.Verify;

import static java.lang.Float.valueOf;

public class PaymentsActivity extends AppCompatActivity {
    Intent callIntent;
    private static final int REQUEST_CALL= 1;
    private static boolean state = false;
    Payment payment;
    EditText txtTithe, txtOffering, txtBuilding, txtOther, txtID;
    TextView vTithe, vOffering, vBuilding, vOther, vID, vManual;
    Button btnPay;

    //Firebase declarations





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payments);

        vTithe = findViewById(R.id.labelTithe);
        txtTithe = findViewById(R.id.txtTithe);
        vOffering= findViewById(R.id.labelOffering);
        txtOffering =  findViewById(R.id.txtOffering);
        vBuilding =  findViewById(R.id.labelBuilding);
        txtBuilding = findViewById(R.id.txtBuilding);
        vOther = findViewById(R.id.labelOther);
        txtOther = findViewById(R.id.txtOther);
        vID = findViewById(R.id.labelTransactionID);
        txtID = findViewById(R.id.IDField);
        vManual =findViewById(R.id.labelManualCapture);
        btnPay = findViewById(R.id.btnPayment);

        Bundle extras = getIntent().getExtras();
        if (extras != null){
            payment = new Payment();
            payment.amount =  valueOf(extras.getString("Amount"));
            payment.sender = extras.getString("Phone");
            payment.tdatetime = extras.getString("date");
            payment.ID = extras.getString("ID");
            Toast.makeText(this,"Paid: " + payment.amount + " TransactionID: " + payment.ID, Toast.LENGTH_SHORT).show();
        }

        btnPay.setOnClickListener((v) ->{
            final FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
            if (state){
                payment.ID = txtID.getText().toString();
            }
            if (Verify.checkTransID(payment.ID)){
//                save payment object to firebase
//                firebaseAuth
            }
        });
    }


    public void onButtonClick(View view){
        int totalAmount = 0;

    }

    protected void makePayment(){
        callIntent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + Uri.encode("*125#")));
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE)!=
                PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CALL_PHONE}, REQUEST_CALL);
        }else{
            startActivity(callIntent);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case REQUEST_CALL:
            {
                if (grantResults.length>0 && grantResults[0]==PackageManager.PERMISSION_GRANTED){
                    startActivity(callIntent);
                }
            }
        }
    }

    public void selectItem(View view){

        boolean checked = ((CheckBox) view).isChecked();
        switch (view.getId()){
            case R.id.checkboxTithe:
                if (checked){
                    txtTithe.setVisibility(View.VISIBLE);
                    vTithe.setVisibility(View.VISIBLE);
                }else{
                    txtTithe.setVisibility(View.GONE);
                    vTithe.setVisibility(View.GONE);
                }
                break;

            case R.id.checkboxOffering:
                if (checked){
                    txtOffering.setVisibility(View.VISIBLE);
                    vOffering.setVisibility(View.VISIBLE);
                }else{
                    txtOffering.setVisibility(View.GONE);
                    vOffering.setVisibility(View.GONE);
                }
                break;

            case R.id.checkboxBuilding:
                if (checked){
                    txtBuilding.setVisibility(View.VISIBLE);
                    vBuilding.setVisibility(View.VISIBLE);
                }else{
                    txtBuilding.setVisibility(View.GONE);
                    vBuilding.setVisibility(View.GONE);
                }
                break;

            case R.id.checkboxOther:
                if (checked){
                    txtOther.setVisibility(View.VISIBLE);
                    vOther.setVisibility(View.VISIBLE);
                }else{
                    txtOther.setVisibility(View.GONE);
                    vOther.setVisibility(View.GONE);
                }
                break;

            case R.id.labelManualCapture:
                String labelTitle = vManual.getText().toString();
                switch (labelTitle){
                    case "Manual Capture":
                        vID.setVisibility(View.VISIBLE);
                        txtID.setVisibility(View.VISIBLE);
                        vManual.setText(R.string.payment_manual_hide);
                        state= true;
                        break;

                    case "Use Automatic":
                        vID.setVisibility(View.GONE);
                        txtID.setVisibility(View.GONE);
                        vManual.setText(R.string.payment_manual);
                        state = false;
                        break;
                }

        }
    }
}
