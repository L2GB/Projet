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

		void zwave_startNetwork();
		void init_objects();
		void addObjectToRoom(json_t *_room);
		void addObjectToList(int _instanceNum, int _deviceId, const std::string _name, const std::string _typeObject);
		void setDay(json_t *_day);
		void setWeek(json_t *_week);
		void setObject(json_t *_object);
		void loadDays(json_t *_days);
		void loadWeeks(json_t *_weeks);
		void newInstanceObject(json_t *object);
		void loadObjects(json_t *_objects);
		void loadRooms(json_t *_rooms);
		Object *getObject(int _deviceId, int _instanceNum);
		Room *getRoom(std::string _name);
		void notifyObjectChanges(Object *_object);
		Object *getObjectToConfigure(){ return m_objectToConfigure;}

		/*
		 * Liste des commandes vers les objets.
		 */
		void mode_inclusion();
		void powerPlug_switchON(int _deviceId, int _instanceNum);
		void powerPlug_switchOFF(int _deviceId, int _instanceNum);

	private:
		void initializeMapping();
		void init_callback2(Object *_object);

	private:
		std::map<std::string, TypeObjet> m_typeObjet;
		std::vector<Object *> m_objects;
		std::vector<Room *> m_rooms;
		PlanningManager m_planningManager;
		ZWaveController m_zwaveController;
		Object *m_objectToConfigure;
};

#endif /* OBJECT_OBJECTMANAGER_H_ */
