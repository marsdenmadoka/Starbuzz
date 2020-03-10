package com.madoka.starbuzz;


import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
public class DrinkCategoryActivity extends ListActivity { //This is significant when it comes to handling user clicks. A key difference
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
        ArrayAdapter<Drink> listAdapter = new ArrayAdapter<Drink>( //This populates the list view with data from the drinks array.The array contains Drink objects.
                this,
                android.R.layout.simple_list_item_1,//This is a built-in layout resource. It tells the array adapter to display each item in the array in a single text view.
                Drink.drinks);
        listDrinks.setAdapter(listAdapter);
    }
    @Override
    public void onListItemClick(ListView listView, View itemView, int position, long id) { //Implement the onListItemClick() method so that DrinkActivity is launched when the user clicks on an item in the list view.
        Intent intent = new Intent(DrinkCategoryActivity.this, DrinkActivity.class);
        intent.putExtra(DrinkActivity.EXTRA_DRINKNO, (int) id);
        startActivity(intent);
    }
}


