package com.mycompany.aide_rx_android;

import android.os.Bundle;
import android.widget.TextView;
import com.trello.rxlifecycle2.components.RxActivity;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;

public class MainActivity extends RxActivity
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        final MainViewModel vm = new MainViewModel();

        vm.dateTime
            .compose(bindToLifecycle())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(bind((TextView)findViewById(R.id.clock)));
    }

    private static Consumer<Object> bind(final TextView textView)
    {
        return new Consumer<Object>()
        {
            @Override
            public void accept(Object value) throws Exception
            {
                textView.setText(value.toString());
            }
        };
    }
}
