/*
 * ZWaveController.cpp
 *
 *  Created on: 27 janv. 2015
 *      Author: CAILLOT Kilian
 */

#include "ZWaveController.h"
#include <iostream>
#include <string>

using namespace std;

void print_D_I_CC_event(const ZWay zway, ZWDeviceChangeType type, ZWBYTE node_id, ZWBYTE instance_id, ZWBYTE command_id, void *arg);
void print_zway_terminated(ZWay zway, void* arg);

ZWaveController::ZWaveController() {
	// TODO Auto-generated constructor stub

}

ZWaveController::~ZWaveController() {
	// TODO Auto-generated destructor stub
}

int ZWaveController::startNetwork() {

	int resultStartNetworkMethod(-1);

	// Chemin vers les dossiers permettant la création du contexte Z-Way
	ZWCSTR configFolderPath("/opt/z-way-server/config");
	ZWCSTR translationFolderPath("/opt/z-way-server/translations");
	ZWCSTR zddxFolderPath("/opt/z-way-server/ZDDX");

	cout << "Programme de test ZWave en C++ " << endl;

	// Création du contexte de logging
	ZWLog logger = zlog_create(stdout, Debug);
	cout << "Contexte des logs créé" << endl;

	ZWay zway = NULL;

	// Création du contexte Z-Way
	ZWError result = zway_init(&zway, ZSTR("/dev/ttyAMA0"), configFolderPath, translationFolderPath, zddxFolderPath, "TestRpi", logger);
	// En cas d'erreur lors de la création du contexte Z-Way on inscrit l'erreur dans les logs
	if(result != NoError){
		zway_log_error(zway, Critical, "Failed to init ZWay", result);
		// Affichage à titre de debug
		cout << "Initialisation du contexte z-way non successfull" << endl;
		return -1;
	}
	cout << "Contexte du Z-Way créé" << endl;

	zway_device_add_callback(zway, DeviceAdded | DeviceRemoved | InstanceAdded | InstanceRemoved | CommandAdded | CommandRemoved, print_D_I_CC_event, NULL);

	// Ouvre le port série et lance un thread qui va gérer toute les communications avec le transmetteur
	// S'il n'y a pas d'erreur, le Z-Way peut maintenant recevoir des paquets du réseaux (ceux-ci ne sont
	// pour l'instant pas parsé (puisque les devices n'ont pas encore été découvert), tous les paquets
	// reçu sont alors stockés pour ensuite être parsé lorsque la découverte des devices aura été effectuée
	result = zway_start(zway, (ZTerminationCallback)print_zway_terminated, NULL);

	// En cas d'erreur lors du démarrage du réseau on inscrit l'erreur dans les logs
	if(result != NoError){
		zway_log_error(zway, Critical, "Failed to start ZWay", result);
		return -1;
	}
	cout << "Réseau Z-Way démarré" << endl;

	// Découverte du réseau, topologie, versionn, capacités ....
	// Si la découverte échoue on l'inscrit dans les logs
	result = zway_discover(zway);
	if(result != NoError){
		zway_log_error(zway, Critical, "Failed to negotiate with Z-Wave stick",result);
		result = -1;
	}
	cout << "La découverte du réseau a été correctement effectuée" << endl;



	return resultStartNetworkMethod;
}

void print_D_I_CC_event(const ZWay zway, ZWDeviceChangeType type, ZWBYTE node_id, ZWBYTE instance_id, ZWBYTE command_id, void *arg)
{
    switch (type)
    {
        case  DeviceAdded:
            zway_log(zway, Information, ZSTR("New device added: %i"), node_id);
            break;

        case DeviceRemoved:
            zway_log(zway, Information, ZSTR("Device removed: %i"), node_id);
            break;

        case InstanceAdded:
            zway_log(zway, Information, ZSTR("New instance added to device %i: %i"), node_id, instance_id);
            break;

        case InstanceRemoved:
            zway_log(zway, Information, ZSTR("Instance removed from device %i: %i"), node_id, instance_id);
            break;

        case CommandAdded:
            zway_log(zway, Information, ZSTR("New Command Class added to device %i:%i: %i"), node_id, instance_id, command_id);
            break;

        case CommandRemoved:
            zway_log(zway, Information, ZSTR("Command Class removed from device %i:%i: %i"), node_id, instance_id, command_id);
            break;
    }
}

void print_zway_terminated(ZWay zway, void* arg){
	zway_log(zway, Information, ZSTR("Z-Way terminated"));

}

