package com.example.smartflex;

import static com.example.smartflex.Database.income;
import static com.example.smartflex.Database.needsCategory;
import static com.example.smartflex.Database.savingsCategory;
import static com.example.smartflex.Database.wantsCategory;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentContainer;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.firebase.Firebase;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity{

    FirebaseAuth auth;
    Button button;
    TextView textView;
    FirebaseUser user;

    private RecyclerView recyclerView;
    private CardRecyclerAdapter cardAdapter;
    private  bottoms_icon BottomIconFragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FragmentManager fragmentManager = getSupportFragmentManager();
        BottomIconFragment = (bottoms_icon) fragmentManager.findFragmentById(R.id.fragmentContainerView);


        Button btn = (Button)findViewById(R.id.createbudgetButton);

        /*//firebase
        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
        //for logout button
        //logout button and textview
        if(user == null){
            Intent intent = new Intent(getApplicationContext(), Login.class);
            startActivity(intent);
            finish();
        }
        else{
            textView.setText(user.getEmail());
        }

        //to logout
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            FirebaseAuth.getInstance().signOut();
            Intent intent = new Intent(getApplicationContext(),Login.class);
            startActivity(intent);
            finish();
            }
        });*/

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, createbudget_firstpage.class));
            }
        });

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 3)); // Display items in a line of three
        cardAdapter = new CardRecyclerAdapter(MainActivity.this, needsCategory);

        recyclerView.setAdapter(cardAdapter);

        recyclerView = findViewById(R.id.recyclerView2);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 3)); // Display items in a line of three
        cardAdapter = new CardRecyclerAdapter(MainActivity.this, wantsCategory);

        recyclerView.setAdapter(cardAdapter);

        recyclerView = findViewById(R.id.recyclerView3);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 3)); // Display items in a line of three
        cardAdapter = new CardRecyclerAdapter(MainActivity.this, savingsCategory);

        recyclerView.setAdapter(cardAdapter);

        TextView incomeTextView = findViewById(R.id.income);
        TextView balanceTextView = findViewById(R.id.balance);

        incomeTextView.setText("$" + income);
        float balance = income;
        for(Category category : needsCategory){
            balance -= category.moneySpent;
        }
        for(Category category : wantsCategory){
            balance -= category.moneySpent;
        }
        for(Category category : savingsCategory){
            balance -= category.moneySpent;
        }
        balanceTextView.setText("$" + Float.toString(balance));
    }

    @Override
    protected void onStart() {
        super.onStart();
        BottomIconFragment.setColorHome();
    }
}