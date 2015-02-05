//============================================================================
// Name        : Centrale_Soft.cpp
// Author      : Caillot Peruchette
// Version     :
// Copyright   : Your copyright notice
// Description : Hello World in C++, Ansi-style
//============================================================================

// External include
#include <iostream>
#include <unistd.h>

// Internal include
#include "communication/zwave/ZWaveController.h"
#include "communication/transmission/Transmitter.h"
#include "Object/PowerPlug.h"


int main()
{

//	Transmitter transmitter;

	ZWaveController zController;

	zController.print_data_tree();

	bool cc_holder_exist(false);
	bool device_holder(false);

	char answer('n');

	PowerPlug  powerP(&zController,5,0,"PRISE1");
	bool stateOn(false);

	while(answer != 'y'){

		if(stateOn){
			powerP.switchOFF();
			stateOn = false;
		}
		else{
			powerP.switchON();
			stateOn = true;
		}

		std::cout << std::endl;
		std::cout << "Which Command class of which instance of which device would you like to know the name ?" << std::endl;
		std::cout << "Device num : ";
		int deviceNumTest;
		std::cin >> deviceNumTest ;
		std::cin.ignore();

		std::cout << " => ";
		zController.printDeviceInfoShortVersion(deviceNumTest);

		std::cout << std::endl << "Instance : ";
		int instanceNumTest;
		std::cin >> instanceNumTest;
		std::cin.ignore();
		std::cout << std::endl << "Command class : ";
		int commandClassNumTest;
		std::cin >> commandClassNumTest ;
		std::cin.ignore();
		std::cout << "Quel est le nom de la data dont vous voulez vérifier l'existence d'un holder ? " << std::endl;
		std::cout <<"> ";
		std::string dataNameTest("no name");
		std::getline(std::cin,dataNameTest);


		if(zController.zNetwork_is_there_device_instance_cc_holder(deviceNumTest, instanceNumTest, commandClassNumTest, dataNameTest) == true){
			std::cout << " Il existe bien un holder " << std::endl;

			std::string holderType(zController.zNetwork_get_holder_value_type(deviceNumTest, instanceNumTest, commandClassNumTest, dataNameTest));
			std::cout << " Le holder contient une valeur de type " << holderType << std::endl;
			bool value(zController.zNetwork_get_boolean(deviceNumTest, instanceNumTest, commandClassNumTest, dataNameTest));
			std::cout << " Le holder contient la valeur : " << value << std::endl;
			std::string holderName(zController.zdata_get_holder_name(deviceNumTest, instanceNumTest, commandClassNumTest, dataNameTest));
			std::cout << "Le nom du holder est " << holderName << std::endl;

			std::cout << " Le holder correspondant au Device " << deviceNumTest << " ";

			std::cout << std::endl << " Quel champ du deviceHolder voulez vous intéroger ? > ";
			std::string dataDeviceName("noValueYet");
			std::getline(std::cin,dataDeviceName);

			if(zController.zNetwork_is_there_device_holder(deviceNumTest,dataDeviceName)){
				std::cout << " Il existe bien un holder " << std::endl;
				std::string holderDeviceType(zController.zNetwork_get_holder_value_type(deviceNumTest, 0, 0, dataDeviceName));
				std::cout << " Le holder contient une valeur de type " << holderDeviceType << std::endl;
				bool value(zController.zNetwork_get_boolean(deviceNumTest,0,0,dataDeviceName));
				std::cout << " La valeur du holder est ";
			}
		}
		else{
			std::cout << " Il n'existe pas d'holder " << std::endl;
		}
		powerP.switchOFF();
		std::cout << std::endl << " Do you want to quit ? > ";

		std::cin >> answer;
		std::cin.ignore();

	}

	return 0;

	while(1)
	{
		sleep(1);
	}

	return 0;
}
