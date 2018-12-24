package com.xadapter.sample

/**
 * by y.
 *
 *
 * Description:
 */
interface MainView {

    val page: Int

    fun onChangeRootLayoutStatus(status: String)

    fun onRemoveAll()

    fun onNetComplete(type: Int)

    fun onNetError(type: Int)

    fun onLoadNoMore()

    fun onNetSuccess(entity: List<Entity>)

    fun onPagePlus()

}
