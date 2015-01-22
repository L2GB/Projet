/*
 * PowerPlug.h
 *
 *  Created on: 21 janv. 2015
 *      Author: menard
 */

#ifndef OBJECT_POWERPLUG_H_
#define OBJECT_POWERPLUG_H_

// Internal include
#include "Object.h"

class PowerPlug : public Object
{
	public:
		PowerPlug(std::string _name, Week *_planning, std::string _type);
		virtual ~PowerPlug();
};

#endif /* OBJECT_POWERPLUG_H_ */
