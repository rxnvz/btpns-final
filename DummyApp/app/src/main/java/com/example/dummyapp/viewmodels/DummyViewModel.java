package com.example.dummyapp.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.dummyapp.model.APIResponse;
import com.example.dummyapp.model.Login;
import com.example.dummyapp.repository.DummyRepository;

public class DummyViewModel extends ViewModel {
    private DummyRepository dmRepo;
    private LiveData<APIResponse> liveData;

    public void init() {
        if (liveData!=null) {
            return;
        }
        dmRepo =dmRepo.getInstance();
    }

    public LiveData<APIResponse> masuk(Login login) {
        if (liveData == null) {
            dmRepo.getInstance();
        }
        liveData = dmRepo.login(login);
        return liveData;
    }

    public LiveData<APIResponse> lihatSaldo(String s){
        if (liveData == null) {
            dmRepo.getInstance();
        }
        liveData = dmRepo.getSaldo(s);
        return liveData;
    }

    public LiveData<APIResponse> keluar(String s) {
        if (liveData == null) {
            dmRepo.getInstance();
        }
        liveData = dmRepo.logout(s);
        return liveData;
    }
}
