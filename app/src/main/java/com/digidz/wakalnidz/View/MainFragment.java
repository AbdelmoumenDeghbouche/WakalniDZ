package com.digidz.wakalnidz.View;

import static android.app.Activity.RESULT_OK;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.digidz.wakalnidz.Model.Cart_food_Model;
import com.digidz.wakalnidz.Model.FoodModel;
import com.digidz.wakalnidz.R;
import com.digidz.wakalnidz.ViewModel.Food_list_adapter;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;
import java.util.ArrayList;


public class MainFragment extends Fragment {
    private static final String TAG = "";
    private static final int CHOOSE_IMAGE_REQUEST_CODE = 101;
    public static RecyclerView rv_food_with_add_to_cart;
    public static ArrayList<FoodModel> foods_list = new ArrayList<>();
    private final ArrayList<FoodModel> foodCategories_list = new ArrayList<>();
    private EditText edit_txt_find_food;
    private FirebaseAuth mAuth;
    private FirebaseUser user;
    private FirebaseDatabase rootNode;
    private DatabaseReference cartRef;
    private Food_list_adapter adapter2;
    private Food_list_adapter adapter;
    private ImageView img_profile_pic;
    private ProgressBar prg_bar_profile_pic;
    private RecyclerView categories_rv;
    private RelativeLayout rel_mid_main, rel_layout_error;
    private String url_of_img_profile, userName;
    private TextView txt_username, txt_categories, txt_popular;
    private Uri uri_img_profile;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = LayoutInflater.from(getContext()).inflate(R.layout.fragment_main, container, false);
        AssingElements(view);
        setting_up_list_1();
        setting_up_rv_1();
        setting_up_list_2();
        setting_up_rv();
        businessLogic();
        getCartArrayListFromFirebase();
        return view;
    }


    private void businessLogic() {
        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();
        setImageSelectedFromPhoneStorage();
        if (user != null) {
            initUserData();

        }
        foodSearchFuncLogic();

    }


    private void foodSearchFuncLogic() {
        edit_txt_find_food.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                filterFoodsList(editable.toString().toLowerCase().trim());

                treatViewsChangeWhenSearchingOnFood(editable);

            }
        });
    }

    private void treatViewsChangeWhenSearchingOnFood(Editable editable) {
        categories_rv.setVisibility(View.GONE);
        rel_mid_main.setVisibility(View.GONE);
        txt_categories.setVisibility(View.GONE);
        rel_layout_error.setVisibility(View.GONE);
        if (editable.toString().trim().equals("")) {
            rel_mid_main.setVisibility(View.VISIBLE);
            rv_food_with_add_to_cart.setVisibility(View.VISIBLE);
            txt_categories.setVisibility(View.VISIBLE);
            txt_popular.setVisibility(View.VISIBLE);
            rel_layout_error.setVisibility(View.GONE);
            categories_rv.setVisibility(View.VISIBLE);


        } else if (adapter2.isEmpty()) {
            txt_popular.setVisibility(View.GONE);
            rv_food_with_add_to_cart.setVisibility(View.GONE);
            rel_layout_error.setVisibility(View.VISIBLE);

        }
    }

    private void filterFoodsList(String trim) {
        ArrayList<FoodModel> foodModelArrayList = new ArrayList<>();
        for (FoodModel foodModel : foods_list) {
            if (foodModel.getFoodSpecialName().trim().toLowerCase().contains(trim.trim().toLowerCase())) {
                foodModelArrayList.add(foodModel);
            }
        }
        adapter2.filterList(foodModelArrayList);
    }

    private void initUserData() {
        txt_username.setText(user.getDisplayName());
        if (user.getPhotoUrl() != null) {
            Glide.with(this)
                    .load(user.getPhotoUrl().toString())
                    .into(img_profile_pic);
        }

    }


    private void downloadImageFromFirebase() {
        StorageReference storageReferenceDownloadImage = FirebaseStorage.getInstance().getReference();
        StorageReference reference = storageReferenceDownloadImage.child("ProfilePics/" + user.getUid());
        reference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                prg_bar_profile_pic.setVisibility(View.GONE);

                Toast.makeText(getContext(), "Photo Uploaded Successfully", Toast.LENGTH_SHORT).show();
                UserProfileChangeRequest userProfileChangeRequest = new UserProfileChangeRequest.Builder()
                        .setPhotoUri(uri)
                        .build();

                if (user != null) {
                    user.updateProfile(userProfileChangeRequest);

                }

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void uploadImageToFirebaseStorage() {

        StorageReference storageReferenceOfProfileImages = FirebaseStorage.getInstance().getReference("ProfilePics/" + user.getUid());
        if (uri_img_profile != null) {
            prg_bar_profile_pic.setVisibility(View.VISIBLE);
            img_profile_pic.setVisibility(View.GONE);
            storageReferenceOfProfileImages.putFile(uri_img_profile)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            Toast.makeText(getContext(), "Image Uploaded successfully", Toast.LENGTH_SHORT).show();
                            downloadImageFromFirebase();

                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
        }
    }


    private void AssingElements(View view) {
        categories_rv = view.findViewById(R.id.rv_food_categories);
        rv_food_with_add_to_cart = view.findViewById(R.id.rv_food_with_add_to_cart);
        img_profile_pic = view.findViewById(R.id.img_profile_pic);
        edit_txt_find_food = view.findViewById(R.id.edit_txt_find_food);
        txt_username = view.findViewById(R.id.txt_username);
        rel_mid_main = view.findViewById(R.id.rel_mid_main);
        prg_bar_profile_pic = view.findViewById(R.id.prg_bar_profile_pic);
        txt_categories = view.findViewById(R.id.txt_categories);
        rel_layout_error = view.findViewById(R.id.rel_layout_error);
        txt_popular = view.findViewById(R.id.txt_popular);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case CHOOSE_IMAGE_REQUEST_CODE:
                if (resultCode == RESULT_OK && data != null && data.getData() != null) {
                    uri_img_profile = data.getData();
                    uploadImageToFirebaseStorage();
                    Bitmap bitmap = null;
                    try {
                        bitmap = MediaStore.Images.Media.getBitmap(getContext().getContentResolver(), uri_img_profile);
                        img_profile_pic.setImageBitmap(bitmap);

                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }
        }
    }

    private void setImageSelectedFromPhoneStorage() {
        img_profile_pic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Please Choose an image"), CHOOSE_IMAGE_REQUEST_CODE);

            }
        });

    }

    private void setting_up_rv() {
        adapter2 = new Food_list_adapter(foods_list, getContext());
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        rv_food_with_add_to_cart.setAdapter(adapter2);
        rv_food_with_add_to_cart.setLayoutManager(linearLayoutManager);

    }

    private void setting_up_list_2() {
        getCartArrayListFromFirebase();
        //TO clear repeated elements when u choose a k
        foods_list.clear();
        FoodModel foodModel = new FoodModel("Pizza", "Pepperoni Pizza", "pop_1", "400", R.drawable.cardview_style_white);
        foods_list.add(foodModel);
        foods_list.add(new FoodModel("Burger", "        Big Burger        ", "pop_2", "300", R.drawable.cardview_style_white));
        foods_list.add(new FoodModel("Pizza", "Margarette Pizza", "pop_3", "200", R.drawable.cardview_style_white));
    }

    private void getCartArrayListFromFirebase() {
        rootNode = FirebaseDatabase.getInstance();
        cartRef = rootNode.getReference().child("cart");
        if (user != null) {
            cartRef.child(user.getUid()).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (snapshot.exists()) {
                        ArrayList<Cart_food_Model> cartFoodListFromDB = (ArrayList<Cart_food_Model>) snapshot.getValue();
                        Log.d(TAG, "onDataChange list: " + cartFoodListFromDB.toString());
                    }

                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });

        }


    }

    private void setting_up_list_1() {

        FoodModel pizza_category = new FoodModel(getActivity().getDrawable(R.drawable.cat_1), " Pizza ", R.drawable.cardview_style_2);
        foodCategories_list.add(pizza_category);
        foodCategories_list.add(new FoodModel(getActivity().getDrawable(R.drawable.cat_2), "Burger", R.drawable.cardview_style_3));
        foodCategories_list.add(new FoodModel(getActivity().getDrawable(R.drawable.cat_3), "HotDog", R.drawable.cardview_style_4));
        foodCategories_list.add(new FoodModel(getActivity().getDrawable(R.drawable.cat_4), "  Drink  ", R.drawable.cardview_style_5));
        foodCategories_list.add(new FoodModel(getActivity().getDrawable(R.drawable.cat_5), "Donuts", R.drawable.cardview_style));

    }

    private void setting_up_rv_1() {
        adapter = new Food_list_adapter(foodCategories_list, getContext());
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext().getApplicationContext(), RecyclerView.HORIZONTAL, false);
        categories_rv.setLayoutManager(linearLayoutManager);
        categories_rv.setAdapter(adapter);


    }


    private ArrayList<FoodModel> foodModelListByName(String foodName, ArrayList<FoodModel> foodModelArrayList1) {
        ArrayList<FoodModel> arrayList = new ArrayList<>();
        for (FoodModel foodModel : foodModelArrayList1) {
            if (foodModel.getFoodSpecialName().trim().toLowerCase().contains(foodName.trim().toLowerCase())) {
                arrayList.add(foodModel);
            }
        }
        return arrayList;
    }

}