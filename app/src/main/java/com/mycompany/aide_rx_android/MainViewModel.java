package com.mycompany.aide_rx_android;

import android.util.Log;
import io.reactivex.Observable;
import io.reactivex.functions.Function;
import java.time.LocalDateTime;
import java.util.concurrent.TimeUnit;

public class MainViewModel
{
    private static final String TAG = MainViewModel.class.getName();

    public final Observable<String> dateTime;

    public MainViewModel()
    {
        dateTime = Observable.interval(1, TimeUnit.SECONDS)
            .map(toLocalDateTime());
    }

    private Function<Object, String> toLocalDateTime()
    {
        return new Function<Object, String>()
        {
            @Override
            public String apply(Object p) throws Exception
            {
                String now = LocalDateTime.now().toString();
                Log.d(TAG, "now " + now);
                return now;
            }
        };
    }
}
