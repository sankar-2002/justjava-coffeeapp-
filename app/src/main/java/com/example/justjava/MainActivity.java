package com.example.justjava;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    //findviewid and and setcontentview are present in Appcompatactivity which is extented here in main activity...
    //oncreate sets the hierarchy and findviewbyid searches for the view in that hierarchy...

    int numberOfCoffees = 2;

    //this method is called when plus button is clicked....
    public void increment(View view) {
        if(numberOfCoffees == 100)
        {
            Toast.makeText(this,"you cannot have more than 100 coffees..", Toast.LENGTH_SHORT).show();
            return;  //exits the method immediately without executing next lines of code...
        }
        numberOfCoffees = numberOfCoffees + 1;
        displayQuantity(numberOfCoffees);
    }

    //this method is called when minus button is clicked....
    public void decrement(View view) {
        if(numberOfCoffees == 1)
        {
            Toast.makeText(this,"you cannot have less than 1 coffee..", Toast.LENGTH_SHORT).show();
            return;  //exits the method immediately without executing next lines of code...
        }
        numberOfCoffees = numberOfCoffees - 1;
        displayQuantity(numberOfCoffees);
    }


    //this method is called when order button is clicked....
    public void submitOrder(View view) {

        EditText namefield = (EditText) findViewById(R.id.name_field); //read the text input in the name and store it in value using getText method..
        String name = namefield.getText().toString(); //getText returns editable object which cannot be stored in name..therefore getText method is chained with toString method which will store name...
        Log.v("MainActivity", "Name: "+ name); //log message to check whether it is stored in the name variable or not...

        CheckBox whippedCreamCheckBox =(CheckBox)findViewById(R.id.whipped_cream_checkbox); //creating an object of checkbox and finding it;s view using view id and typecasting it to checkbox
        boolean hasWhippedCream = whippedCreamCheckBox.isChecked();  //using isChecked method returning true or false and storing it in variable hasWhippedCream..
        Log.v("MainActivity", "Has whipped Cream : " + hasWhippedCream); //log message to check whether it stores or not... can be removed after checking the o/p in the device

        CheckBox chocolateCheckbox =(CheckBox)findViewById(R.id.chocolate_checkbox); //same steps as we added whippedcream checkbox..
        boolean hasChocolate = chocolateCheckbox.isChecked();

        int price = calculatePrice(hasWhippedCream,hasChocolate);
        String priceMessage = createOrderSummary(name,price,hasWhippedCream,hasChocolate);

        //intent to send it to email app..
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:")); //only email app should handle this...
        intent.putExtra(Intent.EXTRA_SUBJECT, "Just Java Order For " + name);
        intent.putExtra(Intent.EXTRA_TEXT, priceMessage);
        if (intent.resolveActivity(getPackageManager()) != null) { //will not do anything if the expected app is not found...
            startActivity(intent);
        }

//        displayMessage(createOrderSummary(name,price,hasWhippedCream,hasChocolate));
    }

    private int calculatePrice(boolean addWhippedCream,boolean addChocolate) {
        int basePrice = 5;

        if(addWhippedCream)
        {
            basePrice = basePrice + 1;

        }

        if(addChocolate)
        {
            basePrice = basePrice + 2;
        }

        return numberOfCoffees * basePrice;
    }

    private String createOrderSummary(String name,int price,boolean addWhippedCream,boolean addChocolate) {
        String priceMessage = "Name : " + name + "\nAdd Whipped Cream ? :  "+ addWhippedCream + "\nAdd Chocolate ? :  "+ addChocolate + "\nQuantity : " + numberOfCoffees + "\nTotal : $" + price + " WooHoo!" + "\nThank You!";
        return priceMessage;
    }

    //this method displays quantity value on the screen...
    private void displayQuantity(int number) {
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        quantityTextView.setText(number + "");
    }

//    this displays the given text on the screen......
//    private void displayMessage(String message) {
//        TextView orderSummaryTextView = (TextView) findViewById(R.id.order_summary_text_view); //typecasted from view to textview to find settext,,,
//        orderSummaryTextView.setText(message);
//    }

}