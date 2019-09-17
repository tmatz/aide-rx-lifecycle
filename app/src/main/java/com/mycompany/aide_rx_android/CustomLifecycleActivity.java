package com.mycompany.aide_rx_android;

import android.app.Activity;
import android.os.Bundle;
import com.trello.rxlifecycle2.LifecycleProvider;
import com.trello.rxlifecycle2.LifecycleTransformer;
import com.trello.rxlifecycle2.RxLifecycle;
import com.trello.rxlifecycle2.android.ActivityEvent;
import com.trello.rxlifecycle2.android.RxLifecycleAndroid;
import io.reactivex.Observable;
import io.reactivex.subjects.BehaviorSubject;

public class CustomLifecycleActivity extends Activity
implements LifecycleProvider<ActivityEvent>
{
    private final BehaviorSubject<ActivityEvent> mLifecycle = BehaviorSubject.create();

    @Override
    public final Observable<ActivityEvent> lifecycle()
    {
        return mLifecycle.hide();
    }

    @Override
    public final <T> LifecycleTransformer<T> bindUntilEvent(ActivityEvent event)
    {
        return RxLifecycle.bindUntilEvent(lifecycle(), event);
    }

    @Override
    public final <T> LifecycleTransformer<T> bindToLifecycle()
    {
        return RxLifecycleAndroid.bindActivity(lifecycle());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        mLifecycle.onNext(ActivityEvent.CREATE);
    }

    @Override
    protected void onStart()
    {
        super.onStart();
        mLifecycle.onNext(ActivityEvent.START);
    }

    @Override
    protected void onResume()
    {
        super.onResume();
        mLifecycle.onNext(ActivityEvent.PAUSE);
    }

    @Override
    protected void onPause()
    {
        super.onPause();
        mLifecycle.onNext(ActivityEvent.PAUSE);
    }

    @Override
    protected void onStop()
    {
        super.onStop();
        mLifecycle.onNext(ActivityEvent.STOP);
    }

    @Override
    protected void onDestroy()
    {
        super.onDestroy();
        mLifecycle.onNext(ActivityEvent.DESTROY);
    }
}
