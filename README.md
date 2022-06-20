# Stitch Money Assessment

### Tools
- Java
- Selenium WebDriver
- TestNG
- WebDriver Manager
- Lombok

### Setup environment
- Download and install sdkman - https://sdkman.io/install
- Once sdkman is installed then install the following (commands included):
  - jdk - sdk install java <version>
  - maven - sdk install maven <version>
  - clone or copy source code to your local machine - https://github.com/garthbosch/upwork-assessment.git
  
  Note: To list the versions of java you can run this command (you can do the same for maven) - sdk list java 

### Framework Architecture
	Project-Name
        |_src
        |   |_main
        |   |   |_java
        |   |   |   |_gfb
        |   |   |   |   |_actions
        |   |   |   |   |   |_UIActions.java
        |   |   |   |   |_models
        |   |   |   |   |   |_BingPageLocators.java
        |   |   |   |   |   |_GooglePageLocators.java
        |   |   |   |   |   |_SearchResultsDetails.java
        |   |   |   |   |   |...
        |   |   |   |   |_utils
        |   |   |   |   |   |_FileHelper.java
        |   |   |   |   |   |_SeleniumWebDriverUtils.java
        |   |   |   |   |   |...
        |   |   |_resources
        |   |   |   |_config.properties
        |   |   |   |_pageLocators.json
        |   |   |   |...
        |   |_test
        |   |   |_java
        |   |   |   |_gfbTest
        |   |   |   |   |_BaseTest.java
        |   |   |   |   |_SearchTest.java
        |   |   |   |   |...
        |   |_resources
        |   |   |   |_searchTestSuite.xml
        |   |   |   |...
        |_pom.xml
        |_README.md

### Running test
1. Via maven command
   - Go to your project directory from terminal and hit following commands:
      - mvn -e test -Dtest.script=src/test/resources/searchTestSuite.xml -DbrowserType=chrome

    Note: -Dtest.script is where the testNG xml file is. -DbrowserType is the parameter to run tests on chrome or firefox.

2. Via IDE
   - Navigate to the test script in src/test/resources.
   - Right-click the searchTestSuite.xml file and select to run the test.

### Output
- A reportingAndImages folder will be created with screenshots.
- On the console the logs will be outputted. 
     
----

## Specifications
### Objectives


### Test case:

### Requirements:
