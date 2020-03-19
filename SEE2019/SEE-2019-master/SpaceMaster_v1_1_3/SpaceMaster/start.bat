@ECHO OFF

for /f tokens^=2-5^ delims^=.-_^" %%j in ('java -fullversion 2^>^&1') do set "jver=%%j"

Set CP=%PRTI1516E_HOME%/lib/prticore.jar;./SpaceMaster.jar;

IF "%jver%"=="10" (
java --add-modules java.xml.bind -cp "%CP%" se.pitch.spacemaster.Main
) ELSE (
java -cp "%CP%" se.pitch.spacemaster.Main
)

pause