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

PowerPlug::PowerPlug(std::string _name, Week *_planning, std::string _type) : Object(_name, _planning, _type), m_level(OFF), m_state(POWERPLUG_INIT)
{
}

PowerPlug::~PowerPlug()
{
}

void PowerPlug::run()
{
	while(isRunning())
	{
		switch(m_state)
		{
			case POWERPLUG_INIT:
				// TODO par kilian
				m_state = POWERPLUG_RUNNING;
				break;
			case POWERPLUG_RUNNING:
				getCurrentTime();
				checkLevel();
				sleep(1);
				break;
		}
	}
}

void PowerPlug::checkLevel()
{
	std::vector<TimeSlot> ts = m_planning->getDays()[m_time->tm_wday]->getTimeSlot();

	for(std::size_t i = 0 ; i < ts.size() ; i++)
	{
		if(ts[i].getStart().tm_hour == m_time->tm_hour)
		{
			if(ts[i].getStart().tm_min == m_time->tm_min)
			{
				switchON();
			}
		}

		if(ts[i].getEnd().tm_hour == m_time->tm_hour)
		{
			if(ts[i].getEnd().tm_min == m_time->tm_min)
			{
				switchOFF();
			}
		}
	}
}


void PowerPlug::switchON(){
	ZWaveController::basic_set(this->m_deviceId, this->m_instanceNum, 1);
}

void PowerPlug::switchOFF()
{
// TODO connect with kilian
}
