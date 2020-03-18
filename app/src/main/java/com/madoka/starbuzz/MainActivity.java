package com.madoka.starbuzz;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.widget.AdapterView;
import android.widget.CursorAdapter;
import android.widget.ListView;
import android.view.View;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private SQLiteDatabase db;
    private Cursor favoritesCursor;

    public MainActivity() {
    }

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

                /*}else if(position==1){
                   Intent intent = new Intent(MainActivity.this, DrinkCategoryActivity.class);
                    startActivity(intent);

                }else if(position ==2){
                    Intent intent = new Intent(MainActivity.this, DrinkCategoryActivity.class);
                    startActivity(intent);

                 */

                }
            }
        };
        //add listerner to the list view
        ListView listView = (ListView) findViewById(R.id.list_options);
        listView.setOnItemClickListener(itemClickListener);


        //Populate the list_favorites ListView from a cursor
        //  Get the favorites list view.
        ListView listFavorites = (ListView) findViewById(R.id.list_favorites);
        try {
            SQLiteOpenHelper starbuzzDatabaseHelper = new StarbuzzDatabaseHelper(this);
            db = starbuzzDatabaseHelper.getReadableDatabase();
            //Create a cursor that gets the values of the _id and NAME columns where FAVORITE=1.
            favoritesCursor = db.query("DRINK", new String[]{"_id", "NAME"}, //Get the names of the user’s favorite drinks.
                    "FAVORITE = 1",
                    null, null, null, null);
            CursorAdapter favoriteAdapter = new SimpleCursorAdapter(MainActivity.this,
                    android.R.layout.simple_list_item_1,
                    favoritesCursor,
                    new String[]{"NAME"},
                    new int[]{android.R.id.text1}, 0);
            listFavorites.setAdapter(favoriteAdapter);
        } catch (SQLiteException e) {
            Toast toast = Toast.makeText(this, "Database unavailable", Toast.LENGTH_SHORT);
            toast.show();
        }
        //Navigate to DrinkActivity if a drink is clicked
        listFavorites.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> listView, View v, int position, long id) {
                Intent intent = new Intent(MainActivity.this, DrinkActivity.class);
                intent.putExtra(DrinkActivity.EXTRA_DRINKNO, (int) id);
                startActivity(intent);
            }
        });
    }


    //Close the cursor and database in the onDestroy() method
    @Override
    public void onDestroy() {
        super.onDestroy();
        favoritesCursor.close();
        db.close();
    }

    //This gets called when the user returns to the  MainActivity method we restart in order to get the favourite list since Cursors don’t automatically refresh
    public void onRestart() {
        super.onRestart();
        try {
            StarbuzzDatabaseHelper starbuzzDatabaseHelper = new StarbuzzDatabaseHelper(this);
            db = starbuzzDatabaseHelper.getReadableDatabase();
            Cursor newCursor = db.query("DRINK",
                    new String[]{"_id", "NAME"},
                    "FAVORITE = 1",
                    null, null, null, null);
            ListView listFavorites = (ListView) findViewById(R.id.list_favorites);
            CursorAdapter adapter = (CursorAdapter) listFavorites.getAdapter();//Get the list view’s adapter.
            adapter.changeCursor(newCursor);//chnage the coursor used  by the cursor adapter to new one
            favoritesCursor = newCursor;
        } catch (SQLiteException e) {
            Toast toast = Toast.makeText(this, "Database unavailable", Toast.LENGTH_SHORT);
            toast.show();
        }
    }
}