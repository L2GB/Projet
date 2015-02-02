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
#include "../tools/thread/Thread.h"
#include "../communication/zwave/ZWaveController.h"

enum State
{
	INIT,
	RUNNING,
};
//-------------------------------------
struct CommandClasse{
	int commandClasseId;
	std::string description;
};


class Object : public Thread
{
	public:
		Object(ZWaveController *_zwaveController, int _deviceId, int _instanceNum);
		virtual ~Object();

		int getDeviceId(){return m_deviceId;}
		int getInstanceNum(){return m_instanceNum;}
		bool isConnected(){return m_connected;}
		bool isUnknown(){return m_unknown;}
		std::string getName(){return m_name;}
		std::string getType(){return m_type;}
		void setName(const std::string _name){m_name = _name;}
		Week *getPlanning(){return m_planning;}
		void setPlanning(Week *_planning){m_planning = _planning;}
		//--------------------------------------------------------
		void automatic_initName_setting();
		//std::string guessType();

	protected:
		virtual void run() = 0;
		void getCurrentTime();

	protected:
		struct tm *m_time;
		std::string m_name;
		Week *m_planning;
		std::string m_type;
		ZWaveController *m_zwaveController;
		int m_deviceId;
		int m_instanceNum;
		//--------------------------------------------------------
		char * m_initName;
		bool m_connected;
		bool m_unknown;
		std::vector<struct CommandClasse> m_commandClassesList;
};

#endif /* OBJECT_OBJECT_H_ */
