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
		Object(std::string _name, Week *_planning, std::string _type, ZWaveController *_zwaveController, int _deviceId, int instanceNum);
		virtual ~Object();

		std::string getName(){return m_name;}
		std::string getType(){return m_type;}
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
		//--------------------------------------------------------
		char * m_initName;
		int m_deviceId;
		int m_instanceNum;
		std::vector<struct CommandClasse> m_commandClassesList;
		ZWaveController *m_zwaveController;
};

#endif /* OBJECT_OBJECT_H_ */
