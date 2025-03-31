# Changelog
All notable changes to this project will be documented in this file.

<h1>v0.0.1</h1>

## Initial Commit:
### Added
    - Created a basic Java Spring boot project with Maven
    - Added a general project structure with the following packages conforming to the chosen multitier architecture:
        - db
        - quiz
        - ip
        - visualizer
        - webInterface
        
    - And the following auxiliary packages:
        - enumerations
        - exceptions
        - validation
    - Added empty classes that fit the data model (to be implemented)

<h1>v1.0.0</h1>

## First Release (MVP): IP Calculator

### Added
    - Implemented IPv4 and IPv6 Objects and their parent class + interface to represent data model
    - Added business logic for IPv4 calculation
    - Added business logic for IPv6 calculation
    - Added business logic for IPv4 validation
    - Added business logic for IPv6 validation
    - Added exception infrastructure to notify frontend of invalid inputs / server errors
    - Added IPv4 and IPv6 calculator routes to frontend jsx app
    - Added IPv4 and IPv6 calculator routes to WebController
    - Added DMTY character video file to be displayed on application pages
    - Added React components for IP calculation
    - Added rendering of components for IP calculation
    - Added fetch via http post method to communicate user input for IP calculation
    - Added helper methods for binary string operations in BinaryHelper

### Fixed:
    - Fixed faulty pom.xml lines and added swagger-ui dependency

<h1>v1.1.0</h1>

## Feature Release : IPv4 Visualizer
### Added
    - Implemented IPv4 and IPv6 Visualizer Objects and their parent class + interface to represent data model
    - Added business logic for IPv4 visualization
    - Added business logic for IPv6 visualization
    - Added IPv4 and IPv6 visualization routes to frontend jsx app
    - Added IPv4 and IPv6 visualization routes to WebController
    - Added React components for IP visualization
    - Added rendering of components for IP visualization
    - Added fetch via http post method to communicate user input for IP visualization
    - Added UnitTests for IPFactory
    - Added UnitTests for BinaryHelper  

<h1>v1.2.0</h1>

## Feature Release : IP Learntool
### Added
    - Implemented PromptQuestion Objects and their parent class + interface to represent data model
    - Added DB_Manager for abstract persistance managing
    - Added PostgreSQL Manager for concrete database access
    - Added Dockerfile to facilitate custom postgres image delivery
    - Added build-bash script for database instantiation
    - Added business logic for PromptQuestion generation via DB
    - Added business logic for PromptQuestion dynamic random parameter generations
    - Added business logic for PromptQuestion answer generation based on generated parameters
    - Added Leantool request route to frontend jsx app
    - Added IP Learntool validation route to frontend jsx app
    - Added IP Learntool request routes to WebController
    - Added IP Learntool validation route to WebController
    - Added React components for IP Learntool
    - Added rendering of components for IP Learntool
    - Added fetch via http post method to communicate user input for PromptQuestion
    - Added fetch via http post method to get answer validation for PromptQuestion user answer
    - Added UnitTests for PromptQuestion
    - Added UnitTests for PromptQuestionUtility

<h1>v1.3.0</h1>

## Feature Release : OSI Layer Quiz
### Added
    - Implemented MCQuestion (multiple choice question) Objects to represent data model
    - Added business logic for MCQuestion generation via DB
    - Added Quiz request route to frontend jsx app
    - Added Quiz request route to WebController
    - Added React components for OSI Quiz
    - Added rendering of components for OSI Quiz
    - Added fetch via http post method to communicate user input for quiz category selection and request a question
    - Added UnitTests for MCQuestions
    - Added UnitTests for WebController
    - Added Javadoc documentation and partially included logging

<h1>v1.3.1</h1>

## Patch: Fixed OSI Quiz fetching
### Added
    - Added minor visual improvements to the applications frontend.
    - Finished adding Documentation:
        - Javadoc
        - ReadMe
     - Added more Jsavadoc documentation and fully included logging
     - Added CI/CD pipeline via .gitlab.ci-yml:
        - Build
        - Test
        - Package
     - Added Unit Tests for db package

### Fixed
    - Reverted commenting out of fetch methods for the quiz page that was done for testing purposes
    - Fixed improper escaping of string for PreparedStatement
    - Removed invalid HTML tags from javadoc in db package

### Removed
    - Requirement for the user to filter OSI Quiz questions by every possible category, now any combination or no filter works