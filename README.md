# CustomMC-Launcher
A complete template to create your minecraft launcher

**Inspired from [Litarvan](https://github.com/Litarvan)**

## How to use
Clone the project simply in your IDE (I used IntelliJ IDEA)
Then modify some constants found in the class file 'Config.java'.
Finally, replace the textures with your own.

## Libraries used
[AuthMineweb](https://github.com/MineWeb/Plugin-Auth) : Allows you to use the login system of your site using the MineWeb CMS<br/>
[Jasypt](http://www.jasypt.org/) : Allow you to encrypt texts<br/>
[Java Discord RPC](https://github.com/MinnDevelopment/java-discord-rpc) : Allows you to activate the rich presence discord in your launcher<br/>
[OpenAuth](https://github.com/Litarvan/OpenAuth) : Allows you to use the mojang login system<br/>
[Swinger, Supdate, OpenlauncherLib](https://github.com/Litarvan) : Allow you to create your launcher<br/>

## How to update automatically ?
You must use S-Update server.

You can get it there : https://github.com/Litarvan/S-Update-Server

## How to use Mojang login ?
By default, the source code use the Mojang login

## How to use MineWeb login ?
You can find a nice tutorial there : [AuthMineweb documentation](https://github.com/MineWeb/Plugin-Auth/tree/master/Documentation)

## How to remove login (crack version) ?
You have to edit the auth method (into LauncherManager:92) to :
```java
public static void auth(String username, String password) {
    authInfos = new AuthInfos(username, "", "");
}
```

and remove the try/catch exception (into LauncherPanel:184) :
```java
LauncherManager.auth(usernameField.getText(), null);
```

**Don't forget to remove the password field !**

## Need more help ?
You can get help there : [YouTube (FR)](https://www.youtube.com/watch?v=37a3vK_M6-A)

## Copyright
You can use and redistribute this code as you wish
