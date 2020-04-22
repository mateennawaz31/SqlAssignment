package com.example.sqlassignment;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private Button createDbBtn,addValuesBtn,getValuesBtn;
    private SQLiteDBClass objectSqLiteDBClass;

    private EditText nameET,addressET;
    private TextView getValuesTV;

    private RecyclerView objectRecyclerView;
    private ArrayList<DBModelClass> objectModelClassArrayList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        connectXML();
    }

    private void connectXML()
    {
        try
        {
            createDbBtn=findViewById(R.id.createBtn);
            objectSqLiteDBClass=new SQLiteDBClass(this);

            nameET=findViewById(R.id.nameET);
            addressET=findViewById(R.id.addressET);

            addValuesBtn=findViewById(R.id.addValuesBtn);
            getValuesBtn=findViewById(R.id.getValuesBtn);

            getValuesTV=findViewById(R.id.getValuesTV);
            objectModelClassArrayList=new ArrayList<>();

            objectRecyclerView=findViewById(R.id.RV);
            createDbBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    createDb();
                }
            });

            addValuesBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    addValuesToDatabase();
                }
            });

            getValuesBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    getValuesFromDB();
                }
            });
        }
        catch (Exception e)
        {
            Toast.makeText(this, "connectXML:"+e.getMessage(),Toast.LENGTH_SHORT).show();
        }
    }

    private void createDb()
    {
        try
        {
            objectSqLiteDBClass.getReadableDatabase();
        }
        catch (Exception e)
        {
            Toast.makeText(this, "createDb:"+e.getMessage(),Toast.LENGTH_SHORT).show();
        }
    }

    private void addValuesToDatabase()
    {
        try
        {
            if (!nameET.getText().toString().isEmpty() && !addressET.getText().toString().isEmpty())
            {
                SQLiteDatabase objectSqLiteDatabase=objectSqLiteDBClass.getWritableDatabase();
                if(objectSqLiteDatabase!=null)
                {
                    ContentValues objectContentValues=new ContentValues();
                    objectContentValues.put("Name",nameET.getText().toString());

                    objectContentValues.put("Address",addressET.getText().toString());
                    long check=objectSqLiteDatabase.insert("classtable",null,objectContentValues);

                    if (check!=-1)
                    {
                        Toast.makeText(this, "Values Inserted!", Toast.LENGTH_SHORT).show();
                        nameET.setText(null);

                        addressET.setText(null);
                        nameET.requestFocus();
                    }
                }
                else
                {
                    Toast.makeText(this, "DB object is null", Toast.LENGTH_SHORT).show();
                }
            }
            else if(nameET.getText().toString().isEmpty())
            {
                Toast.makeText(this, "Enter the name please", Toast.LENGTH_SHORT).show();
                nameET.requestFocus();
            }
            else if(addressET.getText().toString().isEmpty())
            {
                Toast.makeText(this, "Enter the address please", Toast.LENGTH_SHORT).show();
                addressET.requestFocus();
            }
        }
        catch (Exception e)
        {
            Toast.makeText(this, "addValuesToDatabase:"+e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    private void getValuesFromDB()
    {
        try
        {
            SQLiteDatabase objectSqLiteDatabase=objectSqLiteDBClass.getReadableDatabase();
            if(objectSqLiteDatabase!=null)
            {
                Cursor objectCursor=objectSqLiteDatabase.rawQuery("select * from classtable", null);
                StringBuffer objectStringBuffer=new StringBuffer();

                if(objectCursor.getCount()!=0)
                {
                    while (objectCursor.moveToNext())
                    {
                        DBModelClass objectDbModelClass=new DBModelClass();
                        objectDbModelClass.setName(objectCursor.getString(0));

                        objectDbModelClass.setAddress(objectCursor.getString(1));
                        objectModelClassArrayList.add(objectDbModelClass);
                        //                       objectStringBuffer.append("Name:"+objectCursor.getString(0)+"\n");
                        //                       objectStringBuffer.append("Address:"+objectCursor.getString(1)+"\n");
                    }

                    //                   getValuesTV.setText(objectStringBuffer);
                    objectRecyclerView.setLayoutManager(new LinearLayoutManager(this));
                    DBAdapterClass objectDBAdapterClass=new DBAdapterClass(objectModelClassArrayList);

                    objectRecyclerView.setAdapter(objectDBAdapterClass);
                }
                else
                {
                    Toast.makeText(this, "No values retrieved", Toast.LENGTH_SHORT).show();
                }
            }
            else
            {
                Toast.makeText(this, "DB object is null!", Toast.LENGTH_SHORT).show();
            }
        }
        catch (Exception e)
        {
            Toast.makeText(this, "getValuesFromDB:"+e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
}
