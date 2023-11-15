package com.common.data.database

import com.common.data.SharePreference
import com.common.data.config.LocalDataConfig
import com.common.exception.OtherException

/**
 * Created by yangy
 *2020-02-24
 *Describe:
 */

class SQLiteVersionMigrate {
    interface MigrateListener {
        fun onMigrate(oldVersion: Int, newVersion: Int)
    }

    fun setMigrateListener(migrate: MigrateListener) {
        if (LocalDataConfig.instance == null) {
            throw OtherException("LocalDataConfig is not init!")
        }
        LocalDataConfig
        val tableVersion = SharePreference.instance.getInt(
            SqlHelper.PREFS_TABLE_VERSION_KEY,
            0
        )

        if (LocalDataConfig.instance!!.getSqliteDBVersion() > tableVersion) {
            migrate.onMigrate(tableVersion, LocalDataConfig.instance!!.getSqliteDBVersion())
        }
        SharePreference.instance.putInt(SqlHelper.PREFS_TABLE_VERSION_KEY, LocalDataConfig.instance!!.getSqliteDBVersion())
    }
}