package ru.matveev.lab_4

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity (tableName = "works")
data class Work(
    @PrimaryKey(autoGenerate = true) var id: Int? = null,
    @ColumnInfo(name = "title") var title: String,
    @ColumnInfo(name = "description") var description: String?,
    @ColumnInfo(name = "date") var date: Long,
    @ColumnInfo(name = "status") var status: Boolean = false,
)
