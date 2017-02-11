package com.zappos.ilovezappos;

import android.content.Intent;

import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.support.v7.widget.CardView;
import android.view.View;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.SearchView;

import com.zappos.ilovezappos.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Window window = this.getWindow();
        final ActivityMainBinding activityMainBinding = DataBindingUtil.setContentView(this,R.layout.activity_main);

        window.setStatusBarColor(this.getResources().getColor(R.color.titleBar));

        TranslateAnimation translateAnimation = new TranslateAnimation(RelativeLayout.CENTER_HORIZONTAL,RelativeLayout.CENTER_HORIZONTAL,RelativeLayout.CENTER_VERTICAL,RelativeLayout.CENTER_VERTICAL-200);
        translateAnimation.setStartOffset(500);
        translateAnimation.setDuration(500);
        translateAnimation.setFillAfter(true);
        activityMainBinding.imageView.startAnimation(translateAnimation);
        translateAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                activityMainBinding.cardView.setVisibility(View.VISIBLE);

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });


        final Intent intent= new Intent(MainActivity.this,ProductActivity.class);




        activityMainBinding.searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                intent.putExtra("Query",query);
                startActivity(intent);
                //MainActivity.this.finish();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
    }
}
