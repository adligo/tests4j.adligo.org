Requirements of this project
1) No Dependencies!
2) Uml diagrams are roughly as important as the code
3) Assertion api that will be compatible with GWT, codenameone (desktop, iPhone) and JSE ,
    I am not planning to support JME, unless it starts to allow the GWT emulation classes 
    		(Enum, List, Map, Annotations, Generics exc).
    		


Check-in procedure;
1) Run org.adligo.test4J_tests.RunAllTrials in the tests4j_tests project at least 3x
2) Run tests4j_4gwt (Compile it into javascript) and run it on a few browsers (Firefox, Chrome, and Safari or IE) at least 3x


@diagram_sync should be in the following format
@diagram_sync on (date) with (optional other project name)/diagram.(type i.e. seq)
Also each diagram sequence arrow should point have at least
two comments in the code;
1) The call
2) The method that got called
and if there is a interface add;
3) The interface that was passed through
Also all @diagram_sync comments should be copied and pasted for a particular
diagram when you synchronize it, so you can search for the comment later.

Also the bottom right hand of each diagram 
should contain info about when it was last 
synchronized with the java code.

# Branches of Note
- b0 This branch will build on JDK 1.8 classes (class version 52),
    Also I believe it will compile back to JDK 1.6 class format (class version 50), 
    by simply editing the main build.gradle.kts file, using JDK 1.8 to complie and 
    switching the target to JDK 1.6.