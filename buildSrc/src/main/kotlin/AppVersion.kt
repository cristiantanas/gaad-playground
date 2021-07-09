import java.io.File
import java.util.*

class AppVersion private constructor(
    major: Int,
    minor: Int,
    patch: Int,
    build: Int
) {

    val versionCode: Int = major * 100000000 + minor * 1000000 + patch * 10000 + build
    val versionName: String = "$major.$minor.${patch}_$build"

    companion object {
        private const val KEY_VERSION_MAJOR = "VERSION_MAJOR"
        private const val KEY_VERSION_MINOR = "VERSION_MINOR"
        private const val KEY_VERSION_PATCH = "VERSION_PATCH"
        private const val KEY_VERSION_BUILD = "VERSION_BUILD"

        fun of(filename: String): AppVersion {
            val versionFile = File(filename)
            if (!versionFile.exists()) {
                throw Error("Missing $filename file in the project's root folder.")
            }

            val properties = loadVersionProperties(versionFile)
            return AppVersion(
                (properties[KEY_VERSION_MAJOR] as String).toInt(),
                (properties[KEY_VERSION_MINOR] as String).toInt(),
                (properties[KEY_VERSION_PATCH] as String).toInt(),
                (properties[KEY_VERSION_BUILD] as String).toInt()
            )
        }

        private fun loadVersionProperties(versionFile: File): Properties {
            val properties = Properties()
            versionFile.inputStream().use { properties.load(it) }
            return properties
        }
    }
}
