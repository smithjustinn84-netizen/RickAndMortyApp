package io.github.smithjustinn84_netizen.rickandmortyapp.database.dao

import android.content.Context
import androidx.paging.PagingSource
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import io.github.smithjustinn84_netizen.rickandmortyapp.database.AppDatabase
import io.github.smithjustinn84_netizen.rickandmortyapp.database.model.CharacterEntity
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException

@RunWith(AndroidJUnit4::class)
class CharacterDaoTest {

    private lateinit var db: AppDatabase
    private lateinit var dao: CharacterDao

    @Before
    fun setup() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(context, AppDatabase::class.java)
            .allowMainThreadQueries() // OK for tests
            .build()
        dao = db.characterDao()
    }

    @After
    @Throws(IOException::class)
    fun tearDown() {
        db.close()
    }

    @Test
    fun insertAll_and_getCharacterCount_isCorrect() = runBlocking {
        // Given
        val characters = sampleCharacters()

        // When
        dao.insertAll(characters)

        // Then
        val count = dao.getCharacterCount()
        assertEquals(characters.size, count)
        assertFalse(dao.isEmpty())
    }

    @Test
    fun getCharacter_returnsExpectedEntity() = runBlocking {
        // Given
        val characters = sampleCharacters()
        dao.insertAll(characters)

        // When
        val result = dao.getCharacter(characters.first().id)

        // Then
        assertEquals(characters.first(), result)
    }

    @Test
    fun upsert_replacesExistingRow() = runBlocking {
        // Given initial insert
        val characters = sampleCharacters()
        dao.insertAll(characters)
        assertEquals(characters.size, dao.getCharacterCount())

        // When: upsert same id with changed name
        val updated = characters.first().copy(name = "Updated Name")
        dao.insertAll(listOf(updated))

        // Then: count unchanged and row updated
        assertEquals(characters.size, dao.getCharacterCount())
        val fetched = dao.getCharacter(updated.id)
        assertEquals("Updated Name", fetched.name)
    }

    @Test
    fun pagingSource_returnsInsertedItems() = runBlocking {
        // Given
        val characters = sampleCharacters()
        dao.insertAll(characters)

        // When
        val pagingSource: PagingSource<Int, CharacterEntity> = dao.pagingSource()
        val loadResult = pagingSource.load(
            PagingSource.LoadParams.Refresh(
                key = null,
                loadSize = 50,
                placeholdersEnabled = false
            )
        )

        // Then
        val page = loadResult as PagingSource.LoadResult.Page
        assertEquals(characters.size, page.data.size)
        // Order is by table insertion without ORDER BY; compare as sets
        assertTrue(page.data.containsAll(characters))
    }

    @Test
    fun clearAll_emptiesTable_and_isEmptyTrue() = runBlocking {
        // Given
        dao.insertAll(sampleCharacters())
        assertFalse(dao.isEmpty())

        // When
        dao.clearAll()

        // Then
        assertTrue(dao.isEmpty())
        assertEquals(0, dao.getCharacterCount())
    }

    private fun sampleCharacters(): List<CharacterEntity> = listOf(
        CharacterEntity(
            id = 1,
            name = "Rick Sanchez",
            status = "Alive",
            species = "Human",
            gender = "Male",
            origin = "Earth (C-137)",
            image = "https://example.com/rick.png",
            location = "Citadel of Ricks",
            episode = listOf("S01E01", "S01E02")
        ),
        CharacterEntity(
            id = 2,
            name = "Morty Smith",
            status = "Alive",
            species = "Human",
            gender = "Male",
            origin = "Earth (Replacement Dimension)",
            image = "https://example.com/morty.png",
            location = "Earth",
            episode = listOf("S01E01")
        )
    )
}
