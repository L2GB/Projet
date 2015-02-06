/*
 * Heater.h
 *
 *  Created on: 4 déc. 2014
 *      Author: CAILLOT Kilian
 */

#ifndef OBJECT_HEATER_H_
#define OBJECT_HEATER_H_

// Internal include
#include "Object.h"

enum Heater_state
{
	HEATER_INIT,
	HEATER_RUNNING,
};

class Heater : public Object
{
	public:
		/**
		 * Constructor for the loaded objects.
		 */
		Heater(ZWaveController *_zwaveController, int _deviceId, int _instanceNum, const std::string _name, Week *_week, bool _connected, bool _unknown, int _Tconfort, int _Teco);

		/**
		 * Constructor for the new Objects added to the network.
		 */
		Heater(ZWaveController *_zwaveController, int _deviceId, int _instanceNum, const std::string _name);
		virtual ~Heater();

		void setTconfort(int _tconfort){m_Tconfort = _tconfort;}
		void setTeco(int _teco){m_Teco = _teco;}
		int getTconfort(){return m_Tconfort;}
		int getTeco(){return m_Teco;}
		int getAllume(){return m_allume;}

		/**
		 * Print details about the object.
		 */
		void print();

		/**
		 * Transform parameters in a json message
		 */
		json_t *json_transform_object();

	private:
		void run();
		void init();

		/**
		 * Get the current temperature.
		 */
		void getCurrentTemp();

		void switchON();

		void switchOFF();

	private:
		Heater_state m_state;
		int m_currentTemp;
		bool m_allume;
		int m_Tconfort;
		int m_Teco;
};

#endif /* OBJECT_HEATER_H_ */
