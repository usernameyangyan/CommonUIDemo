package com.common.data
import com.common.data.database.PagingList
import com.common.data.database.ResultSet
import com.common.data.database.SQLiteDataBase
import com.common.exception.OtherException

/**
Create by yangyan
Create time:2023/11/15 15:04
Describe:
 */
class LocalDataManager {
    class Sqlite{
        companion object{
            /**
             * 插入数据
             * -1 代表失败
             */
            fun <T> insert(model: T): Boolean {
                return SQLiteDataBase.instance.insert(model)
            }


            /**
             * 批量插入数据
             */
            fun <T> insertList(clazz: Class<T>, dataList: List<T>): Boolean {
                return SQLiteDataBase.instance.batchInsert(clazz,dataList)
            }

            fun <T> insertListBySync(clazz: Class<T>, dataList: List<T>,onInsertDataCompleteListener: SQLiteDataBase.InsertDataCompleteListener) {
                SQLiteDataBase.instance.batchInsertBySync(clazz,dataList,onInsertDataCompleteListener)
            }


            /**
             * 根据条件查询
             * selection:条件语句 ：id=?   id=? or age=?
             */
            fun <T> queryByFirstByWhere(
                clazz: Class<T>,
                selection: String,
                vararg selectionArgs: String
            ): T ?{
                return SQLiteDataBase.instance.queryByFirstByWhere(clazz,selection,
                    *selectionArgs)
            }

            /**
             * 查询表里的全部数据
             */
            fun <T> queryAll(clazz: Class<T>): List<T>? {
                return SQLiteDataBase.instance.queryAll(clazz)
            }

            /**
             * 根据条件查询表里的全部数据
             */
            fun <T> queryAllByWhere(clazz: Class<T>,selection: String,
                                    vararg selectionArgs: String): List<T>? {
                return SQLiteDataBase.instance.queryAllByWhere(clazz,selection,*selectionArgs)
            }

            fun <T> queryAllBySync(clazz: Class<T>,onQueryDataComplete: SQLiteDataBase.QueryDataCompleteListener<T>){
                SQLiteDataBase.instance.queryAllBySync(clazz,onQueryDataComplete)
            }

            /**
             * 查询表里的第一条数据
             */
            fun <T> queryByFirst(clazz: Class<T>): T? {
                return SQLiteDataBase.instance.queryByFirst(clazz)
            }


            /**
             * 根据条件删除
             * selection:条件语句 ：id=?   id=? or age=?
             */

            fun <T> delete(clazz: Class<T>, whereClause: String, vararg whereArgs: String?): Boolean {
                return SQLiteDataBase.instance.delete(clazz,whereClause, *whereArgs)
            }
            /**
             * 删除全部数据
             */
            fun <T> deleteAll(clazz: Class<T>): Boolean {
                return SQLiteDataBase.instance.deleteAll(clazz)
            }

            /**
             * 删除表
             */
            fun <T> deleteTable(clazz: Class<T>) {
                SQLiteDataBase.instance.deleteTable(clazz)
            }



            /**
             * 更新数据
             * -1失败
             */
            fun <T> update(model: T, whereClause: String, vararg whereArgs: String): Boolean? {
                return SQLiteDataBase.instance.update(model,whereClause, *whereArgs)
            }


            /***
             * 分页查询，实体类必须包含PrimaryKey
             */


            fun <T> queryOfPageByWhere(
                clazz: Class<T>,
                selection: String?,
                selectionArgs: Array<String>?,
                page: Int,
                pageSize: Int
            ): PagingList<T>? {

                return SQLiteDataBase.instance.queryOfPageByWhere(clazz,selection,selectionArgs,page,pageSize)

            }

            fun <T> queryOfPage(
                clazz: Class<T>,
                page: Int,
                pageSize: Int
            ): PagingList<T>? {
                return SQLiteDataBase.instance.queryOfPage(clazz,page,pageSize)
            }

            /**
             * 使用SQL语句
             */

            fun execQuerySQL(sql: String): List<ResultSet>? {
                return SQLiteDataBase.instance.execQuerySQL(sql)
            }


            /**
             * 更新表,用于更新表格字段，只可增加字段，需要配合版本号已经SQLiteVersionMigrate使用
             */
            fun <T> updateTable(clazz: Class<T>) {
                SQLiteDataBase.instance.updateTable(clazz)
            }

        }

    }


    class SharePreferences{
        companion object{
            fun <T> saveObject(key:String,con:T){

                when (con) {
                    is Int -> {
                        SharePreference.instance.putInt(key,con)
                    }
                    is String -> {
                        SharePreference.instance.putString(key,con)
                    }
                    is Boolean -> {
                        SharePreference.instance.putBoolean(key,con)
                    }
                    is Long -> {
                        SharePreference.instance.putLong(key,con)
                    }
                    is Float -> {
                        SharePreference.instance.putFloat(key,con)
                    }
                    else ->throw OtherException("暂不支持该类型数据")
                }
            }

            @Suppress("UNCHECKED_CAST")
            fun <T>getObject(key:String,defaultValue:T):T? {
                when (defaultValue) {
                    is Int -> {
                        return SharePreference.instance.getInt(key,defaultValue) as T
                    }
                    is String -> {
                        return SharePreference.instance.getString(key,defaultValue) as? T
                    }
                    is Boolean -> {
                        return SharePreference.instance.getBoolean(key,defaultValue) as? T
                    }
                    is Long -> {
                        return SharePreference.instance.getLong(key,defaultValue) as? T
                    }
                    is Float -> {
                        return SharePreference.instance.getFloat(key,defaultValue) as? T
                    }
                }

                return null
            }

        }
    }
}