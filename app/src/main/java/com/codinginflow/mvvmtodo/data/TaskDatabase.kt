package com.codinginflow.mvvmtodo.data

import androidx.room.Database
import androidx.room.DatabaseConfiguration
import androidx.room.InvalidationTracker
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import androidx.sqlite.db.SupportSQLiteOpenHelper
import com.codinginflow.mvvmtodo.di.ApplicationScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Provider

@Database(entities = [Task::class], version = 1)
abstract class TaskDatabase : RoomDatabase() {


    abstract fun taskDao(): TaskDao

    class CallBack @Inject constructor(
        private val database: Provider<TaskDatabase>,
        @ApplicationScope private val applicationScope: CoroutineScope
    ) : RoomDatabase.Callback() {
        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
            val dao = database.get().taskDao()

            applicationScope.launch {
                dao.insert(Task("Wash the dishes"))
                dao.insert(Task("Call Mom", important = true))
                dao.insert(Task("Go Shopping", completed = true))
                dao.insert(Task("Finish Kotlin Course"))
                dao.insert(Task("Visit Grandpa"))
            }
        }
    }
}