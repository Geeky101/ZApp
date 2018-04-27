package com.justinmutsito.zapp.ui;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.widget.Toast;

public class MyReceiver extends BroadcastReceiver {
    private String amountPaid="none";
    private  String transactionID;
    private StringBuffer sb;

    public String getAmountPaid(int posAmountStart, int posAmountEnd) {
        amountPaid = sb.substring(posAmountStart, posAmountEnd);
        return amountPaid;
    }

    public String getTransactionID(int posTxnIDStart, int posTxnIDEnd) {
        transactionID = sb.substring(posTxnIDStart, posTxnIDEnd);
        return transactionID;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        final Bundle bundle = intent.getExtras();
        try {
            if (bundle != null) {
                // A PDU is a "protocol data unit". This is the industrial standard for SMS message
                final Object[] pdusObj = (Object[]) bundle.get("pdus");
                for (int i = 0; i < pdusObj.length; i++) {
                    // This will create an SmsMessage object from the received pdu
                    SmsMessage sms = SmsMessage.createFromPdu((byte[]) pdusObj[i]);
                    // Get sender phone number
                    String phoneNumber = sms.getDisplayOriginatingAddress();
                    String sender = phoneNumber;
                    String message = sms.getDisplayMessageBody();
                    //Filter incoming text messages...accept messages from Ecocash number '+263164'
                    if (phoneNumber=="+263164"){
                        //Check if message contains merchant code to avoid import of wrong message
                        sb = new StringBuffer(sms.getDisplayMessageBody());
                        String merchantNumber = "94092";
                        if (message.contains(merchantNumber)){
                            //import transction details
                            //Get amount paid
                            getAmountPaid(sb.indexOf("paid"), sb.indexOf(" to "));

                            //Get Transaction ID
                            getTransactionID(sb.indexOf("Txn ID" + 21), sb.indexOf("Txn ID" + 27));
                        }
                    }

                    // Display the SMS message in a Toast
                    Toast.makeText(context, message, Toast.LENGTH_LONG).show();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
