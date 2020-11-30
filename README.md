# ToIdleIsSin

To Run the new version Open the ToIdleIsSin folder in Android Studio.
Once it has finished uploading and syncing the gradle you can either run the app on a virtual or real device like you are used to, or you can set it up for testing on desktop after you use Texture Packer.

To do that click The android button right next to the green hammer and the drop down where you select the device to run it on.
Select Edit Configurations.
Click the plus sign and make a new Application

Change the Main Class to com.wearethreestudios.toidleissin.desktop.DesktopLauncher
Change the Working Directory to <otherfolders>/android/assets
Use classpath of module should be set to desktop
click ok. Now you can run the game in the desktop version.

Next use Texture Packer to get all the texture atlases, follow these steps:
1) In Android Studio, click Run >> Edit Configurations
2) click the plus and add "Application"
3) Name application.
4) Main Class should be "com.badlogic.gdx.tools.texturepacker.TexturePacker"
5) Program Arguments should be "../holdassets ../android/assets/atlas atlassprites"
6) Working Directory should be "actual\path\to\holdassets" 
7) Use classpath of module "ToIdleIsSin.desktop"

save the results and then run the application, it will pack up the textures in holdassets, and drop the texture atlasses in android/assets/atlas folder

Lastly run this command to link the submodules that go to the monero miner:
git submodule update --init --recursive
