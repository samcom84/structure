package com.retrofitjavalibrary.app;
import com.example.commonstructure.MyLibraryModule;
import com.retrofitjavalibrary.model.CallsInfo;
import io.realm.annotations.RealmModule;

@RealmModule(library = true, allClasses = true)
public class AppModule extends MyLibraryModule {

}
