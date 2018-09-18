package com.evatar.blackjack.views;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.evatar.blackjack.R;

import java.util.ArrayList;
import java.util.List;

public class BuyActivity extends AppCompatActivity {
    private MyAdapter mAdapter;
    private RecyclerView mRecyclerView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buy_card);


        ArrayList<CardInfo> myDataset = new ArrayList<>();

        //////////////////////////////////////////////
        {
            CardInfo tmp = new CardInfo();
            tmp.num = 0;
            tmp.imgID = R.drawable.back_yellow;
            tmp.name = "yellow";
            tmp.price = 200;
            myDataset.add(tmp);
        }
        {
            CardInfo tmp = new CardInfo();
            tmp.num = 1;
            tmp.imgID = R.drawable.back_green;
            tmp.name = "green";
            tmp.price = 200;
            myDataset.add(tmp);
        }
        {
            CardInfo tmp = new CardInfo();
            tmp.num = 2;
            tmp.imgID = R.drawable.back_purple;
            tmp.name = "purple";
            tmp.price = 200;
            myDataset.add(tmp);
        }

        /*
        {
            CardInfo tmp = new CardInfo();
            tmp.num = 3;
            tmp.imgID = R.drawable.back_red;
            tmp.name = "red1";
            tmp.price = 200;
            myDataset.add(tmp);
        }
        */
        //////////////////////////////////////////////

        mAdapter = new MyAdapter(myDataset);
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView_buy_list);
        final LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setAdapter(mAdapter);

    }



    public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {
        private List<CardInfo> mData;
        private int currentPos = -1;

        public class ViewHolder extends RecyclerView.ViewHolder {
            private TextView mNumTextView;
            private ImageView mImageView;
            private TextView mNameTextView;
            private TextView mPriceTextView;
            private Button mBuyCardButton;
            private ViewHolder(View v) {
                super(v);
                mNumTextView = (TextView) v.findViewById(R.id.textView_card_num);
                mImageView = (ImageView) v.findViewById(R.id.imageView_card_img);
                mNameTextView = (TextView) v.findViewById(R.id.textView_card_name);
                mPriceTextView = (TextView) v.findViewById(R.id.textView_card_price);
                mBuyCardButton = (Button) v.findViewById(R.id.button_card_buy);
            }
        }

        public MyAdapter(List<CardInfo> data) {
            mData = data;
        }

        @Override
        public BuyActivity.MyAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.buy_item, parent, false);
            BuyActivity.MyAdapter.ViewHolder vh = new BuyActivity.MyAdapter.ViewHolder(v);
            return vh;
        }

        @Override
        public void onBindViewHolder(MyAdapter.ViewHolder holder, final int position) {
            CardInfo card = mData.get(position);
            holder.mNumTextView.setText(Integer.toString(card.num));
            holder.mImageView.setImageDrawable(getResources().getDrawable(card.imgID));
            holder.mNameTextView.setText(card.name);
            holder.mPriceTextView.setText("$"+Integer.toString(card.price));
            holder.mBuyCardButton.setOnClickListener( new Button.OnClickListener() {
                @Override
                public void onClick(View v) {

                    new AlertDialog.Builder(BuyActivity.this)
                            .setTitle("Buy Card")
                            .setMessage("Confirm buy?")
                            .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                }
                            })
                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                    Intent intent = new Intent();
                                    intent.putExtra("CardInfo", card);
                                    setResult(RESULT_OK, intent);

                                    BuyActivity.this.finish();
                                }
                            })
                            .show();
                }
            });
        }


        @Override
        public int getItemCount() {
            return mData.size();
        }
    }
}
