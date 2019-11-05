package com.patelheggere.hamsa.threesixty.cardstack;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.patelheggere.hamsa.threesixty.R;
import com.wenchao.cardstack.CardStack;

public class CardStackActivity extends AppCompatActivity {

    private CardStack mCardStack;
    private CardsDataAdapter mCardAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card_stack);

        mCardStack = (CardStack) findViewById(R.id.container);

        mCardStack.setContentResource(R.layout.card_content);
//        mCardStack.setStackMargin(20);

        mCardAdapter = new CardsDataAdapter(getApplicationContext());
        DataModel dataModel = new DataModel();
        dataModel.setName("test1");

        mCardAdapter.add(dataModel);

        DataModel dataModel2 = new DataModel();
        dataModel2.setName("test2");
        mCardAdapter.add(dataModel2);


        DataModel dataModel3 = new DataModel();
        dataModel3.setName("test3");
        mCardAdapter.add(dataModel3);

        DataModel dataModel4 = new DataModel();
        dataModel4.setName("test4");
        mCardAdapter.add(dataModel4);

        mCardStack.setAdapter(mCardAdapter);

    }
}
