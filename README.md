# Lotto Generator Console App

Lotto Console Interface is a simple console interface application that operates on real lotto draw datasets. This project is intended for fun and contains many flaws.

## About
This project provides basic functionalities to interact with lotto-draw data. Users can view the latest draw, add new draw results, delete existing draws, view winning numbers frequency, view special numbers frequency, generate random plays, and generate random plays with frequency.

## How to Run

### 1. Prerequisites:

    - Intellij IDEA Community 2023 installed on your system.
    - Amazon Corretto 17 JDK installed on your system.
    
### 2. Installation:

    - Clone or download the code to your IDE.
    
### 3. Running the Program:

    - Open Intellij IDEA Community 2023.
    - Import the project by selecting File > Open and navigating to the directory 
      where you cloned or downloaded the code.
    - You can also clone the repo directly from the IDE.
    - Locate the run button, typically in the top-right corner of the IDE or directly in the Main class.
    - Start the program by clicking the run button.
    - Follow the instructions provided in the interface to navigate the program.

## Issues

1. **Empty JSON File Crash:** Loading an empty JSON file causes the program to crash.
2. **Minimal Error Handling:** Currently, there is minimal error handling in the program.
3. **Input Validation:** There is no check on input values to ensure they are in the valid format.
4. **Duplicate Values in Random Play with Frequency:** The "Generate Random Plays with Frequency" functionality does not work properly; it generates duplicate values.
