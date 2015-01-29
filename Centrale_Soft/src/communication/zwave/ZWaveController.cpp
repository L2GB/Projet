/*
 * ZWaveController.cpp
 *
 *  Created on: 27 janv. 2015
 *      Author: CAILLOT Kilian
 */

#include "ZWaveController.h"
#include <iostream>

using namespace std;

void print_D_I_CC_event(const ZWay zway, ZWDeviceChangeType type, ZWBYTE node_id, ZWBYTE instance_id, ZWBYTE command_id, void *arg);
void print_zway_terminated(ZWay zway, void* arg);

ZWaveController::ZWaveController() {

	int returnValue = -1;

	this->m_zway = NULL;

	returnValue = startNetwork();

}

ZWaveController::~ZWaveController() {

	int result = -1;

	// On stop le réseau zwave
	result = zway_stop(this->m_zway);

	// On libére l'espace du alloué au zway
	zway_terminate(&this->m_zway);
}

int ZWaveController::startNetwork() {

	int resultStartNetworkMethod(-1);

	// Chemin vers les dossiers permettant la création du contexte Z-Way
	ZWCSTR configFolderPath("/opt/z-way-server/config");
	ZWCSTR translationFolderPath("/opt/z-way-server/translations");
	ZWCSTR zddxFolderPath("/opt/z-way-server/ZDDX");

	cout << "Programme de test ZWave en C++ " << endl;

	// Création du contexte de logging
	ZWLog logger = zlog_create(NULL, Debug); //TODO remettre stdout à la place de NULL
	cout << "Contexte des logs créé" << endl;

	this->m_zway = NULL;

	// Création du contexte Z-Way
	ZWError result = zway_init(&m_zway, ZSTR("/dev/ttyAMA0"), configFolderPath, translationFolderPath, zddxFolderPath, "TestRpi", logger);
	// En cas d'erreur lors de la création du contexte Z-Way on inscrit l'erreur dans les logs
	if(result != NoError){
		zway_log_error(m_zway, Critical, "Failed to init ZWay", result);
		// Affichage à titre de debug
		cout << "Initialisation du contexte z-way non successfull" << endl;
		return -1;
	}
	cout << "Contexte du Z-Way créé" << endl;

	zway_device_add_callback(m_zway, DeviceAdded | DeviceRemoved | InstanceAdded | InstanceRemoved | CommandAdded | CommandRemoved, print_D_I_CC_event, NULL);

	// Ouvre le port série et lance un thread qui va gérer toute les communications avec le transmetteur
	// S'il n'y a pas d'erreur, le Z-Way peut maintenant recevoir des paquets du réseaux (ceux-ci ne sont
	// pour l'instant pas parsé (puisque les devices n'ont pas encore été découvert), tous les paquets
	// reçu sont alors stockés pour ensuite être parsé lorsque la découverte des devices aura été effectuée
	result = zway_start(m_zway, (ZTerminationCallback)print_zway_terminated, NULL);

	// En cas d'erreur lors du démarrage du réseau on inscrit l'erreur dans les logs
	if(result != NoError){
		zway_log_error(m_zway, Critical, "Failed to start ZWay", result);
		return -1;
	}
	cout << "Réseau Z-Way démarré" << endl;

	// Découverte du réseau, topologie, versionn, capacités ....
	// Si la découverte échoue on l'inscrit dans les logs
	result = zway_discover(m_zway);
	if(result != NoError){
		zway_log_error(m_zway, Critical, "Failed to negotiate with Z-Wave stick",result);
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

int ZWaveController::print_data_tree(){

	int running = TRUE;

	while(running){
		if (!zway_is_running(this->m_zway)){
			cout << " Le réseau n'est pas up and running, on passe running à false" << endl;
			running = FALSE;
			break;
		}
		cout << " Le réseau est up and running " << endl;

		if (!zway_is_idle(this->m_zway))
		{
			cout << " Le réseau est occupé, il reste des paquets dans la queue " << endl;
			sleep_ms(10);
			continue;
		}
		cout << " Le réseau est disponnible " << endl;

		cout << endl ;
		cout << "------------------------------------------------------" << endl;
		cout << "		 Arbre devices / command classes " << endl;
		cout << "------------------------------------------------------" << endl;
		ZWDevicesList deviceList = zway_devices_list(this->m_zway);
		if (deviceList != NULL) {
			int i = 0;
			cout << endl;
			cout << "------------------------------------------------------" << endl;
			cout << " Devices list: " << endl;
			cout << "------------------------------------------------------" << endl;
			while (deviceList[i]) {
				printf("	Device %i :\n", deviceList[i]);
				printDeviceInfoShortVersion(deviceList[i]);
				ZWInstancesList instancesList = zway_instances_list(this->m_zway,deviceList[i]);

				int k = 0;

				cout << "		Instance 0 ";
				ZWCommandClassesList commandClassesList = zway_command_classes_list(this->m_zway,deviceList[i],instancesList[k]);
				cout << endl;
				cout << "			Command Classes ";
				int j = 0;
				while(commandClassesList[j]){
					printf("%i ",commandClassesList[j]);
					j++;
				}
				zway_command_classes_list_free(commandClassesList);
				cout << endl;

				while(instancesList[k]){
					printf("		Instance %i ", instancesList[k]);

					ZWCommandClassesList commandClassesList = zway_command_classes_list(this->m_zway,deviceList[i],instancesList[k]);
					cout << endl;
					cout << "			Command Classes ";
					int j = 0;
					while(commandClassesList[j]){
						printf("%i ",commandClassesList[j]);
						j++;
					}
					zway_command_classes_list_free(commandClassesList);
					cout << endl;
					k++;
				}
				cout << "------------------------------------------------------" << endl;
				i++;
			}
			zway_devices_list_free(deviceList);
			cout << "	End of Devices list " << endl;
		}

		else{
			printf("Error happened requesting devices list\n");
			cout << " La liste de device est null or il devrait au moins il y avoir le controller " << endl;
		}

	running = FALSE;
	}

	return 0;
}

// Affiche la type de produit si la détection automatique à retourné une certitude de 10
void ZWaveController::printDeviceInfoShortVersion(int deviceNodeId){
	if (deviceNodeId >= 0) {
		ZGuessedProduct * productsList = zway_device_guess(this->m_zway, deviceNodeId);
		struct _ZGuessedProduct * pProduct = *productsList;
		if(pProduct->score == 10 || (pProduct->score - 100) == 10){
			printf(" %s ", pProduct->product);
		}

		zway_device_guess_free(productsList);
	} else {
		printf(" No information found on device\n");
	}
}

void ZWaveController::printDeviceInfoLongVersion(){
	cout << "Quel est le device dont vous souhaitez afficher les informations ? " << endl;
	cout << "> ";
	int deviceNodeId = -1;
	cin >> deviceNodeId;
	cin.ignore();

	if (deviceNodeId >= 0) {
		ZGuessedProduct * productsList = zway_device_guess(this->m_zway, deviceNodeId);
		struct _ZGuessedProduct * pProduct = *productsList;
		if(pProduct->score == 10 || (pProduct->score - 100) == 10){
			cout << endl << " Guessed product : " << pProduct;
			cout << " Score : " << pProduct->score << endl;
			cout << " Product : " << pProduct->product << endl;
			cout << " File name : " << pProduct->file_name << endl;
			printf("Guessed product (%p): score=%d product=%s file_name=%s\n", pProduct, pProduct->score, pProduct->product, pProduct->file_name);
		}

		zway_device_guess_free(productsList);
	} else {
		printf(" No information found on device\n");
	}
}

int ZWaveController::get_cc_presence(){

	cout << endl;
	cout << "Which Command class of which instance of which device would you like to know the name ?" << endl;
	cout << "Device num : ";
	int deviceNum;
	cin >> deviceNum ;
	cin.ignore();
	cout << endl << "Instance : ";
	int instanceNum;
	cin >> instanceNum;
	cin.ignore();
	cout << endl << "Command class : ";
	int commandClassNum;
	cin >> commandClassNum ;
	cin.ignore();
	cout << "Quel est le nom de la data dont vous voulez vérifier l'existence d'un holder ? " << endl;
	cout <<"> ";
	string dataName("no name");
	std::getline(cin,dataName);
	cout << endl << "Vous voulez donc vérifier si la commande class " << "'" << commandClassNum << "'";
	cout << " de l'instance " << "'" << instanceNum << "'" << " du device " << "'" << deviceNum << "'" << " posséde un data holder pour la variable ";
	cout << "'" << dataName << "'" << endl;


	zdata_acquire_lock(ZDataRoot(this->m_zway));

	ZDataHolder cc_holder = zway_find_device_instance_cc_data(this->m_zway,deviceNum,instanceNum,commandClassNum,dataName.c_str());

	if(cc_holder){
		cout << endl << " Il y a bien une donnée d'attachée à la cc " << commandClassNum << " de l'instance " << instanceNum;
		cout << " du device " << deviceNum << endl;
	}
	else{
		cout << endl << " Il n'y a pas de donnée attachée à la cc " << commandClassNum << " de l'instance " << instanceNum;
		cout << " du device " << deviceNum << " dont le nom serait " << dataName << endl;
	}
	zdata_release_lock(ZDataRoot(this->m_zway));

	return 0;

}

int ZWaveController::inclusion_mode_ON(){

	zway_fc_add_node_to_network(this->m_zway, TRUE, TRUE, NULL, NULL, NULL);

	cout << endl << "Inclusion ON " << endl;

	return 0;
}

int ZWaveController::inclusion_mode_OFF(){

	zway_fc_add_node_to_network(this->m_zway, FALSE, TRUE, NULL, NULL, NULL);

	cout << endl << "Inclusion OFF" << endl;

	return 0;
}

int ZWaveController::exclusion_mode_ON(){

	zway_fc_remove_node_from_network(this->m_zway, TRUE, TRUE, NULL, NULL, NULL);

	cout << endl <<"Exclusion ON " << endl;

	return 0;
}

int ZWaveController::exclusion_mode_OFF(){

	zway_fc_remove_node_from_network(this->m_zway, FALSE, TRUE, NULL, NULL, NULL);

	cout << endl << "Exclusion OFF " << endl;

	return 0;
}

/** Méthode permettant d'envoyer la commande set basic à une instance d'un device
 * @param deviceNodeId : identifiant du device auquel on veut envoyer le basic set
 * @param instanceId : identifiant de l'instance du device auquel on veut envoyer le basic set
 * @valeur : valeur que l'on veut attribuer via le basic set
 * @return result : 0 si la commande a bien été envoyé, -1 sinon
 **/
int ZWaveController::basic_set(int deviceNodeId, int instanceId, int valeur){

	int result = -1;

	if(zway_cc_basic_set(this->m_zway, deviceNodeId, instanceId, valeur, NULL, NULL, NULL) == NoError){
		cout << endl << "La commande set a bien été envoyée " << endl << endl;
		result = 0;
	}
	else{
		cout << endl << "La commande set n'a pas été envoyée " << endl << endl;
	}


	return result;
}

ZWay ZWaveController::get_zway(){
	return this->m_zway;
}

bool ZWaveController::zNetwork_is_idle(){
	bool isIdle(false);

	if(zway_is_idle(this->m_zway)){
		isIdle = true;
	}

	return isIdle;
}

bool ZWaveController::zNetwork_is_working(){
	bool isWorking(false);

	if(zway_is_running(this->m_zway)){
			isWorking = true;
	}

	return isWorking;
}

void ZWaveController::zdata_mutex_lock(){
	zdata_acquire_lock(ZDataRoot(this->m_zway));
}

void ZWaveController::zdata_mutex_unlock(){
	zdata_release_lock(ZDataRoot(this->m_zway));
}

bool ZWaveController::zNetwork_is_there_device_instance_cc_holder(int deviceNum, int instanceNum, int commandClassNum, std::string dataName){

	bool presence(false);

	ZDataHolder cc_holder = zway_find_device_instance_cc_data(this->m_zway,deviceNum,instanceNum,commandClassNum,dataName.c_str());

	if(cc_holder){
		presence = true;
	}

	return presence;
}


