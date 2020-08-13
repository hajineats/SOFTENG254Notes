#!/bin/bash

for i in bad{A..J}.jar ; do
	javac -cp ./text/hamcrest.jar:./text/junit.jar:../$i:. text/TestFlushLeft.java
	java -cp ./text/hamcrest.jar:text/junit.jar:../$i:. org.junit.runner.JUnitCore text.TestFlushLeft
done
