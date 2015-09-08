#!/system/bin/sh
/system/bin/id > /data/local/tmp/bebop
/system/bin/mount -o remount,rw /system 
/system/bin/cp /data/local/tmp/su /system/bin/su
/system/bin/cp /data/local/tmp/su /system/bin/daemonsu
/system/bin/chmod 755 /system/bin/su
/system/bin/chmod 755 /system/bin/daemonsu
/system/bin/daemonsu --auto-daemon &
