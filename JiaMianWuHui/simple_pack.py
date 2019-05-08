# coding=utf-8
import os
import sys

reload(sys)

bundle_path = "app/src/main/assets/index.android.bundle"
bundle_meta_path = "app/src/main/assets/index.android.bundle.meta"
bundle_assets_imgs = "app/src/main/res/"

apks_release_dir = "app/build/outputs/apk/jmwh/release/"
apk_name="app-release.apk"

print "脚本名：", sys.argv[0]

_clean = False; #清除资源
_jenkins = False; #是否执行jenkins
_install = False; #是否执行安装

_api_mode="DEV";


#遍历参数，如果参数带dev就是dev，否则打release
# for i in range(1, len(sys.argv)):
#     if (sys.argv[i].find("dev") != -1):
#         _dev = "true";

#遍历参数，如果参数带release就是release，否则打dev
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

# 删除资源图片
def removeFilesStartsWith(dir,str):
    if os.path.exists(dir):
        fileNames = os.listdir(dir);
        for name in fileNames:
            path = os.path.join(dir,name)
            if os.path.isfile(path) and name.find(str)!=-1:
                os.remove(path)
            if os.path.isdir(path):
                removeFilesStartsWith(path,str)

removeFilesStartsWith(bundle_assets_imgs,"app_assets_imgs_")
removeFilesStartsWith(bundle_assets_imgs,"node_modules_")
removeFilesStartsWith(apks_release_dir,"app-")

# 删除bundle
if os.path.exists(bundle_path):
    os.remove(bundle_path)
if os.path.exists(bundle_meta_path):
    os.remove(bundle_meta_path)

print "清理bundle文件和资源图片"

assets_dir="app/src/main/assets"

if not os.path.exists(assets_dir):
    os.makedirs(assets_dir)

#打包bundle
bundleOrder = "react-native bundle --platform android --dev false --reset-cache --entry-file index.android.js --bundle-output app/src/main/assets/index.android.bundle --assets-dest "+bundle_assets_imgs

# clean 情况下不用打包bundle

if _clean==False:
    print '打包资源bundle', bundleOrder;
    os.system(bundleOrder);


# clean 情况下直接退出打包流程
if _clean==True:
    sys.exit(0)
# jenkins 打包时不用执行gradle
if _jenkins==True:
    sys.exit(0)

# =================执行打包流程====================

# gradle 打包脚本
print 'gradleOrder', gradleOrder;

# 安装脚本
maybeinstall="adb install -r "+apks_release_dir.replace("","")+apk_name

# 创建android打包的python命令文件,位于
pack_path = "android_pack.py"
outFile = open(pack_path, "w");
outFile.write("# coding=utf-8\n");
outFile.write("import os\n");
outFile.write("os.system(\""+gradleOrder+"\");\n\n");

# 是否执行安装
if _install==True:
    outFile.write("print \'开始执行安装命令:"+maybeinstall+"\';\n");
    outFile.write("os.system(\""+maybeinstall+"\");\n")

outFile.close()
os.system("chmod 777 "+pack_path)
print "创建python打包APK脚本"+pack_path+"成功!\n开始执行...\n";


gradle_pack_path = "_gradle_pack.sh"
outFile = open(gradle_pack_path, "w");
outFile.write("#!/bin/sh\n");
outFile.write("cd JiaMianWuHui\n");
outFile.write("pwd\n");
outFile.write("python android_pack.py\n");
outFile.close()


os.system("chmod 777 "+gradle_pack_path)
os.system(". ./"+gradle_pack_path)




