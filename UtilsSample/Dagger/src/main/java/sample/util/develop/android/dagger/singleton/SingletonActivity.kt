package sample.util.develop.android.dagger.singleton

import android.os.Bundle
import kotlinx.android.synthetic.main.dagger_activity_singleton.*
import sample.util.develop.android.dagger.BaseActivity
import sample.util.develop.android.dagger.R
import javax.inject.Inject


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


class SingletonActivity : BaseActivity() {

    @Inject
    lateinit var singletonTest2: SingletonTest
    @Inject
    lateinit var singletonTest1: SingletonTest

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.dagger_activity_singleton)
        val build = DaggerSingletonComponent
            .builder()
            .build()
        build.register(this)
        test1.text = singletonTest1.toString()
        test2.text = singletonTest2.toString()
    }
}
