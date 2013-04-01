package br.org.eldorado.pagemarker;

import android.app.Application;
import android.content.Context;

/**
 * Application class create to statically hold the app context.
 * See {@link PageMarkerApplication#getContext()}.
 * @author auro
 *
 */
public class PageMarkerApplication extends Application {

        private static Context context;
        
        @Override
        public void onCreate() {
                super.onCreate();
                context = this.getApplicationContext();
        }
        
        public static Context getContext() {
                return context;
        }
        
}
