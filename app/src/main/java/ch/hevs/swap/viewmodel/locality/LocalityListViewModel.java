package ch.hevs.swap.viewmodel.locality;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;
import android.util.Log;

import java.util.List;

import ch.hevs.swap.BaseApp;
import ch.hevs.swap.data.entity.LocalityEntity;
import ch.hevs.swap.data.model.Locality;
import ch.hevs.swap.data.repository.LocalityRepository;

public class LocalityListViewModel extends AndroidViewModel {

    private static final String TAG = "LocalityListViewModel";

    private LocalityRepository mRepository;

    // MediatorLiveData can observe other LiveData objects and react on their emissions.
    private final MediatorLiveData<List<LocalityEntity>> mObservableClientAccounts;
   // private final MediatorLiveData<List<PeopleEntity>> mObservableOwnAccounts;

    public LocalityListViewModel(@NonNull Application application, LocalityRepository localityRepository) {
        super(application);

        mRepository = localityRepository;

        mObservableClientAccounts = new MediatorLiveData<>();

        // set by default null, until we get data from the database.
        mObservableClientAccounts.setValue(null);


     //  LiveData<List<ClientWithAccounts>> clientAccounts = clientRepository.getOtherClientsWithAccounts(ownerId);
            //LiveData<PeopleEntity> people = mRepository.getClient(ownerId);
          LiveData<List<LocalityEntity>> localities = mRepository.getLocalities();

        // observe the changes of the entities from the database and forward them
        mObservableClientAccounts.addSource(localities, mObservableClientAccounts::setValue);
       //  mObservableOwnAccounts.addSource(ownAccounts, mObservableOwnAccounts::setValue);
    }

    /**
     * A creator is used to inject the account id into the ViewModel
     */
    public static class Factory extends ViewModelProvider.NewInstanceFactory {

        @NonNull
        private final Application mApplication;


        private final LocalityRepository mClientRepository;


        public Factory(@NonNull Application application) {
            mApplication = application;
            mClientRepository = ((BaseApp) application).getLocalityRepository();
        }

        @Override
        public <T extends ViewModel> T create(Class<T> modelClass) {
            //noinspection unchecked
            return (T) new LocalityListViewModel(mApplication,  mClientRepository);
        }
    }

    /**
     * Expose the LiveData ClientAccounts query so the UI can observe it.
     */
    public LiveData<List<LocalityEntity>> getLocalities() {
        return mObservableClientAccounts;
    }

    /**
     * Expose the LiveData AccountEntities query so the UI can observe it.

    public LiveData<List<AccountEntity>> getOwnAccounts() {
        return mObservableOwnAccounts;
    }
     */
   /* public void deleteAccount(PeopleEntity account) {
        new DeletePerson(getApplication(), new OnAsyncEventListener() {
            @Override
            public void onSuccess() {
                Log.d(TAG, "deleteAccount: success");
            }

            @Override
            public void onFailure(Exception e) {
                Log.d(TAG, "deleteAccount: failure", e);
            }
        }).execute(account);
    }*/
}
