package com.example.cictfeeds.utils;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.view.Gravity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.annotation.ColorRes;
import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;

import com.example.cictfeeds.MainActivity;
import com.example.cictfeeds.R;
import com.example.cictfeeds.views.SignupActivity;
import com.google.android.material.snackbar.Snackbar;

public class Helper {

    public static void showSnackbar(
            @NonNull View view,
            @NonNull String message,
            @ColorRes int backgroundColor,
            @DrawableRes int iconResId
    ) {
        if (view.getContext() == null) return;

        Context context = view.getContext();
        Snackbar snackbar = Snackbar.make(view, "", Snackbar.LENGTH_LONG);

        View snackbarView = snackbar.getView();

        GradientDrawable background = new GradientDrawable();
        background.setColor(ContextCompat.getColor(context, backgroundColor));
        background.setCornerRadius(24f);
        snackbarView.setBackground(background);

        TextView textView = snackbarView.findViewById(com.google.android.material.R.id.snackbar_text);
        textView.setTextColor(ContextCompat.getColor(context, android.R.color.white));
        textView.setTextSize(16);
        textView.setMaxLines(5);
        textView.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);

        Drawable icon = ContextCompat.getDrawable(context, iconResId);
        if (icon != null) {
            icon.setBounds(0, 0, icon.getIntrinsicWidth(), icon.getIntrinsicHeight());
            textView.setCompoundDrawablesRelative(icon, null, null, null);
            textView.setCompoundDrawablePadding(16);
        }

        textView.setText(message);

        FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) snackbarView.getLayoutParams();
        params.gravity = Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL;
        params.setMargins(32, 32, 32, 170);
        snackbarView.setLayoutParams(params);

        snackbar.show();
    }


    public static void showErrorSnackbar(@NonNull View view, @NonNull String message) {
        showSnackbar(view, message, R.color.red, R.drawable.icon_warning);
    }

    public static void showSucccessSnackbar(@NonNull View view, @NonNull String message) {
        showSnackbar(view, message, R.color.green, R.drawable.icon_success);
    }

    public static void goToMainScreen(Context context){
        Intent intent = new Intent(context, MainActivity.class);
        context.startActivity(intent);
    }

    public static void goToSignUpScreen(Context context){
        Intent intent = new Intent(context, SignupActivity.class);
        context.startActivity(intent);
    }
}
