package com.android.collect.library.base;

import android.os.Bundle;

public interface BasePresenter {
    void subscribe(Bundle bundle);

    void unSubscribe();
}
