package com.spacefire.androidabout;
	
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.preference.Preference;
import android.preference.Preference.OnPreferenceClickListener;
import android.preference.PreferenceActivity;
import android.preference.PreferenceScreen;
import android.view.View;
import android.view.View.OnClickListener;
import android.content.Intent;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.content.Intent;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.Uri;
import android.os.Bundle;

	public class MainSettings extends PreferenceActivity {
		long[] mHits = new long[3];
		
	    @Override
	    public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        addPreferencesFromResource(R.xml.mainpref);
	        Preference AndroidVersion = (Preference) findPreference("and_ver");
	        AndroidVersion.setSummary(" "+Build.VERSION.RELEASE);
	        
	        Preference KernelVersion = (Preference) findPreference("kernel_ver");
	        findPreference("kernel_ver").setSummary(getFormattedKernelVersion());
	        
	        Preference Pro = (Preference) findPreference("pro");
	        Pro.setSummary(" "+android.os.Build.MODEL);
	        
	        Preference ROM = (Preference) findPreference("rom");
	        ROM.setSummary("MOSP");
	        
	        Preference Donithing = (Preference) findPreference("donothing");
	        Donithing.setOnPreferenceClickListener(new OnPreferenceClickListener() {
	                     public boolean onPreferenceClick(Preference preference) {
								return true;
								
	                     }
	                 });
	        
	        Preference Developers = (Preference) findPreference("developers");
	        Developers.setOnPreferenceClickListener(new OnPreferenceClickListener() {
	                     public boolean onPreferenceClick(Preference preference) {
								Intent i = new Intent();
								i.setClassName("com.spacefire.androidabout", "com.spacefire.androidabout.SettingsActivity");
								startActivity(i);
								return true;
	                     }
	                 });	        
	        
}
	    
	    @Override
	    public boolean onPreferenceTreeClick(PreferenceScreen preferenceScreen, Preference preference) {
	        if (preference.getKey().equals("and_ver")) {
	            System.arraycopy(mHits, 1, mHits, 0, mHits.length-1);
	            mHits[mHits.length-1] = SystemClock.uptimeMillis();
	            if (mHits[0] >= (SystemClock.uptimeMillis()-500)) {
	                Intent intent = new Intent(Intent.ACTION_MAIN);
	                intent.setClassName("com.spacefire.androidabout",
	                		com.spacefire.androidabout.PlatLogoActivity.class.getName());
	                try {
	                    startActivity(intent);
	                } catch (Exception e) {
	                }
	            }
	        }
	        else if (preference.getKey().equals("kernel_ver")) {
		            System.arraycopy(mHits, 1, mHits, 0, mHits.length-1);
		            mHits[mHits.length-1] = SystemClock.uptimeMillis();
		            if (mHits[0] >= (SystemClock.uptimeMillis()-500)) {
		            	Toast.makeText(getApplicationContext(), getFormattedKernelVersion(), Toast.LENGTH_LONG).show();
		            }	            
	        }
	        else if (preference.getKey().equals("pro")) {
	            System.arraycopy(mHits, 1, mHits, 0, mHits.length-1);
	            mHits[mHits.length-1] = SystemClock.uptimeMillis();
	            if (mHits[0] >= (SystemClock.uptimeMillis()-500)) {
	            	Toast.makeText(getApplicationContext(), "You own a "+android.os.Build.MANUFACTURER+" - "+android.os.Build.MODEL+" device Running Android "+android.os.Build.VERSION.RELEASE, Toast.LENGTH_LONG).show();
	            }	            
	        } 	        
	        return super.onPreferenceTreeClick(preferenceScreen, preference);
	    }
	    
	    private String getFormattedKernelVersion() {
	        String procVersionStr;

	        try {
	            BufferedReader reader = new BufferedReader(new FileReader("/proc/version"), 256);
	            try {
	                procVersionStr = reader.readLine();
	            } finally {
	                reader.close();
	            }

	            final String PROC_VERSION_REGEX =
	                "\\w+\\s+" + /* ignore: Linux */
	                "\\w+\\s+" + /* ignore: version */
	                "([^\\s]+)\\s+" + /* group 1: 2.6.22-omap1 */
	                "\\(([^\\s@]+(?:@[^\\s.]+)?)[^)]*\\)\\s+" + /* group 2: (xxxxxx@xxxxx.constant) */
	                "\\((?:[^(]*\\([^)]*\\))?[^)]*\\)\\s+" + /* ignore: (gcc ..) */
	                "([^\\s]+)\\s+" + /* group 3: #26 */
	                "(?:PREEMPT\\s+)?" + /* ignore: PREEMPT (optional) */
	                "(.+)"; /* group 4: date */

	            Pattern p = Pattern.compile(PROC_VERSION_REGEX);
	            Matcher m = p.matcher(procVersionStr);

	            if (!m.matches()) {
	                return "Unavailable";
	            } else if (m.groupCount() < 4) {
	                return "Unavailable";
	            } else {
	                return (new StringBuilder(m.group(1)).append("\n").append(
	                        m.group(2)).append(" ").append(m.group(3)).append("\n")
	                        .append(m.group(4))).toString();
	            }
	        } catch (IOException e) {
	            return "Unavailable";
	        }
	    }	    
	}