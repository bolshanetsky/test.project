####This is technical task for screening interview.

##How To Run Tests:
Run next Maven command in command line, from the root of project:  
`mvn clean compile test -Dselenium.hub={Your_selenium_hub_url}"`

Tests should be pointed to the existing selenium hub instance, defaults to `http://localhost:4444/wd/hub` if not specified.<br/>
Your local instance of the hub could be started by running command file under `Selenium/StartSeleniumChrome.command`. <br />
*Note: works only for Mac* 


##Load test project and report is under load-test/ folder.
