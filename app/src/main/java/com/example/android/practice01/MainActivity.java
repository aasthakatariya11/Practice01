package com.example.android.practice01;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

    import android.app.LauncherActivity;
        import android.content.ClipData;
        import android.content.Intent;
        import android.os.AsyncTask;
        import android.support.v7.app.AppCompatActivity;
        import android.os.Bundle;
        import android.util.Log;
        import android.view.View;
        import android.widget.AdapterView;
        import android.widget.ArrayAdapter;
        import android.widget.ListView;
        import android.widget.TextView;

        import org.json.JSONArray;
        import org.json.JSONException;
        import org.json.JSONObject;

        import java.io.BufferedInputStream;
        import java.io.BufferedReader;
        import java.io.IOException;
        import java.io.InputStream;
        import java.io.InputStreamReader;
        import java.net.HttpURLConnection;
        import java.net.MalformedURLException;
        import java.net.URL;

public class MainActivity extends AppCompatActivity {

    TextView tv;
    //String[] subjects_list=new String[];
    String[] sname,unit1,unit2,unit3,unit4;
    String name1,name2,name3,name4,year;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        new jsonparser().execute();
    }

    private class jsonparser extends AsyncTask<String,String,String>
    {
        ListView lv;

        @Override
        protected String doInBackground(String... strings) {

            StringBuilder bl = null;

            try {
                URL url=new URL("https://api.myjson.com/bins/z8nb");
                HttpURLConnection conn= (HttpURLConnection) url.openConnection();
                InputStream in=new BufferedInputStream(conn.getInputStream());
                BufferedReader br=new BufferedReader(new InputStreamReader(in));
                bl=new StringBuilder();
                String line;
                while((line=br.readLine())!=null)
                {
                    bl.append(line);
                }

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            String jsonstr= String.valueOf(bl);
            Log.v("MainActivity",jsonstr);

            try {
                JSONObject obj=new JSONObject(jsonstr);
                JSONArray jarray=obj.getJSONArray("Subject ");
                sname=new String[jarray.length()];
                unit1=new String[jarray.length()];
                unit2=new String[jarray.length()];
                unit3=new String[jarray.length()];
                unit4=new String[jarray.length()];
                for(int i=0;i<jarray.length();i++)
                {
                    JSONObject ob=jarray.getJSONObject(i);
                    sname[i]=ob.getString("sname");
                    unit1[i]=ob.getString("unit1");
                    unit2[i]=ob.getString("unit2");
                    unit3[i]=ob.getString("unit3");
                    unit4[i]=ob.getString("unit4");
                }

                year=jarray.getJSONObject(0).getString("year");
                name1=jarray.getJSONObject(0).getString("name1");
                name2=jarray.getJSONObject(0).getString("name2");
                name3=jarray.getJSONObject(0).getString("name3");
                name4=jarray.getJSONObject(0).getString("name4");

            } catch (JSONException e) {
                e.printStackTrace();
            }

            //  Log.v("MainActivity", String.valueOf(sname));


            return bl.toString();
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            lv=findViewById(R.id.listView);
           /* for(int i=0;i<7;i++) {
                Log.v("MainActivity", sname[i]);
            }*/
            ArrayAdapter adapter=new ArrayAdapter(MainActivity.this,R.layout.layout,sname);
            lv.setAdapter(adapter);
            lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Intent intent=new Intent(MainActivity.this,Main2Activity.class);
                    intent.putExtra("subjectName",sname[position]);
                    intent.putExtra("name1",name1);
                    intent.putExtra("unit1",unit1[position]);
                    intent.putExtra("name2",name2);
                    intent.putExtra("unit2",unit2[position]);
                    intent.putExtra("name3",name3);
                    intent.putExtra("unit3",unit3[position]);
                    intent.putExtra("name4",name4);
                    intent.putExtra("unit4",unit4[position]);
                    intent.putExtra("year",year);
                    startActivity(intent);

                }
            });

        }
    }




}
