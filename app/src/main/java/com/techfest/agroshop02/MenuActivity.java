package com.techfest.agroshop02;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.techfest.agroshop02.adapter.MenuListAdpater;
import com.techfest.agroshop02.databinding.ActivityMainBinding;
import com.techfest.agroshop02.databinding.ActivityMenuBinding;
import com.techfest.agroshop02.listeners.MenuItemListners;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import Models.FarmersModel;
import Models.MenuItem;
import Models.PreferanceManager;

public class MenuActivity extends AppCompatActivity implements MenuItemListners {
    ActivityMenuBinding binding;
    PreferanceManager preferanceManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMenuBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.swappableRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getMenuList();

            }
        });
        preferanceManager = new PreferanceManager(getApplicationContext());
        getMenuList();
        loadUserDetails();
        setListners();


    }

    private void setListners() {
        binding.menuSearchbar.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                searchdata(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                searchdata(newText);
                return false;
            }
        });
    }

    private void searchdata(String query) {
        binding.recyclerViewMenuItem.setVisibility(View.GONE);
        if(query.isEmpty()){
            getMenuList();

        }

        FirebaseFirestore firebaseFirestore=FirebaseFirestore.getInstance();
        if(preferanceManager.getString(FarmersModel.KEY_DESIGNATION).matches("Farmer")){
            firebaseFirestore.collection(FarmersModel.KEY_MENU_COLLECTION)
                    .whereEqualTo(FarmersModel.KEY_ITEM_NAME,query)
                    .get()
                    .addOnCompleteListener(task -> {
                        String currrntUser=preferanceManager.getString(FarmersModel.KEY_USERID);
                        if(task.isSuccessful()&&task.getResult()!=null){
                            List<MenuItem> lists=new ArrayList<>();
                            lists.clear();
                            for(QueryDocumentSnapshot snapshot:task.getResult()){
                                MenuItem menuItem=new MenuItem();
                                if(snapshot.getString(FarmersModel.KEY_FARMER_ID).matches(currrntUser)){
                                    menuItem.productStatus=snapshot.getString(FarmersModel.KEY_ITEM_STATUS);
                                    menuItem.productId=snapshot.getId();
                                    menuItem.productPrice=snapshot.getString(FarmersModel.KEY_ITEM_PRICE);
                                    menuItem.productdate=snapshot.getString(FarmersModel.KEY_ITEM_PRICE);
                                    menuItem.productName=snapshot.getString(FarmersModel.KEY_ITEM_NAME);
                                   menuItem.productImage=snapshot.getString(FarmersModel.KEY_ITEM_PICTURE);
                                   menuItem.personDesignation=snapshot.getString(FarmersModel.KEY_DESIGNATION);
                                   menuItem.productDesciption=snapshot.getString(FarmersModel.KEY_ITEM_DESCRIPTION);
                                   lists.add(menuItem);

                                }
                            }
                            if(lists.size()>0) {
                           MenuListAdpater adpater = new MenuListAdpater(lists, this);
                           binding.recyclerViewMenuItem.setAdapter(adpater);
                                binding.recyclerViewMenuItem.setVisibility(View.VISIBLE);
 }

                        }
                    });

        }
        else if (preferanceManager.getString(FarmersModel.KEY_DESIGNATION).matches("Distributor")) {
            firebaseFirestore.collection(FarmersModel.KEY_MENU_COLLECTION)

                                      .whereEqualTo(FarmersModel.KEY_ITEM_NAME,query)
                                       .get()
                    .addOnCompleteListener(task -> {
                        if(task.isSuccessful()&&task.getResult()!=null){
                            List<MenuItem> lists=new ArrayList<>();
                            lists.clear();
                            for(QueryDocumentSnapshot snapshot:task.getResult()){
                                MenuItem menuItem=new MenuItem();
                                if(snapshot.getString(FarmersModel.KEY_ITEM_STATUS).matches("1")){
                                    menuItem.productStatus = snapshot.getString(FarmersModel.KEY_ITEM_STATUS);
                                   menuItem.productId = snapshot.getId();
                                   menuItem.personDesignation = snapshot.getString(FarmersModel.KEY_DESIGNATION);
                                   menuItem.productPrice = snapshot.getString(FarmersModel.KEY_ITEM_PRICE);
                                   menuItem.productdate = snapshot.getString(FarmersModel.KEY_ITEM_PRICE);
                                   menuItem.productName = snapshot.getString(FarmersModel.KEY_ITEM_NAME);
                                   menuItem.productImage = snapshot.getString(FarmersModel.KEY_ITEM_PICTURE);
                                   menuItem.productDesciption = snapshot.getString(FarmersModel.KEY_ITEM_DESCRIPTION);
                                   lists.add(menuItem);

                                }
                            }
                            if(lists.size()>0){
                                MenuListAdpater adpater=new MenuListAdpater(lists,this);
                               binding.recyclerViewMenuItem.setAdapter(adpater);
                                binding.recyclerViewMenuItem.setVisibility(View.VISIBLE);
                             }

                        }
                    });

        }

    }


    private String getReadableDateTime(Date date){

        return new SimpleDateFormat("dd-MMMM-yyyy", Locale.getDefault()).format(date);
    }
    private void loadUserDetails() {
        Long date=System.currentTimeMillis();
        SimpleDateFormat dateFormat =new SimpleDateFormat("dd-MMMM-yyyy - HH:mm a", Locale.getDefault());
        String dateStr = dateFormat.format(date);

        binding.MenuTime.setText(dateStr);
        getName();
    }

    private void getName() {
        if(preferanceManager.getString(FarmersModel.KEY_FNAME)!=null){
            binding.NameMenu.setText(preferanceManager.getString(FarmersModel.KEY_FNAME));
        }
       else if(preferanceManager.getString(FarmersModel.KEY_DNAME)!=null){
            binding.NameMenu.setText(preferanceManager.getString(FarmersModel.KEY_DNAME));
        }
    }

    private void getMenuList() {
        binding.recyclerViewMenuItem.setVisibility(View.VISIBLE);
        FirebaseFirestore firebaseFirestore=FirebaseFirestore.getInstance();
        firebaseFirestore.collection(FarmersModel.KEY_MENU_COLLECTION).get()
                .addOnCompleteListener(task -> {
                    List<MenuItem> lists=new ArrayList<>();
                  String currentUser=preferanceManager.getString(FarmersModel.KEY_USERID);
                  if(task.isSuccessful()&&task.getResult()!=null){

                      lists.clear();
                      for(QueryDocumentSnapshot queryDocumentSnapshot:task.getResult()){
                          Log.d("UserId",preferanceManager.getString(FarmersModel.KEY_USERID));
                          if(currentUser.matches(queryDocumentSnapshot.getString(FarmersModel.KEY_FARMER_ID)))
                          { MenuItem item=new MenuItem();
                              Log.d("ItemName",queryDocumentSnapshot.getString(FarmersModel.KEY_ITEM_NAME));


                              item.productdate=getReadableDateTime(queryDocumentSnapshot.getDate(FarmersModel.KEY_ITEM_DATE));
                              item.productDesciption=queryDocumentSnapshot.getString(FarmersModel.KEY_ITEM_DESCRIPTION);
                              item.productId=queryDocumentSnapshot.getId();
item.productStatus=queryDocumentSnapshot.getString(FarmersModel.KEY_ITEM_STATUS);

                              item.productImage=queryDocumentSnapshot.getString(FarmersModel.KEY_ITEM_PICTURE);
                              item.productName=queryDocumentSnapshot.getString(FarmersModel.KEY_ITEM_NAME);
                              item.productPrice=queryDocumentSnapshot.getString(FarmersModel.KEY_ITEM_PRICE);
                              item.personDesignation=queryDocumentSnapshot.getString(FarmersModel.KEY_DESIGNATION);

                         lists.add(item);
                          }
                          else if (preferanceManager.getString(FarmersModel.KEY_DESIGNATION).matches("Distributor"))
                          { MenuItem item=new MenuItem();

                            if(queryDocumentSnapshot.getString(FarmersModel.KEY_ITEM_STATUS).matches("1")){
                                Log.d("ItemName",queryDocumentSnapshot.getString(FarmersModel.KEY_ITEM_NAME));
                                item.productdate=getReadableDateTime(queryDocumentSnapshot.getDate(FarmersModel.KEY_ITEM_DATE));
                                item.productDesciption=queryDocumentSnapshot.getString(FarmersModel.KEY_ITEM_DESCRIPTION);
                                item.productId=queryDocumentSnapshot.getId();
                                item.productImage=queryDocumentSnapshot.getString(FarmersModel.KEY_ITEM_PICTURE);
                                item.productName=queryDocumentSnapshot.getString(FarmersModel.KEY_ITEM_NAME);
                                item.productPrice=queryDocumentSnapshot.getString(FarmersModel.KEY_ITEM_PRICE);
                                lists.add(item);
                            }
                          }
                      }
                      if(lists.size()>0){
                          LinearLayoutManager llm=new LinearLayoutManager(this);
                          llm.setOrientation(LinearLayoutManager.VERTICAL);
                          MenuListAdpater adpater=new MenuListAdpater(lists,this);
                          binding.recyclerViewMenuItem.setLayoutManager(llm);
                          binding.recyclerViewMenuItem.setHasFixedSize(true);
                          binding.recyclerViewMenuItem.setAdapter(adpater);


                      }
                      else {
                          Toast.makeText(this, "Empty list", Toast.LENGTH_SHORT).show();
                      }
                  }
                });
        binding.swappableRefresh.setRefreshing(false);
    }

    @Override
    public void onItemClicked(MenuItem menuItem) {
        Toast.makeText(this, "Clicked", Toast.LENGTH_SHORT).show();
        int count=0;

    }
}