@Echo off

setlocal

@REM Save current directory
set OLDDIR=%CD%

@REM Set the current directory to the installation directory
chdir /d %~dp0

@REM Use JAVA_HOME if it is set
if "%JAVA_HOME%"=="" (
 set JAVA_CMD=java
) else (
 set JAVA_CMD="%JAVA_HOME%\bin\java.exe"
)

%JAVA_CMD% -jar confman-command-${project.version}.jar

@REM Save the exit code
set JAVA_EXIT_CODE=%ERRORLEVEL%

@REM Restore current directory
chdir /d %OLDDIR%

@REM Exit using the same code returned from Java
EXIT /B %JAVA_EXIT_CODE%