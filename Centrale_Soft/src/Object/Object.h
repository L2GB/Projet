/*
 * Object.h
 *
 *  Created on: 4 déc. 2014
 *      Author: mil
 */

#ifndef OBJECT_OBJECT_H_
#define OBJECT_OBJECT_H_

// Internal include
#include "../planning/Week.h"

class Object
{
	public:
		Object(std::string _name, Week *_planning, std::string _type);
		virtual ~Object();

		std::string getName(){return m_name;}
		std::string getType(){return m_type;}
		Week *getPlanning(){return m_planning;}
		void setPlanning(Week *_planning){m_planning = _planning;}

	private:
		std::string m_name;
		Week *m_planning;
		std::string m_type;
};

#endif /* OBJECT_OBJECT_H_ */
