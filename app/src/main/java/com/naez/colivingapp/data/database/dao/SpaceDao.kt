package com.naez.colivingapp.data.database.dao

import androidx.room.*
import com.naez.colivingapp.data.database.entities.Space

@Dao
interface SpaceDao {

    @Query("SELECT * FROM Space")
    fun getAll(): List<Space>

    @Query("SELECT * FROM Space WHERE id = :id")
    fun findById(id: Int): Space

    @Query("SELECT COUNT(id) FROM Space")
    fun spaceCount(): Int

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertSpace(spaces: List<Space>)

    @Update
    fun updateSpace(space: Space)
}