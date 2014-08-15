package com.spacefire.androidabout;
	
import java.io.IOException;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.content.Intent;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.content.Intent;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.Uri;
import android.os.Bundle;

	public class MainSettings extends PreferenceActivity {
	    @Override
	    public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        addPreferencesFromResource(R.xml.mainpref);
	        Preference AndroidVersion = (Preference) findPreference("and_ver");
	        AndroidVersion.setSummary(" "+Build.VERSION.RELEASE);
	        
	        Preference Pro = (Preference) findPreference("pro");
	        Pro.setSummary(" "+android.os.Build.MODEL);
	        
	        Preference ROM = (Preference) findPreference("rom");
	        ROM.setSummary("MOSP");
	        
}
	}