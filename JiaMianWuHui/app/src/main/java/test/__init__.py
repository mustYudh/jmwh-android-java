import os
import sys

reload(sys)


apks_release_dir = "app/build/outputs/apk/jmwh/release/"
apk_name="app-release.apk"



_clean = False;
_install = False;

_api_mode="DEV";



for i in range(1, len(sys.argv)):
    if (sys.argv[i]=="release"):
        _api_mode="ONLINE";
    if (sys.argv[i]=="pre"):
        _api_mode="PRE_RELEASE"
    if (sys.argv[i]=="clean"):
        _clean = True;
    if (sys.argv[i]=="install"):
        _install = True;

gradleOrder="./gradlew clean assembleJmwhRelease -PAPI_MODE="+_api_mode;

print gradleOrder



sys.exit(0)


pack_path = "android_pack1.py"
outFile = open(pack_path, "w");
