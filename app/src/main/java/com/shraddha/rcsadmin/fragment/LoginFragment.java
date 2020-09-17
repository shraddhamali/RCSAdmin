package com.shraddha.rcsadmin.fragment;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;


import com.shraddha.rcsadmin.R;
import com.shraddha.rcsadmin.activites.MainActivity;
import com.shraddha.rcsadmin.model.User;
import com.shraddha.rcsadmin.services.MyInterface;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.shraddha.rcsadmin.activites.MainActivity.appPrefrence;


public class LoginFragment extends Fragment {
    MyInterface myInterface;
    Button login_button;
    EditText emailInput_op,passwordInput_op;
    TextView registerTV_op;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_login,container,false);
        emailInput_op=view.findViewById(R.id.emailInput);
        passwordInput_op=view.findViewById(R.id.passwordInput);
        login_button=view.findViewById(R.id.loginBtn);
        login_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loginUser();
            }
        });
        registerTV_op=view.findViewById(R.id.registerTV);
        registerTV_op.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myInterface.register();

            }
        });
        return view;
    }

    private void loginUser() {
        String email=emailInput_op.getText().toString().trim();
        String password=passwordInput_op.getText().toString().trim();
        if (TextUtils.isEmpty(email))
        {
            appPrefrence.showToast("Enter your emailId");
        }else if (TextUtils.isEmpty(password))
        {
            appPrefrence.showToast("Enter your password");
        }else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches())
        {
            appPrefrence.showToast("Email id is invalid");
        }else if (password.length()<6)
        {
            appPrefrence.showToast("Password too short!");
        }else
        {
            Call<User> userCall = MainActivity.serviceApi.doLogin(email,password);
            userCall.enqueue(new Callback<User>() {
                @Override
                public void onResponse(@NonNull Call<User> call,@NonNull Response<User> response) {
                    if(response.body().getResponse().equals("data"))
                    {
                        myInterface.login(response.body().getName(),response.body().getEmail(),response.body().getCreatedAt());
                        System.out.println(" "+response.body().getName()+response.body().getEmail()+response.body().getCreatedAt());
                        Toast.makeText(getActivity(), "Login Successfull!!", Toast.LENGTH_SHORT).show();

                    }
                    else  if(response.body().getResponse().equals("login_failed")){
                        Toast.makeText(getActivity(), "Login Failed!!", Toast.LENGTH_SHORT).show();
                        emailInput_op.setText("");
                        passwordInput_op.setText("");

                    }

                }

                @Override
                public void onFailure(@NonNull Call<User> call,@NonNull Throwable t) {

                }
            });


        }
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        Activity activity= (Activity) context;
        myInterface= (MyInterface) activity;
    }
}