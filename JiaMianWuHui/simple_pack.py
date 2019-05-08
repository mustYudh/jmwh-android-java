# coding=utf-8
import os
import sys

reload(sys)


apks_release_dir = "app/build/outputs/apk/jmwh/release/"
apk_name="app-release.apk"

print "脚本名：", sys.argv[0]

_clean = False;
_jenkins = False;
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
    if (sys.argv[i]=="jenkins"):
        _jenkins = True;

gradleOrder="./gradlew clean assembleJmwhRelease -PAPI_MODE="+_api_mode;

print "环境参数 ", " api_mode=",_api_mode, " clean=",_clean, "install=",_install,"jenkins=",_jenkins


def removeFilesStartsWith(dir,str):
    if os.path.exists(dir):
        fileNames = os.listdir(dir);
        for name in fileNames:
            path = os.path.join(dir,name)
            if os.path.isfile(path) and name.find(str)!=-1:
                os.remove(path)
            if os.path.isdir(path):
                removeFilesStartsWith(path,str)

removeFilesStartsWith(apks_release_dir,"app-")



if _clean==True:
    sys.exit(0)

if _jenkins==True:
    sys.exit(0)




print 'gradleOrder', gradleOrder;


maybeinstall="adb install -r "+apks_release_dir +apk_name


pack_path = "android_pack.py"
outFile = open(pack_path, "w");
outFile.write("# coding=utf-8\n");
outFile.write("import os\n");
outFile.write("os.system(\""+gradleOrder+"\");\n\n");


if _install==True:
    outFile.write("print \'开始执行安装命令:"+maybeinstall+"\';\n");
    outFile.write("os.system(\""+maybeinstall+"\");\n")

outFile.close()
os.system("chmod 777 "+pack_path)
print "创建python打包APK脚本"+pack_path+"成功!\n开始执行...\n";


gradle_pack_path = "_gradle_pack.sh"
outFile = open(gradle_pack_path, "w");
outFile.write("#!/bin/sh\n");
outFile.write("pwd\n");
outFile.write("python android_pack.py\n");
outFile.close()


os.system("chmod 777 "+gradle_pack_path)
os.system(". ./"+gradle_pack_path)




