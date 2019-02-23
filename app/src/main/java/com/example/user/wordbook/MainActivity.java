package com.example.user.wordbook;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.user.wordbook.DataBase.DicContract;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import javax.net.ssl.HttpsURLConnection;

public class MainActivity extends AppCompatActivity {


    String p=null;
    private DicDbHelper mDbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mDbHelper=new DicDbHelper(this);

    }

    public void searchItem(View view) {

        EditText editText=(EditText) findViewById(R.id.word);
        p=editText.getText().toString().trim().toLowerCase();
       if(!isDownloaded(p))
       {
           Toast.makeText(this,"From Internate",Toast.LENGTH_SHORT).show();
           new Download().execute();
       }



    }



    private boolean isDownloaded(String p) {

        DicDbHelper mDbHelper = new DicDbHelper(this);
        SQLiteDatabase db = mDbHelper.getReadableDatabase();
        boolean bool=false;


        String query = "select * from "+DicContract.DicEntry.TABLE_NAME+" where "+DicContract.DicEntry.C_WORD+" =\""+ p + "\"";
        Cursor cursor = db.rawQuery(query, null);




        try {
            if(cursor.getCount()==0)
            {
                Toast.makeText(this,"Not Found in DataBase",Toast.LENGTH_SHORT).show();
            }
            else
            {
                Toast.makeText(this,"From DataBase",Toast.LENGTH_SHORT).show();
                int a,b,c;
                a=cursor.getColumnIndex(DicContract.DicEntry.C_TYPE);
                b=cursor.getColumnIndex(DicContract.DicEntry.C_WORD_MEANING);
                c=cursor.getColumnIndex(DicContract.DicEntry.C_WORD_EXAMPLE);
                String result=null,a1=null,a2=null;
                final ArrayList<Item> items= new ArrayList<Item>();




                while(cursor.moveToNext())
                {
                    result=cursor.getString(a);
                    a1=cursor.getString(b);
                    a2=cursor.getString(c);
                    items.add(new Item(result,a1,a2));
                }


                ItemAdapter itemAdapter=new ItemAdapter(MainActivity.this,items);
                ListView listView = (ListView) findViewById(R.id.list);
                listView.setAdapter(itemAdapter);


                bool=true;
            }
        } finally {
            cursor.close();
        }
        return  bool;
    }



///Downloding
private class Download extends AsyncTask<String,Void,String> {
    String jsonFeed = "{ " + "\"word\":";


    protected String doInBackground(String... params) {
        String urlStr = "https://owlbot.info/api/v2/dictionary/"+p+"?format=json";
        String re = null;
        try {
            URL url = new URL(urlStr);

            HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
            InputStream is = connection.getInputStream();
            InputStreamReader r = new InputStreamReader(is);
            BufferedReader br = new BufferedReader(r);


            String line = "";
            while (line != null) {
                line = br.readLine();
                jsonFeed += line;
            }

        } catch (MalformedURLException e1) {
            e1.printStackTrace();
        } catch (IOException e1) {
            e1.printStackTrace();
        }

        StringBuilder sb = new StringBuilder(jsonFeed);
        sb.delete(sb.length() - 4, sb.length());
        return sb.toString();
    }

    protected void onPostExecute(String result) {

        result += "}";

        JSONObject obj = null;
        JSONArray ar = null;
        try {
            obj = new JSONObject(result);
            ar = obj.getJSONArray("word");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        JSONObject obj1 = null;
        final ArrayList<Item> items= new ArrayList<Item>();
        if (ar != null) {
            for(int i = 0; i<ar.length(); i++)
            {
                try {
                    obj1 = ar.getJSONObject(i);
                    result = obj1.getString("type");
                    String a1=obj1.getString("definition");
                    String a2=obj1.getString("example");
                    items.add(new Item(result,a1,a2));

                    //DataBase Input
                    insertWord(result,a1,a2);
                    //DataBase Input Complete

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }



        ItemAdapter itemAdapter=new ItemAdapter(MainActivity.this,items);
        ListView listView = (ListView) findViewById(R.id.list);
        listView.setAdapter(itemAdapter);
    }
}



    private void insertWord(String result,String a1,String a2)
    {
        SQLiteDatabase db=mDbHelper.getWritableDatabase();
        ContentValues values=new ContentValues();

        values.put(DicContract.DicEntry.C_WORD,p);
        values.put(DicContract.DicEntry.C_TYPE,result);
        values.put(DicContract.DicEntry.C_WORD_MEANING,a1);
        values.put(DicContract.DicEntry.C_WORD_EXAMPLE,a2);

        long newRow=db.insert(DicContract.DicEntry.TABLE_NAME,null,values);


        if(newRow==-1)
        {
            Toast.makeText(this,"Row is not alocated",Toast.LENGTH_SHORT).show();
        }

    }


    public  void goToMap(View view)
    {
        Intent i=new Intent(this,MapsActivity.class);
        startActivity(i);

    }


}