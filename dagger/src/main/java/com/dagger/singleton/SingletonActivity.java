package com.dagger.singleton;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatTextView;

import com.dagger.BaseActivity;
import com.dagger.R;

import javax.inject.Inject;

/**
 * by y on 2017/5/31.
 */

/*
 * 不是单例
 *
public final class MVPPresenterImpl_Factory implements Factory<MVPPresenterImpl> {
         private final Provider<MVPView> mvpViewProvider;

         public MVPPresenterImpl_Factory(Provider<MVPView> mvpViewProvider) {
         assert mvpViewProvider != null;
         this.mvpViewProvider = mvpViewProvider;
         }

         @Override
         public MVPPresenterImpl get() {
         return new MVPPresenterImpl(mvpViewProvider.get());
         }

         public static Factory<MVPPresenterImpl> create(Provider<MVPView> mvpViewProvider) {
         return new MVPPresenterImpl_Factory(mvpViewProvider);
         }
 }
 */

/*
 * 单例

 public final class SingletonTest_Factory implements Factory<SingletonTest> {
         private static final SingletonTest_Factory INSTANCE = new SingletonTest_Factory();

         @Override
         public SingletonTest get() {
         return new SingletonTest();
         }

         public static Factory<SingletonTest> create() {
         return INSTANCE;
         }
 }
 */


public class SingletonActivity extends BaseActivity {


    @Inject
    SingletonTest test1;
    @Inject
    SingletonTest test2;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_singleton);
        AppCompatTextView tv1 = (AppCompatTextView) findViewById(R.id.test1);
        AppCompatTextView tv2 = (AppCompatTextView) findViewById(R.id.test2);
        SingletonComponent build = DaggerSingletonComponent
                .builder()
                .build();
        build.register(this);
        tv1.setText(test1.toString());
        tv2.setText(test2.toString());
    }


}
