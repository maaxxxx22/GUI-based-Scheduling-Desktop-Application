# Project Overview
---
The main objective of this project is to develop a user-friendly and efficient desktop application that enables the organization to schedule appointments and manage their customers with ease. The application will be designed to meet specific business requirements outlined by the organization and will be developed using Java and JavaFX technology. Additionally, the application will incorporate prepopulated read-only data from third-party sources for Country and First-Level-Division tables and a Contacts table prepopulated by our company, with administrative functions for adding users handled by our IT support staff.

## Deliverables
---
1. A fully functional GUI-based scheduling desktop application that meets the specific business requirements outlined by the organization.
2. Documentation including a user manual, installation guide, and technical specification.
3. Code files and all necessary files for the application's deployment.

*Key Features*

1. Supports adding, modifying, and removing customers and appointments.
2. Implement a notification system that generates reminders for upcoming appointments.
3. Generate various types of reports for the application.
4. Localize the application in English, Spanish, and French.
5. Automatically adjust the date and time based on the system's location.
6. Provide comprehensive documentation, including design patterns, Javadoc comments, and user manuals.
7. Deliver the application's source code.

## Dependencies
---
- IntelliJ IDEA 2022.2.2 - https://blog.jetbrains.com/idea/2022/09/intellij-idea-2022-2-2/
- Java SE Development Kit 19.0.2 - https://www.oracle.com/java/technologies/javase/jdk19-archive-downloads.html
- JavaFX-SDK-19.0.2.1 - https://gluonhq.com/products/javafx/
- mysql-connector-java-8.0.32 - https://dev.mysql.com/downloads/connector/j/?os=26

## How to Run 
---
##### (IntelliJ IDEA)
A.)  *Set up a project SDK*

1. From the main menu, select File | Project Structure | Project Settings | Project.
2. If the necessary SDK is already defined in IntelliJ IDEA, select it from the SDK list.

If the SDK is installed on your computer, but not defined in the IDE, select Add SDK | 'SDK name', and specify the path to the SDK home directory.


B.)  *Define a project library*

1. From the main menu, select File | Project Structure Ctrl+Alt+Shift+S.
2. Under Project Settings, select Libraries.
3. Click the Add button and select Java to add a library from the files located on your computer.
4. Select the module or modules to which you want to add the new library.


C.)  *Create a run/debug configuration from a template (Application)*

1. From the main menu, select Run | Edit Configurations. Alternatively, press Alt+Shift+F10, then 0.
2. In the Run/Debug Configuration dialog, click App general add on the toolbar or press Alt+Insert. The list shows the run/debug configuration templates. Select Application.
3. Specify the run/debug configuration name in the Name field. This name will be shown in the list of the available run/debug configurations.
4. In the Build and run section, specify the parameters listed below. To access the fields using keyboard, hold Alt and use the shortcut according to the hints that appear.
JDK or JRE – the JDK or JRE that will be used for running your program.
Main class – the main class defines the entry point of your application. The main class must contain the public static void main(String[] args) method.
5. click Modify options, Add VM options – specify the VM options for running the application. Add this,--module-path /path/to/javafx-sdk-15.0.1/lib --add-modules javafx.controls,javafx.fxml 
where 'path' would refer to your javafx lib filepath.
6. Apply the changes and close the dialog.


D.)  *Add mysql JAR file*
1. Download and unzip mysql-connector-java-8.0.32
2. Copy and paste 'mysql-connector-j-8.0.32.jar' into 'javaFX-SDK-19.0.2.1' lib folder

F.)  *Run application*

1. Click Run on the toolbar to run the application.
2. Once launched, username - test, password - test


## Generate Reports
---
To generate reports in the application, navigate to the Reports window by clicking on the Reports button located on the main screen. In the Reports window, there are three types of reports that can be generated. Each report is saved to the project's root folder with a specific file name.

- The first report lists the number of customer appointment types scheduled for each month.

- The second report displays the upcoming schedule for each contact, it includes appointment ID, title, type and description, start date and time, end date and time, and customer ID

- The third report shows the user schedule by location, it includes appointment ID, title, description, start date and time, end date and time, and customer ID
- 
## Contact
---
Name: Okunta Braide
Email: obraid1@wgu.edu
date: 03/07/2023
application version: 1.0

