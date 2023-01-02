WSA Bluetooth Proxy
This software aims to bring bluetooth support to WSA. 
It forwards all bluetooth requests to an app running on your host OS. It does not interact directly with your host OS or dongle, so it's pretty limited right now.
It could also theoretically be used to bring bluetooth support to any android emulator by building aosp with this code.

This software has not been tested extensively. This is just a proof-of-concept version. There may be security holes and bugs. Use this at your own risk.
Currently tested: Sennheiser Smart Control version 3.8.1 (Version 4.1.6 hangs, but not sure if it's a WSA or bluetooth issue) with PXC550.
This only works with RFCOMM devices currently. L2CAP protocols are not supported by the windows bluetooth driver.
Acting as a bluetooth server is also not implemented completely. The emulator can only connect as a client.

Requirements: 
- Windows 11 x64. Arm64 will not work
- Windows Subsystem for Linux (WSL) 2
- install ant unzip zip openjdk-17-jdk build-essential in WSL. If you choose to use WSAGAScript, it has additional dependencies.
- Clone the WSAGAScript to use apps with bluetooth. Most apps I found would not work without GApps support.

Modifying Code:
1) Run the build instructions once to populate the lib folder. No need to let it continue once it's done extracting the files.
2) Modify code in src and make or modify run.sh
3) To modify framework.jar or services.jar, examples are provided in run.sh and commented out

Build Instructions:
1) Build winbt.jar in the winbt folder
2) Create an empty folder named "wsa" in this directory
3) Download WSA - Instructions taken from WSAGAScript 
	Ensure that the version you download is Android 12. Android 13 will probably not work due to the new bluetooth module.
	a) Go to https://store.rg-adguard.net/
	b) Select ProductId
	c) Enter 9P3395VX91NR into the text box
	d) Select Fast
	e) Download the .msixbundle named: MicrosoftCorporationII.WindowsSubsystemForAndroid_2210.40000.7.0_neutral_~_8wekyb3d8bbwe.msixbundle
4) Extract the downloaded .msixbundle and find WsaPackage_2204.40000.20.0_x64_Release-Nightly.msix
5) Extract WsaPackage_2204.40000.20.0_x64_Release-Nightly.msix, rename the resulting folder "x64", and copy it into the wsa folder you created
6) Make a copy of this folder anywhere on your local drive. Network drives won't work.
7) Open a WSL shell in the main directory for this project
8) Go to your windows developer settings and allow installation of loose apps and files.
9) sudo --preserve-env=PATH ./run.sh <installLocation>
	a) <installLocation> is the folder you made in step 5
	b) Two administrator powershell prompts will open at the end of the script, you must run them both to automatically install WSA. Otherwise, you can manually run the commands to install them.

Run Instructions:
Before starting android, run winbt.jar. It will minimize itself to a tray icon.
I recommend making winbt.jar a startup program so that it is always running before you need bluetooth.

To add a bluetooth device (you currently have to do this every time you want to use a device):
1) Pair the device to your computer using the Windows settings and ensure winbt.jar is running.
2) Turn Advanced Networking on if possible. There is a fallback if not, but it may not work properly.
3) Enable bluetooth (either through an app or android settings)
	a) The devices will automatically be discovered and paired to the emulator. This may take some time and some apps will timeout.
	b) There may be no indication that the device connected, but it should be marked as paired

Using software: 
BlueCove from http://www.bluecove.org/
dex-tools-2.1 from https://github.com/pxb1988/dex2jar
Android source code and binaries from source.android.com and cs.android.com
Windows Subsystem for Android™️ from Microsoft https://learn.microsoft.com/en-us/windows/android/wsa/
WSAGAScript from https://github.com/WSA-Community/WSAGAScript
