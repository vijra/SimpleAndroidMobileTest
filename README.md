Simple Android Mobile Test Automation for emulators also for real devices.

0. Preconditions
	Android SDKs
	Selenium Webdriver latest version (ex: http://selenium-release.storage.googleapis.com/2.45/selenium-java-2.45.0.zip)
	Appium (On mac: npm install -g appium)
	Also get Appium Client driver latest version (ex: https://repo1.maven.org/maven2/io/appium/java-client/2.1.0/java-client-2.1.0.jar)
	Java 1.7 or above.
	Any Editor like Eclipse/IntelliJ
	Emulator (Android avd or genymotion) or Real Mobile Device.

1. Download
	Clone this code.

2. Settings
	Make sure Java Path already in there.
	Add selenium and appium jars to the library path.
	Connect the device or Start the emulator.
	Update the perf_config.properties file with required parameters.

3. Edit
	Add your Locators in the Locators class.
	Add your TODO activities on the performActivity method.

4. Test	
	Start the Appium server.
	Run the project.

