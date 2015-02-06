Développement sur la raspberry pi B+.

- Récupération de la cross tool chain
	-> https://github.com/raspberrypi/tools
-> La placer dans le dossier /opt 

- Récupération du projet (git sur redmine)


* Sur le projet dans eclipse:

-> Clique droit sur le projet
-> Properties
-> Dans l'onglet [C/C++ Build] / [Environment]
	-> "Add" (Name : PATH, Value : /opt/tools/arm-bcm2708/gcc-linaro-arm-linux-gnueabihf-raspbian-x64/bin:/usr/sbin:/sbin:/bin:/usr/games:/usr/local/sbin:/usr/local/bin:/usr/bin:/usr/lib/jvm/default/bin:/usr/bin/site_perl:/usr/bin/vendor_perl:/usr/bin/core_perl:/opt/tools/arm-bcm2708/gcc-linaro-arm-linux-gnueabihf-raspbian/bin)
-> Dans l'onglet [C/C++ Build] / [Settings] / [Tool Settings] /
[Cross Settings] : Prefix : arm-linux-gnueabihf-
				   Path : /opt/tools/arm-bcm2708/gcc-linaro-arm-linux-gnueabihf-raspbian-x64/bin

-> Dans l'onglet [C/C++ Build] / [Settings] / [Tool Settings] / 
[Cross G++ Linker] : All options : -L"/path/to/libzway/folder"
					 

-> Dans l'onglet [C/C++ Build] / [Settings] / [Tool Settings] / [Cross G++ Linker] /
[Libraries] : Libraries (-l) : z, zway, zcommons, zwayjs, zenocean, xml2, archive, v8, ssl, crypto, pthread

-> Dans l'onglet [C/C++ Build] / [Settings] / [Tool Settings] / [Cross G++ Linker] /
[Libraries] : Library search path (-L) : /path/to/libzway