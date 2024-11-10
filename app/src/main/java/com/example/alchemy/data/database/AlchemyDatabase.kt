package com.example.alchemy.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.alchemy.data.converters.StringListConverter
import com.example.alchemy.data.dao.IngredientDao
import com.example.alchemy.data.model.Effect
import com.example.alchemy.data.model.Ingredient
import com.example.alchemy.data.model.IngredientEffectCrossRef

@Database(
    entities = [
        Ingredient::class,
        Effect::class,
        IngredientEffectCrossRef::class
    ],
    version = 3
)
@TypeConverters(StringListConverter::class)
abstract class AlchemyDatabase : RoomDatabase() {
    abstract fun ingredientDao(): IngredientDao

    companion object {
        val MIGRATION_2_3 = object : Migration(2, 3) {
            override fun migrate(database: SupportSQLiteDatabase) {
                // Создаем таблицу эффектов
                database.execSQL(
                    """
                    CREATE TABLE effects (
                        id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
                        name TEXT NOT NULL
                    )
                """
                )

                // Создаем таблицу связей
                database.execSQL(
                    """
                    CREATE TABLE ingredient_effect_cross_ref (
                        ingredient_id INTEGER NOT NULL,
                        effect_id INTEGER NOT NULL,
                        PRIMARY KEY(ingredient_id, effect_id),
                        FOREIGN KEY(ingredient_id) REFERENCES ingredients(id) ON DELETE CASCADE,
                        FOREIGN KEY(effect_id) REFERENCES effects(id) ON DELETE CASCADE
                    )
                """
                )

                // Создаем временную таблицу для ингредиентов
                database.execSQL(
                    """
                    CREATE TABLE ingredients_new (
                        id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
                        name TEXT NOT NULL,
                        material_equivalent TEXT NOT NULL,
                        main_effect TEXT NOT NULL
                    )
                """
                )

                // Копируем данные в новую таблицу
                database.execSQL(
                    """
                    INSERT INTO ingredients_new (id, name, material_equivalent, main_effect)
                    SELECT id, name, material_equivalent, main_effect FROM ingredients
                """
                )

                // Удаляем старую таблицу
                database.execSQL("DROP TABLE ingredients")

                // Переименовываем новую таблицу
                database.execSQL("ALTER TABLE ingredients_new RENAME TO ingredients")
            }
        }

        fun getInstance(context: Context): AlchemyDatabase {
            return Room.databaseBuilder(
                context.applicationContext,
                AlchemyDatabase::class.java,
                "alchemy_database"
            )
                .addMigrations(MIGRATION_2_3)
                .build()
        }
    }
} 