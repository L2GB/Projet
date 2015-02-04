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
		PowerPlug(ZWaveController *_zwaveController, int _deviceId, int _instanceNum, const std::string _name, Week *_week, bool _connected, bool _unknown);
		PowerPlug(ZWaveController *_zwaveController, int _deviceId, int _instanceNum, const std::string _name);
		virtual ~PowerPlug();

		void switchON();
		void switchOFF();
		json_t *json_object();

	private:
		void run();
		void init();
		void checkTime();
		PowerPlug_level getScheduledLevel();

	private:
		PowerPlug_level m_level;
		PowerPlug_state m_state;
};

#endif /* OBJECT_POWERPLUG_H_ */
