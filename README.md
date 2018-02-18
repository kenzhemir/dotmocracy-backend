# dotmocracy-backend
This a repository for Software Engineering project

### How to setup  

#####Install TomEE
1. Download TomEE webprofile 7.0.4 ZIP from [here](http://tomee.apache.org/download-ng.html)
2. Unzip it somewhere safe (i.e. %userprofile%/TomEE/)
3. In Intellij IDEA go to Run -> Edit Configurations
4. Find the TomEE Server under Defaults and click on "Local"
5. In the Application server field click "Configure"
6. Click + and set TomEE Home to the place where you unpacked TomEE in step 2

#####Setup project
######Open new project
1. In Intellij go to File -> New -> Project from Version Control -> Github
2. Login to github if prompted
3. Enter link https://github.com/kenzhemir/dotmocracy-backend or your forked repo. 
The project should be successfully downloaded and opened
######Add Framework Support
1. Right-click on project name in "Project" view (usually left sidebar)
1. Click on "Add Framework Support..."
2. Tick Web and Maven frameworks and click Ok.
######Configure project
1. Open File -> Project Structure or Ctrl+Alt+Shift+S
2. In "Project" section, choose your JavaSDK (SDK 9.x is preferable)
3. In "Artifacts" section, double-click on all "Available Elements"
4. Save
######Create Run Configuration
1. Go to Run -> Edit Configurations
2. Click "+" and find TomEE Server Local
3. Rename the configuration (e.g. TomEE 8.5.20)
4. Click "Fix" in the bottom-right
5. Click OK
#####Run the project

When you run the project, http://localhost:8080/ page should pop up.
Go to http://localhost:8080/test and if you see message that is not an error, then everything works fine!