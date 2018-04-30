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
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.justinmutsito.zapp.R;

public class PaymentsActivity extends AppCompatActivity {
    Intent callIntent;
    private static final int REQUEST_CALL= 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payments);

        Bundle extras = getIntent().getExtras();
        if (extras != null){

        }
    }


    public void onButtonClick(View view){
        int totalAmount = 0;
        StringBuffer sb = new StringBuffer("You have successfully paid USD49.80 to GMB Head Office (94092) Merchant. Txn ID MP180425.0925.D07123. New wallet balance is USD120.25.");
        //Check if message contains Merchant Code
        if (sb.toString().contains("94092")){
            //Get amount paid
            int posAmountStart = sb.indexOf("paid");
            int posAmountEnd = sb.indexOf(" to ");
            String amountPaid = sb.substring(posAmountStart, posAmountEnd);
            Toast.makeText(this, "Amount Paid: " + amountPaid, Toast.LENGTH_SHORT).show();

            //Get Transaction ID
            int posTxnStart = sb.indexOf("Txn ID") + 21;
            int posTxnEnd = posTxnStart + 6;
            String TxnID = sb.substring(posTxnStart, posTxnEnd);
            Toast.makeText(this, "Txn ID: " + TxnID, Toast.LENGTH_SHORT).show();
        }
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
        EditText txtTithe, txtOffering, txtBuilding, txtOther;
        TextView vTithe, vOffering, vBuilding, vOther;

        txtTithe = findViewById(R.id.txtTithe);
        txtOffering =  findViewById(R.id.txtOffering);
        txtBuilding = findViewById(R.id.txtBuilding);
        txtOther = findViewById(R.id.txtOther);

        vTithe = findViewById(R.id.labelTithe);
        vOffering= findViewById(R.id.labelOffering);
        vBuilding =  findViewById(R.id.labelBuilding);
        vOther = findViewById(R.id.labelOther);

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

        }
    }
}
