package com.parse.starter;

import android.app.Application;

import com.parse.Parse;
import com.parse.ParseACL;
import com.parse.ParseCrashReporting;
import com.parse.ParseUser;

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
    ParseACL defaultACL = new ParseACL();  //像 Twitter 這樣的應用程式，使用者內容一般為對外公開
    defaultACL.setPublicReadAccess(true);
    ParseACL.setDefaultACL(defaultACL, true);
    // Optionally enable public read access.
    // defaultACL.setPublicReadAccess(true);

    
  }
}
