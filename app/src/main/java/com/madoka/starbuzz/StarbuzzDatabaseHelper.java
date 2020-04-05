package com.madoka.starbuzz;
//our sqlite database


import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class StarbuzzDatabaseHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "starbuzz"; // the name of our database
    private static final int DB_VERSION = 2; // the version of the database

    public StarbuzzDatabaseHelper(Context context) {
        super(context,DB_NAME, null, DB_VERSION);
    }


    //the onCreate method and onUpgrade method are mandatory
    @Override
    public void onCreate(SQLiteDatabase db) {

        updateMyDatabase(db, 0, DB_VERSION);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        updateMyDatabase(db, 0, DB_VERSION);


    }
    private void updateMyDatabase(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion < 1) {
            db.execSQL("CREATE TABLE DRINK (_id INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + "NAME TEXT, "
                    + "DESCRIPTION TEXT, "
                    + "IMAGE_RESOURCE_ID INTEGER);");
            insertDrink(db, "Latte", "Espresso and steamed milk", R.drawable.latte);
            insertDrink(db, "Cappuccino", "Espresso, hot milk and steamed-milk foam",
                    R.drawable.cappuccino);
            insertDrink(db, "Filter", "Our best drip coffee", R.drawable.filter);
            insertDrink(db, "Tea", "Espresso and steamed milk", R.drawable.latte);
            insertDrink(db, "Coffe", "Espresso, hot milk and steamed-milk foam",
                    R.drawable.cappuccino);
            insertDrink(db, "water", "Espresso and steamed milk", R.drawable.latte);
            insertDrink(db, "soda", "Espresso, hot milk and steamed-milk foam",
                    R.drawable.cappuccino);
            insertDrink(db, "fanta", "Espresso and steamed milk", R.drawable.latte);
            insertDrink(db, "juice", "Espresso, hot milk and steamed-milk foam",
                    R.drawable.cappuccino);
            insertDrink(db, "mongoj", "Espresso and steamed milk", R.drawable.latte);
            insertDrink(db, "alcohol", "Espresso, hot milk and steamed-milk foam",
                    R.drawable.cappuccino);
            insertDrink(db, "legend", "Espresso and steamed milk", R.drawable.latte);
            insertDrink(db, "bluemoon", "Espresso, hot milk and steamed-milk foam",
                    R.drawable.cappuccino);
            insertDrink(db, "majimoto", "Espresso and steamed milk", R.drawable.latte);
            insertDrink(db, "mahibaridi", "Espresso, hot milk and steamed-milk foam",
                    R.drawable.cappuccino);
            insertDrink(db, "sweetmelonwater", "Espresso and steamed milk", R.drawable.latte);
            insertDrink(db, "pawpawjucie", "Espresso, hot milk and steamed-milk foam",
                    R.drawable.cappuccino);
        }
        if (oldVersion < 2) {
            db.execSQL("ALTER TABLE DRINK ADD COLUMN FAVORITE NUMERIC;");
        }
    }

    private static void insertDrink(SQLiteDatabase db, String name, String description, int resourceId) {  //we need to create several drinks so we created this method
        ContentValues drinkValues = new ContentValues();
        drinkValues.put("NAME", name);
        drinkValues.put("DESCRIPTION", description);
        drinkValues.put("IMAGE_RESOURCE_ID", resourceId);
        db.insert("DRINK", null, drinkValues);
    }

}
