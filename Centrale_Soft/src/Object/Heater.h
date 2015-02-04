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
		Heater(ZWaveController *_zwaveController, int _deviceId, int _instanceNum, const std::string _name, Week *_week, bool _connected, bool _unknown, int _Tconfort, int _Teco);
		Heater(ZWaveController *_zwaveController, int _deviceId, int _instanceNum, const std::string _name);
		virtual ~Heater();

		void setTconfort(int _tconfort){m_Tconfort = _tconfort;}
		void setTeco(int _teco){m_Teco = _teco;}
		int getTconfort(){return m_Tconfort;}
		int getTeco(){return m_Teco;}
		void print();
		json_t *json_object();

	private:
		void run();
		void init();
		void getCurrentTemp();
		void switchON();
		void switchOFF();

	private:
		Heater_state m_state;
		int m_currentTemp;
		int m_Tconfort;
		int m_Teco;
};

#endif /* OBJECT_HEATER_H_ */
