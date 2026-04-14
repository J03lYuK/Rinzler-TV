plugins {
	alias(libs.plugins.android.application)
	alias(libs.plugins.kotlin.compose)
}

android {
	namespace = "uk.rinzler.mobile"
	compileSdk = libs.versions.android.compileSdk.get().toInt()

	defaultConfig {
		applicationId = "uk.rinzler.mobile"
		minSdk = libs.versions.android.minSdk.get().toInt()
		targetSdk = libs.versions.android.targetSdk.get().toInt()
		versionCode = 1
		versionName = "0.1.0"
	}

	buildFeatures {
		compose = true
		buildConfig = true
	}

	compileOptions {
		isCoreLibraryDesugaringEnabled = true
	}

	lint {
		lintConfig = file("$rootDir/android-lint.xml")
		abortOnError = false
	}
}

dependencies {
	implementation(libs.androidx.core)
	implementation(libs.androidx.activity.compose)
	implementation(libs.androidx.appcompat)
	implementation(libs.bundles.androidx.compose)
	implementation(libs.androidx.lifecycle.runtime)
	implementation(libs.androidx.lifecycle.viewmodel)
	implementation(libs.bundles.koin)
	implementation(libs.timber)

	coreLibraryDesugaring(libs.android.desugar)
}
