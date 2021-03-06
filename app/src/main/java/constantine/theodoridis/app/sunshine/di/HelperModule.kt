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

package constantine.theodoridis.app.sunshine.di

import android.content.SharedPreferences
import android.content.res.Resources
import constantine.theodoridis.app.sunshine.data.helpers.AndroidResources
import constantine.theodoridis.app.sunshine.data.helpers.AndroidSharedPreferences
import constantine.theodoridis.app.sunshine.domain.helpers.ResourcesHelper
import constantine.theodoridis.app.sunshine.domain.helpers.SharedPreferencesHelper
import dagger.Module
import dagger.Provides

@Module
class HelperModule {
	@Provides
	fun provideResourcesHelper(resources: Resources): ResourcesHelper {
		return AndroidResources(resources)
	}

	@Provides
	fun provideSharedPreferencesHelper(sharedPreferences: SharedPreferences):
					SharedPreferencesHelper {
		return AndroidSharedPreferences(sharedPreferences)
	}
}