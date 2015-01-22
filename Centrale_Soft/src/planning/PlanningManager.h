/*
 * PlanningManager.h
 *
 *  Created on: 19 janv. 2015
 *      Author: menard
 */

#ifndef PLANNING_PLANNINGMANAGER_H_
#define PLANNING_PLANNINGMANAGER_H_

// Internal include
#include "Day.h"
#include "Week.h"
#include "TimeSlot.h"
#include "../tools/json/jansson.h"

// External include
#include <vector>

class PlanningManager
{
	public:
		PlanningManager();
		virtual ~PlanningManager();

		void day_set(std::string _name, std::vector<TimeSlot> _timeSlot);
		Day *day_get(const std::string _name);
		void day_del(const std::string _name);

		void week_set(const std::string _name, std::vector<Day*> _days);
		Week *week_get(std::string _name);
		void week_del(const std::string _name);

	private:
		std::vector<Day> m_days;
		std::vector<Week> m_weeks;
};

#endif /* PLANNING_PLANNINGMANAGER_H_ */
