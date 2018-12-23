package fragment.develop.android.tab

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentTransaction
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.tab_activity_main.*

@Suppress("UNCHECKED_CAST")
fun <T : Fragment> AppCompatActivity.findFragmentByTag(tag: String, ifNone: (String) -> T): T =
    supportFragmentManager.findFragmentByTag(tag) as T? ?: ifNone(tag)

class TabMainActivity : AppCompatActivity() {

    private lateinit var fragmentOne: Fragment
    private lateinit var fragmentTwo: Fragment
    private lateinit var fragmentThree: Fragment
    private lateinit var fragmentFour: Fragment

    companion object {
        private const val FRAGMENT_ONE = "one"
        private const val FRAGMENT_TWO = "two"
        private const val FRAGMENT_THREE = "three"
        private const val FRAGMENT_FOUR = "four"
    }

    private fun hideFragment(): FragmentTransaction {
        val beginTransaction = supportFragmentManager.beginTransaction()
        if (::fragmentOne.isInitialized) {
            beginTransaction.hide(fragmentOne)
        }
        if (::fragmentTwo.isInitialized) {
            beginTransaction.hide(fragmentTwo)
        }
        if (::fragmentThree.isInitialized) {
            beginTransaction.hide(fragmentThree)
        }
        if (::fragmentFour.isInitialized) {
            beginTransaction.hide(fragmentFour)
        }
        return beginTransaction
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.tab_activity_main)
        setTabSelect(0)
        rgGroup.setOnCheckedChangeListener { _, i ->
            when (i) {
                R.id.rb_one -> setTabSelect(0)
                R.id.rb_two -> setTabSelect(1)
                R.id.rb_three -> setTabSelect(2)
                R.id.rb_four -> setTabSelect(3)
            }
        }
    }

    private fun setTabSelect(i: Int) {
        val beginTransaction = hideFragment()
        when (i) {
            0 -> {
                fragmentOne = findFragmentByTag(FRAGMENT_ONE) { FragmentOne.startFragment() }
                if (fragmentOne.isAdded) {
                    beginTransaction.show(fragmentOne)
                } else {
                    beginTransaction.add(R.id.fragment, fragmentOne, FRAGMENT_ONE)
                }
            }
            1 -> {
                fragmentTwo = findFragmentByTag(FRAGMENT_TWO) { FragmentTwo.startFragment() }
                if (fragmentTwo.isAdded) {
                    beginTransaction.show(fragmentTwo)
                } else {
                    beginTransaction.add(R.id.fragment, fragmentTwo, FRAGMENT_TWO)
                }
            }
            2 -> {
                fragmentThree = findFragmentByTag(FRAGMENT_THREE) { FragmentThree.startFragment() }
                if (fragmentThree.isAdded) {
                    beginTransaction.show(fragmentThree)
                } else {
                    beginTransaction.add(R.id.fragment, fragmentThree, FRAGMENT_THREE)
                }
            }
            3 -> {
                fragmentFour = findFragmentByTag(FRAGMENT_FOUR) { FragmentFour.startFragment() }
                if (fragmentFour.isAdded) {
                    beginTransaction.show(fragmentFour)
                } else {
                    beginTransaction.add(R.id.fragment, fragmentFour, FRAGMENT_FOUR)
                }
            }
        }
        beginTransaction.commit()
    }
}

