package com.PongZhong.querycreditcardofccb;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;

public class QueryCreditCardOfCCBActivity extends Activity {

    private Button btnSendText;
    private RadioGroup radioActions;
    private EditText editText;
    private dbHelper db;
    private String bankNumber = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_querycreditcardofccb);

        bankNumber = getString(R.string.ccbTextNumber);

        btnSendText = (Button) findViewById(R.id.btnSendText);
        editText = (EditText) findViewById(R.id.creditId);
        radioActions = (RadioGroup) findViewById(R.id.radioActions);

        db = new dbHelper(this);

        editText.setText(db.getCardId());

        btnSendText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String cardId = editText.getText().toString();

                if (cardId.equals("")) {
                    AlertDialog dialog = new AlertDialog.Builder(QueryCreditCardOfCCBActivity.this).create();
                    dialog.setMessage(getString(R.string.emptyCardIdTip));
                    dialog.setButton(DialogInterface.BUTTON_POSITIVE, getString(R.string.OKText), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            editText.requestFocus();
                            dialogInterface.dismiss();
                        }
                    });
                    dialog.show();

                    return;
                }

                String smsText = null;
                switch (radioActions.getCheckedRadioButtonId()) {
                    case R.id.currentBill:
                        smsText = "CCZD#";
                        break;
                    case R.id.leftMoney:
                        smsText = "CCYE#";
                        break;
                    case R.id.limit:
                        smsText = "CCED#";
                        break;
                    case R.id.scores:
                        smsText = "CCJF#";
                        break;

                }
                smsText = smsText + cardId;

                SmsManager sms = SmsManager.getDefault();
                sms.sendTextMessage(bankNumber, null, smsText, null, null);

                AlertDialog aDialog = new AlertDialog.Builder(QueryCreditCardOfCCBActivity.this).create();
                aDialog.setMessage(getString(R.string.sendOK));
                aDialog.setButton(DialogInterface.BUTTON_POSITIVE, getString(R.string.OKText), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });
                aDialog.show();

                if (!cardId.equals("")) {
                    db.insert(cardId);
                }

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.query_credit_card_of_ccb, menu);
        return true;
    }

}
