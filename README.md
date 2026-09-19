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


