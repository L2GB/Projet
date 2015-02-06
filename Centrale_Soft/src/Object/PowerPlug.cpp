/*
 * PowerPlug.cpp
 *
 *  Created on: 21 janv. 2015
 *      Author: menard
 */

// Internal include
#include "PowerPlug.h"

// External include
#include "unistd.h"

PowerPlug::PowerPlug(ZWaveController *_zwaveController, int _deviceId, int _instanceNum, const std::string _name, Week *_week, bool _connected, bool _unknown) : Object(_zwaveController, _deviceId, _instanceNum, _name, _week, _connected, _unknown), m_state(POWERPLUG_INIT)
{
}

PowerPlug::PowerPlug(ZWaveController *_zwaveController, int _deviceId, int _instanceNum, const std::string _name) : Object(_zwaveController, _deviceId, _instanceNum, _name), m_level(OFF), m_state(POWERPLUG_INIT)
{
}

PowerPlug::~PowerPlug()
{
}

void PowerPlug::print()
{
	std::cout << std::endl;
	std::cout << "HEATER" << std::endl;
	std::cout << "Name : " << m_name << std::endl;
	std::cout << "Type : " << m_type << std::endl;
	std::cout << "Connecté : " << m_connected << std::endl;
	std::cout << "Inconnu : " << m_unknown << std::endl;
	std::cout << "Allumé : " << m_level << std::endl;
	m_planning->print();
	std::cout << std::endl;
}

json_t *PowerPlug::json_transform_object()
{
	json_t *object = json_object();
	json_object_set(object, "typeObjet", json_string(m_type.c_str()));
	json_object_set(object, "nomObjet", json_string(m_name.c_str()));
	json_object_set(object, "planning", json_string(m_planning->getName().c_str()));
	json_object_set(object, "instanceNum", json_integer(m_instanceNum));
	json_object_set(object, "deviceId", json_integer(m_deviceId));
	json_object_set(object, "inconnu", json_boolean(m_unknown));
	json_object_set(object, "connecte", json_boolean(m_connected));
	if(m_level == ON)
		json_object_set(object, "allume", json_boolean(true));
	else
		json_object_set(object, "allume", json_boolean(false));

	return object;
}

void PowerPlug::init()
{
	// TODO when Kilian will have done his job
	//m_connected = m_zwaveController->zNeztwork_is_device_connected(m_deviceId, m_instanceNum);

	getCurrentTime();
	if(getScheduledLevel() == ON)
	{
		switchON();
	}
	else
	{
		switchOFF();
	}
}

void PowerPlug::run()
{
	while(isRunning())
	{
		switch(m_state)
		{
			case POWERPLUG_INIT:
				init();
				m_state = POWERPLUG_RUNNING;
				break;
			case POWERPLUG_RUNNING:
				getCurrentTime();
				checkTime();
				sleep(59);
				break;
		}
	}
}

void PowerPlug::checkTime()
{
	std::vector<TimeSlot> ts = m_planning->getDays()[m_time->tm_wday]->getTimeSlot();

	for(std::size_t i = 0 ; i < ts.size() ; i++)
	{
		if(ts[i].getStart().tm_hour == m_time->tm_hour
		&& ts[i].getStart().tm_min == m_time->tm_min)
		{
			switchON();
		}

		if(ts[i].getEnd().tm_hour == m_time->tm_hour
		&& ts[i].getEnd().tm_min == m_time->tm_min)
		{
			switchOFF();
		}
	}
}

PowerPlug_level PowerPlug::getScheduledLevel()
{
	std::vector<TimeSlot> ts = m_planning->getDays()[m_time->tm_wday]->getTimeSlot();

	for(std::size_t i = 0 ; i < ts.size() ; i++)
	{
		if(ts[i].getStart().tm_hour >= m_time->tm_hour
		&& ts[i].getStart().tm_min >= m_time->tm_min
		&&ts[i].getEnd().tm_hour <= m_time->tm_hour
		&& ts[i].getEnd().tm_min <= m_time->tm_min)
		{
			if(ts[i].getEnd().tm_hour == m_time->tm_hour
			&& ts[i].getEnd().tm_min == m_time->tm_min)
			{
				return OFF;
			}
			else
			{
				return ON;
			}
		}
		else
		{
			return OFF;
		}
	}
	return OFF;
}

void PowerPlug::switchON(){
	m_zwaveController->basic_set(this->m_deviceId, this->m_instanceNum, 1);
}

void PowerPlug::switchOFF()
{
	m_zwaveController->basic_set(this->m_deviceId, this->m_instanceNum, 0);
}

bool PowerPlug::getLevel(){

	bool value(m_zwaveController->zNetwork_get_boolean(this->m_deviceId, m_instanceNum, 37, "level"));

	if(value == true){
		m_level = ON;
	}
	else{
		m_level = OFF;
	}
	return value;
}

