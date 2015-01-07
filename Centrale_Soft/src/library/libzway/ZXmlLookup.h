//
//  ZXmlLookup.h
//  Part of Z-Way.C library
//
//  Created by Alex Skalozub on 2/5/12.
//  Based on Z-Way source code written by Christian Paetz and Poltorak Serguei
//
//  Copyright (c) 2012 Z-Wave.Me
//  All rights reserved
//  info@z-wave.me
//
//  This source file is subject to the terms and conditions of the
//  Z-Wave.Me Software License Agreement which restricts the manner
//  in which it may be used.
//

#ifndef zway_xml_lookup_h
#define zway_xml_lookup_h

#include <libxml/xpath.h>
#include "ZPlatform.h"
#include "ZMalloc.h"

ZWEXPORT_PRIVATE ZWBOOL _xpath_select_boolean(const xmlDocPtr doc, ZWBOOL default_value, const char *path, ...);

ZWEXPORT_PRIVATE int _xpath_select_integer(const xmlDocPtr doc, int default_value, const char *path, ...);

ZWEXPORT_PRIVATE float _xpath_select_float(const xmlDocPtr doc, float default_value, const char *path, ...);

ZWEXPORT_PRIVATE ZWSTR _xpath_select_string(const xmlDocPtr doc, const char *path, ...);

ZWEXPORT_PRIVATE ZWSTR _xpath_node_to_string(const xmlNodePtr node);

ZWEXPORT_PRIVATE void _xpath_free_nodes(xmlNodeSetPtr nodes);

ZWEXPORT_PRIVATE xmlNodeSetPtr _xpath_select_nodes(const xmlDocPtr doc, const char *path, ...);

#endif
