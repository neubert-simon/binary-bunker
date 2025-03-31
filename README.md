# Binary Bunker



## Requirements
- npm (Node package manager) must be installed.
- <a href="https://app.docker.com/login">Docker Account</a> (with verified email address!)
- <a href="https://app.docker.com">Docker Desktop</a> must be installed. 
- Java 21 or higher must be installed and accessible.
- Maven must be installed

<br>

## Running the application
All command line instructions assume you are currently in the content root directory unless specified otherwise.
On Windows machines, we strongly recommend you use the Unix commands via WSL.
If you choose not to, please start the Docker Desktop App and ensure it is up and running during the whole process.

### 1. Initialize the Database
- Change the credentials in the <code>.env.example</code> file and rename it to <code>.env</code> before continuing!<br>
- Make sure you are signed in into your docker account in the Docker Desktop application.
- Make sure Docker Desktop is up to date: -> Settings -> Software Updates

**For Unix systems:**
```bash
./build_bash.sh
```

- On older hardware, the Docker Container might not start automatically. If this is the case for you, please run:
```bash
docker-compose up -d
```

**For Windows (experimental):**
```terminal
./build_powershell.ps1
```
<br>

### 2. Start the Backend
To fetch the target jar, copy the ID of the latest CI/CD jobs "packaging" stage:<br>
[Binary Bunker - CI/CD Jobs](https://gitlab.mi.hdm-stuttgart.de/sn067/binary-bunker/-/jobs)<br><br>
Enter that ID into the following link to fetch the newest packaged backend version from the jobs artifacts:
```url
https://gitlab.mi.hdm-stuttgart.de/sn067/binary-bunker/-/jobs/<LATEST JOB ID>/artifacts/browse/target/
```
<br>

_Or package the source code yourself:_
```bash
mvn package
```
<br>

Now, run the spring boot server:
```bash
java -jar target/BinaryBunker-1.3.1.jar
```
<br>

_Alternatively, you can just run the backend from your command line directly:_
```bash
mvn spring-boot:run
```
_You may append a "&" if you are using a Unix system to run the application in the background._

<br>

### 3. Start the Frontend
Navigate to the client directory and install the necessary packages:
```bash
cd src/main/client
npm install
```
From the same directory, launch the server:
```bash
npm run dev
```

<br>

### 4. Open the web application
Now, start your browser and open:<br>
**[Binary Bunker - IP and Subnetting](http://localhost:5173/)**<br>

If this URL doesn't work for you, the port used by default (port 5173) is most likely already in use.
Please check the console output of the npm run dev command to find out on which port the application is running.

<br>

## Usage
Time to learn Subnetting 🚀
<ul>
<li>Head over to the IP page to calculate IP addresses and their subnets.</li>
<li>Visit the subnetting page to get the addresses visualized.</li>
<li>Try our learning tool to get a suite of randomly generated questions.</li>
<li>Test your knowledge in the quiz section against a vast array of questions.</li>
</ul>
To terminate the application, kill the running processes.

<p>
<br>

## Documentation
To read our documentation, please visit our project wiki:<br>
[Binary Bunker - Wiki](https://gitlab.mi.hdm-stuttgart.de/sn067/binary-bunker/-/wikis/home)