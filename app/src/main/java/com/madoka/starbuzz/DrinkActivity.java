package com.madoka.starbuzz;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class DrinkActivity extends AppCompatActivity {
    public static final String EXTRA_DRINKNO = "drinkNo";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drink);
        //Get the drink from the intent
        int drinkNo = (Integer) getIntent().getExtras().get(EXTRA_DRINKNO);
        //Drink drink = Drink.drinks[drinkNo];-uncomment this line when you want to use the drink class

        //using the sqlite Create a cursor
        try {
            SQLiteOpenHelper starbuzzDatabaseHelper = new StarbuzzDatabaseHelper(this);
            // SQLiteDatabase db = starbuzzDatabaseHelper.getReadableDatabase(); //reading from te sqlite db
            SQLiteDatabase db = starbuzzDatabaseHelper.getWritableDatabase();//You need read/write access to the db to update it
            // /Create a cursor the database to update it.
            Cursor cursor = db.query("DRINK", //from the drink table
                    new String[]{"NAME", "DESCRIPTION", "IMAGE_RESOURCE_ID", "FAVORITE"},
                    "_id = ?",
                    new String[]{Integer.toString(drinkNo)},
                    null, null, null);
            //Move to the first record in the Cursor
            if (cursor.moveToFirst()) {
                //use data from the cursor to populate the views
                //Get the drink details from the cursor
                String nameText = cursor.getString(0); //This is the first column in the cursor.
                String descriptionText = cursor.getString(1);//This is the second column in the cursor.
                int photoId = cursor.getInt(2);//This is the third column in the cursor.
                boolean isFavorite = (cursor.getInt(3) == 1); //Get the value of the FAVORITE column. It’s stored in the database as 1 for true, 0 for false.
                //Populate the drink name
                TextView name = findViewById(R.id.name);
                name.setText(nameText);
                //Populate the drink description
                TextView description = findViewById(R.id.description);
                description.setText(descriptionText);
                //Populate the drink image
                ImageView photo = findViewById(R.id.photo);
                photo.setImageResource(photoId);
                photo.setContentDescription(nameText);
                //Populate the favorite checkbox
                CheckBox favorite = findViewById(R.id.favorite);
                favorite.setChecked(isFavorite);
            }
            cursor.close();
            db.close();

        } catch (SQLiteException e) {
            Toast toast = Toast.makeText(this, "Database unavailable", Toast.LENGTH_SHORT);
            toast.show();
        }
     /*
     ////when using the drink class use this
     //Populate the drink image
        ImageView photo = (ImageView)findViewById(R.id.photo);
        photo.setImageResource(drink.getImageResourceId());
        photo.setContentDescription(drink.getName());
//Populate the drink name
        TextView name = (TextView)findViewById(R.id.name);
        name.setText(drink.getName());
//Populate the drink description
        TextView description = (TextView)findViewById(R.id.description);
        description.setText(drink.getDescription());

      */
    }

    public void onFavoriteClicked(View view) {
        int drinkNo = (Integer) getIntent().getExtras().get("drinkNo");
        /*
        CheckBox favorite = (CheckBox) findViewById(R.id.favorite);
        ContentValues drinkValues = new ContentValues();
        drinkValues.put("FAVORITE", favorite.isChecked());
        SQLiteOpenHelper starbuzzDatabaseHelper = new StarbuzzDatabaseHelper(DrinkActivity.this);
        try {
            SQLiteDatabase db = starbuzzDatabaseHelper.getWritableDatabase();
            db.update("DRINK", drinkValues,
                    "_id = ?", new String[]{Integer.toString(drinkNo)});
            db.close();
        } catch (SQLiteException e) {
            Toast toast = Toast.makeText(this, "Database unavailable", Toast.LENGTH_SHORT);
            toast.show();

        }*/
        new UpdateDrinkTask().execute(drinkNo);//Execute the AsyncTask and pass it the drink ID.
    }
       //Inner class to update the drink.using the async-task using threads
    private class UpdateDrinkTask extends AsyncTask<Integer, Void, Boolean> {
           ContentValues drinkValues;

           protected void onPreExecute() {
               CheckBox favorite = findViewById(R.id.favorite);
               drinkValues = new ContentValues();
               drinkValues.put("FAVORITE", favorite.isChecked());
           }
                  //run the database code in background
           protected Boolean doInBackground(Integer... drinks) {
               int drinkNo = drinks[0];
               SQLiteOpenHelper starbuzzDatabaseHelper =
                       new StarbuzzDatabaseHelper(DrinkActivity.this);
               try {
                   SQLiteDatabase db = starbuzzDatabaseHelper.getWritableDatabase();
                   db.update("DRINK", drinkValues,
                           "_id = ?", new String[]{Integer.toString(drinkNo)});//Update the value of the FAVORITE column.
                   db.close();
                   return true;
               } catch (SQLiteException e) {
                   return false;
               }
           }

           protected void onPostExecute(Boolean success) { //if the database code didn’t run OK, display a message to the user.
               if (!success) {
                   Toast toast = Toast.makeText(DrinkActivity.this,
                           "Database unavailable", Toast.LENGTH_SHORT);
                   toast.show();

               }
           }
       }//end of inner class
}