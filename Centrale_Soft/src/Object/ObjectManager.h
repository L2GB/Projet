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

// External include
#include <map>

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

		void setDay(json_t *_day);
		void setWeek(json_t *_week);
		void setObject(json_t *_object);
		void loadDays(json_t *_days);
		void loadWeeks(json_t *_weeks);
		void loadObjects(json_t *_objects);
		Object *getObject(std::string _name);

	private:
		void initializeMapping();

	private:
		std::map<std::string, TypeObjet> m_typeObjet;
		std::vector<Object *> m_objects;
		PlanningManager m_planningManager;
};

#endif /* OBJECT_OBJECTMANAGER_H_ */
