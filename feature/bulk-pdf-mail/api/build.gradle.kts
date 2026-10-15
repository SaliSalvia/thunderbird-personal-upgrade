plugins {
    id(ThunderbirdPlugins.Library.android)
}

android {
    namespace = "net.thunderbird.feature.bulkpdfmail.api"
}

dependencies {
    api(projects.core.common)
}

codeCoverage {
    branchCoverage = 0
    lineCoverage = 0
}
