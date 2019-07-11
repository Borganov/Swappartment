package ch.hevs.swap;

import android.app.Application;

import ch.hevs.swap.data.AppDatabase;
import ch.hevs.swap.data.model.Locality;
import ch.hevs.swap.data.repository.LocalityRepository;


/**
 * Android Application class. Used for accessing singletons.
 */
public class BaseApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
    }

    public AppDatabase getDatabase() {
        return AppDatabase.getInstance(this);
    }


    public LocalityRepository getLocalityRepository() {
        return LocalityRepository.getInstance(getDatabase());
    }

}