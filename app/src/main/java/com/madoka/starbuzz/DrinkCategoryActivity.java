package com.madoka.starbuzz;


import android.app.ListActivity;
//import android.content.Intent;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
//import android.view.View;
//import android.widget.ArrayAdapter;
import android.view.View;
import android.widget.CursorAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

public class DrinkCategoryActivity extends ListActivity {
    private SQLiteDatabase db; //We’re adding these as private variables so we can close the database and the cursor in our onDestroy method.
    private Cursor cursor;
    //This is significant when it comes to handling user clicks. A key difference
        // between Activity and ListActivity is that the ListActivity
         //  class already implements an on item click event listener Instead of creating your
           //own event listener, when you use a list activity you just need to
        //implement the onListItemClick() method.

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
      // The other difference is that you don’t need to use the setContentView()
        //method to say what layout the list activity should use. This is because list
        //activities define their own layouts so you don’t need to create one yourself.
        //setContentView(R.layout.activity_drink_category);
        ListView listDrinks = getListView();

        //using the CursorAdapter to fetch data from the sqlite
        try {
            SQLiteOpenHelper starbuzzDatabaseHelper = new StarbuzzDatabaseHelper(this);
            db = starbuzzDatabaseHelper.getReadableDatabase();
            cursor = db.query("DRINK",new String[]{"_id", "NAME"},//display the name of the drink in the list view
                    null, null, null, null, null);

            CursorAdapter listAdapter = new SimpleCursorAdapter(this,
                    android.R.layout.simple_list_item_1, //This is the same layout we used with the array adapter. It displays a single value for each row in the list view.
                    cursor,
                    new String[]{"NAME"},
                    new int[]{android.R.id.text1},//Map the contents of the NAME column to the text in the ListView.
                    0);
            listDrinks.setAdapter(listAdapter);//We’re still using an adapter, but this time it’s a cursor adapter.Use setAdapter() to connect the adapter to the list view.
        } catch (SQLiteException e) {
            Toast toast = Toast.makeText(this, "Database unavailable", Toast.LENGTH_SHORT);
            toast.show();
        }
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        cursor.close();
        db.close();
    }

    @Override
    public void onListItemClick(ListView listView, View itemView, int position, long id) {
        Intent intent = new Intent(DrinkCategoryActivity.this, DrinkActivity.class);
        intent.putExtra(DrinkActivity.EXTRA_DRINKNO, (int) id);
        startActivity(intent);
        Toast.makeText(getApplicationContext(), "you clicked"+ position , Toast.LENGTH_SHORT).show();
    }
        /*
        //when using the drink class use this
        //This populates the list view with data from the drinks array.The array contains Drink objects.
        ArrayAdapter<Drink> listAdapter = new ArrayAdapter<Drink>(this, android.R.layout.simple_list_item_1, Drink.drinks);
        listDrinks.setAdapter(listAdapter);// android.R.layout.simple_list_item_1 This is a built-in layout resource. It tells the array adapter to display each item in the array in a single text view.
    }

    //this is a very helpful method
    @Override
    public void onListItemClick(ListView listView, View itemView, int position, long id) { //Implement the onListItemClick() method so that DrinkActivity is launched when the user clicks on an item in the list view.
        Intent intent = new Intent(DrinkCategoryActivity.this, DrinkActivity.class);
        intent.putExtra(DrinkActivity.EXTRA_DRINKNO, (int) id);
        startActivity(intent);

         */

}


