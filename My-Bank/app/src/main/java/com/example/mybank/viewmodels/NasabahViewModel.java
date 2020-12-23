package com.example.mybank.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.mybank.model.APIResponse;
import com.example.mybank.model.Login;
import com.example.mybank.model.Nasabah;
import com.example.mybank.repositories.NasabahRepository;

public class NasabahViewModel extends ViewModel {
    private NasabahRepository nbRepo;
    private LiveData<APIResponse> liveData;

    public void init() {
        if (liveData!=null) {
            return;
        }
        nbRepo = nbRepo.getInstance();
    }

    public LiveData<APIResponse> registrasi(Nasabah regis) {
        if (liveData == null) {
            nbRepo = nbRepo.getInstance();
        }
        liveData = nbRepo.doRegister(regis);
        return liveData;
    }

    public LiveData<APIResponse> masuk(Login login) {
        if (liveData == null) {
            nbRepo.getInstance();
        }
        liveData = nbRepo.login(login);
        return liveData;
    }

    public LiveData<APIResponse> lihatSaldo(String s){
        if (liveData == null) {
            nbRepo.getInstance();
        }
        liveData = nbRepo.getSaldo(s);
        return liveData;
    }
}
