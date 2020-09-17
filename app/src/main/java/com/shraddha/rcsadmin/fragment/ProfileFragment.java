package com.shraddha.rcsadmin.fragment;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.shraddha.rcsadmin.R;
import com.shraddha.rcsadmin.activites.MainActivity;
import com.shraddha.rcsadmin.services.MyInterface;


public class ProfileFragment extends Fragment {
    private ImageView imageView;
    MyInterface myInterface_profile;
    TextView name_input,email_input;
    Button logout_button;
    public ProfileFragment()
    {

    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_profile,container,false);
        name_input=view.findViewById(R.id.name);
        email_input=view.findViewById(R.id.email);
        logout_button=view.findViewById(R.id.logoutBtn);




        String Name=""+ MainActivity.appPrefrence.getDisplayName();
        String Email = " "+ MainActivity.appPrefrence.getDisplayEmail();
     //   String Date= " Since " +MainActivity.appPrefrence.getDisplayName();

        System.out.println(""+Name);
        System.out.println(""+Email);
      //  System.out.println(""+Date);
        name_input.setText(Name);
        email_input.setText(""+Email+"");//+Date);

        logout_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myInterface_profile.logout();
            }
        });
        return view;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        Activity activity= (Activity) context;
        myInterface_profile= (MyInterface) activity;
    }
}