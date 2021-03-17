package com.example.spillthetea;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;

public class TeaViewModel extends ViewModel {
    private ApiRepository mRepository;

    public TeaViewModel()
    {
        mRepository = new ApiRepository();
    }

    public LiveData<ArrayList<TeaItem>> getTea()
    {
        return mRepository.getTea();
    }
}
