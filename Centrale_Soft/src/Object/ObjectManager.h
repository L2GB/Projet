/*
 * ObjectManager.h
 *
 *  Created on: 7 janv. 2015
 *      Author: menard
 */

#ifndef OBJECT_OBJECTMANAGER_H_
#define OBJECT_OBJECTMANAGER_H_

// Internal include
#include "../communication/Communicator.h"
#include "../tools/json/jansson.h"
#include "../planning/PlanningManager.h"
#include "../Object/Object.h"
#include "Room.h"
#include "../communication/zwave/ZWaveController.h"

// External include
#include <map>
#include <iostream>

//TODO complete the list
/**
 * Object type
 */
enum  TypeObjet{
		PRISE,
		CHAUFFAGE
};

class ObjectManager : public Communicator
{
	public:
		ObjectManager();
		virtual ~ObjectManager(){}

		void addObjectToRoom(json_t *_room);
		void setDay(json_t *_day);
		void setWeek(json_t *_week);
		void setObject(json_t *_object);
		void loadDays(json_t *_days);
		void loadWeeks(json_t *_weeks);
		void loadObjects(json_t *_objects);
		void loadRooms(json_t *_rooms);
		Object *getObject(int _deviceId, int _instanceNum);
		Room *getRoom(std::string _name);
		void powerPlug_switchON(std::string _name);
		void powerPlug_switchOFF(std::string _name);

	private:
		void initializeMapping();

	private:
		std::map<std::string, TypeObjet> m_typeObjet;
		std::vector<Object *> m_objects;
		std::vector<Room *> m_rooms;
		PlanningManager m_planningManager;
		ZWaveController m_zwaveController;
};

#endif /* OBJECT_OBJECTMANAGER_H_ */
