/* ZWaveController.cpp
 *
 *  Created on: 27 janv. 2015
 *    Author: CAILLOT Kilian
 */

#include "ZWaveController.h"
#include <iostream>

using namespace std;

void print_D_I_CC_event(const ZWay zway, ZWDeviceChangeType type, ZWBYTE node_id, ZWBYTE instance_id, ZWBYTE command_id, void *arg);
void print_zway_terminated(ZWay zway, void* arg);

ZWaveController::ZWaveController() {

	this->m_zway = NULL;

	this->startNetwork();

}

ZWaveController::~ZWaveController() {

	// On stop le réseau zwave
	cout << " On stoppe le réseau zwave " << endl;
	zway_stop(this->m_zway);

	// On libére l'espace du alloué au zway
	cout << " On libére l'espace alloué au zway " << endl;
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
	ZWLog logger = zlog_create(stdout, Debug); //TODO remettre stdout à la place de NULL
	cout << "Contexte des logs créé" << endl;

	this->m_zway = NULL;

	// Création du contexte Z-Way
	ZWError result = zway_init(&m_zway, ZSTR("/dev/ttyAMA0"), configFolderPath, translationFolderPath, zddxFolderPath, "L2GB",NULL); //logger à la place de NULL (NULL pour ne pas avoir tous les logs dans la console)
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
	if (deviceNodeId >= 1) {
		ZGuessedProduct * product = zway_device_guess(this->m_zway, deviceNodeId);
		struct _ZGuessedProduct * pProduct = *product;
//		if(pProduct->score == 10 || (pProduct->score - 100) == 10){
//			printf(" %s ", pProduct->product);
//		}
		if(pProduct->product){
			cout << " " << pProduct->product << " ";
		}

		zway_device_guess_free(product);
	} else {
		printf(" nodeIdNotValid\n");
	}
}

/**
 * printDeviceInfoLongVersion
 * Demande à l'utilisateur l'id du Device dont il souhaite afficher les informations
 * et les lui affiche
 */
void ZWaveController::printDeviceInfoLongVersion(){
	cout << endl << "Quel est le device dont vous souhaitez afficher les informations ? " << endl;
	cout << "> ";
	int deviceNodeId = -1;
	cin >> deviceNodeId;
	cin.ignore();
	ZWDevicesList deviceList = zway_devices_list(this->m_zway);
	if (deviceNodeId >= 1) {
		ZGuessedProduct * productsList = zway_device_guess(this->m_zway, deviceNodeId);
		struct _ZGuessedProduct * pProduct = *productsList;
		if(pProduct->score == 10 || (pProduct->score - 100) == 10){
			cout << endl << " Guessed product : " << pProduct;
			cout << " Score : " << pProduct->score << endl;
			cout << " Product : " << pProduct->product << endl;
			cout << " File name : " << pProduct->file_name << endl;
			printf("Guessed product (%p): score=%d product=%s file_name=%s\n", pProduct, pProduct->score, pProduct->product, pProduct->file_name);
		}
		zway_devices_list_free(deviceList);
		zway_device_guess_free(productsList);
	} else {
		printf(" No information found on device\n");
	}
}

int ZWaveController::inclusion_mode_ON(){
	// Si passe pas comme ça y a une autre méthode
	ZWError zerror = zway_fc_add_node_to_network(this->m_zway, TRUE, TRUE, NULL, NULL, NULL);
	if(zerror == NoError){
		cout << endl << "Inclusion ON " << endl;
	}
	else{
		cout << endl << "Inclusion activation failed " << endl;
	}

	return 0;
}

int ZWaveController::inclusion_mode_OFF(){
	// Si passe pas comme ça y a une autre méthode
	ZWError zerror = zway_fc_add_node_to_network(this->m_zway, FALSE, TRUE, NULL, NULL, NULL);
	if(zerror == NoError){
		cout << endl << "Inclusion OFF " << endl;
	}
	else{
		cout << endl << "Inclusion desactivation failed " << endl;
	}

	return 0;

}

int ZWaveController::exclusion_mode_ON(){
	// Si passe pas comme ça y a une autre méthode
	ZWError zerror = zway_fc_remove_node_from_network(this->m_zway, TRUE, TRUE, NULL, NULL, NULL);
	if(zerror == NoError){
		cout << endl << "Exclusion ON " << endl;
	}
	else{
		cout << endl << "Exclusion activation failed " << endl;
	}

	return 0;
}

int ZWaveController::exclusion_mode_OFF(){
	// Si passe pas comme ça y a une autre méthode
	ZWError zerror = zway_fc_remove_node_from_network(this->m_zway, FALSE, TRUE, NULL, NULL, NULL);
	if(zerror == NoError){
		cout << endl << "Exclusion OFF " << endl;
	}
	else{
		cout << endl << "Exclusion desactivation failed " << endl;
	}

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

// Méthode useless
int ZWaveController::basic_get(int deviceNodeId, int instanceId){
	int result(-666);

	if(zway_cc_basic_get(this->m_zway, deviceNodeId, instanceId, NULL, NULL, NULL) == NoError){
		cout << endl << "La commande get a bien été envoyée " << endl << endl;
		result = 0;
	}
	else{
		cout << endl << "La commande get n'a pas été envoyée " << endl << endl;
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

	this->zdata_mutex_lock();

	ZDataHolder cc_holder = zway_find_device_instance_cc_data(this->m_zway,deviceNum,instanceNum,commandClassNum,dataName.c_str()); // dataName.c_str() //à la place du dernier arg

	if(cc_holder){
		presence = true;
	}
	else{
		presence = false;
	}

	this->zdata_mutex_unlock();

	return presence;
}

bool ZWaveController::zNetwork_is_there_device_holder(int deviceNum, std::string dataName){

	bool presence(false);

	this->zdata_mutex_lock();

	ZDataHolder cc_holder = zway_find_device_data(this->m_zway, deviceNum, dataName.c_str());

	if(cc_holder){
		presence = true;
	}
	else{
		presence = false;
	}

	this->zdata_mutex_unlock();

	return presence;
}
/*
 * Méthode zNetwork_get_holder_value_type
 * Retourne le type d'une valeur stockée dans le holder
 * (Mettre instanceNum et commandClassNum à 0 pour un holder de device)
 */
std::string ZWaveController::zNetwork_get_holder_value_type(int deviceNum, int instanceNum, int commandClassNum, std::string dataName){

	std::string type("NOTINIT");
	ZWDataType holderValueType;
	ZDataHolder holder;

	this->zdata_mutex_lock();

	if(instanceNum == 0 && commandClassNum == 0){
		holder = zway_find_device_data(this->m_zway, deviceNum, dataName.c_str());
	}
	else{
		holder = zway_find_device_instance_cc_data(this->m_zway,deviceNum, instanceNum, commandClassNum, dataName.c_str());
	}

	if(holder != NULL){
		if(zdata_get_type(holder, &holderValueType) == NoError){
			switch (holderValueType){

			case Empty:
				type = "Empty";
			break;

			case Boolean:
				type = "Boolean";
			break;

			case Integer:
				type= "Integer";
			break;

			case Float:
				type = "Float";
			break;

			case String:
				type = "String";
			break;

			case Binary:
				type = "Binary";
			break;

			case ArrayOfInteger:
				type = "ArrayOfInteger";
			break;

			case ArrayOfFloat:
				type = "ArrayOfFloat";
			break;

			case ArrayOfString:
				type = "ArrayOfString";
			break;

			default:
				type = "noHolderValueTypeFound";
			break;
			}
		}
		else{
			type = "cantFindHolderValueType";
		}
	}
	else{
		type = "noHolder";
	}
	this->zdata_mutex_unlock();

	return type;
}

int ZWaveController::zNetwork_get_integer(int deviceNum, int instanceNum, int commandClassNum, std::string dataName){
	int value(-123);
	ZDataHolder holder;

	this->zdata_mutex_lock();

	if(instanceNum == 0 && commandClassNum == 0){
		holder = zway_find_device_data(this->m_zway, deviceNum, dataName.c_str());
	}
	else{
		holder = zway_find_device_instance_cc_data(this->m_zway,deviceNum, instanceNum, commandClassNum, dataName.c_str());
	}

	if(holder != NULL && zNetwork_get_holder_value_type(deviceNum, instanceNum, commandClassNum, dataName) == "Integer"){
		zdata_get_integer(holder, &value);
	}

	this->zdata_mutex_unlock();

	return value;
}

bool ZWaveController::zNetwork_get_boolean(int deviceNum, int instanceNum, int commandClassNum, std::string dataName){
	bool value(NULL);
	ZWBOOL zValue;
	ZDataHolder holder;

	this->zdata_mutex_lock();

	if(instanceNum == 0 && commandClassNum == 0){
		holder = zway_find_device_data(this->m_zway, deviceNum, dataName.c_str());
	}
	else{
		holder = zway_find_device_instance_cc_data(this->m_zway,deviceNum, instanceNum, commandClassNum, dataName.c_str());
	}

	if(holder != NULL && zNetwork_get_holder_value_type(deviceNum, instanceNum, commandClassNum, dataName) == "Boolean"){
		zdata_get_boolean(holder, &zValue);
		if(zValue == true){
			value = true;
		}
		else{
			value = false;
		}
	}
	this->zdata_mutex_unlock();

	return value;
}

float ZWaveController::zNetwork_get_float(int deviceNum, int instanceNum, int commandClassNum, std::string dataName){
	float value(-123.0);
	ZDataHolder holder;

	this->zdata_mutex_lock();

	if(instanceNum == 0 && commandClassNum == 0){
		holder = zway_find_device_data(this->m_zway, deviceNum, dataName.c_str());
	}
	else{
		holder = zway_find_device_instance_cc_data(this->m_zway,deviceNum, instanceNum, commandClassNum, dataName.c_str());
	}

	if(holder != NULL && zNetwork_get_holder_value_type(deviceNum, instanceNum, commandClassNum, dataName) == "Float"){
		zdata_get_float(holder, &value);
	}

	this->zdata_mutex_unlock();

	return value;
}

std::string ZWaveController::zNetwork_get_string(int deviceNum, int instanceNum, int commandClassNum, std::string dataName){
	const char * value;
	ZDataHolder holder;

	this->zdata_mutex_lock();

	if(instanceNum == 0 && commandClassNum == 0){
		holder = zway_find_device_data(this->m_zway, deviceNum, dataName.c_str());
	}
	else{
		holder = zway_find_device_instance_cc_data(this->m_zway,deviceNum, instanceNum, commandClassNum, dataName.c_str());
	}

	if(holder != NULL && zNetwork_get_holder_value_type(deviceNum, instanceNum, commandClassNum, dataName) == "String"){
		zdata_get_string(holder, &value);
	}

	this->zdata_mutex_unlock();

	return value;
}

std::string ZWaveController::zNetwork_get_device_name(int deviceNum){
	cout << " On rentre dans la méthode get device name " << endl;
	std::string nameInitString ("Chaine Initialisée");
	cout << " Initialisation de la chaine nameInitString done " << endl;
	if (deviceNum >= 0) {
		cout << " On rentre dans le if car le deviceNum est sup à 0 " << endl;
		ZGuessedProduct * product = zway_device_guess(this->m_zway, deviceNum);
		cout << " On vient d'effectuer le device guess " << endl;
		struct _ZGuessedProduct * pProduct = *product;
		cout << " On vient de créer pProduct pour pointer sur product" << endl;
		if(pProduct->score == 10 || (pProduct->score - 100) == 10){
			printf("Guessed product (%p): score=%d product=%s file_name=%s\n", pProduct, pProduct->score, pProduct->product, pProduct->file_name);
		}
		zway_device_guess_free(product);
	} else {
		nameInitString = "DeviceNum inférieur à 0 ";
	}

	return nameInitString;
}


std::string ZWaveController::zNetwork_get_device_type(int deviceNum){
	std::string typeString;
	if (deviceNum >= 0) {
		ZGuessedProduct * product = zway_device_guess(this->m_zway, deviceNum);
		struct _ZGuessedProduct * pProduct = *product;
		if(pProduct->score == 10 || (pProduct->score - 100) == 10){
			typeString = pProduct->product;
			printf("Guessed product (%p): score=%d product=%s file_name=%s\n", pProduct, pProduct->score, pProduct->product, pProduct->file_name);
		}
		zway_device_guess_free(product);
	} else {
		typeString = "DeviceNum inférieur à 0 ";
	}

	return typeString;
}
/**
 * zNetwork_get_nb_devices
 * Récupère le nombre de devices appairés
 */
int ZWaveController::zNetwork_get_nb_devices_paired(){
	int i(0);
	ZWDevicesList deviceList = zway_devices_list(this->m_zway);
	while (deviceList[i]) {
		i++;
	}
	return i;
}

bool ZWaveController::zNetwork_is_device_paired(int deviceId){
	bool isDevicePaired(false);
	ZWDevicesList deviceList = zway_devices_list(this->m_zway);
	int i(0);
	while(deviceList[i]){
		int p = (int)deviceList[i];
		if(p == deviceId){
			isDevicePaired = true;
		}
		i++;
	}
	return isDevicePaired;
}

/**
 * Méthode zNetwork_wake_device_up
//ZWEXPORT void zway_device_awake_queue(const ZWay zway, ZWBYTE node_id);
 * Force le réveil de la queue du device (dont l'id est deviceId)
 * Renvoie le code d'erreur attribué par la libzway (ce code d'erreur
 * peut être retrouvé dans le fichier ZErrors.h)
 */
void ZWaveController::zNetwork_wake_device_up(int deviceId){

	zway_device_awake_queue(this->m_zway, deviceId);
}

std::string ZWaveController::zdata_get_holder_name(int deviceNum, int instanceNum, int commandClassNum, std::string dataName){

	std::string name("nameInit");

	this->zdata_mutex_lock();

	ZDataHolder holder = zway_find_device_instance_cc_data(this->m_zway,deviceNum, instanceNum, commandClassNum, dataName.c_str());
	name = (string)zdata_get_name(holder);

	this->zdata_mutex_unlock();

	return name;
}

void ZWaveController::zNetwork_get_thermostat_mode(int deviceId, int instanceNum){

	zway_cc_thermostat_mode_get(this->m_zway, deviceId, instanceNum, NULL, NULL, NULL);
}

void ZWaveController::zNetwork_set_thermostat_mode(char deviceId, char instanceNum, char mode){

	zway_cc_thermostat_mode_set(this->m_zway, deviceId, instanceNum, (ZWBYTE)mode, NULL, NULL, NULL);
}

void ZWaveController::zdata_set_callback(int deviceNum, int instanceNum, int commandClassNum, std::string dataName, ZDataChangeCallback callback, void *args) {

		ZDataHolder holder;

		this->zdata_mutex_lock();

		if(instanceNum == 0 && commandClassNum == 0){
			holder = zway_find_device_data(this->m_zway, deviceNum, dataName.c_str());
		}
		else{
			holder = zway_find_device_instance_cc_data(this->m_zway,deviceNum, instanceNum, commandClassNum, dataName.c_str());
		}

		if(holder != NULL) {
			zdata_add_callback(holder, callback, false, args);
		}

		this->zdata_mutex_unlock();

}

void ZWaveController::zNetwork_get_capabilities(char deviceId, char instanceNum){
	zway_cc_wakeup_capabilities_get(this->m_zway, deviceId, instanceNum, NULL, NULL, NULL);
}

// Search a Data holder by name starting from a defined Data holder
//
// @param: data
// Root object instance
//
// @param: path
// Path to search for (dot separated)
//
//ZWEXPORT ZDataHolder zdata_find(const ZDataHolder data, const char *path);

bool ZWaveController::zNeztwork_is_device_connected(int deviceId, int instanceNum){
	// Valeur de retour indiquant si l'object (dont l'id est deviceId et le numéro d'instance instanceNum est connecté ou non
	bool isDeviceConnected(false);
	// Variable de parcours de la liste des devices
	int i(0);
	// Variable de parcours de la liste des instances d'un devices
	int k(0);
	// Liste des devices connectés
	ZWDevicesList deviceList = zway_devices_list(this->m_zway);

}

//void ZWaveController::dump_zway_data_holder(){
//
//    char *path = zdata_get_path(data);
//    ZWDataType type;
//    zdata_get_type(data, &type);
//
//    ZWBOOL bool_val;
//    int int_val;
//    float float_val;
//    ZWCSTR str_val;
//    const ZWBYTE *binary;
//    const int *int_arr;
//    const float *float_arr;
//    const ZWCSTR *str_arr;
//    size_t len, i;
//
//    switch (type)
//    {
//        case Empty:
//            zway_log(this->m_zway, Debug, ZSTR("DATA %s = Empty"), path);
//            break;
//        case Boolean:
//            zdata_get_boolean(data, &bool_val);
//            if (bool_val)
//                zway_log(this->m_zway, Debug, ZSTR("DATA %s = True"), path);
//            else
//                zway_log(this->m_zway, Debug, ZSTR("DATA %s = False"), path);
//            break;
//        case Integer:
//            zdata_get_integer(data, &int_val);
//            zway_log(this->m_zway, Debug, ZSTR("DATA %s = %d (0x%08x)"), path, int_val, int_val);
//            break;
//        case Float:
//            zdata_get_float(data, &float_val);
//            zway_log(this->m_zway, Debug, ZSTR("DATA %s = %f"), path, float_val);
//            break;
//        case String:
//            zdata_get_string(data, &str_val);
//            zway_log(zway, Debug, ZSTR("DATA %s = \"%s\""), path, str_val);
//            break;
//        case Binary:
//            zdata_get_binary(data, &binary, &len);
//            zway_log(this->m_zway, Debug, ZSTR("DATA %s = byte[%d]"), path, len);
//            zway_dump(this->m_zway, Debug, ZSTR("  "), len, binary);
//            break;
//        case ArrayOfInteger:
//            zdata_get_integer_array(data, &int_arr, &len);
//            zway_log(this->m_zway, Debug, ZSTR("DATA %s = int[%d]"), path, len);
//            for (i = 0; i < len; i++)
//                zway_log(this->m_zway, Debug, ZSTR("  [%02d] %d"), i, int_arr[i]);
//            break;
//        case ArrayOfFloat:
//            zdata_get_float_array(data, &float_arr, &len);
//            zway_log(this->m_zway, Debug, ZSTR("DATA %s = float[%d]"), path, len);
//            for (i = 0; i < len; i++)
//                zway_log(this->m_zway, Debug, ZSTR("  [%02d] %f"), i, float_arr[i]);
//            break;
//        case ArrayOfString:
//            zdata_get_string_array(data, &str_arr, &len);
//            zway_log(this->m_zway, Debug, ZSTR("DATA %s = string[%d]"), path, len);
//            for (i = 0; i < len; i++)
//                zway_log(this->m_zway, Debug, ZSTR("  [%02d] \"%s\""), i, str_arr[i]);
//            break;
//    }
//    free(path);
//
//    ZDataIterator child = zdata_first_child(data);
//    while (child != NULL)
//    {
//        path = zdata_get_path(child->data);
//        zway_log(zway, Debug, ZSTR("CHILD %s"), path);
//        free(path);
//        child = zdata_next_child(child);
//    }
//}
