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
				// TODO
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
	/*std::size_t sizeMax = m_planning->getDays()[m_time->tm_wday]->getTimeSlot().size();

	for(std::size_t i = 0 ; i < sizeMax ; i++)
	{
		TimeSlot *ts = m_planning->getDays()[m_time->tm_wday]->getTimeSlot()[i];
		if(ts->getStart().tm_hour == m_time->tm_hour)
		{
			if(ts->getStart().tm_min == m_time->tm_min)
			{
				switchON();
			}
		}

		if(ts->getEnd().tm_hour == m_time->tm_hour)
		{
			if(ts->getEnd().tm_min == m_time->tm_min)
			{
				switchOFF();
			}
		}
	}*/
}
