/*
 * Heater.cpp
 *
 *  Created on: 4 déc. 2014
 *      Author: CAILLOT Kilian
 */

// Internal include
#include "Heater.h"

// External include
#include <unistd.h>

Heater::Heater(ZWaveController *_zwaveController, int _deviceId, int _instanceNum, const std::string _name) : Object(_zwaveController, _deviceId, _instanceNum, _name), m_state(HEATER_INIT), m_Tconfort(0), m_Teco(0)
{
}

Heater::Heater(ZWaveController *_zwaveController, int _deviceId, int _instanceNum, const std::string _name, Week *_week, bool _connected, bool _unknown, int _Tconfort, int _Teco) : Object(_zwaveController, _deviceId, _instanceNum, _name, _week, _connected, _unknown), m_state(HEATER_INIT), m_Tconfort(_Tconfort), m_Teco(_Teco)
{
}

Heater::~Heater()
{
}

void Heater::run()
{
	while(isRunning())
	{
		switch(m_state)
		{
			case HEATER_INIT:
				// TODO trouver un copain thermostat dans la pièce
				m_state = HEATER_RUNNING;
				break;
			case HEATER_RUNNING:
				getCurrentTemp();
				getCurrentTime();

				sleep(1);
				break;
		}
	}
}


void Heater::switchON()
{

}

void Heater::switchOFF()
{

}

void Heater::getCurrentTemp()
{
	// TODO
}

void Heater::print()
{
	std::cout << std::endl;
	std::cout << "HEATER" << std::endl;
	std::cout << "Name : " << this->getName() << std::endl;
	std::cout << "Type : " << this->getType() << std::endl;
	std::cout << "Tconfort : " << m_Tconfort << std::endl;
	std::cout << "Teco : " << m_Teco << std::endl;
	this->getPlanning()->print();
	std::cout << std::endl;
}
