@echo on
:Compile_Build
cd com
javac -cp C:\java\LWJGL\*; -d ..\bin *.java SandMethods\*.java
pause
