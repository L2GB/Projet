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

void PowerPlug::init()
{
	// TODO when Kilian will have done his job
	//m_connected = m_zwaveController->   IS CONNECTED?

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
				sleep(1);
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
