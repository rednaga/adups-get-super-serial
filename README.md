# adups-get-super-serial
CVE-2015-2231 Proof of Concept

The POC I was using to demonstrate [CVE-2015-2231 'Get Super Serial'](https://github.com/rednaga/disclosures/blob/master/GetSuperSerial.md). Was asked by a few people to post it so they could use similar things on other ADUPS firmware based devices which have this vulnerability. Cleaning up the laptop and posting this before it gets lost.

Usage
=====
This appliciation only creates a button which can be clicked, which would execute `sh /data/local/tmp/yay/shell.sh` as `system`. In the directory ["scripts-for-device"](https://github.com/rednaga/adups-get-super-serial/tree/master/scripts-for-device) are the actual scripts I used, however not all the binaries are available. You can get these from the supersu website. You must also create a world readable/writable directory of `/data/local/tmp/yay/` and place the necessary files in there.

Warning
=======
This is merely a proof of concept and has [hardcoded values](https://github.com/rednaga/adups-get-super-serial/blob/master/scripts-for-device/shell.sh) that will likely only work for a very specific Blu phone I was testing on. Do not attempt to run this on a device without understanding what is going on and modifying it for your purposes. Otherwise you will likely brick your device.

License
=======
    The MIT License (MIT)
    
    Copyright (c) 2015 Red Naga
    
    Permission is hereby granted, free of charge, to any person obtaining a copy
    of this software and associated documentation files (the "Software"), to deal
    in the Software without restriction, including without limitation the rights
    to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
    copies of the Software, and to permit persons to whom the Software is
    furnished to do so, subject to the following conditions:
    
    The above copyright notice and this permission notice shall be included in all
    copies or substantial portions of the Software.

    THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
    IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
    FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
    AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
    LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
    OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
    SOFTWARE.
