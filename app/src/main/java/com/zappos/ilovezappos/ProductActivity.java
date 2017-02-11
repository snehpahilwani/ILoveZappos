package com.zappos.ilovezappos;


import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Paint;
import android.os.AsyncTask;
import android.support.design.widget.Snackbar;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.OvershootInterpolator;
import android.widget.ImageView;
import android.widget.SearchView;
import com.zappos.ilovezappos.databinding.ActivityProductNewBinding;
import java.io.InputStream;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ProductActivity extends AppCompatActivity {
    int flag = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final ActivityProductNewBinding productActivityBinding = DataBindingUtil.setContentView(this, R.layout.activity_product_new);

        Window window = this.getWindow();
        Animation animation = AnimationUtils.loadAnimation(this, R.anim.card_animate);
        productActivityBinding.cardView2.startAnimation(animation);
        window.setStatusBarColor(this.getResources().getColor(R.color.titleBar));
        Intent intent = getIntent();
        CharSequence query = intent.getCharSequenceExtra("Query");
        GetAndSetProduct(query.toString(),productActivityBinding);
        productActivityBinding.searchView2.setIconified(false);
        productActivityBinding.searchView2.setQuery(query,true);

        productActivityBinding.searchView2.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                productActivityBinding.TextViewOriginalPrice.setVisibility(View.GONE);
                GetAndSetProduct(query, productActivityBinding);
                return true;
            }
            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        productActivityBinding.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(flag==1) {
                    productActivityBinding.fab.setImageResource(R.drawable.loaded_cart);
                    Snackbar.make(v, "Item Added to Cart", Snackbar.LENGTH_SHORT)
                            .setAction("Action", null).show();
                    final OvershootInterpolator interpolator = new OvershootInterpolator();
                    ViewCompat.animate(productActivityBinding.fab).rotation(360f).withLayer().setDuration(800).setInterpolator(interpolator).start();
                    flag=2;
                }
                else if(flag==2){
                    Snackbar.make(v, "Item Already added to Cart", Snackbar.LENGTH_SHORT)
                            .setAction("Action", null).show();
                }
                else{
                    Snackbar.make(v, "No Item To Add to Cart", Snackbar.LENGTH_SHORT)
                            .setAction("Action", null).show();
                }
            }
        });

    }
    public class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        private ImageView imageView;
        private Bitmap image;

        public DownloadImageTask(ImageView imageView) {
            this.imageView = imageView;
        }

        protected Bitmap doInBackground(String... urls) {
            String urldisplay = urls[0];
            try {
                InputStream in = new java.net.URL(urldisplay).openStream();
                image = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return image;
        }


        protected void onPostExecute(Bitmap result) {
            if (result != null) {
                imageView.setImageBitmap(result);
            }
        }
    }

    public void GetAndSetProduct(String query,  final ActivityProductNewBinding productActivityBinding){

        final String API_KEY ="b743e26728e16b81da139182bb2094357c31d331";
        productActivityBinding.TextViewProduct.setTextSize(15.0f);

        final Animation animation = AnimationUtils.loadAnimation(this, R.anim.card_animate);
        productActivityBinding.cardView2.startAnimation(animation);

        //productActivityBinding.fab2.


        GetProductAPI.Factory.getInstance().getProductDetails(query, API_KEY).enqueue(new Callback<Product>() {
            @Override
            public void onResponse(Call<Product> call, Response<Product> response) {
                Product body = response.body();
                if(Integer.parseInt(body.getCurrentResultCount())>0)
                {
                    final Result result = body.getResults().get(0);
                    productActivityBinding.setResult(result);
                    productActivityBinding.fab2.setVisibility(View.VISIBLE);
                    productActivityBinding.fab2.startAnimation(animation);
                    productActivityBinding.fab2.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            Intent sendIntent = new Intent();
                            sendIntent.setAction(Intent.ACTION_SEND);
                            sendIntent.putExtra(Intent.EXTRA_TEXT, result.getProductUrl());
                            sendIntent.setType("text/plain");
                            startActivity(Intent.createChooser(sendIntent, getResources().getText(R.string.send)));
                        }
                    });
                    if(!result.getPercentOff().equals("0%")) {

                        productActivityBinding.TextViewOriginalPrice.setVisibility(View.VISIBLE);
                        productActivityBinding.TextViewOriginalPrice.setPaintFlags(productActivityBinding.TextViewOriginalPrice.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);

                    }
                    String replaced_text = result.getThumbnailImageUrl().replace("t-THUMBNAIL","p-MULTIVIEW");
                    new DownloadImageTask(productActivityBinding.imageView1).execute(replaced_text);

                    flag = 1;
                }

                else{
                    productActivityBinding.TextViewProduct.setTextSize(25.0f);
                    productActivityBinding.TextViewProduct.setText("No Results Found");
                    productActivityBinding.fab2.setVisibility(View.GONE);
                    productActivityBinding.TextViewBrand.setText(null);
                    productActivityBinding.TextViewPrice.setText(null);
                    productActivityBinding.TextViewOriginalPrice.setText(null);
                    productActivityBinding.imageView1.setImageDrawable(null);
                    flag = 0;
                }
            }
            @Override
            public void onFailure(Call<Product> call, Throwable throwable) {
                productActivityBinding.TextViewProduct.setText("Failed");
            }
        });
    }

}
