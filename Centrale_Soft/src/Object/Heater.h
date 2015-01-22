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

class Heater : public Object
{
	public:
		Heater(std::string _name, Week *_planning, std::string _type, int _Tconfort, int _Teco);
		virtual ~Heater();
		void setTconfort(int _tconfort){m_Tconfort = _tconfort;}
		void setTeco(int _teco){m_Teco = _teco;}
		int getTconfort(){return m_Tconfort;}
		int getTeco(){return m_Teco;}
		void print();

	private:
		int m_Tconfort;
		int m_Teco;
};

#endif /* OBJECT_HEATER_H_ */
