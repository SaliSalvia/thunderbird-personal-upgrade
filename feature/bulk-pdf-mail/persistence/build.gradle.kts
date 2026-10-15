plugins {
    id(ThunderbirdPlugins.Library.jvm)
}

kotlin {
    jvmToolchain(21)
}

dependencies {
    implementation(projects.feature.bulkPdfMail.api)
    testImplementation(libs.junit.jupiter.api)
    testRuntimeOnly(libs.junit.jupiter.engine)
}

codeCoverage {
    branchCoverage = 0
    lineCoverage = 0
}
