package com.arkapps.onlinetestapp.auth;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.arkapps.onlinetestapp.R;
import com.arkapps.onlinetestapp.databinding.FragmentOTPBinding;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.play.core.tasks.OnCompleteListener;
import com.google.firebase.FirebaseException;
import com.google.firebase.FirebaseTooManyRequestsException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;

import org.jetbrains.annotations.NotNull;

import java.util.concurrent.TimeUnit;


public class OTPFragment extends Fragment {

    private FragmentOTPBinding binding;
    private String number;
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private String mVerificationId;
    private PhoneAuthProvider.ForceResendingToken resendingToken;

    public OTPFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentOTPBinding.inflate(inflater, container, false);
        number = OTPFragmentArgs.fromBundle(getArguments()).getPhoneNumber();
        binding.mobileNumber.setText(number);

        String mNumber = "+91" + number;
        PhoneAuthOptions options =
                PhoneAuthOptions.newBuilder(mAuth)
                        .setPhoneNumber(mNumber)       // Phone number to verify
                        .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
                        .setActivity(getActivity())                 // Activity (for callback binding)
                        .setCallbacks(mCallbacks)          // OnVerificationStateChangedCallbacks
                        .build();
        PhoneAuthProvider.verifyPhoneNumber(options);


        binding.submit.setOnClickListener(v -> {
            String otp = binding.opt.getText().toString();
            if (otp.isEmpty()) {
                Toast.makeText(getContext(), "Please enter a valid OTP", Toast.LENGTH_SHORT).show();
                return;
            }
            PhoneAuthCredential credential = PhoneAuthProvider.getCredential(mVerificationId, otp);
            signInWithPhoneAuthCredential(credential);


        });


        return binding.getRoot();


    }


    PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

        @Override
        public void onVerificationCompleted(PhoneAuthCredential credential) {
            // This callback will be invoked in two situations:
            // 1 - Instant verification. In some cases the phone number can be instantly
            //     verified without needing to send or enter a verification code.
            // 2 - Auto-retrieval. On some devices Google Play services can automatically
            //     detect the incoming verification SMS and perform verification without
            //     user action.
            Log.d("TAG", "onVerificationCompleted:" + credential);
            Toast.makeText(getContext(), "Auto Verification Success", Toast.LENGTH_SHORT).show();

            signInWithPhoneAuthCredential(credential);
        }

        @Override
        public void onVerificationFailed(FirebaseException e) {
            // This callback is invoked in an invalid request for verification is made,
            // for instance if the the phone number format is not valid.
            Log.w("TAG", "onVerificationFailed", e);
            Toast.makeText(getContext(), "Verification Failed", Toast.LENGTH_SHORT).show();

            if (e instanceof FirebaseAuthInvalidCredentialsException) {
                // Invalid request
            } else if (e instanceof FirebaseTooManyRequestsException) {
                // The SMS quota for the project has been exceeded
            }

            // Show a message and update the UI
        }

        @Override
        public void onCodeSent(@NonNull String verificationId,
                               @NonNull PhoneAuthProvider.ForceResendingToken token) {
            // The SMS verification code has been sent to the provided phone number, we
            // now need to ask the user to enter the code and then construct a credential
            // by combining the code with a verification ID.
            Log.d("TAG", "onCodeSent:" + verificationId);
            Toast.makeText(getContext(), "OTP sent", Toast.LENGTH_SHORT).show();
            // Save verification ID and resending token so we can use them later
            mVerificationId = verificationId;

            resendingToken = token;
        }
    };

    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential)
                .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        Log.d("TAG", "signInWithCredential:success");
                        Toast.makeText(getContext(), "Auth Success", Toast.LENGTH_SHORT).show();
                        // update Ui

                        Navigation.findNavController(getView()).popBackStack(R.id.nav_home,true);

                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull @NotNull Exception e) {
                Toast.makeText(getContext(), "Auth Failed", Toast.LENGTH_SHORT).show();
                Log.w("TAG", "signInWithCredential:failure", e);

            }
        });
    }

}