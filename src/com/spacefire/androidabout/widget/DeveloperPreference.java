package com.spacefire.androidabout.widget;

import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.net.http.AndroidHttpClient;
import android.os.AsyncTask;
import android.preference.Preference;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.koushikdutta.urlimageviewhelper.UrlImageViewCallback;
import com.koushikdutta.urlimageviewhelper.UrlImageViewHelper;
import com.spacefire.androidabout.R;

import java.io.InputStream;
import java.net.HttpURLConnection;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.params.HttpClientParams;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpParams;

public class DeveloperPreference extends Preference {

    private static final String TAG = "DeveloperPreference";

    private ImageView githubButton;
    private ImageView photoView;

    private TextView devName;

    private String nameDev;
    private String githubImage;
    private String githubName;

    public DeveloperPreference(Context context, AttributeSet attrs) {
        super(context, attrs);

        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.DeveloperPreference);
        nameDev = a.getString(R.styleable.DeveloperPreference_nameDev);
        githubName = a.getString(R.styleable.DeveloperPreference_githubHandle);
        githubImage = a.getString(R.styleable.DeveloperPreference_githubImage);
        a.recycle();
    }

    @Override
    protected View onCreateView(ViewGroup parent) {
        super.onCreateView(parent);

        View layout = View.inflate(getContext(), R.layout.dev_card, null);

        githubButton = (ImageView) layout.findViewById(R.id.github_button);
        devName = (TextView) layout.findViewById(R.id.name);
        photoView = (ImageView) layout.findViewById(R.id.photo);

        return layout;
    }

    @Override
    protected void onBindView(View view) {
        super.onBindView(view);
   
        if (githubName != null) {
            final OnPreferenceClickListener openTwitter = new OnPreferenceClickListener() {
                @Override
                public boolean onPreferenceClick(Preference preference) {
                    Uri twitterURL = Uri.parse("http://github.com/" + githubName);
                    final Intent intent = new Intent(Intent.ACTION_VIEW, twitterURL);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    getContext().startActivity(intent);
                    return true;
                }
            };

            // changed to clicking the preference to open twitter
            // it was a hit or miss to click the twitter bird
            // twitterButton.setOnClickListener(openTwitter);
            this.setOnPreferenceClickListener(openTwitter);
        } else {
            githubButton.setVisibility(View.INVISIBLE);
        }

        final String url = "https://avatars1.githubusercontent.com/u/" + githubImage + "?v=2&s=400";                    ;
        UrlImageViewHelper.setUrlDrawable(this.photoView, url, R.drawable.ic_null,
                UrlImageViewHelper.CACHE_DURATION_ONE_WEEK);        
        devName.setText(nameDev);

    }
}