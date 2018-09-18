package com.evatar.blackjack.views;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.evatar.blackjack.R;

public class SellActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sell_card);

        int imageID= getIntent().getIntExtra("IMAGE_ID",0);
        ImageView image = (ImageView) findViewById(R.id.imageView_title);
        image.setImageDrawable(getResources().getDrawable(imageID));

        String cardNameStr = getIntent().getStringExtra("CARD_NAME");
        EditText cardName = (EditText) findViewById(R.id.editText_card_name);
        cardName.setText(cardNameStr);

        int cardPriceInt = getIntent().getIntExtra("CARD_PRICE",0);
        EditText cardPrice = (EditText) findViewById(R.id.editText_card_price);
        cardPrice.setText(Integer.toString(cardPriceInt));

        Button confirmButton = (Button) findViewById(R.id.button_confirm);
        confirmButton.setOnClickListener( new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                //StoreActivity.this.MyAdapter.removeData

                new AlertDialog.Builder(SellActivity.this)
                        .setTitle("Sell Card")
                        .setMessage("Confirm sell?")
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                            }
                        })
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                Intent intent = new Intent();
                                intent.putExtra(Intent.EXTRA_TEXT, 0);
                                setResult(RESULT_OK, intent);

                                SellActivity.this.finish();
                            }
                        })
                        .show();
            }
        });


        Button cancelButton = (Button) findViewById(R.id.button_cancel);
        cancelButton.setOnClickListener( new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                SellActivity.this.finish();
            }
        });
    }
}
