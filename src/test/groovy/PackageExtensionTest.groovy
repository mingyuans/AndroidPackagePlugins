import com.mingyuans.gradle.PackageExtension

/**
 * Created by yanxq on 17/1/10.
 */
class PackageExtensionTest extends GroovyTestCase {
    void testGetPackageJarName() {
        PackageExtension extension = new PackageExtension();
        extension.jarBaseName = "test_lib";
        extension.jarVersion = "1.0.00";

        String jarName = extension.getPackageJarName();
        String dirPath = extension.getArchiveFileDir();

        assertEquals("test_lib-1.0.00.jar",jarName)
        assertEquals("artifacts/1.0.00/",dirPath);
    }

    void testGetArchiveFileDir() {

    }

    void testGetArchiveFilePath() {

    }
}
