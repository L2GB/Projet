/*
 * Week.h
 *
 *  Created on: 19 janv. 2015
 *      Author: menard
 */

#ifndef PLANNING_WEEK_H_
#define PLANNING_WEEK_H_

// Internal include
#include <vector>
#include "Day.h"

class Week
{
	public:
		Week(std::string _name, std::vector<Day*> _days);
		virtual ~Week();

		std::string getName() { return m_name; }
		void setDays(std::vector<Day*> _days) { m_days = _days; }
		std::vector<Day*> getDays(){return m_days;}

	private:
		std::string m_name;
		std::vector<Day*> m_days;
};

#endif /* PLANNING_WEEK_H_ */
