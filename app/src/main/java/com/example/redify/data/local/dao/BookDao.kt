package com.example.redify.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.redify.data.local.entity.Book

@Dao
interface BookDao {
    //Untuk mengambil semua data dari entity
    @Query("SELECT * FROM book")
    suspend fun getAllBook(): List<Book>

    // Untuk mengecek udah di Save atau belum
    @Query("SELECT COUNT(*) FROM book WHERE id = :id")
    suspend fun isBookExist(id: String): Int

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertBook(event: Book)

    @Query("DELETE FROM book WHERE id = :id")
    suspend fun deleteBook(id: String)
}