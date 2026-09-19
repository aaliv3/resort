## README
Remote Backup

### How to run

Requirements
 - Oracle GraalVM 25
 - SDKMAN 
```
curl -s "https://get.sdkman.io" | bash

source "$HOME/.sdkman/bin/sdkman-init.sh"

sdk install java 25-graal

java -version ## Should report GraalVM

java ./com/resort/MtBullerAdmin.java ## main method 

```

### Compile to native binary
Requirements
```
gu install native-image ## should be already shipped with graalVM just incase
```
C tool chain
```
sudo pacman -S base-devel zlib 
## or what ever is your package manager
```
Package into jar archive
```
mkdir -p build
javac -d build com/resort/*.java
cd build 
jar --create --file ../MtBullerAdmin.jar --main.class=com.resort.MtBullerAdmin com/resort/*.class

native-image -jar MtBullerAdmin.jar --no-fallback -H:Name=mtbulleradmin -H:+ReportExceptionStackTraces
```



