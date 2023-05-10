package com.stchanga.atmok;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.snackbar.Snackbar;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.View;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.stchanga.atmok.databinding.ActivityMainBinding;

import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private boolean logon=false;
    private static final int REQUEST_LOGIN=100;
    private static final String TAG=MainActivity.class.getSimpleName();
    private AppBarConfiguration appBarConfiguration;
    private ActivityMainBinding binding;
    private List<Function> functions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.toolbar);


        //登入
        if(!logon){
            ActivityResultLauncher<Intent> launcher=registerForActivityResult(
                    new ActivityResultContracts.StartActivityForResult(),
                    result -> {
                        //活動結束，處理
                        if(result.getResultCode()== Activity.RESULT_OK){
                            Intent data=result.getData();
                        }
                    }
            );
            Intent intent=new Intent(this,LoginActivity.class);
            Bundle options=null;
            launcher.launch(intent);
        }
        setupRecyclerView();




    }
    private void setupRecyclerView() {
        RecyclerView recyclerView = findViewById(R.id.recycler);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 3));
        String[] funcs = getResources().getStringArray(R.array.functions);
        functions = new ArrayList<>();
        functions.add(new Function(funcs[0], R.drawable.func_transaction));
        functions.add(new Function(funcs[1], R.drawable.func_balance));
        functions.add(new Function(funcs[2], R.drawable.func_finance));
        functions.add(new Function(funcs[3], R.drawable.func_contacts));
        functions.add(new Function(funcs[4], R.drawable.func_exit));
        IconAdapter adapter=new IconAdapter();
        recyclerView.setAdapter(adapter);
    }

    public class IconAdapter extends RecyclerView.Adapter<IconAdapter.IconHolder>{


        @NonNull
        @Override
        public IconHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view=getLayoutInflater().inflate(R.layout.item_icon,parent,false);

            return new IconHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull IconHolder holder, int position) {
            Function function=functions.get(position);
            holder.nameText.setText(function.getName());
            holder.iconImage.setImageResource(function.getIcon());
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onItemClicked(function);
                }
            });
        }

        @Override
        public int getItemCount() {
            return functions.size();
        }

        public class IconHolder extends RecyclerView.ViewHolder{
            ImageView iconImage;
            TextView nameText;
            public IconHolder(View itemView){
                super(itemView);
                iconImage=itemView.findViewById(R.id.item_icon);
                nameText=itemView.findViewById(R.id.item_name);
            }
        }
    }

    private void onItemClicked(Function function) {

        Log.d(TAG,"onItemClicked"+function.getName());
        switch (function.getIcon()){
            case R.drawable.func_transaction:
                break;
            case R.drawable.func_balance:
                break;
            case R.drawable.func_finance:
                break;
            case R.drawable.func_contacts:
                Intent contacts=new Intent(this,ContactActivity.class);
                startActivity(contacts);
                break;
            case R.drawable.func_exit:
                finish();
                break;
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==REQUEST_LOGIN){
            if(requestCode!=RESULT_OK){
                finish();
            }
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


}