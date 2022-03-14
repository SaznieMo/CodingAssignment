README

Coding Assignment - LOG FILE PARSING

Time Taken: 1h40

Notes: 
- Timestamps are integer values
- Finish timestamps are always > Start timestamps, even if a finish event occurs in the log before a start event (This was specified in the task)
- HashMap was used for computational logic to increase efficiency - Only 1 for loop of full data set necessary for parsing + computations.
- Log4j2 Utilized for logging
- Unit Testing in test/java folder, run via "mvn test"

Build & Run INSTRUCTIONS:
- JDK 8 + Maven installation necessary.
- Download source code.
- Navigate to root folder containing ./src
- On the Command Line, execute the following cmd:
mvn clean test exec:java -Dexec.args="path/to/logfile.txt"
- E.G: mvn clean test exec:java -Dexec.args="/Users/saznie/Downloads/logfile.txt"


Improvements to be made for Production Environments:
- Multithreading - Implement Runnable() threads for application to be called asynchronously on multiple files.