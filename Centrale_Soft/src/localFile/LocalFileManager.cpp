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

			if(!json_is_string(nom))
			{
				throw FormatException("L'objet \"nom\" n'est pas de type string");
			}

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
	int fd = open(PATH_SEMAINES.c_str(),  O_CREAT|O_RDONLY, 0666);
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

	fd = open(PATH_SEMAINES.c_str(),  O_WRONLY|O_TRUNC);

	if(fd < 0)
	{
		throw NotFoundException("Impossible to open" + PATH_SEMAINES);
	}

	json_t *root = json_loads(buf, 0, NULL);
	json_t *semaines = json_object_get(root, "semaines");

	if(!json_is_array(semaines))
	{
		json_t *newRoot = json_object();
		json_t *newSemaines = json_array();
		json_array_append(newSemaines, _week);
		json_object_set(newRoot, "semaines", newSemaines);
		std::string newString = json_dumps(newRoot, 0);
		write(fd, newString.c_str(), newString.size());
		close(fd);
		throw NotFoundException("L'objet \"semaines\" n'est pas une liste ou n'existe pas");
	}

	int index;
	bool trouve = false;
	json_t *semaine;
	json_t *nom;
	std::string nomSemaine;
	std::string nomNouveauSemaine;

	json_array_foreach(semaines, index, semaine)
	{
		if(!trouve)
		{
			nom = json_object_get(semaine, "nomProfil");

			if(!json_is_string(nom))
			{
				throw FormatException("L'objet \"nom\" n'est pas de type string");
			}

			nomSemaine = json_string_value(nom);
			nom = json_object_get(_week, "nomProfil");

			if(!json_is_string(nom))
			{
				throw FormatException("L'objet \"nom\" n'est pas de type string");
			}

			nomNouveauSemaine = json_string_value(nom);

			if(nomSemaine.compare(nomNouveauSemaine) == 0)
			{
				json_array_set(semaines, index, _week);
				trouve = true;
			}
		}
	}

	if(!trouve)
	{
		json_array_append(semaines, _week);
	}

	root = json_object();
	json_object_set(root, "semaines", semaines);
	std::string newString = json_dumps(root, 0);

	write(fd, newString.c_str(), newString.size());
	close(fd);
}

void LocalFileManager::setObject(json_t *_object)
{
	int fd = open(PATH_OBJETS.c_str(),  O_CREAT|O_RDONLY, 0666);
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

	fd = open(PATH_OBJETS.c_str(),  O_WRONLY|O_TRUNC);

	if(fd < 0)
	{
		throw NotFoundException("Impossible to open" + PATH_OBJETS);
	}

	json_t *root = json_loads(buf, 0, NULL);
	json_t *objets = json_object_get(root, "objets");

	if(!json_is_array(objets))
	{
		json_t *newRoot = json_object();
		json_t *newObjets = json_array();
		json_array_append(newObjets, _object);
		json_object_set(newRoot, "objets", newObjets);
		std::string newString = json_dumps(newRoot, 0);
		write(fd, newString.c_str(), newString.size());
		close(fd);
		throw NotFoundException("L'objet \"objets\" n'est pas une liste ou n'existe pas");
	}

	int index;
	bool trouve = false;
	json_t *objet;
	json_t *nom;
	std::string nomObjet;
	std::string nomNouveauObjet;

	json_array_foreach(objets, index, objet)
	{
		if(!trouve)
		{
			nom = json_object_get(objet, "nomObjet");

			if(!json_is_string(nom))
			{
				throw FormatException("L'objet \"nom\" n'est pas de type string");
			}

			nomObjet = json_string_value(nom);
			nom = json_object_get(_object, "nomObjet");

			if(!json_is_string(nom))
			{
				throw FormatException("L'objet \"nom\" n'est pas de type string");
			}

			nomNouveauObjet = json_string_value(nom);

			if(nomObjet.compare(nomNouveauObjet) == 0)
			{
				json_array_set(objets, index, _object);
				trouve = true;
			}
		}
	}

	if(!trouve)
	{
		json_array_append(objets, _object);
	}

	root = json_object();
	json_object_set(root, "objets", objets);
	std::string newString = json_dumps(root, 0);

	write(fd, newString.c_str(), newString.size());
	close(fd);
}

void LocalFileManager::setRoom(json_t *_room)
{
	int fd = open(PATH_PIECES.c_str(),  O_CREAT|O_RDONLY, 0666);
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

	fd = open(PATH_PIECES.c_str(),  O_WRONLY|O_TRUNC);

	if(fd < 0)
	{
		throw NotFoundException("Impossible to open" + PATH_PIECES);
	}

	json_t *root = json_loads(buf, 0, NULL);
	json_t *pieces = json_object_get(root, "pieces");

	if(!json_is_array(pieces))
	{
		json_t *newRoot = json_object();
		json_t *newPieces = json_array();
		json_array_append(newPieces, _room);
		json_object_set(newRoot, "pieces", newPieces);
		std::string newString = json_dumps(newRoot, 0);
		write(fd, newString.c_str(), newString.size());
		close(fd);
		throw NotFoundException("L'objet \"pieces\" n'est pas une liste ou n'existe pas");
	}

	int index;
	bool trouve = false;
	json_t *piece;
	json_t *nom;
	std::string nomPiece;
	std::string nomNouveauPiece;

	json_array_foreach(pieces, index, piece)
	{
		if(!trouve)
		{
			nom = json_object_get(piece, "nomPiece");

			if(!json_is_string(nom))
			{
				throw FormatException("L'objet \"nom\" n'est pas de type string");
			}

			nomPiece = json_string_value(nom);
			nom = json_object_get(_room, "nomPiece");

			if(!json_is_string(nom))
			{
				throw FormatException("L'objet \"nom\" n'est pas de type string");
			}

			nomNouveauPiece = json_string_value(nom);

			if(nomPiece.compare(nomNouveauPiece) == 0)
			{
				json_array_set(pieces, index, _room);
				trouve = true;
			}
		}
	}

	if(!trouve)
	{
		json_array_append(pieces, _room);
	}

	root = json_object();
	json_object_set(root, "pieces", pieces);
	std::string newString = json_dumps(root, 0);

	write(fd, newString.c_str(), newString.size());
	close(fd);
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

			if(!json_is_string(nom))
			{
				throw FormatException("L'objet \"nom\" n'est pas de type string");
			}

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
	int fd = open(PATH_SEMAINES.c_str(),  O_RDONLY, 0666);

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

	fd = open(PATH_SEMAINES.c_str(),  O_WRONLY|O_TRUNC);

	if(fd < 0)
	{
		throw NotFoundException("Impossible to open" + PATH_SEMAINES);
	}

	json_t *root = json_loads(buf, 0, NULL);
	json_t *semaines = json_object_get(root, "semaines");

	if(!json_is_array(semaines))
	{
		close(fd);
		throw NotFoundException("L'objet \"semaines\" n'est pas une liste ou n'existe pas");
	}

	int index;
	bool trouve = false;
	json_t *semaine;
	json_t *nom;
	std::string nomSemaine;
	std::string nomNouveauSemaine;

	json_array_foreach(semaines, index, semaine)
	{
		if(!trouve)
		{
			nom = json_object_get(semaine, "nomProfil");

			if(!json_is_string(nom))
			{
				throw FormatException("L'objet \"nom\" n'est pas de type string");
			}

			nomSemaine = json_string_value(nom);
			nom = json_object_get(_week, "nomProfil");

			if(!json_is_string(nom))
			{
				throw FormatException("L'objet \"nom\" n'est pas de type string");
			}

			nomNouveauSemaine = json_string_value(nom);

			if(nomSemaine.compare(nomNouveauSemaine) == 0)
			{
				json_array_remove(semaines, index);
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
	json_object_set(root, "semaines", semaines);
	std::string newString = json_dumps(root, 0);

	write(fd, newString.c_str(), newString.size());
	close(fd);
}

void LocalFileManager::rmObject(json_t *_object)
{
	int fd = open(PATH_OBJETS.c_str(),  O_RDONLY, 0666);

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

	fd = open(PATH_OBJETS.c_str(),  O_WRONLY|O_TRUNC);

	if(fd < 0)
	{
		throw NotFoundException("Impossible to open" + PATH_OBJETS);
	}

	json_t *root = json_loads(buf, 0, NULL);
	json_t *objets = json_object_get(root, "objets");

	if(!json_is_array(objets))
	{
		close(fd);
		throw NotFoundException("L'objet \"objets\" n'est pas une liste ou n'existe pas");
	}

	int index;
	bool trouve = false;
	json_t *objet;
	json_t *nom;
	std::string nomObjet;
	std::string nomNouveauObjet;

	json_array_foreach(objets, index, objet)
	{
		if(!trouve)
		{
			nom = json_object_get(objet, "nomObjet");

			if(!json_is_string(nom))
			{
				throw FormatException("L'objet \"nom\" n'est pas de type string");
			}

			nomObjet = json_string_value(nom);
			nom = json_object_get(_object, "nomObjet");

			if(!json_is_string(nom))
			{
				throw FormatException("L'objet \"nom\" n'est pas de type string");
			}

			nomNouveauObjet = json_string_value(nom);

			if(nomObjet.compare(nomNouveauObjet) == 0)
			{
				json_array_remove(objets, index);
				trouve = true;
			}
		}
	}

	if(!trouve)
	{
		close(fd);
		throw FormatException("L'objet n'a pas été trouvé");
	}

	root = json_object();
	json_object_set(root, "objets", objets);
	std::string newString = json_dumps(root, 0);

	write(fd, newString.c_str(), newString.size());
	close(fd);
}

void LocalFileManager::rmRoom(json_t *_room)
{
	int fd = open(PATH_PIECES.c_str(),  O_RDONLY, 0666);

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

	fd = open(PATH_PIECES.c_str(),  O_WRONLY|O_TRUNC);

	if(fd < 0)
	{
		throw NotFoundException("Impossible to open" + PATH_PIECES);
	}

	json_t *root = json_loads(buf, 0, NULL);
	json_t *pieces = json_object_get(root, "pieces");

	if(!json_is_array(pieces))
	{
		close(fd);
		throw NotFoundException("L'objet \"pieces\" n'est pas une liste ou n'existe pas");
	}

	int index;
	bool trouve = false;
	json_t *piece;
	json_t *nom;
	std::string nomPiece;
	std::string nomNouveauPiece;

	json_array_foreach(pieces, index, piece)
	{
		if(!trouve)
		{
			nom = json_object_get(piece, "nomPiece");

			if(!json_is_string(nom))
			{
				throw FormatException("L'objet \"nom\" n'est pas de type string");
			}

			nomPiece = json_string_value(nom);
			nom = json_object_get(_room, "nomPiece");

			if(!json_is_string(nom))
			{
				throw FormatException("L'objet \"nom\" n'est pas de type string");
			}

			nomNouveauPiece = json_string_value(nom);

			if(nomPiece.compare(nomNouveauPiece) == 0)
			{
				json_array_remove(pieces, index);
				trouve = true;
			}
		}
	}

	if(!trouve)
	{
		close(fd);
		throw FormatException("La pièce n'a pas été trouvée");
	}

	root = json_object();
	json_object_set(root, "pieces", pieces);
	std::string newString = json_dumps(root, 0);

	write(fd, newString.c_str(), newString.size());
	close(fd);
}

