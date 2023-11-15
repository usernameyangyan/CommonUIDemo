package com.common.data.config
import android.app.Application
import android.content.Context

/**
Create by yangyan
Create time:2023/9/5 17:09
Describe:
 */
class LocalDataConfig private constructor(private val context: Application) {
    companion object {
        var instance: LocalDataConfig? = null
        private fun createInstance(context: Application): LocalDataConfig {
            if (instance == null) {
                instance = LocalDataConfig(context)
            }
            return instance!!
        }

        fun init(context: Application): LocalDataConfig {
            return createInstance(context)
        }


    }

    /*****数据库设置**/
    private var dbName="collection_kotlin_library.db"
    private var dbVersion=0

    private var sharePreferenceName: String = "collection_kotlin_library_user_config"

    fun getContext(): Context {
        return context
    }
    fun setSqliteDBName(dbName:String): LocalDataConfig {
        this.dbName=dbName
        return this
    }
    fun getSqliteDBName():String {
        return dbName
    }
    fun setSqliteDBVersion(dbVersion:Int): LocalDataConfig {
        this.dbVersion=dbVersion
        return this
    }
    fun getSqliteDBVersion():Int {
        return dbVersion
    }

    fun setSharePreferenceName(sharePreferenceName:String): LocalDataConfig {
        this.sharePreferenceName=sharePreferenceName
        return this
    }
    fun getSharePreferenceName():String {
        return sharePreferenceName
    }


}