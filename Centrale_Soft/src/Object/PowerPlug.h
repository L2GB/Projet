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

enum PowerPlug_level
{
	ON,
	OFF
};

enum PowerPlug_state
{
	POWERPLUG_INIT,
	POWERPLUG_RUNNING,
};


class PowerPlug : public Object
{
	public:
		PowerPlug(ZWaveController *_zwaveController, int _deviceId, int _instanceNum);
		virtual ~PowerPlug();
		void switchON();
		void switchOFF();

	private:
		void run();
		void checkLevel();

	private:
		PowerPlug_level m_level;
		PowerPlug_state m_state;
};

#endif /* OBJECT_POWERPLUG_H_ */
