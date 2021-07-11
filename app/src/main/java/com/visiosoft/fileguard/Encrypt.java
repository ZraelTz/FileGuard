package com.visiosoft.fileguard;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import java.util.Calendar;

public class Encrypt extends AppCompatActivity {
    public String encryptedTitle;
    public String encryptedAuthor;
    public String encryptedDate;
    public String encryptedString;

    LinearLayout actionEncrypt;
    Context mcontext;
    EditText encryptionTxt, titleTxt, authorTxt, dateTxt, encryptedtxt;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_encrypt);
        initToolbar();
        initComponent();
        mcontext = this.getApplicationContext();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void initToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(null);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Encrypt File");
        Tools.setSystemBarLight(this);
        Tools.setSystemBarColor(this, R.color.overlay_light_90);
        getWindow().setNavigationBarColor(getResources().getColor(R.color.overlay_light_90));
    }

    private void initComponent() {

        (findViewById(R.id.pick_date)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogDatePickerLight((EditText) view);
            }
        });
        encryptionTxt = (EditText) findViewById(R.id.enc_key);
        titleTxt = (EditText) findViewById(R.id.doctitle);
        authorTxt = (EditText) findViewById(R.id.author);
        dateTxt = (EditText) findViewById(R.id.pick_date);
        encryptedtxt = (EditText) findViewById(R.id.encryptedtxt);
        final String encryptionText = AppUtils.generateEncryptionKey();
        encryptionTxt.setText(encryptionText);
        actionEncrypt = findViewById(R.id.action_encrypt);
        actionEncrypt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Toast.makeText(mcontext, "File Encrypted", Toast.LENGTH_LONG).show()
                String encrypted_title = AppUtils.encryptText(encryptionTxt.getText().toString(), "Title", titleTxt.getText().toString());
                String encryptionText = AppUtils.generateEncryptionKey();
                String encrypted_author = AppUtils.encryptText(encryptionText, "Author", authorTxt.getText().toString());
                encryptionText = AppUtils.generateEncryptionKey();
                String encrypted_date = AppUtils.encryptText(encryptionText, "Date", dateTxt.getText().toString());
                String encryptedText = encrypted_title + "\n" + encrypted_author + "\n" + encrypted_date;
                encryptedtxt.setText(encryptedText);
                encryptedString = encryptedText;
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        } else {
            Toast.makeText(getApplicationContext(), item.getTitle(), Toast.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);
    }

    private void dialogDatePickerLight(final EditText bt) {
        Calendar cur_calender = Calendar.getInstance();
        DatePickerDialog datePicker = DatePickerDialog.newInstance(
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
                        Calendar calendar = Calendar.getInstance();
                        calendar.set(Calendar.YEAR, year);
                        calendar.set(Calendar.MONTH, monthOfYear);
                        calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                        long date_ship_millis = calendar.getTimeInMillis();
                        ((EditText) findViewById(R.id.pick_date)).setText(Tools.getFormattedDateSimple(date_ship_millis));
                    }
                },
                cur_calender.get(Calendar.YEAR),
                cur_calender.get(Calendar.MONTH),
                cur_calender.get(Calendar.DAY_OF_MONTH)
        );
        //set dark light
        datePicker.setThemeDark(false);
        datePicker.setAccentColor(getResources().getColor(R.color.colorPrimary));
        datePicker.setMinDate(cur_calender);
        datePicker.show(getFragmentManager(), "Datepickerdialog");
    }

    private void encryptText(){

    }

}
