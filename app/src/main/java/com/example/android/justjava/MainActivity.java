package com.example.android.justjava;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.NumberFormat;

/**
 * This app displays an order form to order coffee.
 */
public class MainActivity extends ActionBarActivity {
    int quantity = 1;
    int basepricePerCup = 5;
    int whippedCreamPrice;
    int chocolatePrice;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * This method is called when the '+' button is clicked.
     */
    public void increment(View view) {
        if(quantity==100){
            Toast.makeText(MainActivity.this, "You cannot order more than 100 cups", Toast.LENGTH_SHORT).show();
            return;
        }
        quantity = quantity + 1;
        displayQuantity(quantity);
    }

    /**
     * This method is called when the '-' button is clicked.
     */
    public void decrement(View view) {
        if(quantity==1){
            Toast.makeText(MainActivity.this, "You cannot order less than 1 cup", Toast.LENGTH_SHORT).show();
            return;
        }
        quantity = quantity - 1;
        displayQuantity(quantity);
    }

    /**
     * This method is called when the order button is clicked, it displays order summary on the screen.
     */
    public void submitOrder(View view) {
      String orderSummary = createOrderSummary();
        String name = nameEntry();
        String emailSubject = "Just Java order for "+name;
      //displayMessage(orderSummary);
       sendEmailOrder(emailSubject,orderSummary);

    }
    /**
     * This method is called when the order button is clicked, it opens email app and sends order with order summary on the email.
     */
    public void sendEmailOrder( String subject,String message){
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:")); // only email apps should handle this
        intent.putExtra(Intent.EXTRA_SUBJECT, subject);
        intent.putExtra(Intent.EXTRA_TEXT,message);
        if(intent.resolveActivity(getPackageManager()) != null){
            startActivity(intent);
        }
    }

    private String createOrderSummary() {

        String nameEntered = nameEntry();
        boolean hasWhippedCream = whippedCreamCheck();
        boolean hasChocolate = chocolateCheck();
        int price = calculatePrice(hasWhippedCream,hasChocolate);
        Log.v("MainActivity", "Has WhippedCream: " + hasWhippedCream);
        Log.v("MainActivity", "Has Chocolate: " + hasChocolate);
        String orderSummaryMessage = "Name: "+ nameEntered + "\nAdd Whipped Cream? " + hasWhippedCream
                + "\nAdd Chocolate? " + hasChocolate + "\nQuantity: "
                + quantity + "\nTotal = $" + price + "\nThank you!";
        return orderSummaryMessage;

    }

    /**
     * Calculates the price of the order.
     */
    private int calculatePrice(boolean addWhippedCream, boolean addChocolate) {
        if(addWhippedCream){
            whippedCreamPrice = 1;
        }else{
            whippedCreamPrice = 0;
        }
        if(addChocolate){
            chocolatePrice = 2;
        }else{
            chocolatePrice = 0;
        }
        int price = quantity * (basepricePerCup + whippedCreamPrice + chocolatePrice);
        return price;
    }

    /**
     * This method displays the given quantity value on the screen.
     */
    private void displayQuantity(int number) {
        TextView quantityTextView = (TextView) findViewById(
                R.id.quantity_text_view);
        quantityTextView.setText("" + number);
    }

    /**
     * This method displays the given price on the screen.

     private void displayPrice(int number) {
     TextView priceTextView = (TextView) findViewById(R.id.price_text_view);
     priceTextView.setText(NumberFormat.getCurrencyInstance().format(number));
     }
     */

    /**
     * This method displays the given text on the screen.
     */
    private void displayMessage(String message) {
        TextView orderSummaryTextView = (TextView) findViewById(R.id.order_summary_text_view);
        orderSummaryTextView.setText(message);
    }

    /**
     * This method checks the state of the CheckBox Whipped Cream either true or false.
     */

    public boolean whippedCreamCheck() {
        CheckBox checked = (CheckBox) findViewById(R.id.whipped_cream_checkbox);
        return checked.isChecked();
    }
    /**
     * This method checks the state of the CheckBox Chocolate either true or false.
     */

    public boolean chocolateCheck() {
        CheckBox checked = (CheckBox) findViewById(R.id.chocolate_checkbox);
        return checked.isChecked();
    }
    /**
     * This method gets the text entered in the Name EditText field
     */

    private String nameEntry(){
        EditText name = (EditText)findViewById(R.id.name_editText);
        return String.valueOf(name.getText());
    }
}
