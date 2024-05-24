package ru.matveev.lab_4

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface Dao {

    @Insert
    fun insertItem(work: Work)

    @Query("SELECT * FROM works")
    fun getAllWork(): Flow<List<Work>>

    @Query("SELECT * FROM works WHERE id = :id")
    fun getWorkById(id: Int): Work

    @Delete()
    fun delete(vararg work: Work)

    @Update
    fun updateWork(vararg work: Work)

    @Query("DELETE FROM works WHERE id = :id")
    fun deleteById(id: Int)

    @Query("UPDATE works SET status = :status WHERE id = :id")
    fun updateStatusById(id: Int, status: Boolean)

    @Query("SELECT * FROM works ORDER BY status ASC, date ASC")
    fun getAllWorkSortedByStatusAndDate(): Flow<List<Work>>

    @Query("UPDATE works SET date = :date, title = :title, description = :description  WHERE id = :id")
    fun updateFullById(id: Int, title: String, description: String, date: Long)
}