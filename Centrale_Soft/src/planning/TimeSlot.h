/*
 * TimeSlot.h
 *
 *  Created on: 19 janv. 2015
 *      Author: menard
 */

#ifndef PLANNING_TIMESLOT_H_
#define PLANNING_TIMESLOT_H_

// External include
#include <ctime>

class TimeSlot
{
	public:
		TimeSlot(bool m_permission, struct tm m_start, struct tm m_end);
		virtual ~TimeSlot();
		struct tm getStart(){return m_start;}

	private:
		bool m_permission;
		struct tm m_start;
		struct tm m_end;
};

#endif /* PLANNING_TIMESLOT_H_ */
