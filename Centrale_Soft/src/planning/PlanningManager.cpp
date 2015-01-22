/*
 * PlanningManager.cpp
 *
 *  Created on: 19 janv. 2015
 *      Author: menard
 */

// Internal include
#include "PlanningManager.h"

PlanningManager::PlanningManager()
{
}

PlanningManager::~PlanningManager()
{
}

void PlanningManager::day_set(std::string _name, std::vector<TimeSlot> _timeSlot)
{
	bool trouve = false;
	for(std::size_t i = 0 ; i < m_days.size() ; i++)
	{
		if(!trouve)
		{
			if(m_days[i].getName().compare(_name.c_str()) == 0)
			{
				m_days[i].setTimeSlot(_timeSlot);
				trouve = true;
			}
		}
	}
	if(!trouve)
	{
		Day newDay(_name, _timeSlot);
		m_days.push_back(newDay);
	}
}

Day *PlanningManager::day_get(const std::string _name)
{
	for(std::size_t i = 0 ; i < m_days.size() ; i++)
	{
		if(m_days[i].getName().compare(_name.c_str()) == 0)
		{
			return &m_days[i];
		}
	}
	return NULL;
}

void day_del(const std::string _name);

void PlanningManager::week_set(const std::string _name, std::vector<Day*> _days)
{
	bool trouve = false;
	for(std::size_t i = 0 ; i < m_weeks.size() ; i++)
	{
		if(!trouve)
		{
			if(m_weeks[i].getName().compare(_name.c_str()) == 0)
			{
				m_weeks[i].setDays(_days);
				trouve = true;
			}
		}
	}
	if(!trouve)
	{
		Week newWeek(_name, _days);
		m_weeks.push_back(newWeek);
	}
}


Week *PlanningManager::week_get(std::string _name)
{
	for(std::size_t i = 0 ; i < m_weeks.size() ; i++)
	{
		if(m_weeks[i].getName().compare(_name.c_str()) == 0)
		{
			return &m_weeks[i];
		}
	}
	return NULL;
}

void week_del(const std::string _name);

void planning_new(const std::string _name, Week _week);
void planning_del(const std::string _name);


