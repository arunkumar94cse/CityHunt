package in.cityhunt.cityhunt;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v4.util.LruCache;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;

public class Mysingleton {
    private static Mysingleton mInstance;
    private RequestQueue mRequestQueue;
    private ImageLoader mImageLoader;
    private static Context mCtx;

    private Mysingleton(final Context context) {
        mCtx = context;
        mRequestQueue = getRequestQueue();

        mImageLoader = new ImageLoader(mRequestQueue, new ImageLoader.ImageCache() {
                    private final LruBitmapCache cache = new LruBitmapCache(context);

                    @Override
                    public Bitmap getBitmap(String url) {
                        return cache.getBitmap(url);
                    }

                    @Override
                    public void putBitmap(String url, Bitmap bitmap) {
                        cache.putBitmap(url, bitmap);
                    }
                });
    }
    public static synchronized Mysingleton getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new Mysingleton(context);
        }
        return mInstance;
    }
    public RequestQueue getRequestQueue() {
        if (mRequestQueue == null) {
            // getApplicationContext() is key, it keeps you from leaking the
            // Activity or BroadcastReceiver if someone passes one in.
            mRequestQueue = Volley.newRequestQueue(mCtx.getApplicationContext());
        }
        return mRequestQueue;
    }
    public <T> void addToRequestQueue(Request<T> req) {
        getRequestQueue().add(req);
    }
    public ImageLoader getImageLoader() {
        return mImageLoader;
    }
}
