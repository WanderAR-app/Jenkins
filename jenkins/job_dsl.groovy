folder('Projects') {
}

freeStyleJob('link-project') {
    description("When executed, links the specified project in the parameters to the infrastructure by creating a job in the Project folder with a name given by the user.")
    parameters {
        stringParam('PROJECT_NAME', null, 'The name you want to give to the project')
        stringParam('GIT_HTTPS_URL', null, 'The github HTTPS url of the project')
    }

    steps {
        dsl {
            text('''
                freeStyleJob("Projects/$PROJECT_NAME") {
                    scm {
                        git {
                            remote {
                                    name("main")
                                    url("$GIT_HTTPS_URL")
                            }
                        }
                    }
                    triggers {
                        scm("* * * * *")
                    }
                    steps {
                        
                    }
                }

                freeStyleJob("Projects/$PROJECT_NAME/Tests") {
                    scm {
                        git {
                            remote {
                                    name("main")
                                    url("$GIT_HTTPS_URL")
                            }
                        }
                    }
                    triggers {
                        
                    }
                }
            ''')
        }
    }
}
