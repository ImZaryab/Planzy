package com.example.planzy;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;

public class MultiListViewActivity extends AppCompatActivity {

    ListView multiListView;
    ArrayList<String> selectedItems;

    Button finishSelectionBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_multi_list_view);

        finishSelectionBtn = (Button) findViewById(R.id.finishSelectionBtn);

        multiListView = (ListView) findViewById(R.id.multiListView);
        ArrayList<String> arrayList = getList();
        selectedItems = new ArrayList<>();
        ArrayAdapter<String> arrayAdapter =
                new ArrayAdapter<>(MultiListViewActivity.this, android.R.layout.simple_list_item_multiple_choice, arrayList);
        multiListView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        multiListView.setAdapter(arrayAdapter);
        multiListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String items = (String) adapterView.getItemAtPosition(i);
                selectedItems.add(items);
                Utility.showToast(MultiListViewActivity.this, "Selected item: " + items);
            }
        });

        //finish selection button onClick function
        finishSelectionBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent returnData = new Intent();
                returnData.putExtra("selectedContacts", selectedItems);
                setResult(RESULT_OK, returnData);
                finish();
            }
        });
    }

    private ArrayList<String> getList() {
        ArrayList<String> arrayList = new ArrayList<>();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M
            && checkSelfPermission(android.Manifest.permission.READ_CONTACTS)
            != PackageManager.PERMISSION_GRANTED)
        {
            requestPermissions(new String[]{ Manifest.permission.READ_CONTACTS }, 1);
        } else {
            //for lower than Marshmallow
            arrayList = getContact();
        }
        return arrayList;
    }

    private ArrayList<String> getContact() {
        ArrayList<String> arrayList = new ArrayList<>();
        Cursor cursor = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                null, null, null, null);
        while (cursor.moveToNext()){
            @SuppressLint("Range") String number = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));

            arrayList.add(number);
        }
        return arrayList;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        ArrayList<String> arrayList = new ArrayList<>();
        if (requestCode == 1) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getContact();
            }
        }
    }
}