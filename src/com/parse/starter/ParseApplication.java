package com.parse.starter;

import android.app.Application;

import com.parse.Parse;
import com.parse.ParseACL;
import com.parse.ParseAnalytics;
import com.parse.ParseCrashReporting;
import com.parse.ParseException;
import com.parse.ParseInstallation;
import com.parse.ParseObject;
import com.parse.ParseUser;
import com.parse.PushService;
import com.parse.SignUpCallback;

public class ParseApplication extends Application {

  @Override
  public void onCreate() {
    super.onCreate();

    // Initialize Crash Reporting.
    ParseCrashReporting.enable(this);

    // Enable Local Datastore.
    Parse.enableLocalDatastore(this);

    // Add your initialization code here
    Parse.initialize(this, "krmA9wmTZU7nqIRQlaJvx5LwS3GvIl67W8PtrKLe", "zbmgcvXYKadgvNnhCo9Aegp4fVLUEPWMs52YMBN1");

    ParseUser.enableAutomaticUser();
    ParseACL defaultACL = new ParseACL();
    // Optionally enable public read access.
    // defaultACL.setPublicReadAccess(true);

    
  }
}
