package com.example.webtextreader;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    String webContent;
    char tenth_place_char,each_tenth_char;
    TextView webText,tenth_Char,every_tenth_char,word_count;
    Button execute;
    AsyncClass asyncClass;
    public static final String Tag1="Tag1";
    Map<String, Integer> map;
    char[] arr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        webText=findViewById(R.id.webText);
        tenth_Char=findViewById(R.id.tenth_Char);
        every_tenth_char=findViewById(R.id.every_tenth_char);
        word_count=findViewById(R.id.word_count);

        execute=findViewById(R.id.execute);

        execute.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                asyncClass=new AsyncClass();
                asyncClass.execute();
            }
        });

    }


    public class AsyncClass extends AsyncTask<Void,Void,Void>{

        @Override
        protected Void doInBackground(Void... voids) {

            try{

                Document document= (Document) Jsoup.connect("https://blog.truecaller.com/2018/01/22/life-as-an-android-engineer/").get();
                webContent=document.text();
                if(webContent.isEmpty()){
                    Log.d(Tag1,"Opps... Something Went Wrong");
                }
                else{
                    tenth_place_char=webContent.charAt(10);
                    arr=new char[webContent.length()-1];
                    for(int i=10;i<webContent.length()-1;i=i+10)
                    {
                        arr[i]= webContent.charAt(i);
                        Log.d(Tag1, String.valueOf(arr[i]));
                    }
                    //unique word count
                    map=new HashMap<String,Integer>();
                    for(String word:webContent.split(" ")){
                        if(map.containsKey(word)){
                            map.put(word,map.get(word)+1);
                        }else{
                            map.put(word,1);

                        }
                    }
                    Log.d(Tag1,map.toString());
                }
            }catch (Exception e){
                e.printStackTrace();
            }

            return null;
        }

        @SuppressLint("SetTextI18n")
        @Override
        protected void onPostExecute(Void unused) {
            super.onPostExecute(unused);
            webText.setText(webContent);
            if(tenth_place_char==' ')
            {
                tenth_Char.setText("Blank space found");
                Log.d(Tag1,"Blank");

            }else{
                Log.d(Tag1,String.valueOf(tenth_place_char));
            tenth_Char.setText(String.valueOf(tenth_place_char));
            }
            every_tenth_char.setText(String.valueOf(arr));
            word_count.setText(String.valueOf(map));
        }
    }
}