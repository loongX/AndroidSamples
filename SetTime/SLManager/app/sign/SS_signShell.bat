cd ../release

java -jar ../sign/signapk.jar ../sign/platform.x509.pem ../sign/platform.pk8 app-release.apk  app-release-sign.apk
adb uninstall com.slzr.slmanager
adb install -r app-release-sign.apk

pause