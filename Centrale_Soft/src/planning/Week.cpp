/*
 * Week.cpp
 *
 *  Created on: 19 janv. 2015
 *      Author: menard
 */

// Internal include
#include "Week.h"

// External include
#include <iostream>

Week::Week(std::string _name, std::vector<Day*> _days) : m_name(_name), m_days(_days)
{
}

Week::~Week()
{
}

void Week::print()
{
	std::cout << "Planning : " << m_name << std::endl;
	std::cout << "Lundi : " << m_days[0]->getName() << std::endl;
	for(std::size_t i = 0 ; i < m_days[0]->getTimeSlot().size() ; i++)
	{
		std::cout << "\tCreneau " << i << " : " << "debut : " << m_days[0]->getTimeSlot()[i].getStart().tm_hour << "h" << m_days[0]->getTimeSlot()[i].getStart().tm_min << ", fin : "<< m_days[0]->getTimeSlot()[i].getEnd().tm_hour << "h" << m_days[0]->getTimeSlot()[i].getEnd().tm_min << std::endl;
	}
	std::cout << "Mardi : " << m_days[1]->getName() << std::endl;
	for(std::size_t i = 0 ; i < m_days[1]->getTimeSlot().size() ; i++)
	{
		std::cout << "\tCreneau " << i << " : " << "debut : " << m_days[1]->getTimeSlot()[i].getStart().tm_hour << "h" << m_days[1]->getTimeSlot()[i].getStart().tm_min << ", fin : "<< m_days[1]->getTimeSlot()[i].getEnd().tm_hour << "h" << m_days[1]->getTimeSlot()[i].getEnd().tm_min << std::endl;
	}
	std::cout << "Mercredi : " << m_days[2]->getName() << std::endl;
	for(std::size_t i = 0 ; i < m_days[2]->getTimeSlot().size() ; i++)
	{
		std::cout << "\tCreneau " << i << " : " << "debut : " << m_days[2]->getTimeSlot()[i].getStart().tm_hour << "h" << m_days[2]->getTimeSlot()[i].getStart().tm_min << ", fin : "<< m_days[2]->getTimeSlot()[i].getEnd().tm_hour << "h" << m_days[2]->getTimeSlot()[i].getEnd().tm_min << std::endl;
	}
	std::cout << "Jeudi : " << m_days[3]->getName() << std::endl;
	for(std::size_t i = 0 ; i < m_days[3]->getTimeSlot().size() ; i++)
	{
		std::cout << "\tCreneau " << i << " : " << "debut : " << m_days[3]->getTimeSlot()[i].getStart().tm_hour << "h" << m_days[3]->getTimeSlot()[i].getStart().tm_min << ", fin : "<< m_days[3]->getTimeSlot()[i].getEnd().tm_hour << "h" << m_days[3]->getTimeSlot()[i].getEnd().tm_min << std::endl;
	}
	std::cout << "Vendredi : " << m_days[4]->getName() << std::endl;
	for(std::size_t i = 0 ; i < m_days[4]->getTimeSlot().size() ; i++)
	{
		std::cout << "\tCreneau " << i << " : " << "debut : " << m_days[4]->getTimeSlot()[i].getStart().tm_hour << "h" << m_days[4]->getTimeSlot()[i].getStart().tm_min << ", fin : "<< m_days[4]->getTimeSlot()[i].getEnd().tm_hour << "h" << m_days[4]->getTimeSlot()[i].getEnd().tm_min << std::endl;
	}
	std::cout << "Samedi : " << m_days[5]->getName() << std::endl;
	for(std::size_t i = 0 ; i < m_days[5]->getTimeSlot().size() ; i++)
	{
		std::cout << "\tCreneau " << i << " : " << "debut : " << m_days[5]->getTimeSlot()[i].getStart().tm_hour << "h" << m_days[5]->getTimeSlot()[i].getStart().tm_min << ", fin : "<< m_days[5]->getTimeSlot()[i].getEnd().tm_hour << "h" << m_days[5]->getTimeSlot()[i].getEnd().tm_min << std::endl;
	}
	std::cout << "Dimanche : " << m_days[6]->getName() << std::endl;
	for(std::size_t i = 0 ; i < m_days[6]->getTimeSlot().size() ; i++)
	{
		std::cout << "\tCreneau " << i << " : " << "debut : " << m_days[6]->getTimeSlot()[i].getStart().tm_hour << "h" << m_days[6]->getTimeSlot()[i].getStart().tm_min << ", fin : "<< m_days[6]->getTimeSlot()[i].getEnd().tm_hour << "h" << m_days[6]->getTimeSlot()[i].getEnd().tm_min << std::endl;
	}
}
