package com.example.samplecontenprovider_contactextractor;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.ContentResolver;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    ListView list;
    ArrayList<String> listdata;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        list=(ListView)findViewById(R.id.list);
        listdata=new ArrayList<String>();
        fetchContact();
    }

    private void fetchContact() {
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS)!= PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_CONTACTS},0);
        }
        ContentResolver resolver=getContentResolver();
        Uri uri= ContactsContract.CommonDataKinds.Phone.CONTENT_URI;
        
        String[] projection=null;
        String selection=null;
        String[] selectionArgs=null;
        String order=null;
        Cursor cursor=resolver.query(uri, projection, selection, selectionArgs, order);

        if(cursor.getCount()>0)
        {
            while(cursor.moveToNext())
            {
                String name=cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
                String number=cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));

                String fullcontacts=name+number;
                listdata.add(fullcontacts);

            }
        }
        ArrayAdapter<String> adapter=new ArrayAdapter(this,android.R.layout.simple_expandable_list_item_1,listdata);
        list.setAdapter(adapter);
    }
}