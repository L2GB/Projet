/*
 * LocalFileManager.cpp
 *
 *  Created on: 12 janv. 2015
 *      Author: menard
 */

// Internal include
#include "LocalFileManager.h"

#include <fcntl.h>
#include <unistd.h>

#include "../tools/exceptions/FormatException.h"
#include "../tools/exceptions/NotFoundException.h"

const std::string LocalFileManager::PATH_PIECES = "/usr/local/etc/L2GB/pieces.json";
const std::string LocalFileManager::PATH_OBJETS= "/usr/local/etc/L2GB/objets.json";
const std::string LocalFileManager::PATH_JOURS= "/usr/local/etc/L2GB/planning_jours.json";
const std::string LocalFileManager::PATH_SEMAINES= "/usr/local/etc/L2GB/planning_semaines.json";

std::string LocalFileManager::getDays()
{
	int fd = open(PATH_JOURS.c_str(),  O_RDONLY);
	char buf[SIZE + 1] = "";

	if(fd < 0)
	{
		throw NotFoundException("Impossible to open" + PATH_JOURS);
	}

	int ret = read(fd, buf, SIZE);

	if(ret < 0)
	{
		throw NotFoundException("Impossible to read" + PATH_JOURS);
	}

	close(fd);

	return std::string(buf);
}

std::string LocalFileManager::getWeeks()
{
	int fd = open(PATH_SEMAINES.c_str(),  O_RDONLY);
	char buf[SIZE + 1] = "";

	if(fd < 0)
	{
		throw NotFoundException("Impossible to open" + PATH_SEMAINES);
	}

	int ret = read(fd, buf, SIZE);

	if(ret < 0)
	{
		throw NotFoundException("Impossible to read" + PATH_SEMAINES);
	}

	close(fd);

	return std::string(buf);
}

std::string LocalFileManager::getObjects()
{
	int fd = open(PATH_OBJETS.c_str(), O_RDONLY);
	char buf[SIZE + 1] = "";

	if(fd < 0)
	{
		throw NotFoundException("Impossible to open" + PATH_OBJETS);
	}

	int ret = read(fd, buf, SIZE);

	if(ret < 0)
	{
		throw NotFoundException("Impossible to read" + PATH_OBJETS);
	}

	close(fd);

	return std::string(buf);
}

std::string LocalFileManager::getRooms()
{
	int fd = open(PATH_PIECES.c_str(), O_RDONLY);
	char buf[SIZE + 1] = "";

	if(fd < 0)
	{
		throw NotFoundException("Impossible to open" + PATH_PIECES);
	}

	int ret = read(fd, buf, SIZE);

	if(ret < 0)
	{
		throw NotFoundException("Impossible to read" + PATH_PIECES);
	}

	close(fd);

	return std::string(buf);
}

void LocalFileManager::setDay(json_t *_day)
{
	int fd = open(PATH_JOURS.c_str(),  O_CREAT|O_RDONLY, 0666);
	char buf[SIZE + 1] = "";

	if(fd < 0)
	{
		throw NotFoundException("Impossible to open" + PATH_JOURS);
	}

	int ret = read(fd, buf, SIZE);

	if(ret < 0)
	{
		throw NotFoundException("Impossible to read" + PATH_JOURS);
	}

	close(fd);

	fd = open(PATH_JOURS.c_str(),  O_WRONLY|O_TRUNC);

	if(fd < 0)
	{
		throw NotFoundException("Impossible to open" + PATH_JOURS);
	}

	json_t *root = json_loads(buf, 0, NULL);
	json_t *jours = json_object_get(root, "jours");

	if(!json_is_array(jours))
	{
		json_t *newRoot = json_object();
		json_t *newJours = json_array();
		json_array_append(newJours, _day);
		json_object_set(newRoot, "jours", newJours);
		std::string newString = json_dumps(newRoot, 0);
		write(fd, newString.c_str(), newString.size());
		close(fd);
		throw NotFoundException("L'objet \"jours\" n'est pas une liste ou n'existe pas");
	}

	int index;
	bool trouve = false;
	json_t *jour;
	json_t *nom;
	std::string nomJour;
	std::string nomNouveauJour;

	json_array_foreach(jours, index, jour)
	{
		if(!trouve)
		{
			nom = json_object_get(jour, "nomProfil");

			if(!json_is_string(nom))
			{
				throw FormatException("L'objet \"nom\" n'est pas de type string");
			}

			nomJour = json_string_value(nom);
			nom = json_object_get(_day, "nomProfil");
			nomNouveauJour = json_string_value(nom);

			if(nomJour.compare(nomNouveauJour) == 0)
			{
				json_array_set(jours, index, _day);
				trouve = true;
			}
		}
	}

	if(!trouve)
	{
		json_array_append(jours, _day);
	}

	root = json_object();
	json_object_set(root, "jours", jours);
	std::string newString = json_dumps(root, 0);

	write(fd, newString.c_str(), newString.size());
	close(fd);
}

void LocalFileManager::setWeek(json_t *_week)
{

}

void LocalFileManager::setObject(json_t *_object)
{

}

void LocalFileManager::setRoom(json_t *_room)
{

}


void LocalFileManager::rmDay(json_t *_day)
{
	int fd = open(PATH_JOURS.c_str(),  O_RDONLY, 0666);

	char buf[SIZE + 1] = "";

	if(fd < 0)
	{
		throw NotFoundException("Impossible to open" + PATH_JOURS);
	}

	int ret = read(fd, buf, SIZE);

	if(ret < 0)
	{
		throw NotFoundException("Impossible to read" + PATH_JOURS);
	}

	close(fd);

	fd = open(PATH_JOURS.c_str(),  O_WRONLY|O_TRUNC);

	if(fd < 0)
	{
		throw NotFoundException("Impossible to open" + PATH_JOURS);
	}

	json_t *root = json_loads(buf, 0, NULL);
	json_t *jours = json_object_get(root, "jours");

	if(!json_is_array(jours))
	{
		close(fd);
		throw NotFoundException("L'objet \"jours\" n'est pas une liste ou n'existe pas");
	}

	int index;
	bool trouve = false;
	json_t *jour;
	json_t *nom;
	std::string nomJour;
	std::string nomNouveauJour;

	json_array_foreach(jours, index, jour)
	{
		if(!trouve)
		{
			nom = json_object_get(jour, "nomProfil");

			if(!json_is_string(nom))
			{
				throw FormatException("L'objet \"nom\" n'est pas de type string");
			}

			nomJour = json_string_value(nom);
			nom = json_object_get(_day, "nomProfil");
			nomNouveauJour = json_string_value(nom);

			if(nomJour.compare(nomNouveauJour) == 0)
			{
				json_array_remove(jours, index);
				trouve = true;
			}
		}
	}

	if(!trouve)
	{
		close(fd);
		throw FormatException("Le profil n'a pas été trouvé");
	}

	root = json_object();
	json_object_set(root, "jours", jours);
	std::string newString = json_dumps(root, 0);

	write(fd, newString.c_str(), newString.size());
	close(fd);
}

void LocalFileManager::rmWeek(json_t *_week)
{

}

void LocalFileManager::rmObject(json_t *_object)
{

}

void LocalFileManager::rmRoom(json_t *_room)
{

}

