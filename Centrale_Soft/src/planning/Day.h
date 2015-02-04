/*
 * Day.h
 *
 *  Created on: 19 janv. 2015
 *      Author: menard
 */

#ifndef PLANNING_DAY_H_
#define PLANNING_DAY_H_

// External include
#include <vector>
#include <iostream>

// Internal include
#include "TimeSlot.h"

class Day
{
	public:
		Day(std::string _name, std::vector<TimeSlot> _timeSlot);
		virtual ~Day();
		std::string getName() { return m_name; }
		void setName(std::string _name) { m_name = _name; }
		void setTimeSlot(std::vector<TimeSlot> _timeSlot) { m_timeSlot = _timeSlot; }
		std::vector<TimeSlot> getTimeSlot(){return m_timeSlot;}

	private:
		std::string m_name;
		std::vector<TimeSlot> m_timeSlot;
};

#endif /* PLANNING_DAY_H_ */
