package com.evatar.blackjack.views;

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
import com.sxh.bcllib.IBclCallBack;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.util.ArrayList;
import java.util.List;



public class StoreActivity extends AppCompatActivity {
    private MyAdapter mAdapter;
    private RecyclerView mRecyclerView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store);

        ArrayList<CardInfo> myDataset = new ArrayList<>();

        //////////////////////////////////////////////
        {
            CardInfo tmp = new CardInfo();
            tmp.num = 0;
            tmp.imgID = R.drawable.back_red;
            tmp.name = "red";
            tmp.price = 100;
            myDataset.add(tmp);
        }
        /*
        {
            CardInfo tmp = new CardInfo();
            tmp.num = 1;
            tmp.imgID = R.drawable.back_green;
            tmp.name = "green";
            tmp.price = 100;
            myDataset.add(tmp);
        }
        {
            CardInfo tmp = new CardInfo();
            tmp.num = 2;
            tmp.imgID = R.drawable.back_purple;
            tmp.name = "purple";
            tmp.price = 100;
            myDataset.add(tmp);
        }

        {
            CardInfo tmp = new CardInfo();
            tmp.num = 3;
            tmp.imgID = R.drawable.back_yellow;
            tmp.name = "yellow";
            tmp.price = 100;
            myDataset.add(tmp);
        }
        */
        //////////////////////////////////////////////

        mAdapter = new MyAdapter(myDataset);
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView_own_cards);
        final LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setAdapter(mAdapter);

        Button buyButton = (Button) findViewById(R.id.button_buy);
        buyButton.setOnClickListener( new Button.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(StoreActivity.this, BuyActivity.class);
                //intent.putExtra("IMAGE_ID", ownCard.imgID);
                //startActivity(intent);

                //currentPos = position;

                // Start the SecondActivity
                //Intent intentResult = new Intent(StoreActivity.this, SellActivity.class);
                startActivityForResult(intent, BUY_ACTIVITY_REQUEST_CODE);
            }
        });

        Button closeButton = (Button) findViewById(R.id.button_close);
        closeButton.setOnClickListener( new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                StoreActivity.this.finish();
            }
        });

        GlobalClass.getInstance().bclSDK.getAccountBalances(GlobalClass.getInstance().playerName, "1.3.0", new IBclCallBack() {
            @Override
            public void onReceiveValue(String s) {
                int status = 0;
                try {
                    JSONObject jsonObj = (JSONObject) new JSONTokener(s).nextValue();
                    status = jsonObj.getInt("status");
                    JSONObject data = jsonObj.getJSONObject("data");
                    String token = data.getString("MTN");

                    TextView ownToken = (TextView)findViewById(R.id.textView_own_token);
                    if( status > 0 ) {
                        ownToken.setText("Your MTN:$"+token);
                    }
                }catch (JSONException e) {

                }
            }
        });
    }

    private static final int BUY_ACTIVITY_REQUEST_CODE = 0;
    private static final int SELL_ACTIVITY_REQUEST_CODE = 1;

    // This method is called when the second activity finishes
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // check that it is the SecondActivity with an OK result
        if (requestCode == BUY_ACTIVITY_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                CardInfo card = (CardInfo) data.getSerializableExtra("CardInfo");
                card.num = mAdapter.mData.size();
                mAdapter.addData(card);
            }
        }
        if (requestCode == SELL_ACTIVITY_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {

                // get String data from Intent
                String returnString = data.getStringExtra(Intent.EXTRA_TEXT);

                mAdapter.removeData();
            }
        }
    }

    public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {
        private List<CardInfo> mData;
        private int currentPos = -1;

        public class ViewHolder extends RecyclerView.ViewHolder {
            private TextView mTextView;
            private ImageView mImageView;
            private TextView mNameTextView;
            private TextView mPriceTextView;
            private Button mChangeCardButton;
            private Button mSellCardButton;
            private ViewHolder(View v) {
                super(v);
                mTextView = (TextView) v.findViewById(R.id.textView_card_num);
                mImageView = (ImageView) v.findViewById(R.id.imageView_card_img);
                mNameTextView = (TextView) v.findViewById(R.id.textView_card_name);
                mPriceTextView = (TextView) v.findViewById(R.id.textView_card_price);
                mChangeCardButton = (Button) v.findViewById(R.id.button_card_change);
                mSellCardButton = (Button) v.findViewById(R.id.button_card_sell);
            }
        }

        public MyAdapter(List<CardInfo> data) {
            mData = data;
        }

        @Override
        public MyAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.own_item, parent, false);
            ViewHolder vh = new ViewHolder(v);
            return vh;
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, final int position) {
            CardInfo cardInfo = mData.get(position);
            holder.mTextView.setText(Integer.toString(cardInfo.num));
            holder.mImageView.setImageDrawable(getResources().getDrawable(cardInfo.imgID));
            holder.mNameTextView.setText(cardInfo.name);
            holder.mPriceTextView.setText("$"+Integer.toString(cardInfo.price));
            holder.mChangeCardButton.setOnClickListener( new Button.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ((ImageView)StoreActivity.this.findViewById(R.id.imageView_current_card)).setImageDrawable(getResources().getDrawable(cardInfo.imgID));
                    GlobalClass.getInstance().currentCardImgID = cardInfo.imgID;
                }
            });
            holder.mSellCardButton.setOnClickListener( new Button.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if( mData.size() > 1 ) {
                        Intent intent = new Intent(StoreActivity.this, SellActivity.class);
                        intent.putExtra("IMAGE_ID", cardInfo.imgID);
                        intent.putExtra("CARD_NAME", cardInfo.name);
                        intent.putExtra("CARD_PRICE", cardInfo.price);
                        //startActivity(intent);

                        currentPos = position;

                        // Start the SecondActivity
                        //Intent intentResult = new Intent(StoreActivity.this, SellActivity.class);
                        startActivityForResult(intent, SELL_ACTIVITY_REQUEST_CODE);
                    }
                }
            });
        }

        public void addData(CardInfo c) {
            mData.add(c);
            MyAdapter.this.notifyItemInserted(mData.size());
        }

        public void removeData() {
            boolean isDeleteCurrentImgID = false;
            if( GlobalClass.getInstance().currentCardImgID == mData.get(currentPos).imgID )
                isDeleteCurrentImgID = true;
            mData.remove(currentPos);
            if ( isDeleteCurrentImgID ) {
                GlobalClass.getInstance().currentCardImgID = mData.get(0).imgID;
            }
            ((ImageView)StoreActivity.this.findViewById(R.id.imageView_current_card)).setImageDrawable(getResources().getDrawable(GlobalClass.getInstance().currentCardImgID));
            MyAdapter.this.notifyItemRemoved(currentPos);
        }

        @Override
        public int getItemCount() {
            return mData.size();
        }
    }
}
