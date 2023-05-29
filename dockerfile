FROM jenkins/jenkins:lts-jdk11
USER root

# Install docker in jenkins
RUN apt-get update -qq \
    && apt-get install -qqy apt-transport-https ca-certificates curl gnupg2 software-properties-common
RUN curl -fsSL https://download.docker.com/linux/debian/gpg | apt-key add -
RUN add-apt-repository \
   "deb [arch=amd64] https://download.docker.com/linux/debian \
   $(lsb_release -cs) \
   stable"
RUN apt-get update  -qq \
    && apt-get -y install docker-ce
RUN usermod -aG docker jenkins

COPY --chown=jenkins:jenkins ./jenkins/plugins.txt /usr/share/jenkins/ref/plugins.txt
COPY ./jenkins/conf.yml /usr/local/conf.yml
COPY ./jenkins/job_dsl.groovy /usr/local/job_dsl.groovy
COPY ./jenkins /jenkins
COPY ./images /images
RUN jenkins-plugin-cli -f /usr/share/jenkins/ref/plugins.txt
