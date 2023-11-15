package com.common.ui.view.dialog

/**
 * Created by yangy
 *2020/12/14
 *Describe:
 */
class DialogWrapper(YYDialog: YYDialog.Builder) {
    private var YYDialog //统一管理dialog的弹出顺序
            : YYDialog.Builder? = YYDialog


    fun getDialog(): YYDialog.Builder? {
        return YYDialog
    }

    fun setDialog(YYDialog: YYDialog.Builder?) {
        this.YYDialog = YYDialog
    }



}