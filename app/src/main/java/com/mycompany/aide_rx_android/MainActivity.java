package com.mycompany.aide_rx_android;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;
import com.trello.rxlifecycle2.LifecycleProvider;
import com.trello.rxlifecycle2.LifecycleTransformer;
import com.trello.rxlifecycle2.RxLifecycle;
import com.trello.rxlifecycle2.android.ActivityEvent;
import com.trello.rxlifecycle2.android.RxLifecycleAndroid;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.subjects.BehaviorSubject;

public class MainActivity extends Activity
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
        mLifecycle.onNext(ActivityEvent.CREATE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        final MainViewModel vm = new MainViewModel();
        vm.dateTime
            .compose(bindToLifecycle())
            .cast(String.class)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(bind((TextView)findViewById(R.id.clock)));
    }

    @Override
    protected void onStart()
    {
        mLifecycle.onNext(ActivityEvent.START);
        super.onStart();
    }

    @Override
    protected void onResume()
    {
        mLifecycle.onNext(ActivityEvent.PAUSE);
        super.onResume();
    }

    @Override
    protected void onPause()
    {
        mLifecycle.onNext(ActivityEvent.PAUSE);
        super.onPause();
    }

    @Override
    protected void onStop()
    {
        mLifecycle.onNext(ActivityEvent.STOP);
        super.onStop();
    }

    @Override
    protected void onDestroy()
    {
        mLifecycle.onNext(ActivityEvent.DESTROY);
        super.onDestroy();
    }

    private static Consumer<String> bind(final TextView textView)
    {
        return new Consumer<String>()
        {
            @Override
            public void accept(String v) throws Exception
            {
                textView.setText(v.toString());
            }
        };
    }
}
