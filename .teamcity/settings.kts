import jetbrains.buildServer.configs.kotlin.*
import jetbrains.buildServer.configs.kotlin.buildSteps.script
import jetbrains.buildServer.configs.kotlin.triggers.vcs
import jetbrains.buildServer.configs.kotlin.vcs.GitVcsRoot

/*
The settings script is an entry point for defining a TeamCity
project hierarchy. The script should contain a single call to the
project() function with a Project instance or an init function as
an argument.

VcsRoots, BuildTypes, Templates, and subprojects can be
registered inside the project using the vcsRoot(), buildType(),
template(), and subProject() methods respectively.

To debug settings scripts in command-line, run the

    mvnDebug org.jetbrains.teamcity:teamcity-configs-maven-plugin:generate

command and attach your debugger to the port 8000.

To debug in IntelliJ Idea, open the 'Maven Projects' tool window (View
-> Tool Windows -> Maven Projects), find the generate task node
(Plugins -> teamcity-configs -> teamcity-configs:generate), the
'Debug' option is available in the context menu for the task.
*/

version = "2025.03"

project {

    vcsRoot(HttpsGithubComOllvenXmlSettingsRestoreCode)

    buildType(BuildWithTemplate)
    buildType(BuildToTemplate)

    template(id3Steps)
}

object BuildToTemplate : BuildType({
    templates(id3Steps)
    name = "Build to template"

    vcs {
        root(HttpsGithubComOllvenXmlSettingsRestoreCode)
    }

    triggers {
        vcs {
            id = "TRIGGER_1"
            branchFilter = ""
        }
    }
})

object BuildWithTemplate : BuildType({
    templates(id3Steps)
    name = "BuildWithTemplate"

    steps {
        script {
            name = "OneMore Step"
            id = "OneMore_Step"
            enabled = false
            scriptContent = "ps -a"
        }
        script {
            id = "simpleRunner"
            scriptContent = """echo "new step""""
        }
    }

    triggers {
        vcs {
            id = "TRIGGER_2"
            branchFilter = ""
        }
    }
})

object id3Steps : Template({
    id("3Steps")
    name = "3Steps"

    steps {
        script {
            name = "Step1"
            id = "Step1"
            scriptContent = "pwd"
        }
        script {
            name = "Step2"
            id = "Step2"
            scriptContent = "ls"
        }
        script {
            name = "Step3"
            id = "Step3"
            scriptContent = "ls -a"
        }
    }
})

object HttpsGithubComOllvenXmlSettingsRestoreCode : GitVcsRoot({
    name = "https://github.com/ollven/xmlSettingsRestoreCode"
    url = "https://github.com/ollven/xmlSettingsRestoreCode"
    branch = "refs/heads/main"
    authMethod = password {
        userName = "ollven"
        password = "credentialsJSON:00475aef-e01c-4f15-a063-34ec1c7a8a22"
    }
})
