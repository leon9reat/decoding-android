package com.medialink.simplenotif;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {NotifModule.class})
public interface NotifComponent {
    void inject(MainActivity activity);
}
