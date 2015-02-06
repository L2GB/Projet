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

		/**
		 * Print details about the object.
		 */
		void print();

		/**
		 * Switch on the power plug.
		 */
		void switchON();

		/**
		 * Switch off the power pluf
		 */
		void switchOFF();

		/**
		 * Update the level.
		 */
		bool getLevel();

		/**
		 * Transform parameters in a json message
		 */
		json_t *json_transform_object();

	private:
		void run();
		void init();

		/**
		 * Compare with the planning if the power plug need to be switched.
		 */
		void checkTime();

		PowerPlug_level getScheduledLevel();

	private:
		PowerPlug_level m_level;
		PowerPlug_state m_state;
};

#endif /* OBJECT_POWERPLUG_H_ */
