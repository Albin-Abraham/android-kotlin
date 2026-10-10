package com.example.myapp.testing.architecture

import org.junit.Assert.assertTrue
import org.junit.Test
import java.io.File

/**
 * Executable Clean Architecture Boundary Verification Suite.
 * Asserts strict inward dependency flow without framework leakage in Domain layers.
 */
class ArchitectureBoundaryTest {

    @Test
    fun domainLayer_mustNotContain_androidSdkImports() {
        val rootDir = File(System.getProperty("user.dir") ?: ".")
        val srcDir = File(rootDir, "src/main/java")
        val mainDir = if (srcDir.exists()) srcDir else File(rootDir, "app/src/main/java")

        assertTrue("Main source directory must exist: ${mainDir.absolutePath}", mainDir.exists())

        val domainFiles = mainDir.walkTopDown()
            .filter { it.isFile && it.extension == "kt" }
            .filter { it.absolutePath.contains("/domain/") }
            .toList()

        assertTrue("Domain layer files should be discovered", domainFiles.isNotEmpty())

        val violations = mutableListOf<String>()

        domainFiles.forEach { file ->
            val forbiddenImports = file.readLines()
                .filter { line ->
                    val trimmed = line.trim()
                    trimmed.startsWith("import android.") || trimmed.startsWith("import androidx.")
                }

            if (forbiddenImports.isNotEmpty()) {
                violations.add("${file.name}: ${forbiddenImports.joinToString(", ")}")
            }
        }

        assertTrue(
            "Clean Architecture Violation: Domain layer must have ZERO Android framework imports! Found violations:\n" +
                    violations.joinToString("\n"),
            violations.isEmpty()
        )
    }

    @Test
    fun presentationLayer_mustNotImport_dataImplementationsDirectly() {
        val rootDir = File(System.getProperty("user.dir") ?: ".")
        val srcDir = File(rootDir, "src/main/java")
        val mainDir = if (srcDir.exists()) srcDir else File(rootDir, "app/src/main/java")

        val presentationFiles = mainDir.walkTopDown()
            .filter { it.isFile && it.extension == "kt" }
            .filter { it.absolutePath.contains("/presentation/") }
            .toList()

        val violations = mutableListOf<String>()

        presentationFiles.forEach { file ->
            // Presentation screens and components must not import concrete database entities or data repositories
            val forbiddenImports = file.readLines()
                .filter { line ->
                    val trimmed = line.trim()
                    trimmed.startsWith("import ") && trimmed.contains(".data.local.")
                }

            if (forbiddenImports.isNotEmpty()) {
                violations.add("${file.name}: ${forbiddenImports.joinToString(", ")}")
            }
        }

        assertTrue(
            "Presentation layer must not directly couple to local data sources:\n" +
                    violations.joinToString("\n"),
            violations.isEmpty()
        )
    }
}
