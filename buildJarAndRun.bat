@echo on
:Compile_Build
cd com
javac -cp C:\java\LWJGL\*; -d ..\bin *.java SandMethods\*.java
pause
cd ..\bin
jar cfm SandGame.jar MANIFEST.MF com\*
java -cp c:\java\lwjgl\*;SandGame.jar com.SandSim
pause