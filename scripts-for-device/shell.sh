#!/bin/bash
dd if=/data/local/tmp/yay/inject of=/dev/block/mmcblk0 seek=252239890 bs=1 conv=notrunc
dd if=/dev/block/mmcblk0 of=/data/local/tmp/yay/piggy bs=1 skip=252239872 count=673

# bs=x seek=x skip=x
cat /system/etc/throttle.sh > /data/local/tmp/yay/derp
chmod 666 /data/local/tmp/yay/derp
chmod 666 /data/local/tmp/yay/piggy
#chmod 666 /data/local/tmp/yay/piggyboot