Releasing
=========

 1. Change the version in `gradle.properties` to a non-SNAPSHOT version.
 2. Update the `CHANGELOG.md` for the impending release.
 3. `git commit -am "Prepare for release X.Y.Z."` (where X.Y.Z is the new version)
 4. `git tag -a X.Y.Z -m "Version X.Y.Z"` (where X.Y.Z is the new version)
 5.a. `source /keybase/team/bytegain/secrets/env/bg-analytics-android.sh`
 5.b. See /keybase/team/bytegain/secrets/gnupg/README for additional steps.
 5.c. `./gradlew clean uploadArchives`
 6. Update the `gradle.properties` to the next SNAPSHOT version.
 7. `git commit -am "Prepare next development version."`
 8. `git push && git push --tags`
 9. Visit [Sonatype Nexus](https://oss.sonatype.org/) and release the artifact.
    This involves finding it under staging repositories, closing the repo
    (forbids additional uploads), and then releasing the staging repo.
    There can be server-side delays between the actions.

