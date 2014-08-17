package com.spacefire.androidabout;
	
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import android.os.Build;
import android.os.Bundle;
import android.os.SystemClock;
import android.os.UserHandle;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.preference.Preference;
import android.preference.Preference.OnPreferenceClickListener;
import android.preference.PreferenceActivity;
import android.preference.PreferenceScreen;
import android.widget.Toast;

	public class MainSettings extends PreferenceActivity {
		private static final String KEY_BASEBAND_VERSION = "baseband_version";
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
	        
	        Preference CPU = (Preference) findPreference("cpu");
	        findPreference("cpu").setSummary(getCPUInfo());
	        
	        
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
	                "(.+)";/* group 4: date */

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
	      private String getCPUInfo() {
	            String[] info = null;
	            BufferedReader reader = null;

	            try {
	               // Grab a reader to /proc/cpuinfo
	               reader = new BufferedReader (new InputStreamReader(new FileInputStream("/proc/cpuinfo")), 1000);

	               // Grab a single line from cpuinfo
	               String line = reader.readLine();

	               // Split on the colon, we need info to the right of colon
	               info = line.split(":");
	            }
	            catch(IOException io) {
	               io.printStackTrace();
	               info = new String[1];
	               info[1] = "error";
	            }
	            finally {
	               // Make sure the reader is closed no matter what
	               try { reader.close(); }
	               catch(Exception e) {}
	               reader = null;
	            }

	            return info[1];
	          }
	    
	}
