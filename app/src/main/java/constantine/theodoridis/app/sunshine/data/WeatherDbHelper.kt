/*
 *  Copyright (C) 2018 Constantine Theodoridis
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package constantine.theodoridis.app.sunshine.data

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

import constantine.theodoridis.app.sunshine.data.WeatherContract.LocationEntry
import constantine.theodoridis.app.sunshine.data.WeatherContract.WeatherEntry

class WeatherDbHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
	companion object {
		private const val DATABASE_VERSION = 1
		internal const val DATABASE_NAME = "sunshine.db"
	}

	override fun onCreate(sqLiteDatabase: SQLiteDatabase) {
		val sqlCreateLocationTable = "CREATE TABLE " + LocationEntry.TABLE_NAME + " (" +
						LocationEntry.ID + " INTEGER PRIMARY KEY," +
						LocationEntry.COLUMN_LOCATION_SETTING + " TEXT UNIQUE NOT NULL, " +
						LocationEntry.COLUMN_CITY_NAME + " TEXT NOT NULL, " +
						LocationEntry.COLUMN_COORDINATE_LATITUDE + " REAL NOT NULL, " +
						LocationEntry.COLUMN_COORDINATE_LONGITUDE + " REAL NOT NULL " +
						" );"
		val sqlCreateWeatherTable = "CREATE TABLE " + WeatherEntry.TABLE_NAME + " (" +
						WeatherEntry.ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
						WeatherEntry.COLUMN_LOC_KEY + " INTEGER NOT NULL, " +
						WeatherEntry.COLUMN_DATE + " INTEGER NOT NULL, " +
						WeatherEntry.COLUMN_SHORT_DESC + " TEXT NOT NULL, " +
						WeatherEntry.COLUMN_WEATHER_ID + " INTEGER NOT NULL," +
						WeatherEntry.COLUMN_MIN_TEMP + " REAL NOT NULL, " +
						WeatherEntry.COLUMN_MAX_TEMP + " REAL NOT NULL, " +
						WeatherEntry.COLUMN_HUMIDITY + " REAL NOT NULL, " +
						WeatherEntry.COLUMN_PRESSURE + " REAL NOT NULL, " +
						WeatherEntry.COLUMN_WIND_SPEED + " REAL NOT NULL, " +
						WeatherEntry.COLUMN_DEGREES + " REAL NOT NULL, " +
						" FOREIGN KEY (" + WeatherEntry.COLUMN_LOC_KEY + ") REFERENCES " +
						LocationEntry.TABLE_NAME + " (" + LocationEntry.ID + "), " +
						" UNIQUE (" + WeatherEntry.COLUMN_DATE + ", " +
						WeatherEntry.COLUMN_LOC_KEY + ") ON CONFLICT REPLACE);"
		sqLiteDatabase.execSQL(sqlCreateLocationTable)
		sqLiteDatabase.execSQL(sqlCreateWeatherTable)
	}

	override fun onUpgrade(sqLiteDatabase: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
		sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + LocationEntry.TABLE_NAME)
		sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + WeatherEntry.TABLE_NAME)
		onCreate(sqLiteDatabase)
	}
}
