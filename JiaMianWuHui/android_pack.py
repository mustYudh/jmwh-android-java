# coding=utf-8
import os
os.system("./gradlew clean assembleJmwhRelease -PAPI_MODE=ONLINE");

print '开始执行安装命令:adb install -r app/build/outputs/apk/jmwh/release/app-release.apk';
os.system("adb install -r app/build/outputs/apk/jmwh/release/app-release.apk");
