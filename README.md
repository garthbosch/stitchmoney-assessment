# Stitch Money Assessment

### Tools

- Java
- Selenium WebDriver
- TestNG
- WebDriver Manager
- Lombok
- Allure Reports
- Locust - performance testing tool

### Setup environment
- Download and install sdkman - https://sdkman.io/install
- Once sdkman is installed then install the following (commands included):
    - jdk - sdk install java <version>
    - maven - sdk install maven <version>
    - clone the following repositories to your local machine
        1. Automation Test Framework: https://github.com/garthbosch/stitchmoney-assessment.git
        2. Backend: https://github.com/Stitch-Money/Todo-list-api
        3. Frontend: https://github.com/Stitch-Money/todo-front-end

  Note: To list the versions of java you can run this command (you can do the same for maven) - sdk list java

- Download and install allure reports - https://docs.qameta.io/allure/
  - My setup is on a macbook therefore I ran this command: brew install allure
  - The link with show you how to install on other operating systems.
- Download and install locust
  - My setup is on a macbook therefore I ran this command: brew install locust

### Framework Architecture

	Project-Name
        |_src
        |   |_main
        |   |   |_java
        |   |   |   |_gfb
        |   |   |   |   |_actions
        |   |   |   |   |   |_GUIActions.java
        |   |   |   |   |   |...
        |   |   |   |   |_models
        |   |   |   |   |   |_PageLocators.java
        |   |   |   |   |   |_ResponseData.java
        |   |   |   |   |   |_RestAssuredData.java
        |   |   |   |   |   |...
        |   |   |   |   |_utils
        |   |   |   |   |   |_FileHelper.java
        |   |   |   |   |   |_GeneratePayload.java
        |   |   |   |   |   |_RestAssuredClient.java
        |   |   |   |   |   |_SeleniumWebDriverUtils.java
        |   |   |   |   |   |...
        |   |   |_resources
        |   |   |   |_config.properties
        |   |   |   |_pageLocators.json
        |   |   |   |...
        |   |_test
        |   |   |_java
        |   |   |   |_gfbTest
        |   |   |   |   |_api
        |   |   |   |   |   |_TodoAPITest.java
        |   |   |   |   |   |...
        |   |   |   |   |_gui
        |   |   |   |   |   |_TodoGUITest.java
        |   |   |   |   |   |...
        |   |   |   |   |_reportsandvalidations
        |   |   |   |   |   |...
        |   |   |   |   |_BaseTest
        |   |_resources
        |   |   |_apiTodoTests.xml
        |   |   |_guiTodoTests.xml
        |   |   |   |...
        |_locust_report.html
        |_locustfile.py
        |_pom.xml
        |_README.md
        |_runAllTestsAPIandGUI.bat
        |_runAllTestsAPIandGUI.sh
        |_Test Suite.xlsx

### Deploy TODO list apps to your local

1. Please follow the steps in todo-front-end and Todo-list-api README.md to deploy the applications

### Running API and GUI tests

1. Via maven command
    - Go to your project directory from terminal and hit following commands:
        - API TESTS:
            - mvn -e test -Dtest.script=src/test/resources/apiTodoTests.xml
        - GUI Tests:
            - mvn -e test -Dtest.script=src/test/resources/guiTodoTests.xml -DbrowserType=chrome

   Note: -Dtest.script is where the testNG xml file is. -DbrowserType is the parameter to run tests on chrome or firefox
   for GUI tests

2. Via IDE
    - Navigate to the test script in src/test/resources.
    - Right-click the apiTodoTests.xml or guiTodoTests.xml file and select to run the test.

3. Running .sh or .bat files
    - Mac or Linux - Open the file runAllTestsAPIandGUI.sh and set your locations and run the file.
    - Windows - Open the file runAllTestsAPIandGUI.bat and set your locations and run the file.

### Output

1. Screenshots
    - A reportingAndImages folder will be created with screenshots.
    - On the console the logs will be outputted.

2. Reporting
    - Run this command to view the allure reports: allure serve allure-results. A browser will open with the results.

----

### Performance Testing
- Navigate to the root of the stitchmoney-assessment project and run the command: locust
- On your browser navigate to http://localhost:8089/
- Enter the desired values and run the test.
- You can stop it whenever you want to.

#### The output of a test I ran can be found here: /locust_repot.html
- My findings:
  - Duration: 1m30s
  - Number of users: 5
  - Spawning rate: 1 user per second was added.
  - RPS/ Throughput: 1.6 requests per second was made.

### Test Cases and Defect Report
- This can be found in the Test Suite.xlsx file. It has TestCase and Defects sheets