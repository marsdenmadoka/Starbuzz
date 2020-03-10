package com.madoka.starbuzz;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.AdapterView;
import android.widget.ListView;
import android.view.View;
public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Create an OnItemClickListener
        AdapterView.OnItemClickListener itemClickListener = new AdapterView.OnItemClickListener() { //OnItemClickListener is
            //a nested class with the  AdapterView class. A ListView is a subclass of AdapterView.
            public void onItemClick(AdapterView<?> listView, View v, int position, long id) { //These give you more about which item was clicked in the list view such as the item view and its position.
                if (position == 0) { //if we click the first item in the list then launch the DrinkCategoryActivity
                    Intent intent = new Intent(MainActivity.this, DrinkCategoryActivity.class);
                    startActivity(intent);

                }else if(position==1){
                   Intent intent = new Intent(MainActivity.this, DrinkCategoryActivity.class);
                    startActivity(intent);

                }else if(position ==2){
                    Intent intent = new Intent(MainActivity.this, DrinkCategoryActivity.class);
                    startActivity(intent);

                }
            }
        };
        //add listerner to the list view
        ListView listView = (ListView) findViewById(R.id.list_options);
        listView.setOnItemClickListener(itemClickListener);
    }
}