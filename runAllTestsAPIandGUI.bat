mvn -e test -Dtest.script=src/test/resources/apiTodoTests.xml
mvn -e test -Dtest.script=src/test/resources/guiTodoTests.xml -DbrowserType=chrome
allure serve allure-results