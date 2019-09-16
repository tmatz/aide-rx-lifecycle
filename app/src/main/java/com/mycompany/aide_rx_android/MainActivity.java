package com.mycompany.aide_rx_android;

import android.app.Activity;
import android.os.Bundle;
import com.trello.rxlifecycle2.LifecycleProvider;
import com.trello.rxlifecycle2.LifecycleTransformer;
import com.trello.rxlifecycle2.RxLifecycle;
import com.trello.rxlifecycle2.android.ActivityEvent;
import io.reactivex.Observable;
import io.reactivex.subjects.BehaviorSubject;

public class MainActivity extends Activity
implements LifecycleProvider<MainActivity>
{
    private final BehaviorSubject<ActivityEvent> mLifecycle = BehaviorSubject.create();

    @Override
    public Observable lifecycle()
    {
        return mLifecycle;
    }

    @Override
    public final <T extends Object> LifecycleTransformer<T> bindUntilEvent(MainActivity activity)
    {
        return RxLifecycle.bindUntilEvent(mLifecycle, ActivityEvent.DESTROY);
    }

    @Override
    public final <T extends Object> LifecycleTransformer<T> bindToLifecycle()
    {
        return RxLifecycle.bind(mLifecycle);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        mLifecycle.onNext(ActivityEvent.CREATE);
        setContentView(R.layout.main);
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
