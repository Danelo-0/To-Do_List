package ru.matveev.lab_4

import android.content.Context
import androidx.room.AutoMigration
import androidx.room.Database
import androidx.room.DeleteTable
import androidx.room.Query
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

@Database(entities = [Work::class], version = 1)
abstract class MyDB: RoomDatabase() {
    abstract fun getDao(): Dao

    companion object{

        val MIGRATION_1_2: Migration = object : Migration(1, 2) {
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL("ALTER TABLE works ADD COLUMN date INTEGER")
            }
        }

        fun getDB(context: Context): MyDB{
            return Room.databaseBuilder(
                context.applicationContext,
                MyDB::class.java,
                "list_work_DB"
            ).build()
        }
    }
}
