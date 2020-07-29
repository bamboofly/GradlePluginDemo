package com.lianghuan.local

import com.android.build.gradle.BaseExtension
import com.android.build.gradle.internal.plugins.AppPlugin
import org.gradle.BuildListener
import org.gradle.BuildResult
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.initialization.Settings
import org.gradle.api.invocation.Gradle
import org.gradle.api.plugins.ApplicationPlugin


class LocalPlugin implements Plugin<Project> {

    void apply(Project project) {
        println("==apply==")
        println("gradle version " + project.gradle.gradleVersion)

        println("gradleHomeDir " + project.gradle.gradleHomeDir.toString())

        println(project.name)

        println("project.buildDir " + project.buildDir.toString())

        println("project.buildFile " + project.buildFile.toString())

        project.task('CustomLocalTask', group: 'CustomTask').doFirst {
            println("-----")
        }

        project.afterEvaluate {
            println("-----a----")
        }








        project.gradle.afterProject {
            boolean b = project.getPlugins().hasPlugin(AppPlugin.class)
            println("hasPlugin = " + b)
            if (b) {
                project.extensions.findByType(BaseExtension.class).registerTransform(new MyTransform())
            }
            println("lianghuan project.gradle.afterProject ----" + ((Project)it).getPlugins().hasPlugin(ApplicationPlugin.class))
            ((Project)it).getPlugins().hasPlugin(ApplicationPlugin.class)

            project.extensions.each {
                println("it = " + it)
            }
            println("BaseExtension = " + project.extensions.findByType(BaseExtension.class))
        }

        project.gradle.beforeProject {
            println("lianghuan project.gradle.beforeProject --")
        }

        project.gradle.projectsLoaded {
            println("lianghuan project.gradle.projectsLoaded --")
        }



        project.gradle.addBuildListener(new BuildListener() {
            @Override
            void buildStarted(Gradle gradle) {
                println("lianghuan ---- buildStarted --")
            }

            @Override
            void settingsEvaluated(Settings settings) {
                println("lianghuan ---- settingsEvaluated --")
            }

            @Override
            void projectsLoaded(Gradle gradle) {
                println("lianghuan ---- projectsLoaded --")
            }

            @Override
            void projectsEvaluated(Gradle gradle) {
                println("lianghuan ---- projectsEvaluated --")
            }

            @Override
            void buildFinished(BuildResult buildResult) {
                println("lianghuan ---- buildFinished --")
            }
        })

//        project.gradle.addProjectEvaluationListener(new ProjectEvaluationListener() {
//            @Override
//            void beforeEvaluate(Project project) {
//                println("lianghuan ---- beforeEvaluate -- name " + project.name)
//            }
//
//            @Override
//            void afterEvaluate(Project project, ProjectState projectState) {
//                println("lianghuan ---- afterEvaluate -- name " + project.name)
//            }
//        })

    }
}