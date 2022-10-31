package com.arkapps.onlinetestapp;

import android.annotation.SuppressLint;
import android.app.Application;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

public class MyApplication extends Application {

    @SuppressLint("StaticFieldLeak")
    public static FirebaseFirestore firestore;
    public  static FirebaseAuth firebaseAuth;

    @Override
    public void onCreate() {
        super.onCreate();
firestore = FirebaseFirestore.getInstance();
firebaseAuth = FirebaseAuth.getInstance();
    }

}
